package io.example.customframework.mvc.viewresolver.view;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectView implements View {
    public static final String REDIRECT_PREFIX = "redirect:";
    private final String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(name.substring(REDIRECT_PREFIX.length()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RedirectView that = (RedirectView) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
