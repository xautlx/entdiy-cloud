package com.entdiy.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    public ExtLocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String val = parser.getValueAsString();
        if ("".equals(val)) {
            return LocalDateTime.MIN;
        }
        return super.deserialize(parser, context);
    }
}
