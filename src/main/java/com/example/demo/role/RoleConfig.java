package com.example.demo.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleConfig {

    @Bean
    CommandLineRunner commandLineRunnerRole (RoleRepository repository) {
        return args -> {
            Role admin = new Role("admin");

            Role customer = new Role("customer");

            repository.saveAll(List.of(admin, customer));
        };
    }
}
