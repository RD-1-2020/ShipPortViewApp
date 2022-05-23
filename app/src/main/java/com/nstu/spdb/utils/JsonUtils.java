package com.nstu.spdb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper().setTimeZone(TimeZone.getDefault());

    private static JsonUtils INSTANCE;

    private JsonUtils() {

    }

    public static JsonUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JsonUtils();
        }

        return INSTANCE;
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        T result = null;
        if (clazz == null || json == null) {
            return null;
        }
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
        }
        return result;
    }

    public <T> List<T> jsonToListWithGeneric(String json, Class<T> clazz) {
        List<T> result = null;
        try {
            result = objectMapper.readValue(json,
                    constructParametricType(List.class, clazz));
        } catch (IOException e) {
        }
        return result;

    }

    public <K, V> Map<K, V> jsonToMapWithGeneric(String json, Class<K> keyClass, Class<V> valueClass) {
        return jsonToMapWithGeneric(json, Map.class, keyClass, valueClass);
    }

    private <T, K, V> Map<K, V> jsonToMapWithGeneric(String json, Class<T> mapClass, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> result = null;
        try {
            result = objectMapper.readValue(json,
                    constructParametricType(mapClass, keyClass, valueClass));
        } catch (IOException e) {
        }
        return result;
    }

    private JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
        }
        return null;
    }

    public String toString(Object rawData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(rawData);
    }
}
