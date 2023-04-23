package org.ethelred.jte.processor;

import java.util.List;
import java.util.stream.Collectors;

public class FacadeGenerator {
    private final String packageName;
    private final List<Renderer> models;

    public FacadeGenerator(String packageName, List<Renderer> models) {
        this.packageName = packageName;

        this.models = models;
    }

    String interfaceName() {
        return "Templates";
    }

    public String generateInterface() {
        return """
package %s;

import javax.annotation.processing.Generated;
import org.ethelred.jte.model.JteModel;

@Generated("%s")
public interface %s {
    static %<s defaultInstance() {
        return new %s();
    }
%s
}
                """
                .formatted(packageName, this.getClass().getName(), interfaceName(), staticClassname(), interfaceMethods());
    }

    String staticClassname() {
        return "Static" + interfaceName();
    }

    String dynamicClassname() {
        return "Dynamic" + interfaceName();
    }

    private String interfaceMethods() {
        return models.stream()
                .map(this::interfaceMethod)
                .collect(Collectors.joining("\n"));
    }

    private String interfaceMethod(Renderer model) {
        return "JteModel %s(%s);".formatted(model.templateName(), String.join(", ", model.typedParameters()));
    }

    private String staticMethod(Renderer model) {
        return """
                @Override
                public JteModel %s(%s) {
                    StaticJteModel renderFunction = (output, interceptor) -> %s.render(output, interceptor, %s);
                    return renderFunction;
                }""".formatted(model.templateName(), String.join(", ", model.typedParameters()), model.fqcn(), String.join(", ", model.parameterNames()));
    }
    private String dynamicMethod(Renderer model) {
        return """
                @Override
                public JteModel %s(%s) {
                    return new DynamicJteModel(templateEngine, "%s",
                        %s);
                }""".formatted(model.templateName(), String.join(", ", model.typedParameters()), model.templateFile(), model.parametersAsMap());
    }
    public String generateDynamic() {
        return """
package %s;

import java.util.Map;
import javax.annotation.processing.Generated;
import gg.jte.TemplateEngine;
import org.ethelred.jte.model.*;
import static java.util.Map.entry;

@Generated("%s")
public class %s implements %s {
    private final TemplateEngine templateEngine;
    
    public %3$s(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
%s
}
                """
                .formatted(packageName, this.getClass().getName(), dynamicClassname(), interfaceName(), dynamicMethods());

    }

    private String dynamicMethods() {
        return models.stream().map(this::dynamicMethod).collect(Collectors.joining("\n"));
    }

    public String generateStatic() {
        return """
package %s;

import javax.annotation.processing.Generated;
import org.ethelred.jte.model.*;

@Generated("%s")
public class %s implements %s {
%s
}
                """
                .formatted(packageName, this.getClass().getName(), staticClassname(), interfaceName(), staticMethods());
    }

    private String staticMethods() {
        return models.stream().map(this::staticMethod).collect(Collectors.joining("\n"));
    }
}
