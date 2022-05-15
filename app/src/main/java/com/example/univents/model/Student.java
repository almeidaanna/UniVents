package com.example.univents.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Entity
public class Student {
    private String studentFname;
    private String studentLname;
    private String studentPhno;
    @PrimaryKey @NotNull
    private String studentEmailId;
    @NotNull
    private String studentPassword;
    private String studentUniversity;
    private ArrayList<Event> eventHistory;

    public Student(String studentFname, String studentLname, String studentPhno, String studentEmailId, String studentPassword, String studentUniversity) {
        this.studentFname = studentFname;
        this.studentLname = studentLname;
        this.studentPhno = studentPhno;
        this.studentEmailId = studentEmailId;
        this.studentPassword = studentPassword;
        this.studentUniversity = studentUniversity;
        eventHistory = new ArrayList<>();
    }

    public String getStudentFname() {
        return studentFname;
    }

    public void setStudentFname(String studentFname) {
        this.studentFname = studentFname;
    }

    public String getStudentLname() {
        return studentLname;
    }

    public void setStudentLname(String studentLname) {
        this.studentLname = studentLname;
    }

    public String getStudentPhno() {
        return studentPhno;
    }

    public void setStudentPhno(String studentPhno) {
        this.studentPhno = studentPhno;
    }

    public String getStudentEmailId() {
        return studentEmailId;
    }

    public void setStudentEmailId(String studentEmailId) {
        this.studentEmailId = studentEmailId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentUniversity() {
        return studentUniversity;
    }

    public void setStudentUniversity(String studentUniversity) {
        this.studentUniversity = studentUniversity;
    }

    public ArrayList<Event> getEventHistory() {
        return eventHistory;
    }

    public void addToHistory(Event event){
        eventHistory.add(event);
    }

    public void setEventHistory(ArrayList<Event> eventHistory) {
        this.eventHistory = eventHistory;
    }
}
