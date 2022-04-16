package org.example.core.service;

import org.example.core.domain.Student;

public class StudentService {
    private Student student;

    public void setStudent(Student student){
        this.student = student;
    }

    public StudentService() {
        System.out.println("StudentService 기본 생성자");
    }

    public StudentService(Student student) {
        this.student = student;

        System.out.println("생성자 주입");
    }

    public Student getStudent() {
        return student;
    }

    public void info() {
        System.out.println("info test");
    }
}
