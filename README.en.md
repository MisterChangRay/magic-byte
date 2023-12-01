### Magic-Byte
[![GitHub (pre-)release](https://img.shields.io/github/release/misterchangray/magic-byte/all.svg)](https://github.com/misterchangray/magic-byte) 
[![GitHub issues](https://img.shields.io/github/issues/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues) 
[![GitHub closed issues](https://img.shields.io/github/issues-closed/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues?q=is%3Aissue+is%3Aclosed) 
[![GitHub](https://img.shields.io/github/license/misterchangray/magic-byte.svg)](./LICENSE)

[中文](https://github.com/MisterChangRay/magic-byte/blob/master/README.md)|[English](https://github.com/MisterChangRay/magic-byte/blob/master/README.en.md)


#### 1. What does it do
this a tool can converter Java Object to bytes, or bytes to Java Object. like c struct. 



Maven Project :
[Click to Lasted Version](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
  <groupId>io.github.misterchangray</groupId>
  <artifactId>magic-byte</artifactId>
  <version>2.3.6</version>
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
// declare class should be public
@MagicClass(byteOrder = ByteOrder.BIG_ENDIAN)
public class School {
    // 10 byte, default use ASCII encoding
    @MagicField(order = 1, size = 10)
    private String name;
    // 2 byte, this field will be fill actually all data size length
    @MagicField(order = 3, calcLength = true)
    private short length;
    // this field ref Student class
    // totalBytes = students.bytes * length
    @MagicField(order = 5, size = 2)
    private Student[] students;
    // 0 byte,  can't recognize this type, so it will be ignore
    @MagicField(order = 7)
    private List<Object> notSupport;
    // 0 byte, ignore
    @MagicField(order = 9)
    private Object age;
    // 4 byte, size = 4 means use 4 bytes, and timestamp will cast to seconds
    @MagicField(order = 13, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
    private Date[] birthdays;
    // 1 byte, define order not equals actually order 
    @MagicField(order = 15)
    private byte age;
    // 1 byte
    @MagicField(order = 17)
    private byte checkCode;

   
    // getter and setter ...
}

@MagicClass()
public class Student {
    // 10 byte 
    @MagicField(order = 1, size = 10)
    private String name;
    // 4 byte, 
    @MagicField(order = 5)
    private int length;
    // totalBytes = phones.size * length
    // unit 8 byte, the list size equals length value
    @MagicField(order = 10, dynamicSizeOf = "length")
    private List<Long> phones;
    // 1 byte
    @MagicField(order = 15)
    private byte age;
    // use 4 bytes, default 6 bytes
    @MagicField(order = 18, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
    private Date birthDay;
    // getter and setter ...
}

public class Hello {
    void main() {
        // config the data checker logic
        MagicByte.configMagicChecker(Checker::customChecker);
        School school = new School();
        school.setAge((byte) 23);
    
        // you can set other propertis
        // object to bytes
        // you also set checker when you call unpack method
        byte[] bytes = MagicByte.unpack(school, Checker::customChecker); 
        School school2 = MagicByte.pack(bytes, School.class); // bytes to object
        System.out.println(school.getAge() == school2.getAge()); // out put true
    
    }
}

public class Checker {
    /**
     * the data is all of the bytes data 
     * @param data
     * @return
     */
    public static long customChecker(byte[] data) {
        return 0xff;
    }
}

```

#### 4. annotations explain
1. `@MagicClass()` this annotation will used be on class, declare this class can parse by MagicByte
- byteOrder: the byte data endianness
2. `@MagicField()` used on field
- size: declare the bytes length, if used on array/list, that means size of list or array
- order: the field Serialized order,  the smaller it's will be first serialize
- dynamicSizeOf: take array or string size from another field, only used on array/list, the data size will get by other field  
3. `@MagicConverter()` used on field, if you want custom serialize some object
- converter, the converter must implements `MConverter`
- attachParams, the data will use by call converter methods

#### 5. default data type & byte size
| dataType |dataType |defaultSize|
|--------|--------|--------|
|byte|boolean|1|
|short|char|2|
|int|float|4|
|long|double|8|
|Date,Instant,DateTime|LocalTime,LocalDate,LocalDateTime|6|
|String| |custom|


#### 6. recommend
1. the order property decide the Object Field serialize order, so. don't change them
2. it's better use primitive type, like int, short, byte...
3. not support extends property, use combination replace extend
4. all of class must declare public
5. only support One-dimensional array/list
 