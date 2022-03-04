package com.github.misterchangray.core.common.entity;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:33
 **/
@MagicClass()
public class Student {
    @MagicField(order = 1, size = 10)
    private String name;
    @MagicField(order = 2)
    private long phone;
    @MagicField(order = 3, size = 3)
    private int[] bookids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int[] getBookids() {
        return bookids;
    }

    public void setBookids(int[] bookids) {
        this.bookids = bookids;
    }


    public static List<Student> build(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i <count; i++) {
            Student student = new Student();
            student.setName("stu-" + i);
            student.setBookids(new int[]{10 + i, 100 +i ,1000 + i});
            student.setPhone(1000 + i);
            students.add(student);
        }
        return students;
    }

}
