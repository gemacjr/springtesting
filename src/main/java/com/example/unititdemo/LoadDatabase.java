package com.example.unititdemo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository){
        return args -> {
            repository.save(new Customer("Ed","Macky","President"));
            repository.save(new Customer("Pam","Beasley","CEO"));
            repository.save(new Customer("Katy","Smith","COO"));
        };
    }
}
