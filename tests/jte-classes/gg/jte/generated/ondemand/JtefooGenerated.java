package gg.jte.generated.ondemand;
public final class JtefooGenerated {
	public static final String JTE_NAME = "foo.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,2,2,2,2,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, int howMany) {
		jteOutput.writeContent("\n<div>Foo ");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(howMany);
		jteOutput.writeContent("</div>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		int howMany = (int)params.get("howMany");
		render(jteOutput, jteHtmlInterceptor, howMany);
	}
}
