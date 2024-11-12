package com.ltrha.ticket.config.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

@JsonComponent
public class CustomJsonDeserializer {
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        Logger logger = LoggerFactory.getLogger(this.getClass().getName());

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String date = jsonParser.getText();
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
                return null;
            }
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        Logger logger = LoggerFactory.getLogger(this.getClass().getName());

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            List<String> patterns = Arrays.asList(
                    "dd-MM-yyyy",     // First pattern
                    "yyyy-MM-dd",     // Second pattern
                    "MM/dd/yyyy",     // Third pattern
                    "dd/MM/yyyy"      // Add more patterns if needed
            );

            for (String pattern : patterns) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                    String date = jsonParser.getText();
                    return LocalDate.parse(date, formatter);
                } catch (Exception e) {
//                    logger.warn(e.getMessage(), e);
                }
            }

            return null;
        }
    }
}