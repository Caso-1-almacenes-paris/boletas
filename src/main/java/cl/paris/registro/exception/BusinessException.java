package cl.paris.registro.exception;

/** Se lanza al violar una regla de negocio (venta no pagada, comprobante duplicado). -> HTTP 422 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
