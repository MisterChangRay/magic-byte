package com.github.misterchangray.core.dynamic;

import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-21 16:33
 **/
public class DynamicClasses {
    @MagicField(order = 1)
    private int teacherLen;
    @MagicField(order = 2, dynamicSizeOf = 1)
    private DynamicTeacher[] teacher;
    @MagicField(order = 3)
    private short studentLen;
    @MagicField(order = 4, dynamicSizeOf = 3)
    private ArrayList<DynamicStudent> studentList;


    public static List<DynamicClasses> build(int count) {
        List<DynamicClasses> classes = new ArrayList<>();
        for (int i = 0; i <count; i++) {
            DynamicClasses classes1 = new DynamicClasses();
            classes1.setTeacherLen(2);
            classes1.setTeacher(DynamicTeacher.build(classes1.getTeacherLen()).toArray(new DynamicTeacher[classes1.getTeacherLen()]));

            classes1.setStudentLen((short) 3);
            classes1.setStudentList(DynamicStudent.build(classes1.getStudentLen()));
            classes.add(classes1);
        }
        return classes;
    }

    public int getTeacherLen() {
        return teacherLen;
    }

    public void setTeacherLen(int teacherLen) {
        this.teacherLen = teacherLen;
    }

    public DynamicTeacher[] getTeacher() {
        return teacher;
    }

    public void setTeacher(DynamicTeacher[] teacher) {
        this.teacher = teacher;
    }

    public short getStudentLen() {
        return studentLen;
    }

    public void setStudentLen(short studentLen) {
        this.studentLen = studentLen;
    }

    public ArrayList<DynamicStudent> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<DynamicStudent> studentList) {
        this.studentList = studentList;
    }
}
