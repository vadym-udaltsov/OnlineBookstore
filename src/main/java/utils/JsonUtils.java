package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.CustomException;
import io.restassured.response.Response;

import java.util.List;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String objectToJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomException(STR."Serialization error: \{e}");
        }
    }
    public static <T> List<T> jsonToObjectsList(Response response, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(response.getBody().asString(),
                    OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new CustomException(STR."Deserialization error: \{e.getMessage()}");
        }
    }
}
