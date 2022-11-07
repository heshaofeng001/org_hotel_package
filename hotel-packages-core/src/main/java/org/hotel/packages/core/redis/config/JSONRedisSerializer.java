package org.hotel.packages.core.redis.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class JSONRedisSerializer implements RedisSerializer {

    public JSONRedisSerializer() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {

        String message = JSONObject.toJSONString(o, SerializerFeature.WriteClassName);
        return message.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {

        if (bytes == null) {
            return null;
        }
        String message = new String(bytes, StandardCharsets.UTF_8);
        return JSONObject.parse(message);
    }
}
