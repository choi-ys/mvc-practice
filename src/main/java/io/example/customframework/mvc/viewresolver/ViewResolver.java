package io.example.customframework.mvc.viewresolver;

import io.example.customframework.mvc.viewresolver.view.View;

public interface ViewResolver {
    View resolverView(String viewName);
}
