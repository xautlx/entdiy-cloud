package com.entdiy.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExtLocalDateDeserializer extends LocalDateDeserializer {

    public ExtLocalDateDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String val = parser.getValueAsString();
        if ("".equals(val)) {
            return LocalDate.MIN;
        }
        return super.deserialize(parser, context);
    }
}
