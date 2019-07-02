package com.kode.ts.web.rest;

import com.kode.ts.service.BaseTarifaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.BaseTarifaDTO;
import com.kode.ts.service.dto.BaseTarifaCriteria;
import com.kode.ts.service.BaseTarifaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.BaseTarifa}.
 */
@RestController
@RequestMapping("/api")
public class BaseTarifaResource {

    private final Logger log = LoggerFactory.getLogger(BaseTarifaResource.class);

    private static final String ENTITY_NAME = "baseTarifa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseTarifaService baseTarifaService;

    private final BaseTarifaQueryService baseTarifaQueryService;

    public BaseTarifaResource(BaseTarifaService baseTarifaService, BaseTarifaQueryService baseTarifaQueryService) {
        this.baseTarifaService = baseTarifaService;
        this.baseTarifaQueryService = baseTarifaQueryService;
    }

    /**
     * {@code POST  /base-tarifas} : Create a new baseTarifa.
     *
     * @param baseTarifaDTO the baseTarifaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseTarifaDTO, or with status {@code 400 (Bad Request)} if the baseTarifa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/base-tarifas")
    public ResponseEntity<BaseTarifaDTO> createBaseTarifa(@Valid @RequestBody BaseTarifaDTO baseTarifaDTO) throws URISyntaxException {
        log.debug("REST request to save BaseTarifa : {}", baseTarifaDTO);
        if (baseTarifaDTO.getId() != null) {
            throw new BadRequestAlertException("A new baseTarifa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseTarifaDTO result = baseTarifaService.save(baseTarifaDTO);
        return ResponseEntity.created(new URI("/api/base-tarifas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /base-tarifas} : Updates an existing baseTarifa.
     *
     * @param baseTarifaDTO the baseTarifaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseTarifaDTO,
     * or with status {@code 400 (Bad Request)} if the baseTarifaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseTarifaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/base-tarifas")
    public ResponseEntity<BaseTarifaDTO> updateBaseTarifa(@Valid @RequestBody BaseTarifaDTO baseTarifaDTO) throws URISyntaxException {
        log.debug("REST request to update BaseTarifa : {}", baseTarifaDTO);
        if (baseTarifaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseTarifaDTO result = baseTarifaService.save(baseTarifaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseTarifaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /base-tarifas} : get all the baseTarifas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseTarifas in body.
     */
    @GetMapping("/base-tarifas")
    public ResponseEntity<List<BaseTarifaDTO>> getAllBaseTarifas(BaseTarifaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get BaseTarifas by criteria: {}", criteria);
        Page<BaseTarifaDTO> page = baseTarifaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /base-tarifas/count} : count all the baseTarifas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/base-tarifas/count")
    public ResponseEntity<Long> countBaseTarifas(BaseTarifaCriteria criteria) {
        log.debug("REST request to count BaseTarifas by criteria: {}", criteria);
        return ResponseEntity.ok().body(baseTarifaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /base-tarifas/:id} : get the "id" baseTarifa.
     *
     * @param id the id of the baseTarifaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseTarifaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/base-tarifas/{id}")
    public ResponseEntity<BaseTarifaDTO> getBaseTarifa(@PathVariable Long id) {
        log.debug("REST request to get BaseTarifa : {}", id);
        Optional<BaseTarifaDTO> baseTarifaDTO = baseTarifaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseTarifaDTO);
    }

    /**
     * {@code DELETE  /base-tarifas/:id} : delete the "id" baseTarifa.
     *
     * @param id the id of the baseTarifaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/base-tarifas/{id}")
    public ResponseEntity<Void> deleteBaseTarifa(@PathVariable Long id) {
        log.debug("REST request to delete BaseTarifa : {}", id);
        baseTarifaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
