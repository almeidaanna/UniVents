package com.example.univents.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.univents.model.Student;

import java.util.List;

@Dao
public interface StudentDAO
{
    @Query("SELECT * FROM student ORDER BY studentLname ASC")
    LiveData<List<Student>> getAll();

    @Query("SELECT * FROM student WHERE studentEmailId = :studentId LIMIT 1")
    Student findByID(String studentId);

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);

    @Update
    void updateStudent(Student student);

    @Query("DELETE FROM student")
    void deleteAll();
}
