package com.university.student; 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate; 


@SpringBootApplication 
@ComponentScan(basePackages = {"com.university.student", "com.university.common.config"}) 

public class StudentServiceApplication { 
    public static void main(String[] args) { 
        SpringApplication.run(StudentServiceApplication.class, args); 
    } 

     @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();  

    }
} 
