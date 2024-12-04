package com.example.myFashionTrunk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyFashionTrunkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFashionTrunkApplication.class, args);
	}

}
