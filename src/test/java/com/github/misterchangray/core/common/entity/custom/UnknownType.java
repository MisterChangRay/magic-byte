package com.github.misterchangray.core.common.entity.custom;

import com.github.misterchangray.core.annotation.MagicField;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Ray.chang
 * @create: 2021-12-20 16:32
 **/
public class UnknownType {
    @MagicField(order = 1)
    private int id;
    @MagicField(order = 2, size = 10)
    private String name;
    @MagicField(order = 3, id="3")
    private int phoneSize;
    @MagicField(order = 4, dynamicSizeOfId = "3")
    private long[] phones;
    @MagicField(order = 5)
    private Object attr;
    @MagicField(order = 6)
    private Date brithday;
    @MagicField(order = 7)
    private Class<?> clazz;

    @MagicField(order = 9)
    private int age;

    @MagicField(order = 12, size = 2)
    private List<Date> remark;

    @MagicField(order = 15)
    private int ttl;

    @MagicField(order = 18, size = 2)
    private Class<?>[] opt;

    @MagicField(order = 20)
    private long haha;





    public static List<UnknownType> build(int count) {
        List<UnknownType> teachers = new ArrayList<>();
        for (int i = 0; i <count; i++) {

            UnknownType unknow = new UnknownType();
            unknow.setName("teacher1");
            unknow.setId(10 + i);
            unknow.setPhoneSize(3);
            unknow.setPhones(new long[unknow.getPhoneSize()]);
            unknow.setAttr("Asdfasdf");
            unknow.setBrithday(new Date());
            unknow.setClazz(Date.class);
            unknow.age = i + 20;
            unknow.remark = new ArrayList<Date>() {{
                this.add(new Date());
                this.add(new Date());
            }};
            unknow.ttl = 12000 + i;
            unknow.opt = new Class[]{Date.class, File.class};
            unknow.haha = 13000 + i;



            for (int j = 0; j < unknow.getPhoneSize(); j++) {
                unknow.getPhones()[j] = 14000 + j;
            }
            teachers.add(unknow);
        }
        return teachers;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Date> getRemark() {
        return remark;
    }

    public void setRemark(List<Date> remark) {
        this.remark = remark;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public Class<?>[] getOpt() {
        return opt;
    }

    public void setOpt(Class<?>[] opt) {
        this.opt = opt;
    }

    public long getHaha() {
        return haha;
    }

    public void setHaha(long haha) {
        this.haha = haha;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
    }



    public int getPhoneSize() {
        return phoneSize;
    }

    public void setPhoneSize(int phoneSize) {
        this.phoneSize = phoneSize;
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

    public long[] getPhones() {
        return phones;
    }

    public void setPhones(long[] phones) {
        this.phones = phones;
    }
}
