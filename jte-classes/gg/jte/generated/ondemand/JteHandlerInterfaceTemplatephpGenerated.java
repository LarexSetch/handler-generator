package gg.jte.generated.ondemand;
public final class JteHandlerInterfaceTemplatephpGenerated {
	public static final String JTE_NAME = "HandlerInterfaceTemplate.php.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,6,6,6,10,10,12,12,13,13,15,15,17,17,19,19,25,25,25,25,25,25,27,27,27,27,27,27,27,27,27,27,29,29,29,0,0,0,0};
	public static void render(gg.jte.TemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, ru.airux.codegen.handlerbuilder.view.php.HandlerViewModel handler) {
		jteOutput.writeContent("\n<?php\n\ndeclare(strict_types=1);\n\nnamespace ");
		jteOutput.writeUserContent(handler.namespace());
		jteOutput.writeContent(";\n\nuse OpenApi\\Attributes as OA;\n\ninterface ");
		jteOutput.writeUserContent(handler.name());
		jteOutput.writeContent("\n{\n    #[OA\\");
		jteOutput.writeUserContent(handler.methodAnnotation());
		jteOutput.writeContent("(\n        path: '");
		jteOutput.writeUserContent(handler.url());
		jteOutput.writeContent("',\n        requestBody: new OA\\RequestBody(\n");
		if (null != handler.requestClassName()) {
			jteOutput.writeContent("\n            content: new OA\\JsonContent(\n                ref: ");
			jteOutput.writeUserContent(handler.requestClassName());
			jteOutput.writeContent("::class\n            )\n");
		}
		jteOutput.writeContent("\n        ))]\n    #[OA\\Response(\n        response: 200,\n        description: \"Company and administrator created with default settings\",\n        content: new OA\\JsonContent(\n            ");
		if (null != handler.responseClassName()) {
			jteOutput.writeContent("ref: ");
			jteOutput.writeUserContent(handler.responseClassName());
			jteOutput.writeContent("::class");
		}
		jteOutput.writeContent("\n        ))]\n    public function handle(");
		if (null != handler.requestClassName()) {
			jteOutput.writeContent("Request $request");
		}
		jteOutput.writeContent("): ");
		if (null != handler.responseClassName()) {
			jteOutput.writeUserContent(handler.responseClassName());
		} else {
			jteOutput.writeContent("void");
		}
		jteOutput.writeContent(";\n}\n");
	}
	public static void renderMap(gg.jte.TemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		ru.airux.codegen.handlerbuilder.view.php.HandlerViewModel handler = (ru.airux.codegen.handlerbuilder.view.php.HandlerViewModel)params.get("handler");
		render(jteOutput, jteHtmlInterceptor, handler);
	}
}
