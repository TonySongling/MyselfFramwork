package cn.migu.framwork.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json工具类
 * Created by Song on 2016/11/30.
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转换为json
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将json转为POJO
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> type){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJOfailure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
