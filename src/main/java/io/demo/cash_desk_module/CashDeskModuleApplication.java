package io.demo.cash_desk_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CashDeskModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashDeskModuleApplication.class, args);
    }

}
