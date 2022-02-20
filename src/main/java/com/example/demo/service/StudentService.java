package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.dao.StudentRepository;
import com.example.demo.execption.StudentRuntimeException;
import com.example.demo.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentDao dao;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentDao dao){
        this.dao = dao;
    }

    public List<Student> getStudent(Optional<Integer> id){
        if(id.isPresent())
            return getSpecificStudent(id.get());
        else
            return studentRepository.findAll().stream().filter(i->i.getId()>0).collect(Collectors.toList());
    }

    private List<Student> getSpecificStudent(Integer id) {
        List<Student> students = new ArrayList<>();
        try{
            students.add(dao.load(id));
        } catch (StudentRuntimeException e){
            students.add(new Student(e.getMessage(),"",0L));
        }
        return students;
    }

    @Transactional
    public String addOrUpdateStudent(Student s) {
        if(s.getId() == null)
            return addNewStudent(s);
        else
            return updateStudent(s);
    }


    private String updateStudent(Student s) {
        Student existingStudent = dao.load(s.getId());
        if(existingStudent == null)
            return "Cannot find the student with id - "+s.getId();
        existingStudent.setGrade(s.getGrade());
        existingStudent.setName(s.getName());
        int rows = dao.updateStudent(existingStudent);
        if(rows ==1)
            return "Student updated with id - "+s.getId();
        else
            return "Issue while updating student";
    }

    private String addNewStudent(Student s) {
        Long id = dao.save(s);
        return "Student added with id - "+id;
    }
}
