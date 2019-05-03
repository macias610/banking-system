package com.banking.chestnut.credit.helpers;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class HateoasHelper {

    private HateoasHelper (){}

    public static URI getUriWithPathAndParams(String path, Object ... params){
        return fromPath(path).buildAndExpand(params).toUri();

    }
}
