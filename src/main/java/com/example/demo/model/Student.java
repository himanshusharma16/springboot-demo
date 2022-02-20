package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Document("student")
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String grade;

    public Student(){}

    public Student(String name, String grade, Long id){
        this.name = name;
        this.grade = grade;
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
