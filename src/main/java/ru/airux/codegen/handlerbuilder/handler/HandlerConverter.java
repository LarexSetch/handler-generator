package ru.airux.codegen.handlerbuilder.handler;

public class HandlerConverter {
    public Handler toHandler(HandlerViewModel viewModel) {
        return new Handler(
                viewModel.getRootDirectory(),
                viewModel.getName(),
                viewModel.getUri(),
                viewModel.getObjectDeclaration()
        );
    }
}
