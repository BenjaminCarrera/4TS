package com.kode.ts.web.rest;

import com.kode.ts.service.TipoSolicitudService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TipoSolicitudDTO;
import com.kode.ts.service.dto.TipoSolicitudCriteria;
import com.kode.ts.service.TipoSolicitudQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kode.ts.domain.TipoSolicitud}.
 */
@RestController
@RequestMapping("/api")
public class TipoSolicitudResource {

    private final Logger log = LoggerFactory.getLogger(TipoSolicitudResource.class);

    private static final String ENTITY_NAME = "tipoSolicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoSolicitudService tipoSolicitudService;

    private final TipoSolicitudQueryService tipoSolicitudQueryService;

    public TipoSolicitudResource(TipoSolicitudService tipoSolicitudService, TipoSolicitudQueryService tipoSolicitudQueryService) {
        this.tipoSolicitudService = tipoSolicitudService;
        this.tipoSolicitudQueryService = tipoSolicitudQueryService;
    }

    /**
     * {@code POST  /tipo-solicituds} : Create a new tipoSolicitud.
     *
     * @param tipoSolicitudDTO the tipoSolicitudDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoSolicitudDTO, or with status {@code 400 (Bad Request)} if the tipoSolicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-solicituds")
    public ResponseEntity<TipoSolicitudDTO> createTipoSolicitud(@Valid @RequestBody TipoSolicitudDTO tipoSolicitudDTO) throws URISyntaxException {
        log.debug("REST request to save TipoSolicitud : {}", tipoSolicitudDTO);
        if (tipoSolicitudDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoSolicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSolicitudDTO result = tipoSolicitudService.save(tipoSolicitudDTO);
        return ResponseEntity.created(new URI("/api/tipo-solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-solicituds} : Updates an existing tipoSolicitud.
     *
     * @param tipoSolicitudDTO the tipoSolicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSolicitudDTO,
     * or with status {@code 400 (Bad Request)} if the tipoSolicitudDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoSolicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-solicituds")
    public ResponseEntity<TipoSolicitudDTO> updateTipoSolicitud(@Valid @RequestBody TipoSolicitudDTO tipoSolicitudDTO) throws URISyntaxException {
        log.debug("REST request to update TipoSolicitud : {}", tipoSolicitudDTO);
        if (tipoSolicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoSolicitudDTO result = tipoSolicitudService.save(tipoSolicitudDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoSolicitudDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-solicituds} : get all the tipoSolicituds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoSolicituds in body.
     */
    @GetMapping("/tipo-solicituds")
    public ResponseEntity<List<TipoSolicitudDTO>> getAllTipoSolicituds(TipoSolicitudCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TipoSolicituds by criteria: {}", criteria);
        Page<TipoSolicitudDTO> page = tipoSolicitudQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-solicituds/count} : count all the tipoSolicituds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-solicituds/count")
    public ResponseEntity<Long> countTipoSolicituds(TipoSolicitudCriteria criteria) {
        log.debug("REST request to count TipoSolicituds by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoSolicitudQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-solicituds/:id} : get the "id" tipoSolicitud.
     *
     * @param id the id of the tipoSolicitudDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoSolicitudDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-solicituds/{id}")
    public ResponseEntity<TipoSolicitudDTO> getTipoSolicitud(@PathVariable Long id) {
        log.debug("REST request to get TipoSolicitud : {}", id);
        Optional<TipoSolicitudDTO> tipoSolicitudDTO = tipoSolicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoSolicitudDTO);
    }

    /**
     * {@code DELETE  /tipo-solicituds/:id} : delete the "id" tipoSolicitud.
     *
     * @param id the id of the tipoSolicitudDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-solicituds/{id}")
    public ResponseEntity<Void> deleteTipoSolicitud(@PathVariable Long id) {
        log.debug("REST request to delete TipoSolicitud : {}", id);
        tipoSolicitudService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
