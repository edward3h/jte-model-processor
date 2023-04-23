package org.ethelred.jte.processor;

import io.soabase.recordbuilder.core.RecordBuilder;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

@RecordBuilder
@RecordBuilder.Options(useImmutableCollections = true, addSingleItemCollectionBuilders = true)
public record Renderer(
        String templateFile,
        String templateName,
        String packageName,
        String className,
        List<RendererParam> params,
        TypeElement originator
) {
    private static final String INDENT3 = "            ";
    List<String> parameterNames() {
        return params.stream().map(RendererParam::name).toList();
    }

    List<String> typedParameters() {
        return params.stream().map(RendererParam::typedParameter).toList();
    }

    String parametersAsMap() {
        return params.stream()
                .map(RendererParam::asEntry)
                .collect(Collectors.joining(",\n" + INDENT3, "Map.ofEntries(\n" + INDENT3, "\n        )"));
    }

    public String fqcn() {
        return packageName + "." + className;
    }
}
