package com.example.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy 
public class WebProgrammingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProgrammingProjectApplication.class, args);
	}

}
