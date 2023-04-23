package org.ethelred.jte.processor;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record RendererParam(
    String type,
    String name,
    boolean varargs
) {
    String typedParameter() {
        var replaceType = type;
        if (varargs && type.endsWith("[]")) {
            replaceType = type.substring(0, type.length() - 2) + "...";
        }
        return replaceType + " " + name;
    }

    String asEntry() {
        return "entry(\"%s\", %<s)".formatted(name);
    }
}
