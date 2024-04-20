package io.example.customframework.mvc.viewresolver;

import static io.example.customframework.mvc.viewresolver.JspViewResolver.JSP_POSTFIX_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

import io.example.customframework.mvc.viewresolver.view.JspView;
import io.example.customframework.mvc.viewresolver.view.RedirectView;
import io.example.customframework.mvc.viewresolver.view.View;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Resolver:JspView")
class JspViewResolverTest {
    @Test
    @DisplayName("'redirect:' prefix가 붙지 않은 view-name이 전달되면 view-name에 '.jsp' postfix를 붙여 JspView 객체를 생성 후 반환한다.")
    void GivenNotRedirectPrefixedViewName_ThenResolverJspView() {
        // Given
        final String given = "sample";

        // When
        View actual = new JspViewResolver().resolverView(given);

        // Then
        JspView expected = new JspView(String.format(JSP_POSTFIX_FORMAT, given));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("'redirect:' prefix가 붙은 view-name이 전달되면 view-name 그대로 Redirect 객체를 생성 후 반환한다.")
    void GivenRedirectPrefixedViewName_ThenResolverRedirectView() {
        // Given
        final String given = "redirect:sample";

        // When
        View actual = new JspViewResolver().resolverView(given);

        // Then
        RedirectView expected = new RedirectView(given);
        assertThat(actual).isEqualTo(expected);
    }
}
