package com.brilworks.accounts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.InputMismatchException;
@NoArgsConstructor
public class JSONUtility {
    public static String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static <Object> Object toObject(String jsonString, Class<Object> convertIntoClass) throws IOException {
        if (jsonString.isEmpty()) {
            throw new InputMismatchException("Json string can not found");
        }
        return new ObjectMapper().readValue(jsonString, convertIntoClass);
    }
}
