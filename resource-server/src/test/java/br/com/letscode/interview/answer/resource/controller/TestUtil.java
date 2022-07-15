package br.com.letscode.interview.answer.resource.controller;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class TestUtil {

    protected static String obtainAccessToken() throws Exception {

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/oauth/token")
                                .queryParam("grant_type", "password")
                                .queryParam("username", "kaduBorges")
                                .queryParam("password", "12345678")
                                .encode()
                                .toUriString();

        TestRestTemplate testRestTemplate = new TestRestTemplate("appclient", "12345678");
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(response.getBody()).get("access_token").toString();
    }
}
