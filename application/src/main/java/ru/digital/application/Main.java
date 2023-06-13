package ru.digital.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println(new Date().toString());
        SpringApplication.run(Main.class, args);
    }
}