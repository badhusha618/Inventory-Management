package com.makinus.inventory.api.paytm.hash;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class PaytmUtils {

    private PaytmUtils() {

    }

    public static String getResponseBody(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(response, JsonNode.class);
        return jsonNode.get("body").toString();
    }
}
