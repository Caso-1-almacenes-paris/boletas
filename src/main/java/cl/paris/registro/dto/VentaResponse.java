package cl.paris.registro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espejo de la respuesta del servicio "ventas" (GET /api/v1/ventas/{id}).
 * El estado se modela como String para no acoplar enums entre servicios;
 * registro solo necesita comparar contra "PAGADA".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VentaResponse(
        Long id,
        UUID clienteId,
        String estado,
        BigDecimal montoTotal,
        LocalDateTime fecha,
        List<VentaDetalleResponse> detalles
) {
}
