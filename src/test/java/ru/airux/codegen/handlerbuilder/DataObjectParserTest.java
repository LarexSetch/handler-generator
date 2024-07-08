package ru.airux.codegen.handlerbuilder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DataObjectParserTest {
    @Test
    public void parse() throws Exception {
        var source = createSource();
        var parser = new DataObjectParser();

        var objects = parser.parse(source);

        Assertions.assertArrayEquals(
                createExpected(),
                objects.toArray()
        );
    }

    private DataObjectParser.DataObject[] createExpected() {
        var expected = new DataObjectParser.DataObject[4];
        expected[0] = new DataObjectParser.DataObject(
                DataObjectParser.DataObject.Type.REQUEST,
                "CreateRequest",
                "ApiV1CreateUserRequest",
                List.of(
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.PATH,
                                "userType",
                                "user_type",
                                true,
                                "UserType",
                                null
                        ),
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "username",
                                "username",
                                true,
                                "string",
                                null
                        ),
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "email",
                                "email",
                                true,
                                "string",
                                null
                        ),
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "password",
                                "password",
                                true,
                                "string",
                                null
                        ),
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "fullname",
                                "full_name",
                                true,
                                "FullName",
                                null
                        )
                ),
                new ArrayList<>(),
                null
        );
        expected[1] = new DataObjectParser.DataObject(
                DataObjectParser.DataObject.Type.RESPONSE,
                "CreateResponse",
                "ApiV1CreateUserResponse",
                List.of(
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "id",
                                "id",
                                true,
                                "int",
                                null
                        )
                ),
                new ArrayList<>(),
                null
        );
        expected[2] = new DataObjectParser.DataObject(
                DataObjectParser.DataObject.Type.OBJECT,
                "FullName",
                "ApiV1FullName",
                List.of(
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "firstName",
                                "first_name",
                                true,
                                "string",
                                null
                        ),
                        new DataObjectParser.DataObject.Property(
                                DataObjectParser.DataObject.Property.Source.JSON,
                                "lastName",
                                "last_name",
                                true,
                                "string",
                                null
                        )
                ),
                new ArrayList<>(),
                null
        );
        expected[3] = new DataObjectParser.DataObject(
                DataObjectParser.DataObject.Type.ENUM,
                "UserType",
                "ApiV1UserType",
                new ArrayList<>(),
                List.of(
                        new DataObjectParser.DataObject.Case(
                                "CLIENT",
                                "client",
                                null
                        ),
                        new DataObjectParser.DataObject.Case(
                                "PARTNER",
                                "partner",
                                null
                        )
                ),
                null
        );

        return expected;
    }

    private String createSource() {
        return """
                request<ApiV1CreateUserRequest> CreateRequest // user creation request
                Path<user_type> UserType userType
                Json string username
                Json string email
                Json string password
                Json<full_name> FullName fullname

                response<ApiV1CreateUserResponse> CreateResponse // succeed user created
                Json int id

                object<ApiV1FullName> FullName
                Json<first_name> string firstName
                Json<last_name> string lastName
                                
                enum<ApiV1UserType> UserType
                Case<client> CLIENT
                Case<partner> PARTNER
                """
                ;
    }
}
