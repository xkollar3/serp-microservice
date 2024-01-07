package com.kollar.serpmicroservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/***
 * Encode parameter hash into a string
 *  to be sent over HTTP
 *
 *  SOURCE - https://github.com/serpapi/google-search-results-java/blob/master/src/main/java/serpapi/ParameterStringBuilder.java
 */
public class ParamsStringBuilder {
    /***
     * getParamsString
     * @param params search parameters
     * @return concatenated parameters
     */
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
