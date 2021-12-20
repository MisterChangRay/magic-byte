package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:33
 **/
public class Classes {
    @MagicField(order = 1)
    private Teacher[] teacher;
    @MagicField(order = 2)
    private List<Student> studentList;


    public Teacher[] getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher[] teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
