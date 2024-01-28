package br.fiap.projeto.bdd.utils;

import java.util.HashMap;
import java.util.Map;

public class DataTransfer {

    private final static Map<String, Object> DATA_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String key, Class<T> desiredClass) {

        Object value = DATA_MAP.get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public static void setValue(String key, Object value) {

        DATA_MAP.put(key, value);
    }
}
