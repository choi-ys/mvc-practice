package io.example.customframework.mvc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
    private static final String UTF_8 = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
