package io.example.customframework.mvc;

import static io.example.annotation.RequestMethod.GET;
import static io.example.annotation.RequestMethod.POST;
import static io.example.customframework.mvc.HandlerKey.of;

import io.example.customframework.business.controller.Controller;
import io.example.customframework.business.controller.ForwardController;
import io.example.customframework.business.controller.UserCreateController;
import io.example.customframework.business.controller.UsersController;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapping {
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put(of(GET, "/"), new ForwardController("home"));
        mappings.put(of(GET, "/user/form"), new ForwardController("/user/form"));
        mappings.put(of(POST, "/users"), new UserCreateController());
        mappings.put(of(GET, "/users"), new UsersController());
    }

    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }
}
