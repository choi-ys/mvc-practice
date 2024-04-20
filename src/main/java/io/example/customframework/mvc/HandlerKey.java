package io.example.customframework.mvc;

import io.example.annotation.RequestMethod;
import java.util.Objects;

public class HandlerKey {
    private RequestMethod method;
    private String urlPath;

    private HandlerKey(RequestMethod method, String urlPath) {
        this.method = method;
        this.urlPath = urlPath;
    }

    public static HandlerKey of(RequestMethod method, String urlPath) {
        return new HandlerKey(method, urlPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HandlerKey that = (HandlerKey) o;
        return method == that.method && Objects.equals(urlPath, that.urlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath);
    }
}
