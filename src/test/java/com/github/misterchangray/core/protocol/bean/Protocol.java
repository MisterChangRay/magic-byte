package com.github.misterchangray.core.protocol.bean;

import com.github.misterchangray.core.MagicByte;
import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;
import com.github.misterchangray.core.intf.MagicMessage;

import java.util.Arrays;

@MagicClass
public class Protocol implements MConverter<MagicMessage>, MagicMessage {

    @MagicField(order = 1)
    private Head head;

    @MagicField(order = 3)
    @MagicConverter(converter = Protocol.class)
    private MagicMessage body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public MagicMessage getBody() {
        return body;
    }

    public void setBody(MagicMessage body) {
        this.body = body;
    }

    @Override
    public MResult<MagicMessage> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz) {
        Head head = MagicByte.pack(fullBytes, Head.class);
        switch (head.getCmd()) {
            case 1: return MResult.build(fullBytes.length - nextReadIndex, MagicByte.pack(Arrays.copyOfRange(fullBytes, nextReadIndex, fullBytes.length), Student.class));
            case 2: return MResult.build(fullBytes.length - nextReadIndex, MagicByte.pack(Arrays.copyOfRange(fullBytes, nextReadIndex, fullBytes.length), Teacher.class));
        }
        return null;
    }

    @Override
    public byte[] unpack(MagicMessage object, String[] attachParams) {
        return MagicByte.unpackToByte(object);
    }
}
