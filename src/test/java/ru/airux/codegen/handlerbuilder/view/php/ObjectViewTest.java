package ru.airux.codegen.handlerbuilder.view.php;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ObjectViewTest {
    @Test
    public void render() {
        var model = createModel();
        var renderer = new ObjectView();

        Assertions.assertEquals(
                createExpected(),
                renderer.render(model)
        );
    }

    private ObjectViewModel createModel() {
        return new ObjectViewModel(
                "App\\Handler\\V1\\User\\Create",
                "CreateUserRequest",
                "ApiV1CreateUserRequest",
                "User create request dto",
                List.of(
                        new ObjectViewModel.Property(
                                true,
                                new ObjectViewModel.Property.Type(null, "UserType"),
                                "type",
                                null,
                                null,
                                null
                        ),
                        new ObjectViewModel.Property(
                                true,
                                new ObjectViewModel.Property.Type("string", null),
                                "username",
                                "username",
                                "User username",
                                null
                        ),
                        new ObjectViewModel.Property(
                                true,
                                new ObjectViewModel.Property.Type("string", null),
                                "password",
                                "password",
                                "User password",
                                null
                        ),
                        new ObjectViewModel.Property(
                                true,
                                new ObjectViewModel.Property.Type(null, "FullName"),
                                "fullName",
                                "full_name",
                                "User full name",
                                null
                        )
                )
        );
    }

    private String createExpected() {
        return """
                <?php
                                
                declare(strict_types=1);

                namespace App\\Handler\\V1\\User\\Create;
                                
                use OpenApi\\Attributes as OA;
                                
                #[OA\\Schema(
                    schema: 'ApiV1CreateUserRequest',
                    title: 'User create request dto',
                    required: [
                        'username',
                        'password',
                        'full_name',
                    ],
                )]
                final class CreateUserRequest
                {
                    public function __construct(
                        public readonly UserType $type,
                        #[OA\\Property(
                            property: 'username',
                            title: 'User username',
                            type: 'string',
                        )]
                        public readonly string $username,
                        #[OA\\Property(
                            property: 'password',
                            title: 'User password',
                            type: 'string',
                        )]
                        public readonly string $password,
                        #[OA\\Property(
                            property: 'full_name',
                            title: 'User full name',
                            type: 'object',
                            ref: FullName::class,
                        )]
                        public readonly FullName $fullName,
                    ) {
                    }
                }
                """;
    }
}
