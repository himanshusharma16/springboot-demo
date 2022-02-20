package com.example.demo;

import com.example.demo.dao.StudentMongoRepository;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoController {

    @Autowired
    StudentMongoRepository studentRepo;

    @GetMapping("/mongo/create")
    public String createStudent(){
        studentRepo.save(new Student("Himanshu","5",20L));
        studentRepo.save(new Student("Rachna","5",40L));
        studentRepo.save(new Student("Golda","2",60L));
        return "Students created";
    }
}
