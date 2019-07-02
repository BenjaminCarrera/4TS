package com.kode.ts.web.rest;

import com.kode.ts.service.PrioridadReqService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.PrioridadReqDTO;
import com.kode.ts.service.dto.PrioridadReqCriteria;
import com.kode.ts.service.PrioridadReqQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.PrioridadReq}.
 */
@RestController
@RequestMapping("/api")
public class PrioridadReqResource {

    private final Logger log = LoggerFactory.getLogger(PrioridadReqResource.class);

    private static final String ENTITY_NAME = "prioridadReq";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrioridadReqService prioridadReqService;

    private final PrioridadReqQueryService prioridadReqQueryService;

    public PrioridadReqResource(PrioridadReqService prioridadReqService, PrioridadReqQueryService prioridadReqQueryService) {
        this.prioridadReqService = prioridadReqService;
        this.prioridadReqQueryService = prioridadReqQueryService;
    }

    /**
     * {@code POST  /prioridad-reqs} : Create a new prioridadReq.
     *
     * @param prioridadReqDTO the prioridadReqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prioridadReqDTO, or with status {@code 400 (Bad Request)} if the prioridadReq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prioridad-reqs")
    public ResponseEntity<PrioridadReqDTO> createPrioridadReq(@Valid @RequestBody PrioridadReqDTO prioridadReqDTO) throws URISyntaxException {
        log.debug("REST request to save PrioridadReq : {}", prioridadReqDTO);
        if (prioridadReqDTO.getId() != null) {
            throw new BadRequestAlertException("A new prioridadReq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrioridadReqDTO result = prioridadReqService.save(prioridadReqDTO);
        return ResponseEntity.created(new URI("/api/prioridad-reqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prioridad-reqs} : Updates an existing prioridadReq.
     *
     * @param prioridadReqDTO the prioridadReqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prioridadReqDTO,
     * or with status {@code 400 (Bad Request)} if the prioridadReqDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prioridadReqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prioridad-reqs")
    public ResponseEntity<PrioridadReqDTO> updatePrioridadReq(@Valid @RequestBody PrioridadReqDTO prioridadReqDTO) throws URISyntaxException {
        log.debug("REST request to update PrioridadReq : {}", prioridadReqDTO);
        if (prioridadReqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrioridadReqDTO result = prioridadReqService.save(prioridadReqDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prioridadReqDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prioridad-reqs} : get all the prioridadReqs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prioridadReqs in body.
     */
    @GetMapping("/prioridad-reqs")
    public ResponseEntity<List<PrioridadReqDTO>> getAllPrioridadReqs(PrioridadReqCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PrioridadReqs by criteria: {}", criteria);
        Page<PrioridadReqDTO> page = prioridadReqQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /prioridad-reqs/count} : count all the prioridadReqs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/prioridad-reqs/count")
    public ResponseEntity<Long> countPrioridadReqs(PrioridadReqCriteria criteria) {
        log.debug("REST request to count PrioridadReqs by criteria: {}", criteria);
        return ResponseEntity.ok().body(prioridadReqQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prioridad-reqs/:id} : get the "id" prioridadReq.
     *
     * @param id the id of the prioridadReqDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prioridadReqDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prioridad-reqs/{id}")
    public ResponseEntity<PrioridadReqDTO> getPrioridadReq(@PathVariable Long id) {
        log.debug("REST request to get PrioridadReq : {}", id);
        Optional<PrioridadReqDTO> prioridadReqDTO = prioridadReqService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prioridadReqDTO);
    }

    /**
     * {@code DELETE  /prioridad-reqs/:id} : delete the "id" prioridadReq.
     *
     * @param id the id of the prioridadReqDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prioridad-reqs/{id}")
    public ResponseEntity<Void> deletePrioridadReq(@PathVariable Long id) {
        log.debug("REST request to delete PrioridadReq : {}", id);
        prioridadReqService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
