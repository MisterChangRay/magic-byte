package com.github.misterchangray.core.clazzparser;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazzparser.po.Book;
import com.github.misterchangray.core.clazzparser.po.Classes;
import com.github.misterchangray.core.clazzparser.po.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClazzParserTest {

    @Test
    public void testClassMetainfoParser() {

        Book book = new Book();
        book.setId(111);
        book.setName("book1");

        Student student = new Student();
        student.setId(11);
        student.setName("stu11");
        List<Book> objects = new ArrayList<>();
        objects.add(book);
        student.setBooks(objects);

        Classes classes = new Classes();
        classes.setId(1);
        classes.setName("clz1");
        classes.setStudent(new Student[]{student});

        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(classes.getClass());

        Assert.assertEquals(classMetaInfo.getFlatFields().size(), 23);
        Assert.assertEquals(classMetaInfo.getFields().size(), 4);
        Assert.assertNull(classMetaInfo.getParent());

        checkAccessPath(classMetaInfo, "");

        Assert.assertEquals(classMetaInfo.getFlatFields().stream().filter(item -> item.getFieldType() == 1).count(), 16);
        Assert.assertEquals(classMetaInfo.getFlatFields().stream().filter(item -> item.getFieldType() == 2).count(), 7);
        byte[] bytes = MagicByte.unpackToByte(classes);
        Assert.assertNotNull(bytes);



    }

    /**
     * 检查属性访问路径是否正确
     * @param classMetaInfo
     * @param s
     */
    private void checkAccessPath(ClassMetaInfo classMetaInfo, String s) {
        if(s.length() > 0) {
            s += ".";
        }
        if(classMetaInfo.getFields() != null) {
            for (FieldMetaInfo field : classMetaInfo.getFields()) {
                String tmp = s + field.getField().getName();
                Assert.assertEquals(field.getAccessPath(), tmp);
                checkAccessPath(field.getClazzMetaInfo(), tmp);
            }
        }

    }


}
