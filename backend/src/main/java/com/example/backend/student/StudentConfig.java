package com.example.backend.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner studentCommandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student student1 = Student.builder()
                    .name("John")
                    .surname("Doe")
                    .email("john.doe@example.com")
                    .age(20).build();

            Student student2 = Student.builder()
                    .name("Jane")
                    .surname("Doe")
                    .email("jane.doe@example.com")
                    .age(22).build();

            Student student3 = Student.builder()
                    .name("Jim")
                    .surname("Doe")
                    .email("jim.doe@example.com")
                    .age(24).build();

            Student student4 = Student.builder()
                    .name("Jill")
                    .surname("Doe")
                    .email("jill.doe@example.com")
                    .age(26).build();

            studentRepository.saveAll(
                    List.of(student1, student2, student3, student4)
            );
        };
    }
}