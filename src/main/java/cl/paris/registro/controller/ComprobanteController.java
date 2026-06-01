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

@RestController
@RequestMapping("/api/v1/comprobantes")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComprobanteResponse emitir(@Valid @RequestBody EmitirComprobanteRequest request) {
        return ComprobanteMapper.toResponse(comprobanteService.emitir(request));
    }

    @GetMapping("/{id}")
    public ComprobanteResponse obtener(@PathVariable Long id) {
        return ComprobanteMapper.toResponse(comprobanteService.obtener(id));
    }

    @GetMapping("/venta/{ventaId}")
    public ComprobanteResponse obtenerPorVenta(@PathVariable Long ventaId) {
        return ComprobanteMapper.toResponse(comprobanteService.obtenerPorVenta(ventaId));
    }

    @GetMapping("/cliente/{clienteId}")
    public List<ComprobanteResponse> obtenerPorCliente(@PathVariable UUID clienteId) {
        return comprobanteService.obtenerPorCliente(clienteId).stream()
                .map(ComprobanteMapper::toResponse)
                .toList();
    }

    @PatchMapping("/{id}/anular")
    public ComprobanteResponse anular(@PathVariable Long id) {
        return ComprobanteMapper.toResponse(comprobanteService.anular(id));
    }
}
