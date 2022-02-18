package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.execption.StudentRuntimeException;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentService {

    @Autowired
    StudentDao dao;

    public List<Student> getStudent(Optional<Integer> id){
        if(id.isPresent())
            return getSpecificStudent(id.get());
        else
            return dao.loadAll();
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
