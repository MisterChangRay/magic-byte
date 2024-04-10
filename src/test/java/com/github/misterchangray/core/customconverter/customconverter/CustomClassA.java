package com.github.misterchangray.core.customconverter.customconverter;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.clazz.MResult;
import com.github.misterchangray.core.intf.MConverter;

import java.util.Objects;

@MagicClass
public class CustomClassA {

    @MagicField(order = 1)
    private int number;

    @MagicField(order = 2)
    private B b;

    @MagicClass
    public static class B {

        @MagicField(order = 1)
        @MagicConverter(converter = TypeEnumConverter.class)
        private TypeEnum typeEnum;

        public TypeEnum getTypeEnum() {
            return typeEnum;
        }

        public void setTypeEnum(TypeEnum typeEnum) {
            this.typeEnum = typeEnum;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            B b = (B) o;
            return typeEnum == b.typeEnum;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(typeEnum);
        }
    }

    public enum TypeEnum {
        A, B
    }

    public static class TypeEnumConverter implements MConverter<TypeEnum> {


        @Override
        public MResult<TypeEnum> pack(int nextReadIndex, byte[] fullBytes, String[] attachParams, Class clz, Object fieldObj, Object rootObj) {
            return MResult.build(1, fullBytes[nextReadIndex] == 0 ? TypeEnum.A : TypeEnum.B);
        }

        @Override
        public byte[] unpack(TypeEnum object, String[] attachParams, Object rootObj) {
            byte[] array = new byte[1];
            array[0] = (byte) object.ordinal();
            return array;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomClassA that = (CustomClassA) o;
        return number == that.number && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, b);
    }
}
