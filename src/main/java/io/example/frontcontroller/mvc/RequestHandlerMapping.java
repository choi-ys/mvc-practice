package io.example.frontcontroller.mvc;

import io.example.frontcontroller.mvc.controller.Controller;
import io.example.frontcontroller.mvc.controller.HomeController;
import java.util.HashMap;
import java.util.Map;

public class RequestHandlerMapping {
    private Map<String, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put("/", new HomeController());
    }

    public Controller findHandler(String urlPath) {
        return mappings.get(urlPath);
    }
}
