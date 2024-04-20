package io.example.customframework.mvc.handlermappings;

import io.example.annotation.Controller;
import io.example.annotation.RequestMapping;
import io.example.annotation.RequestMethod;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);

        classesWithControllerAnnotation.forEach(clazz ->
            Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {
                RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class);
                Arrays.stream(getRequestMethods(requestMapping))
                    .forEach(requestMethod -> handlers.put(
                        HandlerKey.of(requestMethod, requestMapping.value()),
                        AnnotationHandler.of(clazz, declaredMethod)
                    ));
            })
        );
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
