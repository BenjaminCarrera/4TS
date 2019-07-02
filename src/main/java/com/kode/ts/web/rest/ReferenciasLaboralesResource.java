package com.kode.ts.web.rest;

import com.kode.ts.service.ReferenciasLaboralesService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.ReferenciasLaboralesDTO;
import com.kode.ts.service.dto.ReferenciasLaboralesCriteria;
import com.kode.ts.service.ReferenciasLaboralesQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.ReferenciasLaborales}.
 */
@RestController
@RequestMapping("/api")
public class ReferenciasLaboralesResource {

    private final Logger log = LoggerFactory.getLogger(ReferenciasLaboralesResource.class);

    private static final String ENTITY_NAME = "referenciasLaborales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenciasLaboralesService referenciasLaboralesService;

    private final ReferenciasLaboralesQueryService referenciasLaboralesQueryService;

    public ReferenciasLaboralesResource(ReferenciasLaboralesService referenciasLaboralesService, ReferenciasLaboralesQueryService referenciasLaboralesQueryService) {
        this.referenciasLaboralesService = referenciasLaboralesService;
        this.referenciasLaboralesQueryService = referenciasLaboralesQueryService;
    }

    /**
     * {@code POST  /referencias-laborales} : Create a new referenciasLaborales.
     *
     * @param referenciasLaboralesDTO the referenciasLaboralesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referenciasLaboralesDTO, or with status {@code 400 (Bad Request)} if the referenciasLaborales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/referencias-laborales")
    public ResponseEntity<ReferenciasLaboralesDTO> createReferenciasLaborales(@Valid @RequestBody ReferenciasLaboralesDTO referenciasLaboralesDTO) throws URISyntaxException {
        log.debug("REST request to save ReferenciasLaborales : {}", referenciasLaboralesDTO);
        if (referenciasLaboralesDTO.getId() != null) {
            throw new BadRequestAlertException("A new referenciasLaborales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenciasLaboralesDTO result = referenciasLaboralesService.save(referenciasLaboralesDTO);
        return ResponseEntity.created(new URI("/api/referencias-laborales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /referencias-laborales} : Updates an existing referenciasLaborales.
     *
     * @param referenciasLaboralesDTO the referenciasLaboralesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenciasLaboralesDTO,
     * or with status {@code 400 (Bad Request)} if the referenciasLaboralesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referenciasLaboralesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/referencias-laborales")
    public ResponseEntity<ReferenciasLaboralesDTO> updateReferenciasLaborales(@Valid @RequestBody ReferenciasLaboralesDTO referenciasLaboralesDTO) throws URISyntaxException {
        log.debug("REST request to update ReferenciasLaborales : {}", referenciasLaboralesDTO);
        if (referenciasLaboralesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferenciasLaboralesDTO result = referenciasLaboralesService.save(referenciasLaboralesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referenciasLaboralesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /referencias-laborales} : get all the referenciasLaborales.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referenciasLaborales in body.
     */
    @GetMapping("/referencias-laborales")
    public ResponseEntity<List<ReferenciasLaboralesDTO>> getAllReferenciasLaborales(ReferenciasLaboralesCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ReferenciasLaborales by criteria: {}", criteria);
        Page<ReferenciasLaboralesDTO> page = referenciasLaboralesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /referencias-laborales/count} : count all the referenciasLaborales.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/referencias-laborales/count")
    public ResponseEntity<Long> countReferenciasLaborales(ReferenciasLaboralesCriteria criteria) {
        log.debug("REST request to count ReferenciasLaborales by criteria: {}", criteria);
        return ResponseEntity.ok().body(referenciasLaboralesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /referencias-laborales/:id} : get the "id" referenciasLaborales.
     *
     * @param id the id of the referenciasLaboralesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referenciasLaboralesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/referencias-laborales/{id}")
    public ResponseEntity<ReferenciasLaboralesDTO> getReferenciasLaborales(@PathVariable Long id) {
        log.debug("REST request to get ReferenciasLaborales : {}", id);
        Optional<ReferenciasLaboralesDTO> referenciasLaboralesDTO = referenciasLaboralesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referenciasLaboralesDTO);
    }

    /**
     * {@code DELETE  /referencias-laborales/:id} : delete the "id" referenciasLaborales.
     *
     * @param id the id of the referenciasLaboralesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/referencias-laborales/{id}")
    public ResponseEntity<Void> deleteReferenciasLaborales(@PathVariable Long id) {
        log.debug("REST request to delete ReferenciasLaborales : {}", id);
        referenciasLaboralesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
