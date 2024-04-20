package io.example.customframework.business.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Controller:Forward")
class ForwardControllerTest {
    @Test
    @DisplayName("Forwarding할 viewname으로 ForwardController를 생성하면 handler 결과 Forwarding할 viewname을 반환한다.")
    void whenCreateForwardControllerWithForwardingTargetViewName_ThenHandleResultIsForwardingTargetViewName() {
        // Given
        final String given = "home.jsp";
        ForwardController forwardController = new ForwardController(given);

        // When
        String actual = forwardController.handleRequest(null, null);

        // Then
        assertThat(actual).isEqualTo(given);
    }
}
