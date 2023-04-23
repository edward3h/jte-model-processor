package org.ethelred.jte.model;

import gg.jte.TemplateEngine;
import gg.jte.output.WriterOutput;

import java.io.Writer;
import java.util.Map;

public class DynamicJteModel implements JteModel {
    private final TemplateEngine templateEngine;
    private final String name;
    private final Map<String, Object> parameters;

    public DynamicJteModel(TemplateEngine templateEngine, String name, Map<String, Object> parameters) {
        this.templateEngine = templateEngine;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public void render(Writer writer) {
        templateEngine.render(name, parameters, new WriterOutput(writer));
    }
}
