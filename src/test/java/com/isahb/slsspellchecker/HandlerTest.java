package com.isahb.slsspellchecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isahb.slsspellchecker.model.ApiGatewayResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author isahb
 */
class HandlerTest {
    private Handler handler;

    @BeforeEach
    void before() {
        handler = new Handler();
    }

    @Test
    void shouldReturnError400ForInvalidRequest() throws IOException {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("invalidKey", "");
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("body", requestBodyJson);

        ApiGatewayResponse apiGatewayResponse = handler.handleRequest(input, null);
        assertEquals(400, apiGatewayResponse.getStatusCode());
    }

    @Test
    void shouldReturnTwoSuggestions() throws IOException {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("text", "Some tect with spelling erroz");
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);
        Map<String, Object> input = new LinkedHashMap<>();
        input.put("body", requestBodyJson);

        ApiGatewayResponse apiGatewayResponse = handler.handleRequest(input, null);
        assertEquals(200, apiGatewayResponse.getStatusCode());

        JSONObject response = new JSONObject(apiGatewayResponse.getBody());
        assertEquals(((JSONArray)response.get("spellCheckSuggestions")).length(), 2);
    }
}