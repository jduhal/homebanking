package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {

		LocalDate today = LocalDate.now();
		LocalDateTime now = LocalDateTime.now();

		return (args) -> {
			//Clients
			Client melba = new Client("Melba", "Morel", "mmorel@mindhub.com",
					passwordEncoder.encode("Mmorel!!"));

			Client rodri = new Client("Rodrigo", "Ribeiro", "rribeiro@mindhub.com",
					passwordEncoder.encode("Mribeiro**"));

			Client admin = new Client("Admin", "Admin", "admin@mindhub.com",
					passwordEncoder.encode("admiN123!"));

			clientRepository.save(admin);

            //Accounts
			Account melbaAcc1 = new Account("VIN001", today , 5000, melba);
			Account melbaAcc2 = new Account("VIN002", today.plusDays(1) , 7500, melba);
			Account rodriAcc1 = new Account("VIN003", today, 20000, rodri);
			Account rodriAcc2 = new Account("VIN004", today.plusDays(1) , 15000, rodri);

			//Create Transactions
			Transaction order1 = new Transaction(TransactionType.DEBIT, 3000, "Direct Deposit - Salary", now, melbaAcc1);
			Transaction order2 = new Transaction(TransactionType.CREDIT, 5000, "Direct Deposit - Interest", now, melbaAcc1);
			Transaction order3 = new Transaction(TransactionType.DEBIT, -10000, "Rent Payment", now, rodriAcc1);
			Transaction order4 = new Transaction(TransactionType.CREDIT, -1100, "Buy on EBay", now, rodriAcc2);

			//Cards
			Card card1 = new Card(CardType.DEBIT, "2500-4585-6587-5001", (short)291, today, today.plusYears(5),
					melba.toString(), CardColor.GOLD, melba);
			Card card2 = new Card(CardType.CREDIT, "5894-2158-8421-4587", (short)587, today, today.plusYears(5),
					melba.toString(), CardColor.TITANIUM, melba);
			Card card3 = new Card(CardType.CREDIT, "7587 2587 1278 0479", (short)302, today, today.plusYears(5),
					rodri.toString(), CardColor.SILVER, rodri);


			clientRepository.save(melba);
			melba.addAccount(melbaAcc1);
			melba.addAccount(melbaAcc2);
			accountRepository.save(melbaAcc1);
			accountRepository.save(melbaAcc2);
			melbaAcc1.addTransaction(order1);
			melbaAcc1.addTransaction(order2);
			transactionRepository.save(order1);
			transactionRepository.save(order2);
			melba.addCards(card1);
			melba.addCards(card2);
			cardRepository.save(card1);
			cardRepository.save(card2);


			clientRepository.save(rodri);
			rodri.addAccount(rodriAcc1);
			rodri.addAccount(rodriAcc2);
			accountRepository.save(rodriAcc1);
			accountRepository.save(rodriAcc2);
			rodriAcc1.addTransaction(order3);
			rodriAcc2.addTransaction(order4);
			transactionRepository.save(order3);
			transactionRepository.save(order4);
			rodri.addCards(card3);
			cardRepository.save(card3);


			List<Integer> mortgagePay = new ArrayList<>() {{
                add(12);
                add(24);
                add(36);
                add(48);
                add(60);

            }};
			List<Integer> personalPay = new ArrayList<>() {{
                add(6);
                add(12);
                add(24);
            }};
			List<Integer> automotivePay = new ArrayList<>() {{
                add(6);
                add(12);
                add(24);
                add(36);
            }};

			Loan mortgage = new Loan("Mortgage", 500000, mortgagePay);
			Loan personal = new Loan("Personal", 100000, personalPay);
			Loan automotive = new Loan("Automotive", 300000, automotivePay);

			loanRepository.save(mortgage);
			loanRepository.save(personal);
			loanRepository.save(automotive);

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, melba, mortgage);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, melba, personal);
			ClientLoan clientLoan3 = new ClientLoan(100000, 24, rodri, personal);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36, rodri, automotive);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);


		};
	}

}
