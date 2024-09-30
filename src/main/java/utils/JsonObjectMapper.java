package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.CustomException;

public class JsonObjectMapper {
    private final ObjectMapper objectMapper;

    public JsonObjectMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomException(STR."Serialization error: \{e}");
        }
    }
}
