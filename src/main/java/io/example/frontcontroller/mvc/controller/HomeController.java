package io.example.frontcontroller.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    private static final String HOME = "home.jsp";

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return HOME;
    }
}
