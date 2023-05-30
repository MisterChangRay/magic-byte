package com.github.misterchangray.core.autochecker;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.TestFunctional;
import com.github.misterchangray.core.autochecker.pojo.Office;
import com.github.misterchangray.core.autochecker.pojo.OfficeStrict;
import com.github.misterchangray.core.autochecker.pojo.OfficeWithUnsigend;
import com.github.misterchangray.core.autochecker.pojo.Staff;
import com.github.misterchangray.core.autochecker.pojo_error.OfficeWith2CalcLength;
import com.github.misterchangray.core.autochecker.pojo_error.OfficeWith2CheckCode;
import com.github.misterchangray.core.clazz.warpper.UInt;
import com.github.misterchangray.core.clazz.warpper.UShort;
import com.github.misterchangray.core.exception.InvalidCheckCodeException;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.exception.InvalidParameterException;
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
    @Test
    public void testCalcLengthAndCheckCodeWithUnsigend() throws InterruptedException {
        OfficeWithUnsigend office = new OfficeWithUnsigend();
        office.setHead(11);
        office.setAddr("chen11u");
        office.setName("xiu22an");

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
        OfficeWithUnsigend office2 = MagicByte.pack(unpack.array(), OfficeWithUnsigend.class);

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

        Assert.assertNotNull(office2.getLength());
        Assert.assertEquals(office2.getLength().intValue(), 96);

        Assert.assertNotNull(office2.getCheckCode());
        Assert.assertEquals(office2.getCheckCode(), UShort.valueOf(0x3341));

    }

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
        Assert.assertEquals(office2.getCheckCode(), 65);
        Assert.assertNotNull(office2.getLength());
        Assert.assertEquals(office2.getLength(), 95);
    }


}
