package com.star.bajaj_assesment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BajajAssesmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BajajAssesmentApplication.class, args);
	}

}
