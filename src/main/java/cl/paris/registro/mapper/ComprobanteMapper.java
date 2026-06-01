package cl.paris.registro.mapper;

import java.util.List;

import cl.paris.registro.dto.ComprobanteDetalleResponse;
import cl.paris.registro.dto.ComprobanteResponse;
import cl.paris.registro.model.Comprobante;
import cl.paris.registro.model.ComprobanteDetalle;

public final class ComprobanteMapper {

    private ComprobanteMapper() {
    }

    public static ComprobanteResponse toResponse(Comprobante comprobante) {
        List<ComprobanteDetalleResponse> detalles = comprobante.getDetalles().stream()
                .map(ComprobanteMapper::toDetalleResponse)
                .toList();

        return new ComprobanteResponse(
                comprobante.getId(),
                comprobante.getVentaId(),
                comprobante.getNumero(),
                comprobante.getClienteId(),
                comprobante.getMontoTotal(),
                comprobante.getMedioPago(),
                comprobante.getEstadoPago(),
                comprobante.getFechaEmision(),
                detalles
        );
    }

    private static ComprobanteDetalleResponse toDetalleResponse(ComprobanteDetalle detalle) {
        return new ComprobanteDetalleResponse(
                detalle.getProductoId(),
                detalle.getDescripcion(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}
