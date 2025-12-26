package com.legaltech.judgment_comparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main Application Class
 * 
 * @SpringBootApplication combines:
 *                        - @Configuration - Marks as configuration class
 *                        - @EnableAutoConfiguration - Auto-configures Spring
 *                        beans
 *                        - @ComponentScan - Scans
 *                        for @Component, @Service, @Repository, @Controller
 */

@SpringBootApplication
@EntityScan(basePackages = "com.legaltech.judgment_comparator.entity")
@EnableJpaRepositories(basePackages = "com.legaltech.judgment_comparator.repository")
public class JudgmentComparatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JudgmentComparatorApplication.class, args);
		System.out.println("\n✅ Application Started Successfully!");
		System.out.println("📝 API Documentation: http://localhost:8080");
		System.out.println("💾 H2 Console: http://localhost:8080/h2-console");
		System.out.println("🔍 JDBC URL: jdbc:h2:mem:legaldb\n");
	}

}
