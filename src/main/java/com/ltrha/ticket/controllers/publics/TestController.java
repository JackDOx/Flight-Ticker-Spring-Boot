package com.ltrha.ticket.controllers.publics;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/redirect")
    public RedirectView redirect(){

        return new RedirectView("http://localhost:8080/test/hello");
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
