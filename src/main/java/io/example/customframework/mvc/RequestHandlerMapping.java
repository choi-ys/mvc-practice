package io.example.customframework.mvc;

import io.example.customframework.business.controller.Controller;
import io.example.customframework.business.controller.ForwardController;
import io.example.customframework.business.controller.UserCreateController;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapping {
    private Map<String, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put("/", new ForwardController("home.jsp"));
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users", new UserCreateController());
    }

    public Controller findHandler(String urlPath) {
        return mappings.get(urlPath);
    }
}
