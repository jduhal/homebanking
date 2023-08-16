package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			//Clients
			Client client1 = new Client("melba@mindhub.com", "Melba", "Morel");
			clientRepository.save(client1);
			Client client2 = new Client("dpalmer@mindhub.com", "David", "Palmer");
			clientRepository.save(client2);

			//Accounts
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			client1.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			client1.addAccount(account2);
			accountRepository.save(account2);
			Account account3 = new Account("VIN003", LocalDate.now().minusDays(2), 15000);
			client2.addAccount(account3);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", LocalDate.now().minusDays(2), 20000);
			client2.addAccount(account4);
			accountRepository.save(account4);

			//Create Transactions
			Transaction transaction1 = new Transaction(TransactionType.CREDIT,1500,"Direct Deposit - Salary", LocalDate.now().minusDays(2));
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,1600,"Direct Deposit - Interest", LocalDate.now().minusDays(1));
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,1700,"Rent", LocalDate.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,1800,"Payment", LocalDate.now().minusDays(2));
			Transaction transaction5 = new Transaction(TransactionType.CREDIT,1900,"Direct Deposit - Interest", LocalDate.now().minusDays(1));
			Transaction transaction6 = new Transaction(TransactionType.CREDIT,2000,"Direct Deposit - Salary", LocalDate.now());
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,-500,"Buy on Amazon", LocalDate.now().minusDays(2));
			Transaction transaction8 = new Transaction(TransactionType.DEBIT,-800,"Rent Payment", LocalDate.now().minusDays(1));
			Transaction transaction9 = new Transaction(TransactionType.DEBIT,-200,"Buy on EBay", LocalDate.now());
			Transaction transaction10 = new Transaction(TransactionType.DEBIT,-100,"Internet Service", LocalDate.now().minusDays(1));
			Transaction transaction11 = new Transaction(TransactionType.DEBIT,-250,"Public Services", LocalDate.now());
			Transaction transaction12 = new Transaction(TransactionType.DEBIT,-50,"Buy on AliExpress", LocalDate.now());

			// Add Transaction to Account
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account2.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account1.addTransaction(transaction7);
			account1.addTransaction(transaction8);
			account1.addTransaction(transaction9);
			account2.addTransaction(transaction10);
			account2.addTransaction(transaction11);
			account3.addTransaction(transaction12);

			//Save Transaction on DB
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
			transactionRepository.save(transaction9);
			transactionRepository.save(transaction10);
			transactionRepository.save(transaction11);
			transactionRepository.save(transaction12);

			// Create Loans
			Loan loan1 = new Loan("Mortgage",500000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal",100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotive",300000, List.of(6,12,24,36));

			//Save Loans to DB
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			//Create ClientLoan
			ClientLoan clientLoan1 = new ClientLoan(400000,60);
			ClientLoan clientLoan2 = new ClientLoan(50000,12);
			ClientLoan clientLoan3 = new ClientLoan(100000,24);
			ClientLoan clientLoan4 = new ClientLoan(200000,36);

			// Add Client to ClientLoan
			client1.addLoan(clientLoan1);
			client1.addLoan(clientLoan2);
			client2.addLoan(clientLoan3);
			client2.addLoan(clientLoan4);

			//Add Loan to ClientLoan
			loan1.addClient(clientLoan1);
			loan2.addClient(clientLoan2);
			loan2.addClient(clientLoan3);
			loan3.addClient(clientLoan4);

			// Save ClientLoan to DB
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			//Cards
			Card card1 = new Card("Melba Morel", CardType.DEBIT, CardColor.GOLD, "2500-4585-6587-5001", 291, LocalDate.now(),LocalDate.now().plusYears(5));
			client1.addCard(card1);
			cardRepository.save(card1);
			Card card2 = new Card("Melba Morel",CardType.CREDIT,CardColor.TITANIUM,"5894-2158-8421-4587",587,LocalDate.now(),LocalDate.now().plusYears(5));
			client1.addCard(card2);
			cardRepository.save(card2);
			Card card3 = new Card("David Palmer", CardType.DEBIT, CardColor.GOLD, "7812-2389-5698-4125", 328, LocalDate.now(),LocalDate.now().plusYears(5));
			client1.addCard(card3);
			cardRepository.save(card3);
			Card card4 = new Card("David palmer",CardType.CREDIT,CardColor.SILVER,"8412-3571-9846-5874",852,LocalDate.now(),LocalDate.now().plusYears(5));
			client1.addCard(card4);
			cardRepository.save(card4);

		};
	}
}
	