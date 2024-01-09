package com.example.frontend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class Student {
    protected Long id;
    protected String name;
    protected String surname;
    protected String email;
    protected Integer age;

    public Student(String name, String surname, String email, Integer age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }


}
