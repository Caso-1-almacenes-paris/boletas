package cl.paris.registro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.paris.registro.client.VentaClient;
import cl.paris.registro.dto.EmitirComprobanteRequest;
import cl.paris.registro.dto.VentaDetalleResponse;
import cl.paris.registro.dto.VentaResponse;
import cl.paris.registro.exception.BusinessException;
import cl.paris.registro.exception.ResourceNotFoundException;
import cl.paris.registro.model.Comprobante;
import cl.paris.registro.model.ComprobanteDetalle;
import cl.paris.registro.model.EstadoPago;
import cl.paris.registro.repository.ComprobanteRepository;

@Service
public class ComprobanteService {

    private static final String ESTADO_VENTA_PAGADA = "PAGADA";

    private final ComprobanteRepository comprobanteRepository;
    private final VentaClient ventaClient;

    public ComprobanteService(ComprobanteRepository comprobanteRepository, VentaClient ventaClient) {
        this.comprobanteRepository = comprobanteRepository;
        this.ventaClient = ventaClient;
    }

    @Transactional
    public Comprobante emitir(EmitirComprobanteRequest request) {
        VentaResponse venta = ventaClient.obtenerVenta(request.ventaId());

        if (!ESTADO_VENTA_PAGADA.equalsIgnoreCase(venta.estado())) {
            throw new BusinessException(
                    "No se puede emitir comprobante: la venta " + venta.id()
                            + " no esta PAGADA (estado: " + venta.estado() + ")");
        }


        if (comprobanteRepository.existsByVentaId(venta.id())) {
            throw new BusinessException(
                    "La venta " + venta.id() + " ya tiene un comprobante emitido");
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setVentaId(venta.id());
        comprobante.setNumero("CMP-" + venta.id());
        comprobante.setClienteId(venta.clienteId());
        comprobante.setMontoTotal(venta.montoTotal());
        comprobante.setMedioPago(request.medioPago());
        comprobante.setEstadoPago(EstadoPago.PAGADO);
        comprobante.setFechaEmision(LocalDateTime.now());

        if (venta.detalles() != null) {
            for (VentaDetalleResponse item : venta.detalles()) {
                ComprobanteDetalle detalle = new ComprobanteDetalle();
                detalle.setProductoId(item.productoId());
                detalle.setDescripcion(item.nombreProducto());
                detalle.setCantidad(item.cantidad());
                detalle.setPrecioUnitario(item.precioUnitario());
                detalle.setSubtotal(item.subtotal());
                comprobante.agregarDetalle(detalle);
            }
        }

        return comprobanteRepository.save(comprobante);
    }

    @Transactional(readOnly = true)
    public Comprobante obtener(Long id) {
        return comprobanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprobante " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public Comprobante obtenerPorVenta(Long ventaId) {
        return comprobanteRepository.findByVentaId(ventaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe comprobante para la venta " + ventaId));
    }

    @Transactional(readOnly = true)
    public List<Comprobante> obtenerPorCliente(UUID clienteId) {
        return comprobanteRepository.findByClienteId(clienteId);
    }

    @Transactional
    public Comprobante anular(Long id) {
        Comprobante comprobante = obtener(id);
        if (comprobante.getEstadoPago() == EstadoPago.ANULADO) {
            throw new BusinessException("El comprobante " + id + " ya esta anulado");
        }
        comprobante.setEstadoPago(EstadoPago.ANULADO);
        return comprobanteRepository.save(comprobante);
    }
}
