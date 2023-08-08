package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository ClientRepository, AccountRepository AccountRepository) {
		return (args) -> {

			Client Melba= new Client("Melba", "Morel","melba@mindhub.com");
			Client Chloe= new Client("Chloe", "Brian","cobrian@mindhub.com");
			Client David= new Client("David", "Palmer","dpalmer@mindhub.com");
			Client Michelle= new Client("Michelle", "Desler","mdesler@mindhub.com");
			ClientRepository.save(Melba);
			ClientRepository.save(Chloe);
			ClientRepository.save(David);
			ClientRepository.save(Michelle);
			AccountRepository.save(new Account(Melba, 5000.0, LocalDate.now()));
			AccountRepository.save(new Account(Melba, 7500.0, LocalDate.now().plusDays(1)));
			AccountRepository.save(new Account(Chloe, 1000.0, LocalDate.now()));
			AccountRepository.save(new Account(Chloe, 2000.0, LocalDate.now()));
			AccountRepository.save(new Account(David, 15000.0, LocalDate.now().plusDays(1)));
			AccountRepository.save(new Account(Michelle, 3000.0, LocalDate.now()));
			AccountRepository.save(new Account(Michelle, 5000.0, LocalDate.now()));

		};
	}

}
