package ru.utelksp.upo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Created by ZotovES on 17.03.2019
 * Стартующее приложение
 */
@SpringBootApplication()
public class UpoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {SpringApplication.run(UpoApplication.class, args);}
}

