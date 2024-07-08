@param ru.airux.codegen.handlerbuilder.view.php.HandlerViewModel handler

<?php

declare(strict_types=1);

namespace ${handler.namespace()};

use OpenApi\Attributes as OA;

interface ${handler.name()}
{
    #[OA\${handler.methodAnnotation()}(
        path: '${handler.url()}',
        requestBody: new OA\RequestBody(
@if(null != handler.requestClassName())
            content: new OA\JsonContent(
                ref: ${handler.requestClassName()}::class
            )
@endif
        ))]
    #[OA\Response(
        response: 200,
        description: "Company and administrator created with default settings",
        content: new OA\JsonContent(
            @if(null != handler.responseClassName())ref: ${handler.responseClassName()}::class@endif
        ))]
    public function handle(@if(null != handler.requestClassName())Request $request@endif): @if(null != handler.responseClassName())${handler.responseClassName()}@elsevoid@endif;
}
