package io.example.customframework.business.controller;

import io.example.customframework.business.repo.UserRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersController implements Controller {
    private static final String USER_LIST_VIEW_NAME = "/user/list.jsp";

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", UserRepo.findAll());
        return USER_LIST_VIEW_NAME;
    }
}
