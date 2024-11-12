package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.service.impl.client.UserServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceClientTest {

    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    void getUser() {
        var user = userServiceClient.getUser("1e61ae2d-a409-4600-8415-0e0a868360e8", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MkBnbWFpbC5jb20iLCJpc3MiOiJGbGlnaHRUaWNrZXRTeXN0ZW0iLCJqdGkiOiIxZTYxYWUyZC1hNDA5LTQ2MDAtODQxNS0wZTBhODY4MzYwZTgiLCJpYXQiOjE3Mjc4NzQ1NDcsImV4cCI6MTcyNzg3ODE0N30.mKENMEc3mirUP5TSXWLpxYh6KAHJlB3viY9n04RMoB-_GZ1aCiCReoDKNOZsW0ViquvCzZuyJjyx9H3k26m_iQ");
        if(user.isPresent()) {
            System.out.println(user.get().toString());
        }
        else {
            System.out.println("User not found");
            fail();
        }
    }
}