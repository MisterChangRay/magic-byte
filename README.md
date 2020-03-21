### 魔术字节(magic-byte)
[toc]

##### 1. 简介
本工具解决在自定义二进制协议中二进制和javaBean相互转换的问题;
在当代物联网行业中, 更多的公司选择使用自定义的二进制协议; JAVA同学在对这些协议进行解析时，会陷入序列化/反序列化的痛苦循环中；所以诞生了此项目。
本项目解决了自定义二进制协议的以下痛点：
1. 二进制协议解析
2. 效验计算
3. 位运算难
4. JAVA对象转换
5. 无符号运算操作

maven项目可直接导入:
```
wait
```


##### 2. 快速入门:
1. 引入Jar包;
2. `@MagicClass`对当前类进行全局配置
2. `@MagicField`对需要转换的JAVA对象属性进行标注,支持对象嵌套
3. 使用`MagicByte.pack()`或则`MagicByte.unpack()`对数据或对象进行快速的序列化或反序列化


##### 4. 注意事项
1. 已投入使用的字段请不要随意修改`order`属性(重要); `order`属性时序列化时的字节顺序
1. 大端小端/默认字符集使用`@MagicClass`进行配置
2. 基本数据类型使用下表默认字节长度, `String/List/Array` 需要使用`size`属性指定成员长度或字符串字节长度
3. 请使用基础类型定义报文结构,目前仅对以下数据类型支持:
	1.四类八种基础类型(byte/char/short/int/long/float/double/boolean)
	2.String, 字符串的支持
	3.List和Array的支持, 可以使用泛型; 但不能使用`List<String>`或则`String[]`
4. 数据溢出时工具会自动对数据进行裁剪,如字符串长度申明为5字节, 传递7字节时将只传递前5字节
5. 字符串默认使用ASCII编码
6. 不支持List嵌套或二维数组
7. boolean值 0=false/非0=true
8. 所有类的定义必须为 public , 不支持内部类

##### 5. 开发建议

1. 不建议网络传输浮点数;
2. 不建议网络传输有符号数即负数
3. 不建议网络传输字符串
4. 不建议类中大量声明字符串
5. 建议使用`protobuf`作为通讯,储存方案
6. 集合建议使用数组(Array)而非列表(List), List可能存在数据溢出情况而被裁剪
7. 二进制协议不建议单包超过1KB

##### 6. 默认数据类型字节大小;
| 数据类型 |数据类型 | 数据类型 |字节大小|
|--------|--------|--------|--------|
|byte|boolean|char|1
|||short|2|
||int|float|4|
||long|double|8|
|||String|custom|

##### 5. 使用示例
下面的对象中, Student 总共分配 27 个字节; School 总共分配 67 个字节
```java
// declare class must use public
@MagicClass(enableAutoTrim = true)
public class School {
    @MagicField(order = 1, size = 10)
	private String name;
    @MagicField(order = 2, size = 2)
    private Student[] students;
    @MagicField(order = 3)
    private byte age;
    // getter and setter ...
}

@MagicClass(enableAutoTrim = true)
public class Student {
    @MagicField(order = 1, size = 10)
	private String name;
    @MagicField(order = 2, size = 2)
    private List<Long> phones;
    @MagicField(order = 3)
    private byte age;
    // getter and setter ...
}

void main() {
	School school = new School();
    school.setAge((byte) 23);
    // you can set other propertis
	byte[] bytes = Magic.unpack(school); // object to bytes
    School school2 = Magic.pack(bytes, School.class); // bytes to object
    System.out.println(school.getAge() == school2.getAge()); // out put true

}

```

##### 6. 最后
本人目前也是做物联网相关领域的(共享充电宝), 开始的时候也是走了不少弯路,这个项目算是在这工作了1年多的工作总结;
写完之后才发现谷歌早在13年前就开源了类似框架, 真是学习使人进步; 又重复造轮子了。
附上谷歌的链接:

- [JavaStract](http://code.google.com/p/javastruct/wiki/HowToUseJavaStruct)
- [Javolution](http://javolution.org/)



