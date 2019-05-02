package com.banking.chestnut.models;

import java.util.HashMap;
import java.util.Map;

public class ClientStatus {

    public static final Map<String, String> statuses = new HashMap<String, String>(){{
       put("active", "client active");
        put("nonactive", "client non active");
    }};
}
