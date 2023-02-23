package com.github.misterchangray.core.util;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicConverter;
import com.github.misterchangray.core.annotation.MagicField;

import java.lang.reflect.Field;

public class AnnotationUtil {

    public static MagicField getMagicFieldAnnotation(Field field) {
       return field.getAnnotation(MagicField.class);
    }

    public static MagicConverter getMagicFieldConverterAnnotation(Field field)  {
        return  field.getAnnotation(MagicConverter.class);
    }

    public static MagicClass getMagicClassAnnotation(Class<?> clazz)  {
        return clazz.getAnnotation(MagicClass.class);
    }

    public static MagicConverter getMagicClassConverterAnnotation(Class<?> clazz)  {
        return clazz.getAnnotation(MagicConverter.class);
    }
}
