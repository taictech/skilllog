//package com.example.demo;?
package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@SpringBootApplication
public class SkillLogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillLogAppApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(CategoryRepository categoryRepository) {
	    return args -> {
	        if (categoryRepository.count() == 0) {
	            categoryRepository.save(new Category("Java"));
	            categoryRepository.save(new Category("Spring Boot"));
	            categoryRepository.save(new Category("SQL"));
	        }
	    };
	}


}
