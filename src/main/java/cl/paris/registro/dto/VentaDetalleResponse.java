package cl.paris.registro.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Espejo de un detalle de la respuesta del servicio "ventas". */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VentaDetalleResponse(
        Long productoId,
        Long proveedorId,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
