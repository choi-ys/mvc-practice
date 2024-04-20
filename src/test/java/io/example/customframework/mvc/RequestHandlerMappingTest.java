package io.example.customframework.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import io.example.annotation.RequestMethod;
import io.example.customframework.business.controller.Controller;
import io.example.customframework.business.controller.ForwardController;
import io.example.customframework.mvc.handlermappings.HandlerKey;
import io.example.customframework.mvc.handlermappings.RequestHandlerMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RequestHandlerMapping")
class RequestHandlerMappingTest {
    private RequestHandlerMapping requestHandlerMapping;

    @BeforeEach
    void setUp() {
        requestHandlerMapping = new RequestHandlerMapping();
        requestHandlerMapping.init();
    }

    @Test
    @DisplayName("'/' 요청 시 홈화면 출력을 위한 home.jsp 파일명을 반환하는 ForwardController를 반환 한다.")
    void get() {
        // Given
        HandlerKey handlerKey = HandlerKey.of(RequestMethod.GET, "/");

        // When
        Controller handler = requestHandlerMapping.findHandler(handlerKey);

        // Then
        assertThat(handler.getClass()).isEqualTo(ForwardController.class);
    }
}
