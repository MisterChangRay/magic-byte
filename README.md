### 魔法字节(magic-byte)
[![GitHub (pre-)release](https://img.shields.io/github/release/misterchangray/magic-byte/all.svg)](https://github.com/misterchangray/magic-byte) 
[![GitHub issues](https://img.shields.io/github/issues/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues) 
[![GitHub closed issues](https://img.shields.io/github/issues-closed/misterchangray/magic-byte.svg)](https://github.com/misterchangray/magic-byte/issues?q=is%3Aissue+is%3Aclosed) 
[![GitHub](https://img.shields.io/github/license/misterchangray/magic-byte.svg)](./LICENSE)

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
[查询最新版本](https://mvnrepository.com/artifact/io.github.misterchangray/magic-byte)
```
<dependency>
  <groupId>io.github.misterchangray</groupId>
  <artifactId>magic-byte</artifactId>
  <version>2.1.0</version>
</dependency>
```

#### 2. 快速入门:
1. 引入Jar包;
2. `@MagicClass`对当前类进行全局配置
2. `@MagicField`对需要转换的JAVA对象属性进行标注,支持对象组合嵌套
3. 使用`MagicByte.pack()`或则`MagicByte.unpack()`对数据或对象进行快速的序列化或反序列化

#### 3. 代码示例
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
    // 0 byte, 注意, 此处无法序列化, 不支持的数据类型将会被忽略
    @MagicField(order = 13)
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


#### 4. 注解和属性说明
工具存在三个注解:
1. `@MagicClass()` 类注解; 主要用于数据全局配置
	- byteOrder 配置序列化大小端
	- strict 严格模式, 默认false, 严格模式将会抛出更多的异常
2. `@MagicField()` 属性注解, 未注解的属性不参与序列化/反序列化过程
	- order 定义序列化顺序<b>(重要, 投入使用后请勿修改, 从1开始依次递增)</b>
	- size 属性大小, 仅String和List需要设置, String 代表字节长度, List和Array代表成员长度
	- charset 字符集设置, 仅`String`设置有效; 默认ASCII
	- dynamicSize 标记字段为动态长度, 整个数据结构只能标记一次且仅能标记`String&List&Array`类型字段
	- dynamicSizeOf 从指定的 order 中获取`List或Array`的长度, 仅`List,Array,String`有效；引用字段类型只能为`byte, short, int`
    - calcLength 标记字段为长度字段, 反序列化时将自动将长度填充到此字段; 可能抛出: InvalidLengthException
    - calcCheckCode 标记字段为校验和字段, 序列化或反序列化时将会校验或自动填充; 可能抛出: InvalidCheckCodeException
    - timestampFormat 指定日期时间戳格式,可指定为毫秒,秒,分钟,小时,天;日期类型默认6字节储存空间，可配置size进行调整;如秒其实4个字节就够了
3. `@MagicConverter()`配置自定义序列化
    - converter, 序列化类, 该类必须为`MConverter`的子类
    - attachParams, 附加参数;序列化时将会传入
    - fixSize, 固定数据长度, 可以统一指定自定义数据长度;可忽略并在实际序列化后返回
    
#### 5. 支持的数据类型及字节大小;
| 数据类型 |数据类型 |字节大小|
|--------|--------|--------|
|byte|boolean|1|
|short|char|2|
|int|float|4|
|long|double|8|
|Date,Instant,DateTime|LocalTime,LocalDate,LocalDateTime|6|
|String| |custom|


#### 6. 注意事项
1. 本工具仅用于对象和数据的转换, 报文的效验需在转换前进行; 一般来说有报文头效验和效验和效验
2. <b>已投入使用的字段请不要修改`order`属性(重要),会影响已有业务;新增字段请递增使用下一个`order`值</b>
3. 大端小端使用`@MagicClass`进行配置
4. 基本数据类型使用下表默认字节长度, `String/List/Array` 需要使用`size`属性指定成员长度或字符串字节长度
5. 请使用基础类型定义报文结构,目前仅支持以下数据类型:
	1. 四类八种基础类型(byte/char/short/int/long/float/double/boolean)
	2. 支持 String, 但必须申明 Size
	3. 支持 List & Array, 可以使用泛型; 仅支持一维数组且不能使用`List<String>`或`String[]`
6. 数据溢出时工具会自动对数据进行裁剪,如字符串或数组长度声明为5, 将序列化集合前5个元素
7. 字符串默认使用ASCII编码
8. 不支持一维以上的List或者Array
9. boolean值 0=false/非0=true
10. 所有类的定义必须为 public, 不支持私有内部类; 支持 `public static class XXX {}`
11. 不支持类继承的序列化和反序列化;支持类的嵌套组合使用
12. 序列化null值,如果是包装数据类型,则使用原始类型默认值;如`Short a = null;` 序列化为 `0`; 其他数据类型将会直接填充,如数组，对象等。
13. 更多问题请前往WIKI页面查看 >> [WIKI_HOME](https://github.com/MisterChangRay/magic-byte/wiki)

#### 7. 开发建议

1. 不建议网络传输浮点数;
2. 不建议网络传输有符号数即负数
3. 不建议网络传输字符串
4. 不建议类中大量声明字符串
5. 条件允许建议使用`protobuf`作为序列化框架
7. 二进制协议建议单包大小在1KB以内
8. `order`属性建议跳跃配置(如1,3,5,7), <b>且对于已经投入使用的一定不要修改; 新增字段时必须使用新的(递增后)order值; 重要的事强调3次</b>

#### 8. 最后
本人目前也是做物联网相关领域的(共享充电宝), 开始的时候也是走了不少弯路,这个项目算是在这工作了1年多的工作总结;
写完之后才发现谷歌早在13年前就开源了类似框架, 真是学习使人进步; 又重复造轮子了。
附上谷歌的链接:

- [JavaStract](http://code.google.com/p/javastruct/wiki/HowToUseJavaStruct)
- [Javolution](http://javolution.org/)


#### 9.欢迎大佬入驻交流: 

QQ群  562371124


