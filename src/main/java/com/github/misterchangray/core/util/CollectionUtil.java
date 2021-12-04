package com.github.misterchangray.core.util;

import com.github.misterchangray.core.metainfo.FieldMetaInfo;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class CollectionUtil {

    public static int sizeOfCollection(FieldMetaInfo fieldMetaInfo, Object t) {
        if(Objects.isNull(t)) {
            return 0;
        }
        switch (fieldMetaInfo.getType()) {
            case ARRAY:
                return Array.getLength(t);
            case LIST:
                return ((List) t).size();
        }
        return 0;
    }


}
