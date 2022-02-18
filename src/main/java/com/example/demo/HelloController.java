package com.example.demo;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    StudentService studentService;

    @GetMapping("/greet")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/student")
    @ResponseBody
    public List<Student> getStudent(@RequestParam Optional<Integer> id) {
        return studentService.getStudent(id);
    }

    @PostMapping(value = "/addStudent")
    public String saveData(@RequestParam String name,@RequestParam String grade, @RequestParam(required = false) Long id){
        Student s = new Student(name,grade,id);
        return studentService.addOrUpdateStudent(s);
    }

}