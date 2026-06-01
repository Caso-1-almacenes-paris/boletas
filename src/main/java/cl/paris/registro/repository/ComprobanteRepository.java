package cl.paris.registro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.paris.registro.model.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

    // Query methods
    Optional<Comprobante> findByVentaId(Long ventaId);

    List<Comprobante> findByClienteId(UUID clienteId);

    boolean existsByVentaId(Long ventaId);
}
