package com.avinty.hr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestUtils {

    public static String toJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    public static JSONObject readSingleResult(MockHttpServletResponse response) {
        try {
            return new JSONObject(response.getContentAsString());
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONArray readResultList(MockHttpServletResponse response) {
        try {
            return new JSONArray(response.getContentAsString());
        } catch (Exception e) {
            return null;
        }
    }
}
