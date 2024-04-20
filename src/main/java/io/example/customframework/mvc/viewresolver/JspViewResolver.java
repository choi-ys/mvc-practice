package io.example.customframework.mvc.viewresolver;

import static io.example.customframework.mvc.DispatcherServlet.REDIRECT_PREFIX;

import io.example.customframework.mvc.viewresolver.view.JspView;
import io.example.customframework.mvc.viewresolver.view.RedirectView;
import io.example.customframework.mvc.viewresolver.view.View;

public class JspViewResolver implements ViewResolver {
    public static final String JSP_POSTFIX_FORMAT = "%s.jsp";

    @Override
    public View resolverView(String viewName) {
        if (isRedirectView(viewName)) {
            return new RedirectView(viewName);
        }
        return new JspView(String.format(JSP_POSTFIX_FORMAT, viewName));
    }

    private static boolean isRedirectView(String viewName) {
        return viewName.startsWith(REDIRECT_PREFIX);
    }
}
