### 魔法字节(magic-byte)
[![GitHub (pre-)release](https://img.shields.io/github/release/misterchangray/magic-byte/all.svg)](https://github.com/misterchangray/magic-byte) 
[![GitHub issues](https://img.shields.io/github/issues/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues) 
[![GitHub closed issues](https://img.shields.io/github/issues-closed/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues?q=is%3Aissue+is%3Aclosed) 
[![GitHub](https://img.shields.io/github/license/misterchangray/magic-byte.svg)](./LICENSE)

[中文](https://github.com/MisterChangRay/magic-byte/blob/master/README.md)|[English](https://github.com/MisterChangRay/magic-byte/blob/master/README.en.md)

#### 1. 简介
在当代物联网行业中，由于隐私和安全问题，很多的公司选择使用自定义的私有二进制协议。
在C语言中，由于有结构体的加持，对象和字节数组转换起来就特别简单；但在java中，在没有原生支持的情况下，开发人员就只能够靠码力去读取并解析数据然后转译成为对象
，流程如下图：
 ![](https://github.com/MisterChangRay/magic-byte/blob/master/introduce.png?raw=true)

在这看似简单的转译过程中其实会伴随很多人头疼的问题，例如：
- 大小端/网络字节序的处理
- 无符号数/有符号数的处理
- 多字节整数转换处理
- ASCII码与字节之间的转换处理
- 空指针/填充数据的处理
- 数组对象/嵌套对象的处理

所以此项目项目来了，此项目将尽可能的解决上述问题，在`MagicByte`中，你可以在类的定义时便通过注解申明好这复杂的序列化流程。
并且序列化也只需要简单的调用两个方法，用于对象转字节的`MagicByte.unpack();`和用于字节转对象的`MagicByte.pack()`。
是不是很简单？马上试试吧！


#### 2. 快速入门:
1. 引入Jar包;
2. `@MagicClass`对当前类进行全局配置
2. `@MagicField`对需要转换的JAVA对象属性进行标注,支持对象组合嵌套，注意：不支持继承
3. 正常情况下使用`MagicByte.pack()`或`MagicByte.unpack()`对数据或对象进行快速的序列化或反序列化
4. 支持使用`@MagicConverter()`注解来实现自定义序列化;[前往查看枚举类自定义序列化示例](https://github.com/MisterChangRay/magic-byte/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BA%8F%E5%88%97%E5%8C%96%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)

Maven项目可直接导入:
[点击查看版本列表](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
  <groupId>io.github.misterchangray</groupId>
  <artifactId>magic-byte</artifactId>
  <version>2.3.5</version>
</dependency>
```

#### 3. 代码示例
以下为简单的框架功能展示，实际项目中数据实体类定义建议参考 [数据实体定义的最佳实践](https://github.com/MisterChangRay/magic-byte/wiki/%E6%95%B0%E6%8D%AE%E5%AE%9E%E4%BD%93%E5%AE%9A%E4%B9%89%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)

下面的对象中, 共有 Student 和 School 两个对象
```java
// declare class must use public
// 使用大端模式， 默认为大端
@MagicClass(byteOrder = ByteOrder.BIG_ENDIAN)
public class School {
    // 10 byte, 普通数据类型
    @MagicField(order = 1, size = 10)
    private String name;
    // 2 byte, 长度字段, 数据序列化时将自动填充实际值
    @MagicField(order = 3, calcLength = true)
    private short length;
    // 支持组合模式, 这里嵌入了 Student 对象
    // 总字节数 = students.bytes * length
    @MagicField(order = 5, size = 2)
    private Student[] students;
    // 0 byte, 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 7)
    private List<Object> notSupport;
    // 0 byte, 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 9)
    private Object age;
    // 4 byte, 注意, 此处指定为秒级时间戳, 同时指定使用4个字节保存， 未指定则默认6个字节
    @MagicField(order = 13, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
    private Date[] birthdays;
    // 1 byte, 普通数据类型, 通过order配置序列化顺序, 序列号顺序和定义顺序无关
    @MagicField(order = 15)
    private byte age;
    // 1 byte, 校验和字段, 序列化时将会自动填充
    @MagicField(order = 17)
    private byte checkCode;

   
    // getter and setter ...
}

@MagicClass()
public class Student {
    // 10 byte, 普通数据, 字符串长度为 10
    @MagicField(order = 1, size = 10)
    private String name;
    // 4 byte, 普通数据, 整数, 此字段决定后续 phones 字段长度
    @MagicField(order = 5)
    private int length;
    // 总字节数 = phones.size * length
    // 单个元素 8 byte, 此List并未直接指定大小, 大小由 length 字段决定. length字段数据类型只能为 byte, short, int
    @MagicField(order = 10, dynamicSizeOf = 5)
    private List<Long> phones;
    // 1 byte
    @MagicField(order = 15)
    private byte age;
    // 生日, 这里为秒级时间戳, 使用无符号整形, 指定使用4个字节, 未指定则默认6个字节
    @MagicField(order = 18, size = 4, timestampFormat = TimestampFormatter.TO_TIMESTAMP_SECONDS)
    private Date birthDay;
    // getter and setter ...
}

public class Hello {
    void main() {
        // 全局配置校验和计算函数
        MagicByte.configMagicChecker(Checker::customChecker);
        School school = new School();
        school.setAge((byte) 23);
    
        // you can set other propertis
        // object to bytes
        // 也可以单独传入计算函数
        byte[] bytes = MagicByte.unpack(school, Checker::customChecker); 
        School school2 = MagicByte.pack(bytes, School.class); // bytes to object
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


#### 4. 注解和属性说明
工具存在三个注解:
1. `@MagicClass()` 类注解; 主要用于数据全局配置
	- byteOrder 配置序列化大小端
	- strict 严格模式, 默认false, 严格模式将会抛出更多的异常
2. `@MagicField()` 属性注解, 未注解的属性不参与序列化/反序列化过程
	- order 定义对象属性的序列化顺序<b>(重要, 投入使用后请勿修改, 从1开始递增,建议跳跃配置如:1,3,5...)</b>
	- size 属性大小, 仅String和List需要设置, String 代表字节长度, List和Array代表成员长度
	- charset 字符集设置, 仅`String`设置有效; 默认ASCII
	- dynamicSize 标记字段为动态长度, 整个数据结构只能标记一次且仅能标记`String&List&Array`类型字段
	- dynamicSizeOf 从指定的 order 中获取`List或Array`的长度, 仅`List,Array,String`有效；引用字段类型只能为`byte, short, int`
    - calcLength 标记字段为长度字段, 反序列化时将自动将数据总长度填充到此字段;  可能抛出: InvalidLengthException
    - calcCheckCode 标记字段为校验和字段, 序列化或反序列化时将会校验或自动填充; 可能抛出: InvalidCheckCodeException
    - timestampFormat 可指定时间格式,时间戳或者文本,时间戳可指定为毫秒,秒,分钟,小时,天;日期类型默认6字节储存空间，可使用size进行调整;如秒级时间戳4个字节就足够储存传输
    - formatPattern 日期字段使用,如指定序列化为字符串,这里配置序列化格式。默认为:yyyyMMddHHmmss
3. `@MagicConverter()`配置自定义序列化,更多说明参考 [自定义序列化最佳实践](https://github.com/MisterChangRay/magic-byte/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BA%8F%E5%88%97%E5%8C%96%E7%9A%84%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)
    - converter, 序列化类, 该类必须为`MConverter`的子类
    - attachParams, 附加参数;序列化时将会传入
    - fixSize, 固定数据字节长度, 可以统一指定自定义数据的长度,也可忽略然后在序列化时返回实际数据长度
    
#### 5. 支持的数据类型及字节大小;
| 数据类型 |数据类型 | 扩展类型(无符号数)  |字节大小|
|--------|--------|--------|--------|
|byte|boolean|UByte|1|
|short|char|UShort|2|
|int|float|UInt|4|
|long|double|ULong|8|
|String| | UNumber |custom|
|Date,Instant,DateTime|LocalTime,LocalDate,LocalDateTime| |6|


#### 6. 注意事项
1. 本工具仅用于对象和数据的转换, 报文的效验需在转换前进行; 一般来说有报文头效验和效验和效验
2. <b>因为`order`属性为对象序列化顺序,所以已投入使用的字段请不要随意修改`order`属性(重要)</b>,这样可能会影响已有业务;新增字段请递增使用新的`order`值
3. 大端小端使用`@MagicClass`进行配置
4. 基本数据类型使用下表默认字节长度, `String/List/Array` 需要使用`size`属性指定成员长度或字符串字节长度
5. 请使用基础类型定义报文结构,目前仅支持以下数据类型:
	1. 四类八种基础类型(byte/char/short/int/long/float/double/boolean)
	2. 无符号包装类型(UByte/UShort/Uint/ULong/UNumber) [关于无符号数类型](https://github.com/MisterChangRay/magic-byte/wiki/%E5%85%B3%E4%BA%8E%E6%97%A0%E7%AC%A6%E5%8F%B7%E6%95%B0%E5%8C%85%E8%A3%85%E7%B1%BB%E5%9E%8B)
	3. 支持 String, 但必须申明 Size
	4. 支持 List & Array, 可以使用泛型; 仅支持一维数组且不能使用`List<String>`或`String[]`
6. 数据溢出时工具会自动对数据进行裁剪,如字符串或数组长度声明为5, 将序列化集合前5个元素
7. 字符串默认使用ASCII编码
8. 不支持一维以上的List或者Array
9. boolean值 0=false/非0=true
10. 所有类的定义必须为 public, 不支持私有内部类; 支持 `public static class XXX {}`
11. 不支持类继承的序列化和反序列化;支持类的嵌套组合使用
12. 序列化null值,如果是包装数据类型,则使用原始类型默认值;如`Short a = null;` 序列化为 `0`; 其他数据类型将会直接填充,如数组，对象等。
13. 更多问题请前往WIKI页面查看 >> [WIKI_HOME](https://github.com/MisterChangRay/magic-byte/wiki)
14. 内置枚举类序列化转换器`SimpleEnumConverter`,原理是根据枚举定义顺序进行序列化/反序列化.故使用后请不要更改定义顺序。

#### 7. 开发建议

1. 不建议网络传输浮点数;
2. 不建议网络传输有符号数即负数
3. 不建议网络传输字符串
4. 不建议类中大量声明字符串
5. 条件允许建议使用`protobuf`作为序列化框架
7. 二进制协议建议单包大小在1KB以内
8. `order`属性建议跳跃配置(如1,3,5,7), <b>且对于已经投入使用的一定不要修改; 新增字段时必须使用递增后的order新值; 重要的事强调3次</b>

#### 8. 最后
本人目前也是做物联网相关领域的(共享充电宝), 开始的时候也是走了不少弯路,这个项目算是在这工作了1年多的工作总结;
写完之后才发现谷歌早在13年前就开源了类似框架, 真是学习使人进步; 又重复造轮子了。
附上谷歌的链接:

- [JavaStract](http://code.google.com/p/javastruct/wiki/HowToUseJavaStruct)
- [Javolution](http://javolution.org/)


#### 9.欢迎大佬入驻交流: 

QQ群  562371124


