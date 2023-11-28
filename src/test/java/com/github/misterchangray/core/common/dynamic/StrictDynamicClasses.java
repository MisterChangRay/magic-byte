package com.github.misterchangray.core.common.dynamic;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-21 16:33
 **/
@MagicClass(strict = true)
public class StrictDynamicClasses {
    @MagicField(id="1", order = 1)
    private int teacherLen;
    @MagicField(order = 2, dynamicSizeOfId = "1")
    private DynamicTeacher[] teacher;
    @MagicField(id="3", order = 3)
    private short studentLen;
    @MagicField(order = 4, dynamicSizeOfId = "3")
    private ArrayList<DynamicStudent> studentList;


    public static List<StrictDynamicClasses> build(int count) {
        List<StrictDynamicClasses> classes = new ArrayList<>();
        for (int i = 0; i <count; i++) {
            StrictDynamicClasses classes1 = new StrictDynamicClasses();
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
