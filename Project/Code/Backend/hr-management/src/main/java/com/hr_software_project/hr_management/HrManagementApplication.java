package com.hr_software_project.hr_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.hr_software_project.hr_management.entity")
public class HrManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrManagementApplication.class, args);
	}

}
