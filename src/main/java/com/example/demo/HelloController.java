package com.example.demo;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    StudentDao dao;

    @GetMapping("/greet")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Student> getAllStudents() {
        return dao.loadAll();
    }

    @GetMapping(value = "/addStudent")
    public String saveData(@RequestParam String name,@RequestParam String grade){
        Student s = new Student(name,grade);
        s.setId(1L);
        long response = dao.save(s);
        return "Done";
    }

}