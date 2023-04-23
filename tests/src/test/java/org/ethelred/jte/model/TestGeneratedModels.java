package org.ethelred.jte.model;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.generated.precompiled.DynamicTemplates;
import gg.jte.generated.precompiled.Templates;
import gg.jte.resolve.DirectoryCodeResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGeneratedModels {
    static Templates staticTemplates = Templates.defaultInstance();
    static Templates dynamic = new DynamicTemplates(
            TemplateEngine.create(new DirectoryCodeResolver(Path.of("src/main/jte")), ContentType.Html)
    );

    public static Stream<Arguments> eachTemplates() {
        return Stream.of(Arguments.of(staticTemplates), Arguments.of(dynamic));
    }

    @ParameterizedTest
    @MethodSource("eachTemplates")
    void renderFoo(Templates templates) {
        var model = templates.foo(42);
        assertEquals("""

                <div>Foo 42</div>
                """, model.render());
    }

    @ParameterizedTest
    @MethodSource("eachTemplates")
    void renderBar(Templates templates) {
        // TODO varargs parameter doesn't work in dynamic mode. See https://github.com/casid/jte/issues/211
        if (templates.getClass().getSimpleName().startsWith("Dynamic")) {
            return;
        }
        var model = templates.bar("Arthur", LocalDate.of(1977, 12, 12));
        assertEquals("""
                                
                <div>Hello, Arthur</div>
                                
                 <div>1977-12-12</div>
                """, model.render());
    }
}
