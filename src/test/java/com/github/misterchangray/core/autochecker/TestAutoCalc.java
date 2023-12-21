package com.github.misterchangray.core.autochecker;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.TestFunctional;
import com.github.misterchangray.core.autochecker.pojo.*;
import com.github.misterchangray.core.autochecker.pojo_error.ErrorTypeWithCheckCode;
import com.github.misterchangray.core.autochecker.pojo_error.OfficeWith2CalcLength;
import com.github.misterchangray.core.autochecker.pojo_error.OfficeWith2CheckCode;
import com.github.misterchangray.core.clazz.warpper.UByte;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.clazz.warpper.UShort;
import com.github.misterchangray.core.exception.InvalidCheckCodeException;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.exception.MagicByteException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * 测试
 *
 * calcLength & calcCheckCode
 */
public class TestAutoCalc {
    /**
     * 测试自动填充长度和校验和
     *
     * 两个字段使用 ubyte 的情况
     * @throws InterruptedException
     */
    @Test
    public void testCalcLengthAndCheckCodeWithUnsigend() throws InterruptedException {
        OfficeWithUnsigend office = new OfficeWithUnsigend();
        office.setHead(11);
        office.setNameLen(UByte.valueOf((short) 4));
        office.setName("xiu22an");
        office.setAddr("chen11u");

        office.setStafLen(UInt.valueOf(2));
        office.setStaffs(new ArrayList<>());
        for (int i = 1; i < 4; i++) {
            Staff staff = new Staff();
            staff.setAge(32 + i);
            staff.setName("staff" + i);
            staff.setPhone(18180380200L + i);
            office.getStaffs().add(staff);
        }

        MagicByte.configMagicChecker(TestFunctional::checker);
        ByteBuffer unpack = MagicByte.unpack(office);
        OfficeWithUnsigend office2 = null;
        try {
            office2  = MagicByte.pack(unpack.array(), OfficeWithUnsigend.class);

        } catch (MagicByteException magicByteException) {
            office2 = (OfficeWithUnsigend) magicByteException.getData();
        }

        Assert.assertEquals(office.getHead(), office2.getHead());
        Assert.assertEquals(office.getAddr(), office2.getAddr());
        Assert.assertEquals(office.getNameLen(), office2.getNameLen());
        Assert.assertEquals(office2.getName(), office.getName().substring(0, office.getNameLen().intValue()));

        Assert.assertEquals(office.getStafLen().intValue(), office2.getStaffs().size());
        for (int i = 0; i < office2.getStaffs().size(); i++) {
            Staff staff1 = office.getStaffs().get(i);
            Staff staff2 = office2.getStaffs().get(i);

            Assert.assertEquals(staff1.getAge(), staff2.getAge());
            Assert.assertEquals(staff1.getName(), staff2.getName());
            Assert.assertEquals(staff1.getPhone(), staff2.getPhone());
        }

        Assert.assertNotNull(office2.getLength());
        Assert.assertEquals(office2.getLength().intValue(), 73);

        Assert.assertNotNull(office2.getCheckCode());
        Assert.assertEquals(office2.getCheckCode(), UShort.valueOf(0x3341));

    }

    /**
     * 测试同一个类使用两个 checkcode 的情况
     * 此时将会抛出异常
     * @throws InterruptedException
     */
    @Test
    public void testOfficeWith2CalcCheckCode() throws InterruptedException {
        OfficeWith2CheckCode officeStrict = new OfficeWith2CheckCode();
        officeStrict.setHead(11);
        officeStrict.setAddr("chengdu");
        officeStrict.setName("xiudian");

        MagicByte.configMagicChecker(TestFunctional::checker);

        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(officeStrict, TestFunctional::checker2);
            OfficeWith2CheckCode officeStrict2 = MagicByte.pack(unpack.array(), OfficeWith2CheckCode.class, TestFunctional::checker2);
        });
    }

    /**
     * 测试当一个类中有2个 calclength 被标记时是否抛出异常
     * @throws InterruptedException
     */
    @Test
    public void testOfficeWith2CalcLength() throws InterruptedException {
        OfficeWith2CalcLength officeStrict = new OfficeWith2CalcLength();
        officeStrict.setHead(11);
        officeStrict.setAddr("chengdu");
        officeStrict.setName("xiudian");

        MagicByte.configMagicChecker(TestFunctional::checker);
        Assert.assertThrows(InvalidParameterException.class, () -> {
            ByteBuffer unpack = MagicByte.unpack(officeStrict, TestFunctional::checker2);
            OfficeWith2CalcLength officeStrict2 = MagicByte.pack(unpack.array(), OfficeWith2CalcLength.class, TestFunctional::checker2);
        });
    }

    /**
     * 测试无效长度是否会抛出异常
     */
    @Test
    public void testInvalidLengthException()  {
        OfficeStrict officeStrict = new OfficeStrict();
        officeStrict.setHead(11);
        officeStrict.setAddr("chengdu");
        officeStrict.setName("xiudian");

        MagicByte.configMagicChecker(TestFunctional::checker);
        ByteBuffer unpack = MagicByte.unpack(officeStrict, TestFunctional::checker2);
        unpack.putInt(4, 111);
        Assert.assertThrows(InvalidLengthException.class, () -> {
            OfficeStrict officeStrict2 = MagicByte.pack(unpack.array(), OfficeStrict.class, TestFunctional::checker2);
        });
    }

    /**
     * 测试无效的checkcode是否抛出异常
     * @throws InterruptedException
     */
    @Test
    public void testInvalidCheckCodeException() throws InterruptedException {
        OfficeStrict officeStrict = new OfficeStrict();
        officeStrict.setHead(11);
        officeStrict.setAddr("chengdu");
        officeStrict.setName("xiudian");

        MagicByte.configMagicChecker(TestFunctional::checker);

        ByteBuffer unpack = MagicByte.unpack(officeStrict, TestFunctional::checker2);

        Assert.assertThrows(InvalidCheckCodeException.class, () -> {
            OfficeStrict officeStrict2 = MagicByte.pack(unpack.array(), OfficeStrict.class);
        });
    }

    /**
     * 测试calclength 和 checkcode
     * 是否自动解析并填充
     * @throws InterruptedException
     */
    @Test
    public void testCalcLengthAndCheckCode() throws InterruptedException {
        Office office = new Office();
        office.setHead(11);
        office.setAddr("chengdu");
        office.setName("xiudian");

        office.setStaffs(new ArrayList<>());
        for (int i = 1; i < 4; i++) {
            Staff staff = new Staff();
            staff.setAge(30 + i);
            staff.setName("staff" + i);
            staff.setPhone(18380380200L + i);
            office.getStaffs().add(staff);
        }

        MagicByte.configMagicChecker(TestFunctional::checker);
        ByteBuffer unpack = MagicByte.unpack(office);
        Office office2 = MagicByte.pack(unpack.array(), Office.class);

        Assert.assertEquals(office.getHead(), office2.getHead());
        Assert.assertEquals(office.getAddr(), office2.getAddr());
        Assert.assertEquals(office.getName(), office2.getName());

        Assert.assertEquals(office.getStaffs().size(), office2.getStaffs().size());
        for (int i = 0; i < office.getStaffs().size(); i++) {
            Staff staff1 = office.getStaffs().get(i);
            Staff staff2 = office2.getStaffs().get(i);

            Assert.assertEquals(staff1.getAge(), staff2.getAge());
            Assert.assertEquals(staff1.getName(), staff2.getName());
            Assert.assertEquals(staff1.getPhone(), staff2.getPhone());
        }

        Assert.assertNotNull(office2.getCheckCode());
        Assert.assertEquals(office2.getCheckCode(), 0x33);
        Assert.assertNotNull(office2.getLength());
        Assert.assertEquals(office2.getLength(), 95);
    }

    /**
     * 测试错误的checkCode属性类型
     * @throws InterruptedException
     */
    @Test
    public void testErrorType1() throws InterruptedException {
        ErrorTypeWithCheckCode e1 = new ErrorTypeWithCheckCode();
        Assert.assertThrows( InvalidParameterException.class, () -> {
            byte[] bytes = MagicByte.unpackToByte(e1);
        });
    }


    /**
     * 测试校验和多个数据
     * @throws InterruptedException
     */
    @Test
    public void testBytesCheckCodes() throws InterruptedException {
        CheckCode4BytesWithListByte c1 = new CheckCode4BytesWithListByte();
        byte[] bytes = MagicByte.unpackToByte(c1, (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});
        Assert.assertEquals(bytes[bytes.length -1], 0x8);
        Assert.assertEquals(bytes[bytes.length -2], 0x6);
        Assert.assertEquals(bytes[bytes.length -3], 0x3);
        Assert.assertEquals(bytes[bytes.length -4], 0x1);

        CheckCode4BytesWithListByte pack = MagicByte.pack(bytes, CheckCode4BytesWithListByte.class, (data) -> new byte[]{0x1, 0x3, 0x6, 0x8, 0x13});


    }


    /**
     * 测试校验和多个数据
     * 使用 list<UByte>
     * @throws InterruptedException
     */
    @Test
    public void testBytesCheckCodes2() throws InterruptedException {
        CheckCode4BytesWithListUByte c1 = new CheckCode4BytesWithListUByte();
        byte[] bytes = MagicByte.unpackToByte(c1, (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});
        Assert.assertEquals(bytes[bytes.length -1], 0x8);
        Assert.assertEquals(bytes[bytes.length -2], 0x6);
        Assert.assertEquals(bytes[bytes.length -3], 0x3);
        Assert.assertEquals(bytes[bytes.length -4], 0x1);
        CheckCode4BytesWithListUByte pack = null;
        pack = MagicByte.pack(bytes, CheckCode4BytesWithListUByte.class,
                (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});

        Assert.assertEquals(pack.getCheckCodes().get(0), UByte.valueOf(0x1));
        Assert.assertEquals(pack.getCheckCodes().get(1), UByte.valueOf(0x3));
        Assert.assertEquals(pack.getCheckCodes().get(2), UByte.valueOf(0x6));
        Assert.assertEquals(pack.getCheckCodes().get(3), UByte.valueOf(0x8));
        Assert.assertEquals(pack.getLength(), bytes.length);

    }

    /**
     * 测试校验和多个数据
     * 使用 byte[]
     * @throws InterruptedException
     */
    @Test
    public void testBytesCheckCodes3() throws InterruptedException {
        CheckCode4BytesWithArrBytePrimite c1 = new CheckCode4BytesWithArrBytePrimite();
        byte[] bytes = MagicByte.unpackToByte(c1, (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});
        Assert.assertEquals(bytes[bytes.length -1], 0x8);
        Assert.assertEquals(bytes[bytes.length -2], 0x6);
        Assert.assertEquals(bytes[bytes.length -3], 0x3);
        Assert.assertEquals(bytes[bytes.length -4], 0x1);

        CheckCode4BytesWithArrBytePrimite pack = MagicByte.pack(bytes, CheckCode4BytesWithArrBytePrimite.class,
                (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});

        Assert.assertEquals(pack.getCheckCodes()[0], 1);
        Assert.assertEquals(pack.getCheckCodes()[1], 3);
        Assert.assertEquals(pack.getCheckCodes()[2], 6);
        Assert.assertEquals(pack.getCheckCodes()[3], 8);
        Assert.assertEquals(pack.getLength(), bytes.length);

        CheckCode4BytesWithArrBytePrimite pack2 = null;
        try {
            MagicByte.pack(bytes, CheckCode4BytesWithArrBytePrimite.class,
                    (data)-> new byte[]{0x1,0x1,0x6,0x8,0x13});
        } catch (MagicByteException ae) {
            pack2 = (CheckCode4BytesWithArrBytePrimite) ae.getData();
        }
        Assert.assertNotNull(pack2);
        Assert.assertNotNull(pack2.getCheckCodes());
        Assert.assertEquals(pack2.getCheckCodes()[3], 8);

    }

    /**
     * 测试校验和多个数据
     * 使用 Byte[]
     * @throws InterruptedException
     */
    @Test
    public void testBytesCheckCodes4() throws InterruptedException {
        CheckCode4BytesWithArrByte c1 = new CheckCode4BytesWithArrByte();
        byte[] bytes = MagicByte.unpackToByte(c1, (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});
        Assert.assertEquals(bytes[bytes.length -1], 0x8);
        Assert.assertEquals(bytes[bytes.length -2], 0x6);
        Assert.assertEquals(bytes[bytes.length -3], 0x3);
        Assert.assertEquals(bytes[bytes.length -4], 0x1);

        CheckCode4BytesWithArrByte pack = MagicByte.pack(bytes, CheckCode4BytesWithArrByte.class,
                (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});

        Assert.assertEquals(pack.getCheckCodes()[0], (Byte) (byte)1);
        Assert.assertEquals(pack.getCheckCodes()[1], (Byte) (byte)3);
        Assert.assertEquals(pack.getCheckCodes()[2], (Byte) (byte)6);
        Assert.assertEquals(pack.getCheckCodes()[3], (Byte) (byte)8);
        Assert.assertEquals(pack.getLength(), bytes.length);

    }


    /**
     * 测试校验和多个数据
     * 使用 UByte[]
     * @throws InterruptedException
     */
    @Test
    public void testBytesCheckCodes5() throws InterruptedException {
        CheckCode4BytesWithArrUByte c1 = new CheckCode4BytesWithArrUByte();
        byte[] bytes = MagicByte.unpackToByte(c1, (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});
        Assert.assertEquals(bytes[bytes.length -1], 0x8);
        Assert.assertEquals(bytes[bytes.length -2], 0x6);
        Assert.assertEquals(bytes[bytes.length -3], 0x3);
        Assert.assertEquals(bytes[bytes.length -4], 0x1);

        CheckCode4BytesWithArrUByte pack = MagicByte.pack(bytes, CheckCode4BytesWithArrUByte.class,
                (data)-> new byte[]{0x1,0x3,0x6,0x8,0x13});

        Assert.assertEquals(pack.getCheckCodes()[0], UByte.valueOf(1));
        Assert.assertEquals(pack.getCheckCodes()[1], UByte.valueOf(3));
        Assert.assertEquals(pack.getCheckCodes()[2],UByte.valueOf( (byte)6));
        Assert.assertEquals(pack.getCheckCodes()[3], UByte.valueOf((byte)8));
        Assert.assertEquals(pack.getLength(), bytes.length);

    }
}
