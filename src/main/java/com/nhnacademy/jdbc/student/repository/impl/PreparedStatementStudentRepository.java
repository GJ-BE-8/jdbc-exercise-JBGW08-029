package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
//여긴 statementStudentRepository이다 클래스 오타인듯?

public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 학생 등록
        String sql = String.format("insert into jdbc_students(id, name, gender, age) values ('%s','%s','%s','%d')",
        student.getId(), student.getName(), student.getGender(), student.getAge());

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
        ){
            int result = connection.createStatement().executeUpdate(sql);
            return result;
        }catch(SQLException e){
            e.printStackTrace();

        }
        return 0;
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 학생 조회
        String sql = String.format("SELECT * FROM jdbc_students WHERE id = '%s' ", id);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ){
            while(resultSet.next()){
                Student student = new Student(resultSet.getString("id"), resultSet.getString("name"),
                        Student.GENDER.valueOf(resultSet.getString("gender")), resultSet.getInt("age"), resultSet.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(student);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 학생 수정 , name 수정
        String sql = String.format("UPDATE jdbc_students SET name = '%s', gender = '%s', age = %d WHERE id = '%s' " ,
        student.getName(), student.getGender(), student.getAge(), student.getId());

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
        ){
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteById(String id){
        //todo#4 학생 삭제
        String sql = String.format("DELETE FROM jdbc_students WHERE id = '%s'", id);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
        ){
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
