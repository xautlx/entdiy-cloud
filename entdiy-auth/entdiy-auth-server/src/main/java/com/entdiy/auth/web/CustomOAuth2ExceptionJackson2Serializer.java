package com.entdiy.auth.web;

import com.entdiy.common.model.ViewResult;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2ExceptionJackson2Serializer;

import java.io.IOException;
import java.util.Map;

/**
 * 定制序列化输出，保持与 ExceptionViewResult 一致的输出属性和顺序
 *
 * @see ViewResult
 * @see OAuth2ExceptionJackson2Serializer
 */
public class CustomOAuth2ExceptionJackson2Serializer extends StdSerializer<OAuth2Exception> {

    public CustomOAuth2ExceptionJackson2Serializer() {
        super(OAuth2Exception.class);
    }

    @Override
    public void serialize(OAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField(ViewResult.SUCCESS_KEY_NAME, Boolean.FALSE);
        //jgen.writeStringField("error_description", value.getMessage());
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jgen.writeStringField(key, add);
            }
        }
        jgen.writeEndObject();
    }

}
