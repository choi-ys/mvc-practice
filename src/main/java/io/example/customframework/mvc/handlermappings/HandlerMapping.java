package io.example.customframework.mvc.handlermappings;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}
