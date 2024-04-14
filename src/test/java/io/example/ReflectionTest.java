package io.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.example.refecltion.annotation.Controller;
import io.example.refecltion.service.Service;
import io.example.refecltion.controller.HealthCheckController;
import io.example.frontcontroller.mvc.controller.HomeController;
import io.example.refecltion.controller.SampleService;
import io.example.refecltion.model.User;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("@Controller Annotation이 설정된 모든 클래스를 찾아 출력한다.")
    void controllerScan() {
        // Given & When
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        // Then
        assertThat(beans).containsAll(
            Set.of(
                HomeController.class,
                HealthCheckController.class,
                SampleService.class
            )
        );
        log.debug("beans : [{}]", beans);
    }
    
    @Test
    @DisplayName("클래스의 모든 필드 출력")
    void showClass() {
        // Given
        Class<User> userClass = User.class;
    
        // When
        String className = userClass.getName();
        List<Field> classFields = Arrays.stream(userClass.getDeclaredFields()).collect(Collectors.toList());
        List<Constructor<?>> constructors = Arrays.stream(userClass.getDeclaredConstructors()).collect(Collectors.toList());

        // Then
        log.info(className);
        log.info(classFields.toString());
        log.info(constructors.toString());
    }
    
    @Test
    @DisplayName("클래스 타입의 객체를 가지고 오는 세가지 방법")
    void getClassType() throws ClassNotFoundException {
        // Given & When
        Class<User> userClass = User.class;
        Class<? extends User> userClassByInstance = new User("userId", "name").getClass();
        Class<?> userClassByName = Class.forName("io.example.refecltion.model.User");
    
        // Then
        assertAll(
            () -> assertEquals(userClass, userClassByInstance),
            () -> assertEquals(userClass, userClassByName),
            () -> assertEquals(userClassByInstance, userClassByName)
        );
    }

    private Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("io.example");

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;
    }
}
