package com.github.misterchangray.core.clazzparser;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.clazz.ClassManager;
import com.github.misterchangray.core.clazz.ClassMetaInfo;
import com.github.misterchangray.core.clazzparser.po.Book;
import com.github.misterchangray.core.clazzparser.po.Classes;
import com.github.misterchangray.core.clazzparser.po.Student;
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

        byte[] bytes = MagicByte.unpackToByte(classes);
        System.out.println(111);


    }

}
