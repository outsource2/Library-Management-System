package com.example.MaidsTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.example.MaidsTest")
public class MaidsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaidsTestApplication.class, args);
	}

}
