package io.example.customframework.mvc;

import io.example.annotation.RequestMethod;
import io.example.customframework.business.controller.Controller;
import io.example.customframework.mvc.handlermappings.HandlerKey;
import io.example.customframework.mvc.handlermappings.RequestHandlerMapping;
import io.example.customframework.mvc.viewresolver.JspViewResolver;
import io.example.customframework.mvc.viewresolver.ViewResolver;
import io.example.customframework.mvc.viewresolver.view.View;
import java.io.IOException;
import java.util.HashMap;
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
    public static final String REDIRECT_PREFIX = "redirect:";
    private RequestHandlerMapping requestHandlerMapping;
    private ViewResolver viewResolver;

    @Override
    public void init() {
        requestHandlerMapping = new RequestHandlerMapping();
        requestHandlerMapping.init();

        viewResolver = new JspViewResolver();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HandlerKey handlerKey = HandlerKey.of(RequestMethod.valueOf(request.getMethod()), request.getRequestURI());
        Controller handler = requestHandlerMapping.findHandler(handlerKey);

        try {
            String viewName = handler.handleRequest(request, response);
            log.info("[DispatcherServlet][service] Request url: {}, forward view name: {}", request.getRequestURI(), viewName);

            View view = viewResolver.resolverView(viewName);
            view.render(new HashMap<>(), request, response);
        } catch (Exception e) {
            log.error("[DispatcherServlet][service] Request url: {}", request.getRequestURI());
        }
    }
}
