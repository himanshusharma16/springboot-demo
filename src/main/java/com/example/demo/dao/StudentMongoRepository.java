package com.example.demo.dao;

import com.example.demo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMongoRepository extends MongoRepository<Student,Long> {
    @Query("{id:'?0'}")
    Student findItemByName(Long id);

    @Query(value="{grade:'?0'}", fields="{'name' : 1, 'id' : 1}")
    List<Student> findAll(String grade);

    public long count();

}
