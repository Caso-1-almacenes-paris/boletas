package cl.paris.registro.dto;

import cl.paris.registro.model.MedioPago;
import jakarta.validation.constraints.NotNull;

public record EmitirComprobanteRequest(

        @NotNull(message = "El ventaId es obligatorio")
        Long ventaId,

        @NotNull(message = "El medioPago es obligatorio (TARJETA, DEBITO o TRANSFERENCIA)")
        MedioPago medioPago
) {
}
