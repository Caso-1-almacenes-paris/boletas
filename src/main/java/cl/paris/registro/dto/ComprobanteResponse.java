package cl.paris.registro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import cl.paris.registro.model.EstadoPago;
import cl.paris.registro.model.MedioPago;

public record ComprobanteResponse(
        Long id,
        Long ventaId,
        String numero,
        UUID clienteId,
        BigDecimal montoTotal,
        MedioPago medioPago,
        EstadoPago estadoPago,
        LocalDateTime fechaEmision,
        List<ComprobanteDetalleResponse> detalles
) {
}
