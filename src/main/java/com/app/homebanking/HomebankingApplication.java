package com.app.homebanking;

import com.app.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean

	public CommandLineRunner initData(ClientRepository clientRepository) {
		return (args -> {

			Client client1 = new Client();
			client1.id = "1-1-1";
			client1.name = "Melba";
			client1.lastName = "Morel";
			client1.email = "melba@mindhub.com";

			Client client2 = new Client();
			client2.id = "1-1-2";
			client2.name = "Mario";
			client2.lastName = "Morello";
			client2.email = "mario@mindhub.com";

			clientRepository.save(client1);
			clientRepository.save(client2);
		});

	}
}