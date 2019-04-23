package com.mertpolat.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mertpolat.pojo.DatabaseConnection;


@SpringBootApplication
@ComponentScan(basePackages = {"com.mertpolat.webservice"})
@ComponentScan(basePackages = {"com.mertpolat.pojo"})
public class Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatabaseConnection dt =new DatabaseConnection();
		dt.createTable();
		SpringApplication.run(Application.class, args);


	}
}
