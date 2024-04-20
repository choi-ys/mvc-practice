package io.example.customframework.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private final String forwardPath;

    public ForwardController(String forwardPath) {
        this.forwardPath = forwardPath;
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return forwardPath;
    }
}
