package cl.paris.registro.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.paris.registro.dto.ComprobanteResponse;
import cl.paris.registro.dto.EmitirComprobanteRequest;
import cl.paris.registro.mapper.ComprobanteMapper;
import cl.paris.registro.service.ComprobanteService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;

@Tag(name = "Comprobantes", description = "API para la gestión y emisión de comprobantes")
@RestController
@RequestMapping("/api/v1/comprobantes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

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

    @Operation(summary = "Obtener comprobante", description = "Busca un comprobante específico mediante su ID.")
    @ApiResponse(responseCode = "200", description = "Comprobante encontrado y retornado")
    @GetMapping("/{id}")
    public ComprobanteResponse obtener(@PathVariable Long id) {
        return ComprobanteMapper.toResponse(comprobanteService.obtener(id));
    }

    @Operation(summary = "Obtener por venta", description = "Busca el comprobante asociado al ID de una venta específica.")
    @ApiResponse(responseCode = "200", description = "Comprobante de la venta encontrado")
    @GetMapping("/venta/{ventaId}")
    public ComprobanteResponse obtenerPorVenta(@PathVariable Long ventaId) {
        return ComprobanteMapper.toResponse(comprobanteService.obtenerPorVenta(ventaId));
    }

    @Operation(summary = "Obtener por cliente", description = "Obtiene todo el historial de comprobantes asociados a un cliente.")
    @ApiResponse(responseCode = "200", description = "Lista de comprobantes del cliente retornada")
    @GetMapping("/cliente/{clienteId}")
    public List<ComprobanteResponse> obtenerPorCliente(@PathVariable UUID clienteId) {
        return comprobanteService.obtenerPorCliente(clienteId).stream()
                .map(ComprobanteMapper::toResponse)
                .toList();
    }

    @Operation(summary = "Anular comprobante", description = "Cambia el estado de un comprobante existente a anulado.")
    @ApiResponse(responseCode = "200", description = "Comprobante anulado exitosamente")
    @PatchMapping("/{id}/anular")
    public ComprobanteResponse anular(@PathVariable Long id) {
        return ComprobanteMapper.toResponse(comprobanteService.anular(id));
    }
}