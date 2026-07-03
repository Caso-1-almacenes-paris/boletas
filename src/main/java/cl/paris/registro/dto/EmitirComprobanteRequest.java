@Operation(summary = "Emitir comprobante", description = "Crea y emite un nuevo comprobante en el sistema.")
    @ApiResponse(responseCode = "201", description = "Comprobante emitido exitosamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComprobanteResponse emitir(
            @Valid 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Cuerpo de la solicitud para emitir un comprobante",
                content = @Content(
                    examples = @ExampleObject(
                        name = "Ejemplo de Request",
                        value = "{\n  \"ventaId\": 1045,\n  \"medioPago\": \"TARJETA\"\n}"
                    )
                )
            )
            @RequestBody EmitirComprobanteRequest request) {
        return ComprobanteMapper.toResponse(comprobanteService.emitir(request));
    }