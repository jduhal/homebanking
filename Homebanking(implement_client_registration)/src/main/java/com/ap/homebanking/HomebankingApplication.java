package com.ap.homebanking;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData( ClientRepository clientRepository, AccountRepository accountRepository,
			TransactionRepository transactionRepository, LoanRepository loanRepository,
			ClientLoanRepository clientLoanRepository, CardRepository cardRepository ) {
		return (args) -> {
			//Clients
			Client client1 =new Client( "Melba", "Morel","mmorel@mindhub.com", passwordEncoder.encode("Mmorel!!") );
            Client client2 = new Client( "Rodrigo","Ribeiro","rribeiro@mindhub.com", passwordEncoder.encode("Mribeiro**") );

			Client clientAdmin = new Client( "Admin","Admin","admin@mindhub.com", passwordEncoder.encode("admiN123!") );

            clientRepository.save( client1 );
			clientRepository.save( client2 );
			clientRepository.save( clientAdmin );
			//Accounts
            Account account1 = new Account("VIN001", LocalDate.now(), 5000 );
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500 );
			Account account3 = new Account("VIN003", LocalDate.now(), 15000 );
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 20000 );

			client1.addAccount( account1);
			client1.addAccount( account2 );
			client2.addAccount( account3 );
			client2.addAccount( account4 );

			accountRepository.save( account1 );
			accountRepository.save( account2 );
			accountRepository.save( account3 );
			accountRepository.save( account4 );

			//Transactions

			Transaction trans1 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), 33000, "Direct Deposit - Salary");
            account1.addTransaction( trans1 );
            transactionRepository.save( trans1 );

            Transaction trans2 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 5000, "Direct Deposit - Interest");
            account1.addTransaction( trans2 );
            transactionRepository.save( trans2 );

            Transaction trans3 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -11000, "Rent Payment");
            account2.addTransaction( trans3 );
            transactionRepository.save( trans3 );

            Transaction trans4 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), -1100, "Buy on EBay");
			account3.addTransaction( trans4 );
			transactionRepository.save( trans4 );

			//Loans
            Loan loan1 = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
			loanRepository.save( loan1 );
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			loanRepository.save( loan2 );
			Loan loan3 = new Loan("Automotive", 300000, List.of(6,12,24,36));
			loanRepository.save( loan3 );

            ClientLoan clientLoan1 = new ClientLoan(400000.0, 60);
        	client1.addClientLoans( clientLoan1 );
            loan1.addClientLoans( clientLoan1 );
            clientLoanRepository.save( clientLoan1 );

            ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);
        	client1.addClientLoans( clientLoan2 );
            loan2.addClientLoans( clientLoan2 );
            clientLoanRepository.save( clientLoan2 );

            ClientLoan clientLoan3 = new ClientLoan(100000.0, 24);
        	client2.addClientLoans( clientLoan3 );
            loan2.addClientLoans( clientLoan3 );
            clientLoanRepository.save( clientLoan3 );

            ClientLoan clientLoan4 = new ClientLoan(200000.0, 36);
       		client2.addClientLoans( clientLoan4 );
            loan3.addClientLoans( clientLoan4 );
            clientLoanRepository.save( clientLoan4 );

			//Cards
			Card card1 = new Card( );
			card1.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card1.setType(CardType.DEBIT);
			card1.setColor(CardColor.GOLD);
			card1.setNumber("2500-4585-6587-5001");
			card1.setCvv((short) 291);
			card1.setFromDate(LocalDate.now() );
			card1.setThruDate(LocalDate.now().plusYears(5) );
			client1.addCard(card1);
			cardRepository.save(card1);

			Card card2 = new Card();
			card2.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card2.setType(CardType.CREDIT);
			card2.setColor(CardColor.TITANIUM);
			card2.setNumber("5894-2158-8421-4587");
			card2.setCvv((short) 587);
			card2.setFromDate(LocalDate.now() );
			card2.setThruDate(LocalDate.now().plusYears(5) );
			client1.addCard(card2);
			cardRepository.save(card2);

			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),
			CardType.CREDIT, CardColor.SILVER, "7587 2587 1278 0479", (short)302, LocalDate.now() , LocalDate.now().plusYears(5) );
			client2.addCard(card3);
			cardRepository.save(card3);
		};
	}



}
