package com.example.univents.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.univents.model.Student;
import com.example.univents.repository.StudentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository cRepository;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel (Application application) {
        super(application);
        cRepository = new StudentRepository(application);
        allStudents = cRepository.getAllStudents();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Student> findByIDFuture(final String studentId){
        return cRepository.findByIDFuture(studentId);
    }

    public Student getStudent(String email){
        Student currentUser = cRepository.getStudent(email);
        return currentUser;
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(Student student) {
        cRepository.insert(student);
    }
    public void delete(Student student) {
        cRepository.delete(student);
    }
    public void deleteAll() {
        cRepository.deleteAll();
    }
    public void update(Student student) {
        cRepository.updateStudent(student);
    }
}