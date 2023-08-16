package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
        //attributes
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private TransactionType type;
        private double amount;
        private String description;
        private LocalDate date;

        //transaction's Owner Account
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "account_Id")
        private Account account;

        //constructors
        public Transaction() {
        }

        public Transaction(TransactionType type, double amount, String description, LocalDate date) {
                this.type = type;
                this.amount = amount;
                this.description = description;
                this.date = date;
        }

        //getters & setters

        public Long getId() {
                return id;
        }

        public TransactionType getType() {
                return type;
        }

        public void setType(TransactionType type) {
                this.type = type;
        }

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public LocalDate getDate() {
                return date;
        }

        public void setDate(LocalDate date) {
                this.date = date;
        }

        //upstream
        public void setAccount(Account account) {
                this.account = account;
        }

        public Account getAccount() {
                return account;
        }

}
