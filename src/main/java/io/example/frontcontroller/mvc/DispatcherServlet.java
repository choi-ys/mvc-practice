package io.example.frontcontroller.mvc;

import io.example.frontcontroller.mvc.controller.Controller;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private RequestHandlerMapping requestHandlerMapping;

    @Override
    public void init() {
        requestHandlerMapping = new RequestHandlerMapping();
        requestHandlerMapping.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controller handler = requestHandlerMapping.findHandler(request.getRequestURI());
        try {
            String viewName = handler.handleRequest(request, response);
            log.info("[DispatcherServlet][service] Request url: {}, forward view name: {}", request.getRequestURI(), viewName);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            log.error("[DispatcherServlet][service] Request url: {}", request.getRequestURI());
        }
    }
}
