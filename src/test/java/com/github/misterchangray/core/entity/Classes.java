package com.github.misterchangray.core.entity;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:33
 **/
public class Classes {
    @MagicField(order = 1, size = 1)
    private Teacher[] teacher;
    @MagicField(order = 2, size = 2)
    private List<Student> studentList;



    public static List<Classes> build(int count) {
        List<Classes> classes = new ArrayList<>();
        for (int i = 0; i <count; i++) {
            Classes classes1 = new Classes();
            classes1.setTeacher(Teacher.build(1).toArray(new Teacher[1]));
            classes1.setStudentList(Student.build(2));
            classes.add(classes1);
        }
        return classes;
    }

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
