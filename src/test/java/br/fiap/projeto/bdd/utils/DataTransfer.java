package br.fiap.projeto.bdd.utils;

import java.util.HashMap;
import java.util.Map;

public class DataTransfer {

    private final static Map<DataTransferKey, Object> DATA_MAP = new HashMap<>();

    public static String getValueAsString(DataTransferKey key) {

        return getValue(key, String.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(DataTransferKey key, Class<T> desiredClass) {

        Object value = DATA_MAP.get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public static void setValue(DataTransferKey key, Object value) {

        DATA_MAP.put(key, value);
    }
}
