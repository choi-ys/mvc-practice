package io.example.customframework.mvc.viewresolver.view;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JspView implements View {
    private static final Logger log = LoggerFactory.getLogger(JspView.class);

    private final String name;

    public JspView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Forward to [{}]", name);
        model.forEach(request::setAttribute);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(name);
        requestDispatcher.forward(request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JspView jspView = (JspView) o;
        return Objects.equals(name, jspView.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
