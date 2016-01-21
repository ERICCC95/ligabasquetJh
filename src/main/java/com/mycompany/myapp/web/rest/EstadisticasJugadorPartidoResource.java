package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.EstadisticasJugadorPartido;
import com.mycompany.myapp.repository.EstadisticasJugadorPartidoRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EstadisticasJugadorPartido.
 */
@RestController
@RequestMapping("/api")
public class EstadisticasJugadorPartidoResource {

    private final Logger log = LoggerFactory.getLogger(EstadisticasJugadorPartidoResource.class);

    @Inject
    private EstadisticasJugadorPartidoRepository estadisticasJugadorPartidoRepository;

    /**
     * POST  /estadisticasJugadorPartidos -> Create a new estadisticasJugadorPartido.
     */
    @RequestMapping(value = "/estadisticasJugadorPartidos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EstadisticasJugadorPartido> create(@RequestBody EstadisticasJugadorPartido estadisticasJugadorPartido) throws URISyntaxException {
        log.debug("REST request to save EstadisticasJugadorPartido : {}", estadisticasJugadorPartido);
        if (estadisticasJugadorPartido.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new estadisticasJugadorPartido cannot already have an ID").body(null);
        }
        EstadisticasJugadorPartido result = estadisticasJugadorPartidoRepository.save(estadisticasJugadorPartido);
        return ResponseEntity.created(new URI("/api/estadisticasJugadorPartidos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("estadisticasJugadorPartido", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /estadisticasJugadorPartidos -> Updates an existing estadisticasJugadorPartido.
     */
    @RequestMapping(value = "/estadisticasJugadorPartidos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EstadisticasJugadorPartido> update(@RequestBody EstadisticasJugadorPartido estadisticasJugadorPartido) throws URISyntaxException {
        log.debug("REST request to update EstadisticasJugadorPartido : {}", estadisticasJugadorPartido);
        if (estadisticasJugadorPartido.getId() == null) {
            return create(estadisticasJugadorPartido);
        }
        EstadisticasJugadorPartido result = estadisticasJugadorPartidoRepository.save(estadisticasJugadorPartido);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("estadisticasJugadorPartido", estadisticasJugadorPartido.getId().toString()))
                .body(result);
    }

    /**
     * GET  /estadisticasJugadorPartidos -> get all the estadisticasJugadorPartidos.
     */
    @RequestMapping(value = "/estadisticasJugadorPartidos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EstadisticasJugadorPartido>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<EstadisticasJugadorPartido> page = estadisticasJugadorPartidoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estadisticasJugadorPartidos", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estadisticasJugadorPartidos/:id -> get the "id" estadisticasJugadorPartido.
     */
    @RequestMapping(value = "/estadisticasJugadorPartidos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EstadisticasJugadorPartido> get(@PathVariable Long id) {
        log.debug("REST request to get EstadisticasJugadorPartido : {}", id);
        return Optional.ofNullable(estadisticasJugadorPartidoRepository.findOne(id))
            .map(estadisticasJugadorPartido -> new ResponseEntity<>(
                estadisticasJugadorPartido,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /estadisticasJugadorPartidos/:id -> delete the "id" estadisticasJugadorPartido.
     */
    @RequestMapping(value = "/estadisticasJugadorPartidos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete EstadisticasJugadorPartido : {}", id);
        estadisticasJugadorPartidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("estadisticasJugadorPartido", id.toString())).build();
    }
}
