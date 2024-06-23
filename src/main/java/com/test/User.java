package com.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignore all extra properties that our class can't handle
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public int id;
    public String name;
    public String username;
    public String email;

    
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + "]";

    }
}
