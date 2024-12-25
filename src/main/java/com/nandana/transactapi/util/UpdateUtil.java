package com.nandana.transactapi.util;

import java.util.function.Consumer;

public class UpdateUtil {
    public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
