package com.kode.ts.web.rest;

import com.kode.ts.service.ColoniaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.ColoniaDTO;
import com.kode.ts.service.dto.ColoniaCriteria;
import com.kode.ts.service.ColoniaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Colonia}.
 */
@RestController
@RequestMapping("/api")
public class ColoniaResource {

    private final Logger log = LoggerFactory.getLogger(ColoniaResource.class);

    private static final String ENTITY_NAME = "colonia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColoniaService coloniaService;

    private final ColoniaQueryService coloniaQueryService;

    public ColoniaResource(ColoniaService coloniaService, ColoniaQueryService coloniaQueryService) {
        this.coloniaService = coloniaService;
        this.coloniaQueryService = coloniaQueryService;
    }

    /**
     * {@code POST  /colonias} : Create a new colonia.
     *
     * @param coloniaDTO the coloniaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coloniaDTO, or with status {@code 400 (Bad Request)} if the colonia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colonias")
    public ResponseEntity<ColoniaDTO> createColonia(@Valid @RequestBody ColoniaDTO coloniaDTO) throws URISyntaxException {
        log.debug("REST request to save Colonia : {}", coloniaDTO);
        if (coloniaDTO.getId() != null) {
            throw new BadRequestAlertException("A new colonia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColoniaDTO result = coloniaService.save(coloniaDTO);
        return ResponseEntity.created(new URI("/api/colonias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colonias} : Updates an existing colonia.
     *
     * @param coloniaDTO the coloniaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coloniaDTO,
     * or with status {@code 400 (Bad Request)} if the coloniaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coloniaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colonias")
    public ResponseEntity<ColoniaDTO> updateColonia(@Valid @RequestBody ColoniaDTO coloniaDTO) throws URISyntaxException {
        log.debug("REST request to update Colonia : {}", coloniaDTO);
        if (coloniaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColoniaDTO result = coloniaService.save(coloniaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coloniaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /colonias} : get all the colonias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colonias in body.
     */
    @GetMapping("/colonias")
    public ResponseEntity<List<ColoniaDTO>> getAllColonias(ColoniaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Colonias by criteria: {}", criteria);
        Page<ColoniaDTO> page = coloniaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /colonias/count} : count all the colonias.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/colonias/count")
    public ResponseEntity<Long> countColonias(ColoniaCriteria criteria) {
        log.debug("REST request to count Colonias by criteria: {}", criteria);
        return ResponseEntity.ok().body(coloniaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /colonias/:id} : get the "id" colonia.
     *
     * @param id the id of the coloniaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coloniaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colonias/{id}")
    public ResponseEntity<ColoniaDTO> getColonia(@PathVariable Long id) {
        log.debug("REST request to get Colonia : {}", id);
        Optional<ColoniaDTO> coloniaDTO = coloniaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coloniaDTO);
    }

    /**
     * {@code DELETE  /colonias/:id} : delete the "id" colonia.
     *
     * @param id the id of the coloniaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colonias/{id}")
    public ResponseEntity<Void> deleteColonia(@PathVariable Long id) {
        log.debug("REST request to delete Colonia : {}", id);
        coloniaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
