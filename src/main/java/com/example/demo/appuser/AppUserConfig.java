package com.example.demo.appuser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class AppUserConfig {

    @Bean
    CommandLineRunner commandLineRunner (AppUserRepository repository) {
        return args -> {
            AppUser alex = new AppUser(
                    "Alex",
                    "lexuar2000@gmail.com",
                    LocalDate.of(2000, 8, 5)
            );

            AppUser dasha = new AppUser(
                    "Dasha",
                    "dkad1802@gmail.com",
                    LocalDate.of(2000, 2, 18)
            );

            repository.saveAll(List.of(alex, dasha));
        };
    }
}
