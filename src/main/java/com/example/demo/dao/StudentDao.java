package com.example.demo.dao;

import com.example.demo.execption.StudentGenerateException;
import com.example.demo.execption.StudentLostException;
import com.example.demo.execption.StudentRuntimeException;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long save(Student student) throws StudentRuntimeException {
        String sql = "insert into STUDENT (ID, NAME, GRADE) values (?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, student.getId() != null ? student.getId() : getNewId());
                ps.setString(2, student.getName());
                ps.setString(3, student.getGrade());
                return ps;
            }
        }, holder);
        Number key = holder.getKey();
        if (key != null) {
            return key.longValue();
        }
        throw new StudentGenerateException("No generated primary key returned.");
    }

    /*Returns the number of rows updated*/
    public int updateStudent(Student s){
        int rows = jdbcTemplate.update("update Student set name = ? , grade = ? where id = ?",s.getName(),s.getGrade(),s.getId());
        return rows;
    }

    private long getNewId() {
        long maxID =  jdbcTemplate.query("select max(ID) as ID from STUDENT", (resultSet, i) -> {
            return resultSet.getLong("ID");
        }).get(0);
        return ++maxID;
    }

    public Student load(long id) throws StudentRuntimeException{
        List<Student> persons = jdbcTemplate.query("select * from STUDENT where id =?",
                new Object[]{id}, (resultSet, i) -> {
                    return toReport(resultSet);
                });
        if (persons.size() == 1) {
            return persons.get(0);
        }
        throw new StudentLostException("No item found for id: " + id, 404);
    }

    private Student toReport(ResultSet resultSet) throws SQLException {
        Student report = new Student();
        report.setId(resultSet.getLong("ID"));
        report.setName(resultSet.getString("NAME"));
        report.setGrade(resultSet.getString("GRADE"));
        return report;
    }

    public List<Student> loadAll() {
        return jdbcTemplate.query("select * from STUDENT where ID > 0", (resultSet, i) -> {
                return toReport(resultSet);
        });
    }
}
