package ru.airux.codegen.handlerbuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class DataObjectParser {
    public List<DataObject> parse(String content) throws Exception {
        var reader = new ReaderWrapper(content);
        var dataObjects = new ArrayList<DataObject>();
        var tokenBuilder = new StringBuilder();
        DataObject dataObject = null;
        DataObjectToken dataObjectToken;
        PropertyToken propertyToken;
        CaseToken caseToken;

        int code;
        char ch;
        while (-1 != (code = reader.read())) {
            ch = (char) code;
            tokenBuilder.append(ch);
            if ('\n' == ch && !tokenBuilder.isEmpty()) {
                dataObjectToken = resolveDataObjectToken(tokenBuilder.toString());
                if (null != dataObjectToken) {
                    dataObject = new DataObject(
                            resolveDataObject(dataObjectToken.type),
                            dataObjectToken.name,
                            dataObjectToken.schemaName,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            null
                    );
                    dataObjects.add(dataObject);
                }
                caseToken = resolveCaseToken(tokenBuilder.toString());
                if (null != dataObject && null != caseToken) {
                    dataObject.cases.add(
                            new DataObject.Case(
                                    caseToken.name,
                                    caseToken.schemaName,
                                    caseToken.comment
                            )
                    );
                }


                propertyToken = resolvePropertyToken(tokenBuilder.toString());
                if (null != dataObject && null != propertyToken) {
                    dataObject.properties.add(
                            new DataObject.Property(
                                    resolvePropertySource(propertyToken.source),
                                    propertyToken.name,
                                    null == propertyToken.serializedName ? propertyToken.name : propertyToken.serializedName,
                                    !propertyToken.nullable,
                                    propertyToken.type,
                                    propertyToken.comment
                            )
                    );
                }

                tokenBuilder.setLength(0);
            }
        }

        return dataObjects;
    }


    private CaseToken resolveCaseToken(String token) {
        var pattern = Pattern.compile("Case(<([a-zA-Z0-9_]+)>)? +([a-zA-Z0-9_]+)( *//([.*]))?");
        var matcher = pattern.matcher(token);
        if (matcher.find()) {
            return new CaseToken(
                    matcher.group(3),
                    matcher.group(2),
                    matcher.group(5)
            );
        }

        return null;
    }


    private PropertyToken resolvePropertyToken(String token) {
        var pattern = Pattern.compile("(Path|Query|Json)(<([a-zA-Z0-9_]+)>)? +(\\?)?(int|string|bool|[a-zA-Z0-9_]+) +([a-zA-Z0-9_]+)( *//([.*]))?");
        var matcher = pattern.matcher(token);
        if (matcher.find()) {
            return new PropertyToken(
                    matcher.group(1),
                    matcher.group(3),
                    matcher.group(4) != null,
                    matcher.group(5),
                    matcher.group(6),
                    matcher.group(8)
            );
        }

        return null;
    }

    private DataObjectToken resolveDataObjectToken(String token) {
        var pattern = Pattern.compile("(request|response|object|enum)(<([a-zA-Z0-9_]+)>)?( ([a-zA-Z0-9_]+))?(//([.*]))?");
        var matcher = pattern.matcher(token);
        if (matcher.find()) {
            return new DataObjectToken(
                    matcher.group(1),
                    matcher.group(3),
                    matcher.group(5),
                    matcher.group(7)
            );
        }

        return null;
    }

    private DataObject.Type resolveDataObject(String typeToken) {
        return switch (typeToken) {
            case "request" -> DataObject.Type.REQUEST;
            case "response" -> DataObject.Type.RESPONSE;
            case "enum" -> DataObject.Type.ENUM;
            case "object" -> DataObject.Type.OBJECT;
            default -> throw new IllegalStateException("Unexpected value: " + typeToken);
        };
    }

    private DataObject.Property.Source resolvePropertySource(String source) {
        return switch (source) {
            case "Json" -> DataObject.Property.Source.JSON;
            case "Path" -> DataObject.Property.Source.PATH;
            case "Query" -> DataObject.Property.Source.QUERY;
            default -> throw new IllegalStateException("Unexpected value: " + source);
        };
    }

    private record DataObjectToken(
            String type,
            String schemaName,
            String name,
            String comment
    ) {
    }

    private record CaseToken(
            String name,
            String schemaName,
            String comment
    ) {
    }

    private record PropertyToken(
            String source,
            String serializedName,
            boolean nullable,
            String type,
            String name,
            String comment
    ) {
    }

    private static class ReaderWrapper extends StringReader {
        private final AtomicInteger line = new AtomicInteger();

        public ReaderWrapper(String s) {
            super(s);
        }

        @Override
        public int read() throws IOException {
            var code = super.read();
            if (-1 != code && '\n' == (char) code) {
                line.incrementAndGet();
            }

            return code;
        }
    }

    public record DataObject(
            Type type,
            String name,
            String schemaName,
            List<Property> properties,
            List<Case> cases,
            String comment
    ) {
        public enum Type {
            REQUEST, RESPONSE, ENUM, OBJECT
        }

        public record Case(
                String name,
                String schemaName,
                String comment
        ) {
        }

        public record Property(
                Source source,
                String name,
                String schemaName,
                boolean required,
                String type,
                String comment
        ) {
            enum Source {
                PATH, QUERY, JSON
            }
        }
    }
}
