package dev.decadev.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootSecurityApplication {

    public static void main(String[] args) {
//        genPassword();
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }

//    public static void genPassword(){
//        System.out.println(passwordEncoder().encode("1234"));
//    }

}
