package com.example.budgetmanagerio;

import com.example.budgetmanagerio.config.CorsFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetManagerIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetManagerIoApplication.class, args);
    }
    @Bean
    public Filter corsFilter() {
        return new CorsFilter();
    }
}
