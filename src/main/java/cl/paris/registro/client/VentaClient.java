package cl.paris.registro.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.paris.registro.dto.VentaResponse;
import cl.paris.registro.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

/** Consume el microservicio "ventas" para obtener la venta a facturar. */
@Component
public class VentaClient {

    private final WebClient webClient;

    public VentaClient(@Qualifier("ventasWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /** Trae la venta por id; lanza 404 si no existe. */
    public VentaResponse obtenerVenta(Long ventaId) {
        return webClient.get()
                .uri("/api/v1/ventas/{id}", ventaId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        Mono.error(new ResourceNotFoundException(
                                "Venta " + ventaId + " no encontrada en el servicio de ventas")))
                .bodyToMono(VentaResponse.class)
                .block();
    }
}
