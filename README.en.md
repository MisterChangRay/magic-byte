### Magic-Byte
[![GitHub (pre-)release](https://img.shields.io/github/release/misterchangray/magic-byte/all.svg)](https://github.com/misterchangray/magic-byte) 
[![GitHub issues](https://img.shields.io/github/issues/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues) 
[![GitHub closed issues](https://img.shields.io/github/issues-closed/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues?q=is%3Aissue+is%3Aclosed) 
[![GitHub](https://img.shields.io/github/license/misterchangray/magic-byte.svg)](./LICENSE)

[中文](https://github.com/MisterChangRay/magic-byte/blob/master/README.md)|[English](https://github.com/MisterChangRay/magic-byte/blob/master/README.en.md)

#### 1. Introduction
In the contemporary IoT industry, due to privacy and security issues, many companies choose to use custom private binary protocols.
In the C language, thanks to the support of structures, it is very simple to convert objects and byte arrays; but in Java, without native support, developers can only rely on code power to read and parse the data and then translated into objects
.

This seemingly simple encoding/decoding process is actually accompanied by many headaches, such as:
- Handling of big and small endian/network byte order
- Processing of unsigned/signed numbers
- Multibyte integer conversion processing
- Conversion processing between ASCII code and bytes
- Handling of null pointers/padding data
- Handling of array objects/nested objects

The purpose of this project is to solve the above problems as much as possible so that everyone can focus more time on business.
After introducing `MagicByte`, you only need to use annotations to declare the data type of the field while defining the class.
Next, you only need to call two simple methods to serialize: `MagicByte.unpack();` for converting objects to bytes and `MagicByte.pack()` for converting bytes to objects.
Try it now!


#### 2. Quick Start:
1. Introduce Jar package;
2. `@MagicClass` performs global configuration on the current class
2. `@MagicField` annotates the JAVA object attributes that need to be converted, and supports object combination nesting. Note: inheritance is not supported.
3. Use `MagicByte.registerCMD` to register messages to MagicByte
4. Use `MagicByte.pack()` or `MagicByte.unpack()` to quickly serialize or deserialize data or objects
5. Supports the use of `@MagicConverter()` annotation to implement custom serialization; [Go to see the enumeration class custom serialization example](https://github.com/MisterChangRay/magic-byte/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BA%8F%E5%88%97%E5%8C%96%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)

Maven projects can be imported directly:
[Click to view the version list](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
   <groupId>io.github.misterchangray</groupId>
   <artifactId>magic-byte</artifactId>
   <version>2.4.1</version>
</dependency>
```

#### 3. Code example
The following is a simple framework function display. It is recommended to refer to [Best Practices for Data Entity Definition](https://github.com/MisterChangRay/magic-byte/wiki/%E6%95%B0%E6%8D%AE%E5%AE%9E%E4%BD%93%E5%AE%9A%E4%B9%89%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)

In the following packet example, there are two data packets, Student and School:
```java
// declare class must use public
// Use big endian mode, the default is big endian
@MagicClass(byteOrder = ByteOrder.BIG_ENDIAN)
public class School {
     // 10 byte, common data type, occupies 10 bytes in length
     @MagicField(order = 1, size = 10)
     private String name;
     // 2 byte, length field, the actual data length will be automatically filled during data serialization
     @MagicField(order = 3, calcLength = true)
     private short length;
     //Supports combination mode, Student object is embedded here
     //Total number of bytes = students.bytes * length
     @MagicField(order = 5, size = 2)
     private Student[] students;
     // 0 byte, note, this cannot be serialized, unsupported data types will be ignored
     @MagicField(order = 7)
     private List<Object> notSupport;
     // 0 byte, note, this cannot be serialized, unsupported data types will be ignored
     @MagicField(order = 9)
     private Object age;
     // 4 bytes, note that the second-level timestamp is specified here, and it is also specified to use 4 bytes to save. If not specified, the default is 6 bytes.
     @MagicField(order = 13, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
     private Date[] birthdays;
     // 1 byte, common data type, serialization order configured through order, serial number order has nothing to do with definition order
     @MagicField(order = 15)
     private byte age;
     // 1 byte, checksum field, if a calculation function is provided during serialization, it will be automatically filled in
     @MagicField(order = 17)
     private byte checkCode;

   
     // getter and setter ...
}

@MagicClass()
public class Student {
     // 10 byte, normal data, length is 10 bytes
     @MagicField(order = 1, size = 10)
     private String name;
     // 4 byte, ordinary data, integer, this field determines the subsequent phones field length
     @MagicField(order = 5)
     private int length;
     //Total number of bytes = phones.size * length
     //A single element is 8 bytes. This List does not directly specify the size. The size is determined by the length field. The data type of the length field can only be byte, short, int, UNumber.
     @MagicField(order = 10, dynamicSizeOf = "length")
     private List<Long> phones;
     // 1 byte
     @MagicField(order = 15)
     private byte age;
     //Birthday, here is the second-level timestamp, 4 bytes are used if specified, and the default is 6 bytes if the date type is not specified.
     @MagicField(order = 18, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
     private Date birthDay;
     // getter and setter ...
}

public class Hello {
     void main() {
         // Global configuration checksum calculation function
         MagicByte.configMagicChecker(Checker::customChecker);
         School school = new School();
         school.setAge((byte) 23);
    
         // you can set other properties
         // object to bytes
         // You can also pass in the calculation function separately
         byte[] bytes = MagicByte.unpack(school, Checker::customChecker);
         School school2 = MagicByte.pack(bytes, School.class); // bytes to object
         System.out.println(school.getAge() == school2.getAge()); // out put true
    
     }
}

public class Checker {
     /**
      * When serializing: data data contains all serialized data (including calcLength has also been called and serialized)
      * When deserializing: data data is a copy of the incoming data
      * @param data
      * @return
      */
     public static byte[] customChecker(byte[] data) {
         return new byte[]{0xff};
     }
}

```

#### 4. Annotations and attribute descriptions
There are three annotations for the tool:
1. `@MagicClass()` class annotation; mainly used for global configuration of data
- byteOrder configures serialization big and small ends, which can be configured globally
- strict mode, default false, strict mode will throw more exceptions
2. `@MagicField()` attribute annotation, unannotated attributes do not participate in the serialization/deserialization process
- order defines the serialization order of object attributes<b>(Important, please do not modify it after it is put into use. It starts from 1 and increments. It is recommended to jump to the configuration such as: 1, 3, 5...)</b>
- size attribute size, only UNumber&String&List need to be set, String/UNumber represents the byte length. List and Array represent the number of members
    - cmdField marks this field as a message type. This configuration is used in conjunction with message registration. The default is false; [Message registration reference](https://github.com/MisterChangRay/magic-byte/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BA%8F%E5%88%97%E5%8C%96%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)
- charset character set setting, which can be configured globally, only the `String` setting is valid; the default is ASCII
- dynamicSize marks fields with dynamic length. The entire message can only be marked once and only `String&List&Array` type fields can be marked; [Click to view details](https://github.com/MisterChangRay/magic-byte/wiki/dynamicSize-%E5%B1%9E%E6%80%A7%E8%AF%A6%E8%A7%A3)
- dynamicSizeOf Gets the length of `List or Array` from the specified attribute. Only `List, Array, String` is valid; the reference field type can only be `byte, short, int, UNumber`
    - calcLength marks the field as a length field, and the total length of the data (number of bytes) will be automatically filled into this field during deserialization; may throw: InvalidLengthException
    - calcCheckCode marks the field as a checksum field, which will be verified or automatically filled during serialization or deserialization; may throw: InvalidCheckCodeException
    - timestampFormat can specify the time format, timestamp or text. The timestamp can be specified as milliseconds, seconds, minutes, hours, days; the date type defaults to 6 bytes of storage space, which can be adjusted using size; for example, the second-level timestamp has 4 words. section is enough to store and transmit
    - formatPattern is used for date fields. If serialization is specified as a string, the serialization format is configured here. Default is:yyyyMMddHHmmss
3. `@MagicConverter()` configures custom serialization. For more instructions, please refer to [Custom Serialization Best Practices](https://github.com/MisterChangRay/magic-byte/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BA%8F%E5%88%97%E5%8C%96%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)
    - converter, serialization class, this class must be a subclass of `MConverter`
    - attachParams, additional parameters; will be passed in during serialization
    - fixSize, fixed data byte length, you can specify the length of custom data uniformly, or you can ignore it and return the actual data length during serialization

#### 5. Supported data types and byte sizes;
| Data type | Data type | Extended type (unsigned number) | Byte size |
|--------|--------|--------|----------------|
|byte|boolean|UByte| 1 |
|short|char|UShort| 2 |
|int|float|UInt| 4 |
|long|double|ULong| 8 |
|Date,Instant,DateTime|LocalTime,LocalDate,LocalDateTime| | 6 (with size modification) |
|String| | UNumber | custom (specified with size) |



#### 6. Precautions
1. <b>Because the `order` attribute is the order of object serialization, please do not modify the `order` attribute at will for fields that have been put into use (important). This may affect existing business; please add new fields. Incrementally use new `order` value
2. Big endian and little endian are configured using `@MagicClass`
3. Basic data types use the default byte length in the following table. `String/List/Array/UNumber` needs to use the `size` attribute to specify the member length or string byte length.
4. Please use basic types to define the message structure. Currently, only the following data types are supported:
1. Four categories and eight basic types (byte/char/short/int/long/float/double/boolean)
2. Unsigned packaging type (UByte/UShort/Uint/ULong/UNumber) [About unsigned number type](https://github.com/MisterChangRay/magic-byte/wiki/%E5%85%B3%E4%BA%8E%E6%97%A0%E7%AC%A6%E5%8F%B7%E6%95%B0%E5%8C%85%E8%A3%85%E7%B1%BB%E5%9E%8B)
3. Supports String, but Size must be declared
4. Supports List & Array, generics can be used; only supports one-dimensional arrays and cannot use variable data types, such as: `List<String>` or `String[]` or `List<UNumber>`
5. When the data overflows, the tool will automatically trim the data. If the string or array length is declared as 5, only the first 5 elements of the collection will be serialized.
6. Strings use ASCII encoding by default and can be configured locally or modified globally.
7. List or Array with more than one dimension is not supported
8. boolean value 0=false/non-0=true
9. All class definitions must be public, private inner classes are not supported; `public static class XXX {}` is supported
10. Serialization and deserialization of class inheritance are not supported; nested combination of classes is supported.
11. Serialize null values. If it is a wrapped data type, use the original type default value; such as `Short a = null;` serialized as `0`; other data types will be filled directly, such as arrays, objects, etc.
12. For more questions, please go to the WIKI page >> [WIKI_HOME](https://github.com/MisterChangRay/magic-byte/wiki)
13. The built-in enumeration class serialization converter `SimpleEnumConverter`, the principle is to serialize/deserialize according to the enumeration definition order. Therefore, please do not change the definition order after use. It is recommended to implement it yourself

#### 7. Development suggestions

1. It is not recommended to transmit floating point numbers over the network;
2. It is not recommended to transmit signed numbers, that is, negative numbers, over the network.
3. It is not recommended to transmit strings over the network
4. It is not recommended to declare a large number of strings in a class
5. If conditions permit, it is recommended to use `protobuf` as the serialization framework.
7. Binary protocol recommends that the single packet size be within 100KB
8. It is recommended to jump to the configuration of the `order` attribute (such as 1,3,5,7), <b>and the order of fields that have been put into production must not be modified at will; when adding new fields, the new value of order must be used after increment; Important Emphasized 3 times</b>of object attributes<b>(Important, please do not modify it after it is put into use, it will increase from 1, it is recommended to jump to the configuration