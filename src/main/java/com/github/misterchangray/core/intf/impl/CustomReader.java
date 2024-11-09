package com.github.misterchangray.core.intf.impl;

import com.github.misterchangray.core.clazz.CustomConverterInfo;
import com.github.misterchangray.core.clazz.FieldMetaInfo;
import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.exception.InvalidLengthException;
import com.github.misterchangray.core.intf.MConverter;
import com.github.misterchangray.core.intf.MReader;
import com.github.misterchangray.core.util.DynamicByteBuffer;

import java.util.Objects;

/**
 * @description: read custom data
 * @author: Ray.chang
 * @create: 2023-02-08 10:58
 **/
public class CustomReader extends MReader {
    public CustomReader(FieldMetaInfo _fieldMetaInfo) {
        super(_fieldMetaInfo);
    }

    @Override
    public Object readFormObject(Object object) throws IllegalAccessException {
        if(this.fieldMetaInfo.getOrderId() == -1) {
            return object;
        } else {
           return this.fieldMetaInfo.getField().get(object);
        }
    }

    @Override
    public Object doReadFormBuffer(DynamicByteBuffer buffer, Object obj)  throws IllegalAccessException  {
        CustomConverterInfo converterInfo = this.fieldMetaInfo.getCustomConverter();
        MConverter converter = converterInfo.getConverter();

        MResult<?> pack = converter.pack(buffer.position(), buffer.bytes(), converterInfo.getAttachParams(), this.fieldMetaInfo.getClazz(), obj, buffer.getPackObj());

        if ((Objects.isNull(pack) || Objects.isNull(pack.getBytes())) && !converterInfo.isFixSize()) {
            throw new InvalidLengthException(null,
                    "you should return actually read bytes length when you not set fixSize property, at: " + converter.getClass().getTypeName());
        }

        Integer length = pack.getBytes();
        if (Objects.nonNull(converter) && converterInfo.isFixSize()) {
            length = converterInfo.getFixSize();
        }
        int newPosition = buffer.position() + length;
        if(newPosition > buffer.capacity()) {
            throw new InvalidLengthException(null,
                    "the data incomplete, maybe you custom read or custom write error, at: " + converter.getClass().getTypeName());
        }
        buffer.position(newPosition);

        return pack.getData();
    }

}
