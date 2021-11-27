### 魔法字节(magic-byte)
[toc]

#### 1. 简介
本工具解决在自定义二进制协议中二进制和javaBean相互转换的问题;
在当代物联网行业中, 更多的公司选择使用自定义的二进制协议; JAVA同学在对这些协议进行解析时，会陷入序列化/反序列化的痛苦循环中；所以诞生了此项目。
本项目解决了自定义二进制协议的以下痛点：
1. 二进制协议解析
2. 效验计算
3. 位运算难
4. JAVA对象转换
5. 无符号运算操作

maven项目可直接导入:
[点击查询最新版本](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
  <groupId>io.github.misterchangray</groupId>
  <artifactId>magic-byte</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### 2. 快速入门:
1. 引入Jar包;
2. `@MagicClass`对当前类进行全局配置
2. `@MagicField`对需要转换的JAVA对象属性进行标注,支持对象嵌套
3. 使用`MagicByte.pack()`或则`MagicByte.unpack()`对数据或对象进行快速的序列化或反序列化

#### 3. 代码示例
下面的对象中, Student 总共分配 27 个字节; School 总共分配 67 个字节
```java
// declare class must use public
@MagicClass(autoTrim = true)
public class School {
    // 普通数据类型, 字符串长度为 10 byte 
    @MagicField(order = 1, size = 10)
    private String name;
    // 支持组合模式, 这里嵌入了 Student 对象
    @MagicField(order = 3, size = 2)
    private Student[] students;
    // 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 5)
    private byte List<Object>
    // 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 6)
    private Object age;
    // 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 7)
    private Date[] birthdays;
    // 普通数据类型, 通过order配置序列话顺序, 序列号顺序和定义顺序无关
    @MagicField(order = 2)
    private byte age;
   
    // getter and setter ...
}

@MagicClass(autoTrim = true)
public class Student {
    // 普通数据, 字符串长度为 10
    @MagicField(order = 1, size = 10)
    private String name;
    // 普通数据, 整数, 此字段决定后续 phones 字段长度
    @MagicField(order = 2)
    private int length;
    // 此List并未直接指定大小, 大小由 length 字段决定
    @MagicField(order = 3, dynamicSizeFromOrder = 2)
    private List<Long> phones;
    @MagicField(order = 4)
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


#### 4. 注解和属性说明
工具存在两个注解:
1. `@MagicClass()` 类注解; 主要用于数据全局配置
	- byteOrder 配置序列化大小端
	- autoTrim 自动裁剪, 对于字符串和`List`, 如果数据全为`0x00`或`0xff`将会被裁剪现象
2. `@MagicField()` 属性注解, 未注解的属性不参与序列化/反序列化过程
	- order 定义序列化顺序<b>(重要, 投入使用后请勿修改, 从1开始依次递增)</b>
	- size 属性大小, 仅String和List需要设置, String 代表字节长度, List和Array代表成员长度
	- charset 字符集设置, 仅`String`设置有效; 默认ASCII
	- dynamicSizeFromOrder 从指定的 order 中获取`List或Array`的长度, 仅`List和Array`有效



#### 5. 支持的数据类型及字节大小;
| 数据类型 |数据类型 |字节大小|
|--------|--------|--------|
|byte|boolean|1|
|short|char|2|
|int|float|4|
|long|double|8|
|String| |custom|


#### 6. 注意事项
1. 本工具仅用于对象和数据的转换, 报文的效验需在转换前进行; 一般来说有报文头效验和效验和效验
2. <b>已投入使用的字段请不要修改`order`属性(重要),会影响已有业务;新增字段请递增使用下一个`order`值</b>
3. 大端小端/默认字符集使用`@MagicClass`进行配置
4. 基本数据类型使用下表默认字节长度, `String/List/Array` 需要使用`size`属性指定成员长度或字符串字节长度
5. 请使用基础类型定义报文结构,目前仅对以下数据类型支持:
	1. 四类八种基础类型(byte/char/short/int/long/float/double/boolean)
	2. String, 字符串的支持
	3. List和Array的支持, 可以使用泛型; 但不能使用`List<String>`或则`String[]`
6. 数据溢出时工具会自动对数据进行裁剪,如字符串或数组长度声明为5, 则只会序列化5个元素, 多余的将会被删除
7. 字符串默认使用ASCII编码
8. 不支持List嵌套或二维数组
9. boolean值 0=false/非0=true
10. 所有类的定义必须为 public, 不支持内部类
11. 实体类不支持继承的序列化和反序列化;支持嵌套或组合使用

#### 7. 开发建议

1. 不建议网络传输浮点数;
2. 不建议网络传输有符号数即负数
3. 不建议网络传输字符串
4. 不建议类中大量声明字符串
5. 建议使用`protobuf`作为通讯,储存方案
6. 集合建议使用数组(Array)而非列表(List), List可能存在数据溢出情况而被裁剪
7. 二进制协议不建议单包超过1KB
8. <b>对于属性的`order`配置, 已经投入使用的一定不要修改; 新增字段时必须使用新的(递增后)order值; 重要的事强调3次</b>

#### 8. 最后
本人目前也是做物联网相关领域的(共享充电宝), 开始的时候也是走了不少弯路,这个项目算是在这工作了1年多的工作总结;
写完之后才发现谷歌早在13年前就开源了类似框架, 真是学习使人进步; 又重复造轮子了。
附上谷歌的链接:

- [JavaStract](http://code.google.com/p/javastruct/wiki/HowToUseJavaStruct)
- [Javolution](http://javolution.org/)



