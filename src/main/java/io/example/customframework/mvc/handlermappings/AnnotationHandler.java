package io.example.customframework.mvc.handlermappings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandler {
    private final Class<?> clazz;
    private final Method targetMethod;

    private AnnotationHandler(Class<?> clazz, Method targetMethod) {
        this.clazz = clazz;
        this.targetMethod = targetMethod;
    }

    public static AnnotationHandler of(Class<?> clazz, Method targetMethod) {
        return new AnnotationHandler(clazz, targetMethod);
    }

    public String handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
        Object handler = declaredConstructor.newInstance();

        return (String) targetMethod.invoke(handler, request, response);
    }
}
