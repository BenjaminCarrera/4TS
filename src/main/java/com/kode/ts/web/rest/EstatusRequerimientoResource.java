package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusRequerimientoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusRequerimientoDTO;
import com.kode.ts.service.dto.EstatusRequerimientoCriteria;
import com.kode.ts.service.EstatusRequerimientoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusRequerimiento}.
 */
@RestController
@RequestMapping("/api")
public class EstatusRequerimientoResource {

    private final Logger log = LoggerFactory.getLogger(EstatusRequerimientoResource.class);

    private static final String ENTITY_NAME = "estatusRequerimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusRequerimientoService estatusRequerimientoService;

    private final EstatusRequerimientoQueryService estatusRequerimientoQueryService;

    public EstatusRequerimientoResource(EstatusRequerimientoService estatusRequerimientoService, EstatusRequerimientoQueryService estatusRequerimientoQueryService) {
        this.estatusRequerimientoService = estatusRequerimientoService;
        this.estatusRequerimientoQueryService = estatusRequerimientoQueryService;
    }

    /**
     * {@code POST  /estatus-requerimientos} : Create a new estatusRequerimiento.
     *
     * @param estatusRequerimientoDTO the estatusRequerimientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusRequerimientoDTO, or with status {@code 400 (Bad Request)} if the estatusRequerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-requerimientos")
    public ResponseEntity<EstatusRequerimientoDTO> createEstatusRequerimiento(@Valid @RequestBody EstatusRequerimientoDTO estatusRequerimientoDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusRequerimiento : {}", estatusRequerimientoDTO);
        if (estatusRequerimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusRequerimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusRequerimientoDTO result = estatusRequerimientoService.save(estatusRequerimientoDTO);
        return ResponseEntity.created(new URI("/api/estatus-requerimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-requerimientos} : Updates an existing estatusRequerimiento.
     *
     * @param estatusRequerimientoDTO the estatusRequerimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusRequerimientoDTO,
     * or with status {@code 400 (Bad Request)} if the estatusRequerimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusRequerimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-requerimientos")
    public ResponseEntity<EstatusRequerimientoDTO> updateEstatusRequerimiento(@Valid @RequestBody EstatusRequerimientoDTO estatusRequerimientoDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusRequerimiento : {}", estatusRequerimientoDTO);
        if (estatusRequerimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusRequerimientoDTO result = estatusRequerimientoService.save(estatusRequerimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusRequerimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-requerimientos} : get all the estatusRequerimientos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusRequerimientos in body.
     */
    @GetMapping("/estatus-requerimientos")
    public ResponseEntity<List<EstatusRequerimientoDTO>> getAllEstatusRequerimientos(EstatusRequerimientoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusRequerimientos by criteria: {}", criteria);
        Page<EstatusRequerimientoDTO> page = estatusRequerimientoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-requerimientos/count} : count all the estatusRequerimientos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-requerimientos/count")
    public ResponseEntity<Long> countEstatusRequerimientos(EstatusRequerimientoCriteria criteria) {
        log.debug("REST request to count EstatusRequerimientos by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusRequerimientoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-requerimientos/:id} : get the "id" estatusRequerimiento.
     *
     * @param id the id of the estatusRequerimientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusRequerimientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-requerimientos/{id}")
    public ResponseEntity<EstatusRequerimientoDTO> getEstatusRequerimiento(@PathVariable Long id) {
        log.debug("REST request to get EstatusRequerimiento : {}", id);
        Optional<EstatusRequerimientoDTO> estatusRequerimientoDTO = estatusRequerimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusRequerimientoDTO);
    }

    /**
     * {@code DELETE  /estatus-requerimientos/:id} : delete the "id" estatusRequerimiento.
     *
     * @param id the id of the estatusRequerimientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-requerimientos/{id}")
    public ResponseEntity<Void> deleteEstatusRequerimiento(@PathVariable Long id) {
        log.debug("REST request to delete EstatusRequerimiento : {}", id);
        estatusRequerimientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
