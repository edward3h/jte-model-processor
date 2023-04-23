package org.ethelred.jte.model;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@FunctionalInterface
public interface JteModel {
    default void render(OutputStream outputStream, Charset charset) {
        var writer = new OutputStreamWriter(outputStream, charset);
        render(writer);
    }

    default void render(OutputStream outputStream) {
        render(outputStream, StandardCharsets.UTF_8);
    }

    default String render() {
        var writer = new StringWriter();
        render(writer);
        return writer.toString();
    }

    void render(Writer writer);
}
