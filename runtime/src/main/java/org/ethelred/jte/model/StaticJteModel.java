package org.ethelred.jte.model;

import gg.jte.html.HtmlInterceptor;
import gg.jte.html.HtmlTemplateOutput;
import gg.jte.html.OwaspHtmlTemplateOutput;
import gg.jte.output.WriterOutput;

import java.io.Writer;

@FunctionalInterface
public interface StaticJteModel extends JteModel {
    void render(HtmlTemplateOutput output, HtmlInterceptor interceptor);

    @Override
    default void render(Writer writer) {
        var output = new OwaspHtmlTemplateOutput(new WriterOutput(writer));
        render(output, null);
    }
}
