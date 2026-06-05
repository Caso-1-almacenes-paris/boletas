# registro

Microservicio de **comprobante de pago** del marketplace Paris (Caso 1, DSY1103).

## Responsabilidad
Emitir y almacenar comprobantes de las ventas pagadas. Consume el servicio `ventas`
para obtener los datos de la venta y exige que esté en estado `PAGADA`.

## Puerto
`8086`

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/v1/comprobantes` | Emite un comprobante a partir de una venta pagada |
| GET | `/api/v1/comprobantes/{id}` | Obtiene un comprobante |
| GET | `/api/v1/comprobantes/venta/{ventaId}` | Comprobante de una venta |
| GET | `/api/v1/comprobantes/cliente/{clienteId}` | Comprobantes de un cliente |
| PATCH | `/api/v1/comprobantes/{id}/anular` | Anula un comprobante |

### Ejemplo POST `/api/v1/comprobantes`
```json
{
  "ventaId": 1001,
  "medioPago": "TARJETA"
}
```

## Reglas de negocio
- La venta debe existir (validación cruzada vía WebClient a `ventas`).
- La venta debe estar en estado `PAGADA` (si no → 422).
- Una venta no puede tener más de un comprobante (si no → 422).

## Comunicación (WebClient)
- `GET ventas/api/v1/ventas/{ventaId}` → datos de la venta a facturar.

## Base de datos
BD **independiente** en Neon (`registrodb`). Variables de entorno:
```
REGISTRO_DB_URL, REGISTRO_DB_USER, REGISTRO_DB_PASS
VENTAS_URL (default http://localhost:8085)
```

## Ejecutar
```bash
./mvnw spring-boot:run
```

## Modelo de datos
```
comprobante (1) ──< (N) comprobante_detalle
```
- `MedioPago`: TARJETA, DEBITO, TRANSFERENCIA
- `EstadoPago`: PAGADO, ANULADO
- Folio: `CMP-<ventaId>`
