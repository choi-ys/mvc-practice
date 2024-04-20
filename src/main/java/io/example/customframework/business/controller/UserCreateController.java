package io.example.customframework.business.controller;

import io.example.annotation.Controller;
import io.example.annotation.RequestMapping;
import io.example.annotation.RequestMethod;
import io.example.customframework.business.domain.User;
import io.example.customframework.business.repo.UserRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserCreateController {
    private static final String REDIRECT_USER_LIST = "redirect:users";
    private static final String USER_ID = "userId";
    private static final String NAME = "name";

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        User user = User.of(
            request.getParameter(USER_ID),
            request.getParameter(NAME)
        );
        UserRepo.save(user);
        return REDIRECT_USER_LIST;
    }
}
