package io.example.customframework.mvc;

import io.example.annotation.RequestMethod;
import io.example.customframework.mvc.handleradapters.AnnotationHandlerAdapter;
import io.example.customframework.mvc.handleradapters.ControllerTypeHandlerAdapter;
import io.example.customframework.mvc.handleradapters.HandlerAdapter;
import io.example.customframework.mvc.handleradapters.ModelAndView;
import io.example.customframework.mvc.handlermappings.AnnotationHandlerMapping;
import io.example.customframework.mvc.handlermappings.HandlerKey;
import io.example.customframework.mvc.handlermappings.HandlerMapping;
import io.example.customframework.mvc.handlermappings.RequestHandlerMapping;
import io.example.customframework.mvc.viewresolver.JspViewResolver;
import io.example.customframework.mvc.viewresolver.ViewResolver;
import io.example.customframework.mvc.viewresolver.view.View;
import java.io.IOException;
import java.util.List;
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
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private ViewResolver viewResolver;

    @Override
    public void init() {
        initHandlerMappings();
        initHandlerAdapters();

        viewResolver = new JspViewResolver();
    }

    private void initHandlerMappings() {
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();
        requestHandlerMapping.init();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping();
        annotationHandlerMapping.initialize();

        handlerMappings = List.of(requestHandlerMapping, annotationHandlerMapping);
    }

    private void initHandlerAdapters() {
        handlerAdapters = List.of(new ControllerTypeHandlerAdapter(), new AnnotationHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HandlerKey handlerKey = HandlerKey.of(RequestMethod.valueOf(request.getMethod()), request.getRequestURI());

            Object handler = handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.findHandler(handlerKey) != null)
                .map(handlerMapping -> handlerMapping.findHandler(handlerKey))
                .findFirst()
                .orElseThrow(() -> new ServletException("no handler for [" + handlerKey + "]"));

            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> new ServletException("no adpater for handler [" + handler + "]"));
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            View view = viewResolver.resolverView(modelAndView.getViewName());
            view.render(modelAndView.getModel(), request, response);

            log.info("[DispatcherServlet][service] Request url: {}, forward view name: {}", request.getRequestURI(), modelAndView.getViewName());
        } catch (Exception e) {
            log.error("[DispatcherServlet][service] Request url: {}", request.getRequestURI());
        }
    }
}
