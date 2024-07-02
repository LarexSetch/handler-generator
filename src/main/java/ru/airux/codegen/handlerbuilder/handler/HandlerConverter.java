package ru.airux.codegen.handlerbuilder.handler;

import java.util.Objects;

public class HandlerConverter {
    public Handler toHandler(HandlerViewModel viewModel) {
        return new Handler(
                viewModel.getContext(),
                viewModel.getUri(),
                convertContentType(viewModel.getContentType())
        );
    }

    private Handler.ContentType convertContentType(HandlerViewModel.ContentType contentType) {
        if (Objects.requireNonNull(contentType) == HandlerViewModel.ContentType.JSON) {
            return Handler.ContentType.JSON;
        }

        return null;
    }
}
