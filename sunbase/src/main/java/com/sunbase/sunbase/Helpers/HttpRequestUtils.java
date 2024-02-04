package com.sunbase.sunbase.Helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbase.sunbase.dtos.CustomerDto;
import com.sunbase.sunbase.entity.Customer;
import com.sunbase.sunbase.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HttpRequestUtils {
    @Autowired
    RestTemplate restTemplate;
    @Value("${login_url}")
    private String loginUrl;
    @Value("${getCustomers_url}")
    private String list_url;

    public String getAccessToken(Login login) throws JsonProcessingException {
        HttpEntity<Login> entity = new HttpEntity<>(login);
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        JsonNode node = new ObjectMapper().readTree(response.getBody());
        return node.get("access_token").asText();
    }

    public List<Customer> getList(Login login) throws JsonProcessingException {
        String token = getAccessToken(login);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+token);
        HttpEntity<Login> entity = new HttpEntity<>(login,httpHeaders);
        ResponseEntity<List<Customer>> response=restTemplate.exchange(list_url+"?cmd=get_customer_list",HttpMethod.GET,entity,new ParameterizedTypeReference<List<Customer>>() {});

        return response.getBody();
    }

}
