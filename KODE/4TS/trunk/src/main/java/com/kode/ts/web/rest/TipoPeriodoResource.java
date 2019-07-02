package com.kode.ts.web.rest;

import com.kode.ts.service.TipoPeriodoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TipoPeriodoDTO;
import com.kode.ts.service.dto.TipoPeriodoCriteria;
import com.kode.ts.service.TipoPeriodoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.TipoPeriodo}.
 */
@RestController
@RequestMapping("/api")
public class TipoPeriodoResource {

    private final Logger log = LoggerFactory.getLogger(TipoPeriodoResource.class);

    private static final String ENTITY_NAME = "tipoPeriodo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPeriodoService tipoPeriodoService;

    private final TipoPeriodoQueryService tipoPeriodoQueryService;

    public TipoPeriodoResource(TipoPeriodoService tipoPeriodoService, TipoPeriodoQueryService tipoPeriodoQueryService) {
        this.tipoPeriodoService = tipoPeriodoService;
        this.tipoPeriodoQueryService = tipoPeriodoQueryService;
    }

    /**
     * {@code POST  /tipo-periodos} : Create a new tipoPeriodo.
     *
     * @param tipoPeriodoDTO the tipoPeriodoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPeriodoDTO, or with status {@code 400 (Bad Request)} if the tipoPeriodo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-periodos")
    public ResponseEntity<TipoPeriodoDTO> createTipoPeriodo(@Valid @RequestBody TipoPeriodoDTO tipoPeriodoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoPeriodo : {}", tipoPeriodoDTO);
        if (tipoPeriodoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoPeriodo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPeriodoDTO result = tipoPeriodoService.save(tipoPeriodoDTO);
        return ResponseEntity.created(new URI("/api/tipo-periodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-periodos} : Updates an existing tipoPeriodo.
     *
     * @param tipoPeriodoDTO the tipoPeriodoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPeriodoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoPeriodoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPeriodoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-periodos")
    public ResponseEntity<TipoPeriodoDTO> updateTipoPeriodo(@Valid @RequestBody TipoPeriodoDTO tipoPeriodoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoPeriodo : {}", tipoPeriodoDTO);
        if (tipoPeriodoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPeriodoDTO result = tipoPeriodoService.save(tipoPeriodoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPeriodoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-periodos} : get all the tipoPeriodos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPeriodos in body.
     */
    @GetMapping("/tipo-periodos")
    public ResponseEntity<List<TipoPeriodoDTO>> getAllTipoPeriodos(TipoPeriodoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TipoPeriodos by criteria: {}", criteria);
        Page<TipoPeriodoDTO> page = tipoPeriodoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-periodos/count} : count all the tipoPeriodos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-periodos/count")
    public ResponseEntity<Long> countTipoPeriodos(TipoPeriodoCriteria criteria) {
        log.debug("REST request to count TipoPeriodos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoPeriodoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-periodos/:id} : get the "id" tipoPeriodo.
     *
     * @param id the id of the tipoPeriodoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPeriodoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-periodos/{id}")
    public ResponseEntity<TipoPeriodoDTO> getTipoPeriodo(@PathVariable Long id) {
        log.debug("REST request to get TipoPeriodo : {}", id);
        Optional<TipoPeriodoDTO> tipoPeriodoDTO = tipoPeriodoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPeriodoDTO);
    }

    /**
     * {@code DELETE  /tipo-periodos/:id} : delete the "id" tipoPeriodo.
     *
     * @param id the id of the tipoPeriodoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-periodos/{id}")
    public ResponseEntity<Void> deleteTipoPeriodo(@PathVariable Long id) {
        log.debug("REST request to delete TipoPeriodo : {}", id);
        tipoPeriodoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
