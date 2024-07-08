package ru.airux.codegen.handlerbuilder.view.php;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HandlerViewTest {
    @Test
    public void render() {
        var model = createModel();
        var renderer = new HandlerView();

        Assertions.assertEquals(
                createExpected(),
                renderer.render(model)
        );
    }

    private HandlerViewModel createModel() {
        return new HandlerViewModel(
                "CreateUser",
                "App\\Handler\\V1\\User\\Create",
                HandlerViewModel.Method.POST,
                "/api/v1/user/create/{user_type}",
                "Creating user with type",
                "CreateRequest",
                "CreateResponse"
        );
    }

    private String createExpected() {
        return """
                <?php
                                
                declare(strict_types=1);

                namespace App\\Handler\\V1\\User\\Create;
                                
                use OpenApi\\Attributes as OA;
                                
                interface CreateUserInterface
                {
                    #[OA\\Post(
                        path: '/api/v1/user/create/{user_type}',
                        requestBody: new OA\\RequestBody(
                            content: new OA\\JsonContent(
                                ref: CreateRequest::class
                            )
                        ))]
                    #[OA\\Response(
                        response: 200,
                        description: 'Creating user with type',
                        content: new OA\\JsonContent(
                            ref: CreateResponse::class
                        ))]
                    public function handle(CreateRequest $request): CreateResponse;
                }
                """;
    }
}
