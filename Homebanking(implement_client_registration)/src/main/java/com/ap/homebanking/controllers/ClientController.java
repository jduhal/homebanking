package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping( "/api")
@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping( "/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById( id )
                .map( ClientDTO::new )
                .orElse(null);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register( @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data: ", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use ", HttpStatus.FORBIDDEN);
        }
        clientRepository.save( new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return new ClientDTO( clientRepository.findByEmail( authentication.getName() ) );
    }

}