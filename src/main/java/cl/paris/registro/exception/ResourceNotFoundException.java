package cl.paris.registro.exception;

/** Se lanza cuando un recurso (comprobante, venta) no existe. -> HTTP 404 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
