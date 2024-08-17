package com.pdev.rempms.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @GetMapping(value = "fallback/response/message")
    public ResponseEntity<String> subscribesFallbackmethod(Exception e) {
        return new ResponseEntity("There are some error in connecting, Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
