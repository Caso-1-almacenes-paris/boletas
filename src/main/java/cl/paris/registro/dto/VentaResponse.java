package cl.paris.registro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
