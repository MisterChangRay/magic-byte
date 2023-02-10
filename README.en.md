### Magic-Byte
[![GitHub (pre-)release](https://img.shields.io/github/release/misterchangray/magic-byte/all.svg)](https://github.com/misterchangray/magic-byte) 
[![GitHub issues](https://img.shields.io/github/issues/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues) 
[![GitHub closed issues](https://img.shields.io/github/issues-closed/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues?q=is%3Aissue+is%3Aclosed) 
[![GitHub](https://img.shields.io/github/license/misterchangray/magic-byte.svg)](./LICENSE)

#### 1. What does it do
this tool can coverter 

Maven Project :
[Click to Lasted Version](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
  <groupId>io.github.misterchangray</groupId>
  <artifactId>magic-byte</artifactId>
  <version>2.1.0</version>
</dependency>
```

#### 2. Quickly Start:
1. Import jar file;
2. Use `@MagicClass` mark entity 
2. Use `@MagicField` mark field
3. Use `MagicByte.pack()` or `MagicByte.unpack()` to Serializable or UnSerializable

#### 3. Example
 in this case, have 2 Object. School, Student
```java
// declare class must use public
@MagicClass(byteOrder = ByteOrder.BIG_ENDIAN)
public class School {
    // 10 byte
    @MagicField(order = 1, size = 10)
    private String name;
    // 2 byte, length of data; calcLength auto calc Length and write
    @MagicField(order = 3, calcLength = true)
    private short length;
    // in this case, references Student object 
    // total bytes = students.bytes * length
    @MagicField(order = 5, size = 2)
    private Student[] students;
    // 0 byte, note: not support
    @MagicField(order = 7)
    private List<Object> notSupport;
    // 0 byte, note: not support
    @MagicField(order = 9)
    private Object age;
    // 0 byte, note: not support
    @MagicField(order = 13)
    private Date[] birthdays;
    // 1 byte
    @MagicField(order = 15)
    private byte age;
    // 1 byte, check code of data, automatic calc checkCode and write to result if the calcCheckCode=true
    @MagicField(order = 17, calcCheckCode = true)
    private byte checkCode;

   
    // getter and setter ...
}

@MagicClass()
public class Student {
    // 10 byte, in string, size = bytes
    @MagicField(order = 1, size = 10)
    private String name;
    // 4 byte
    @MagicField(order = 5)
    private int length;
    // totilBytes = phones.size * length
    // 
    @MagicField(order = 10, dynamicSizeOf = 5)
    private List<Long> phones;
    // 1 byte
    @MagicField(order = 15)
    private byte age;
    // getter and setter ...
}

public class Hello {
    void main() {
        // global configuration check code function
        MagicByte.configMagicChecker(Checker::customChecker);
        School school = new School();
        school.setAge((byte) 23);
    
        // you can set other propertis
        // object to bytes
        // spicher other checkCode function
        byte[] bytes = Magic.unpack(school, Checker::customChecker); 
        School school2 = Magic.pack(bytes, School.class); // bytes to object
        System.out.println(school.getAge() == school2.getAge()); // out put true
    
    }
}

public class Checker {
    /**
     * 序列化时： data数据中包含所有已序列化的数据(包括 calcLength 也已经调用并序列化)
     * 反序列化时: data数据为传入数据的副本
     * @param data
     * @return
     */
    public static long customChecker(byte[] data) {
        return 0xff;
    }
}

```