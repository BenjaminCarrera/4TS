package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusReqCanService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusReqCanDTO;
import com.kode.ts.service.dto.EstatusReqCanCriteria;
import com.kode.ts.service.EstatusReqCanQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusReqCan}.
 */
@RestController
@RequestMapping("/api")
public class EstatusReqCanResource {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanResource.class);

    private static final String ENTITY_NAME = "estatusReqCan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusReqCanService estatusReqCanService;

    private final EstatusReqCanQueryService estatusReqCanQueryService;

    public EstatusReqCanResource(EstatusReqCanService estatusReqCanService, EstatusReqCanQueryService estatusReqCanQueryService) {
        this.estatusReqCanService = estatusReqCanService;
        this.estatusReqCanQueryService = estatusReqCanQueryService;
    }

    /**
     * {@code POST  /estatus-req-cans} : Create a new estatusReqCan.
     *
     * @param estatusReqCanDTO the estatusReqCanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusReqCanDTO, or with status {@code 400 (Bad Request)} if the estatusReqCan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-req-cans")
    public ResponseEntity<EstatusReqCanDTO> createEstatusReqCan(@Valid @RequestBody EstatusReqCanDTO estatusReqCanDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusReqCan : {}", estatusReqCanDTO);
        if (estatusReqCanDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusReqCan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusReqCanDTO result = estatusReqCanService.save(estatusReqCanDTO);
        return ResponseEntity.created(new URI("/api/estatus-req-cans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-req-cans} : Updates an existing estatusReqCan.
     *
     * @param estatusReqCanDTO the estatusReqCanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusReqCanDTO,
     * or with status {@code 400 (Bad Request)} if the estatusReqCanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusReqCanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-req-cans")
    public ResponseEntity<EstatusReqCanDTO> updateEstatusReqCan(@Valid @RequestBody EstatusReqCanDTO estatusReqCanDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusReqCan : {}", estatusReqCanDTO);
        if (estatusReqCanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusReqCanDTO result = estatusReqCanService.save(estatusReqCanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusReqCanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-req-cans} : get all the estatusReqCans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusReqCans in body.
     */
    @GetMapping("/estatus-req-cans")
    public ResponseEntity<List<EstatusReqCanDTO>> getAllEstatusReqCans(EstatusReqCanCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusReqCans by criteria: {}", criteria);
        Page<EstatusReqCanDTO> page = estatusReqCanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-req-cans/count} : count all the estatusReqCans.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-req-cans/count")
    public ResponseEntity<Long> countEstatusReqCans(EstatusReqCanCriteria criteria) {
        log.debug("REST request to count EstatusReqCans by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusReqCanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-req-cans/:id} : get the "id" estatusReqCan.
     *
     * @param id the id of the estatusReqCanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusReqCanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-req-cans/{id}")
    public ResponseEntity<EstatusReqCanDTO> getEstatusReqCan(@PathVariable Long id) {
        log.debug("REST request to get EstatusReqCan : {}", id);
        Optional<EstatusReqCanDTO> estatusReqCanDTO = estatusReqCanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusReqCanDTO);
    }

    /**
     * {@code DELETE  /estatus-req-cans/:id} : delete the "id" estatusReqCan.
     *
     * @param id the id of the estatusReqCanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-req-cans/{id}")
    public ResponseEntity<Void> deleteEstatusReqCan(@PathVariable Long id) {
        log.debug("REST request to delete EstatusReqCan : {}", id);
        estatusReqCanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
