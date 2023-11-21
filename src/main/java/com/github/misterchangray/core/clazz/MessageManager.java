package com.github.misterchangray.core.clazz;

import com.github.misterchangray.core.exception.InvalidParameterException;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.intf.MagicMessage;
import com.github.misterchangray.core.util.ConverterUtil;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @description: class manager
 * @author: Ray.chang
 * @create: 2021-12-17 15:11
 **/
public class MessageManager {
    /**
     * 命令长度
     * 所有注册的消息,命令长度应该是相同的
     */
    private static Byte cmdLength = -1;
    /**
     * 命令偏移量
     * 所有注册的消息,命令偏移量应该是相同的
     */
    private static Integer cmdOffset = -1;

    private static Map<Integer, Class<? extends MagicMessage>> cache = new HashMap<>();

    public static boolean hasMessage() {
        return cmdOffset != -1;
    }
    public static Class<? extends MagicMessage> getMessage(DynamicByteBuffer data) {
        if(cmdOffset < 0 || cmdOffset >= data.capacity()) {
            return null;
        }
        byte[] cmdBytes = new byte[cmdLength];
        data.position(cmdOffset);
        data.get(cmdBytes);
        long cmd = ConverterUtil.byteToNumber(cmdBytes);
        data.position(0);
        return cache.get(Long.valueOf(cmd).intValue());
    }

    /**
     * 注册消息
     * @param message
     */
    public static void register(Class<? extends MagicMessage> message) {
        register(null, message);
    }

    /**
     * 注册指定消息
     * @param cmd
     * @param message
     */
    public static void register(Integer cmd, Class<? extends MagicMessage> message) {
        ClassMetaInfo classMetaInfo = ClassManager.getClassMetaInfo(message);

        int tmpOffset = 0;
        byte tmpCmdLen = 0;
        for (FieldMetaInfo field : classMetaInfo.getFlatFields()) {
            if(field.isCmdField()) {
                Integer cmd_ = cmd;
                if(Objects.isNull(cmd)) {
                    try {
                        Number o = (Number) field.getReader().readFormObject(message.getDeclaredConstructor().newInstance());
                        cmd_ = Long.valueOf(ConverterUtil.toNumber(field.getType(), o)).intValue();
                    } catch (Exception ae) {
                        throw new InvalidParameterException("can't access cmd value for register operation, please define cmd value ! ; at: " + classMetaInfo.getFullName());
                    }
                }
                if(cache.containsKey(cmd_.intValue())) {
                    throw new InvalidParameterException("registered failed, duplicated cmd defined ! ; at: " + classMetaInfo.getFullName());
                }
                cache.put(cmd_.intValue(), message);
                tmpCmdLen = (byte)field.getElementBytes();
                break;
            }
            tmpOffset += field.getElementBytes() * field.getSize();

        }
        if(cmdOffset.intValue()  > -1 && !cmdOffset.equals(tmpOffset)) {
            throw new InvalidParameterException("all Of registered message should have same offset! ; at: " + classMetaInfo.getFullName());
        }
        if(cmdLength  > -1 && !cmdLength.equals(tmpCmdLen)) {
            throw new InvalidParameterException("all Of registered message should have same bytes length! ; at: " + classMetaInfo.getFullName());
        }
        cmdLength = (byte)tmpCmdLen;
        cmdOffset = tmpOffset;
    }


}
