package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

@MagicClass(autoTrim = true)
public class HighSchool {
    @MagicField(order = 1, size = 10)
    private String name;

    @MagicField(order = 3)
    private int teacherCount;
    @MagicField(order = 4, dynamicSizeOf = 3)
    private Teacher[] teachers;

    @MagicField(order = 5)
    private int studentCount;
    @MagicField(order = 6, dynamicSizeOf = 5)
    private List<Student> studentList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public int getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(int teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }
}