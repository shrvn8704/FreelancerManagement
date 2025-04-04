package com.ey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreelancerManagementSystemApplication {
	private static final Logger logger = LogManager.getLogger(FreelancerManagementSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FreelancerManagementSystemApplication.class, args);
		
		 logger.info("Application started successfully!");
	     logger.debug("Debugging log example.");
	     logger.error("This is an error log!");
	}

}
