package com.example.univents.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.univents.dao.StudentDAO;
import com.example.univents.database.StudentDatabase;
import com.example.univents.model.Student;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class StudentRepository {
    private StudentDAO studentDao;
    private LiveData<List<Student>> allStudent;

    public StudentRepository(Application application){
        StudentDatabase db = StudentDatabase.getInstance(application);
        studentDao =db.studentDao();
        allStudent= studentDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<Student>> getAllStudents() {
        return allStudent;
    }

    public Student getStudent(String email) {
        return studentDao.findByID(email);
    }

    public void insert(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.insert(student);
            }
        });
    }
    public void deleteAll(){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override public void run()
            {
                studentDao.deleteAll();
            }
        });
    }

    public void delete(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { studentDao.delete(student);
            }
        });
    }
    public void updateStudent(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.updateStudent(student);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Student> findByIDFuture(final String studentId) {
        return CompletableFuture.supplyAsync(new Supplier<Student>() {
            @Override
            public Student get() {
                return studentDao.findByID(studentId);
            }
        }, StudentDatabase.databaseWriteExecutor);
    }
}