package com.github.misterchangray.core.util;

import java.util.List;
import java.util.Objects;

public class SafetyUtil {

    public static <T> T  safeGetInList(int i, List<T> list) {
        if(Objects.isNull(list) || i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }
}
