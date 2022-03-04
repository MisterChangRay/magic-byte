package com.github.misterchangray.core.common.dynamic;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.util.ArrayList;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-21 16:33
 **/
@MagicClass()
public class DynamicStudent {
    @MagicField(order = 2, size = 10)
    private String name;
    @MagicField(order = 3)
    private long phone;

    @MagicField(order = 8)
    private Byte bookLen;
    @MagicField(order = 12, dynamicSizeOf = 8)
    private int[] bookids;
    @MagicField(order = 14)
    private byte emailLen;
    @MagicField(order = 16, dynamicSizeOf = 14)
    private String email;

    public static ArrayList<DynamicStudent> build(int count) {
        ArrayList<DynamicStudent> students = new ArrayList<>();
        for (int i = 0; i <count; i++) {
            DynamicStudent student = new DynamicStudent();
            student.setName("stu-" + i);
            student.setBookLen((byte) i);
            student.setBookids(new int[student.getBookLen()]);
            for (int i1 = 0; i1 < student.getBookids().length; i1++) {
                student.getBookids()[i1] = 1000 + i1;
            }
            student.setPhone(1000 + i);
            student.setEmailLen((byte) ((count - i - 1) * 2));
            student.setEmail("misterchangray@hotmail.com");
            students.add(student);
        }
        return students;
    }


    public Byte getBookLen() {
        return bookLen;
    }

    public void setBookLen(Byte bookLen) {
        this.bookLen = bookLen;
    }

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

    public byte getEmailLen() {
        return emailLen;
    }

    public void setEmailLen(byte emailLen) {
        this.emailLen = emailLen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
