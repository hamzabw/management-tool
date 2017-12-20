package com.acc.bt.managementtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class ManagementToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementToolApplication.class, args);
	}
}
