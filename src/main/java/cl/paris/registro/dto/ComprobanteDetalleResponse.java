package cl.paris.registro.dto;

import java.math.BigDecimal;

public record ComprobanteDetalleResponse(
        Long productoId,
        String descripcion,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
