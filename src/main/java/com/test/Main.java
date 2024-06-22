package com.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        
        public int id;
        public String name;
        public String username;
        public String email;
        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + "]";
        }


        
        
    }

    public static void main(String[] args) {


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1"))
                .build();

        try {
            
            HttpResponse<Supplier<User>> res = client.send(
                request,
                new JsonBodyHandler<>(User.class));

            System.out.println(res.body().get().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}