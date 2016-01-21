package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EstadisticasJugadorPartido;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EstadisticasJugadorPartido entity.
 */
public interface EstadisticasJugadorPartidoRepository extends JpaRepository<EstadisticasJugadorPartido,Long> {

}
