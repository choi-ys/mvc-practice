package io.example.refecltion.controller;

import io.example.refecltion.annotation.Controller;
import io.example.refecltion.annotation.RequestMapping;
import io.example.refecltion.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HealthCheckController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health(HttpServletRequest request, HttpServletResponse response) {
        return "ok";
    }
}
