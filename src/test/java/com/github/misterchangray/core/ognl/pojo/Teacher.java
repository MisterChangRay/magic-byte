package com.github.misterchangray.core.ognl.pojo;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

@MagicClass
public class Teacher {
    @MagicField(order = 1, ognl = "id*3")
    private int id;
    @MagicField(order = 2, size = 10)
    private String name;
    @MagicField(order = 3)
    private Student student;

    @MagicField(order = 4, ognl = "student.name", size = 10)
    private String stuName;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
