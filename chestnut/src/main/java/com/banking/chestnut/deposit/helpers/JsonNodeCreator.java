package com.banking.chestnut.deposit.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeCreator {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private JsonNodeCreator(){}
    
    public static JsonNode createJsonNodeFrom(Object fromValue){
        return OBJECT_MAPPER.valueToTree(fromValue);
    }
}
