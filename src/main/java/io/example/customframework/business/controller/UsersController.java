package io.example.customframework.business.controller;

import io.example.annotation.Controller;
import io.example.annotation.RequestMapping;
import io.example.annotation.RequestMethod;
import io.example.customframework.business.repo.UserRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
    private static final String USER_LIST_VIEW_NAME = "/user/list";

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", UserRepo.findAll());
        return USER_LIST_VIEW_NAME;
    }
}
