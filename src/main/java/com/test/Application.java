package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws JAXBException, IOException {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
