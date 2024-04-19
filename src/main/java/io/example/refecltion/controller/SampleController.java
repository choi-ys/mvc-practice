package io.example.refecltion.controller;

import io.example.annotation.Controller;
import io.example.annotation.RequestMapping;
import io.example.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SampleController {
    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public String sample(HttpServletRequest request, HttpServletResponse response) {
        return "hello controller";
    }
}
