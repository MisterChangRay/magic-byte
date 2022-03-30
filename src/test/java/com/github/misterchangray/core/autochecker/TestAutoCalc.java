package com.github.misterchangray.core.autochecker;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.TestFunctional;
import com.github.misterchangray.core.autochecker.pojo.Office;
import com.github.misterchangray.core.autochecker.pojo.Staff;
import com.github.misterchangray.core.autotrim.pojo.AutoTrimArray;
import com.github.misterchangray.core.autotrim.pojo.AutoTrimNesting;
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
        Assert.assertNotNull(office2.getLength());
    }


}
