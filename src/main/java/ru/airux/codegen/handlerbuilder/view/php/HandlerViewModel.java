package ru.airux.codegen.handlerbuilder.view.php;

public record HandlerViewModel(
        String name,
        String namespace,
        Method method,
        String url,
        String description,
        String requestClassName,
        String responseClassName
) {
    public enum Method {
        POST, GET, PATCH, PUT, OPTION
    }

    public String methodAnnotation() {
        return switch (method) {
            case POST -> "Post";
            case GET -> "Get";
            case PATCH -> "Patch";
            case PUT -> "Put";
            case OPTION -> "Option";
        };
    }
}
