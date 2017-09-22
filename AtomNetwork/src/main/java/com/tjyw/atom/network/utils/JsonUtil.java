package com.tjyw.atom.network.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by stephen on 6/28/16.
 */
public class JsonUtil {

    interface JSON {

        String NODE = "{}";

        String ARRAY = "[]";
    }
    
    private static JsonUtil jackson;
   
    private ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static synchronized JsonUtil getInstance() {
        if (null == jackson) {
            jackson = new JsonUtil();
        }
        return jackson;
    }

    public ObjectNode getEmptyNode() {
        return fromJson(JSON.NODE, ObjectNode.class);
    }

    public ArrayNode getEmptyArray() {
        return fromJson(JSON.ARRAY, ArrayNode.class);
    }

    public <T> T parseObject(String text, Class<T> clazz) {
        try {
            return this.mapper.readValue(text, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T parseObject(InputStream is, Class<T> clazz) {
        try {
            return this.mapper.readValue(is, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T parseArray(String text, Class<T> clazz) {
        try {
            return this.mapper.readValue(text, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> List<T> parseJavaArray(String text, Class clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            return this.mapper.readValue(text, javaType);
        } catch (Exception e) {
            return null;
        }
    }

    public String toJsonString(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Map<String, Object> fromJson(String string) {
        try {
            return (Map) this.mapper.readValue(string, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T fromJson(String string, Class<T> clazz) {
        try {
            return this.mapper.readValue(string, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> fromBean(Object object) {
        return this.fromJson(this.toJsonString(object));
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
