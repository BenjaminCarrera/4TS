package com.kode.ts.web.rest;

import com.kode.ts.service.CodigoPostalService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.CodigoPostalDTO;
import com.kode.ts.service.dto.CodigoPostalCriteria;
import com.kode.ts.service.CodigoPostalQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.CodigoPostal}.
 */
@RestController
@RequestMapping("/api")
public class CodigoPostalResource {

    private final Logger log = LoggerFactory.getLogger(CodigoPostalResource.class);

    private static final String ENTITY_NAME = "codigoPostal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodigoPostalService codigoPostalService;

    private final CodigoPostalQueryService codigoPostalQueryService;

    public CodigoPostalResource(CodigoPostalService codigoPostalService, CodigoPostalQueryService codigoPostalQueryService) {
        this.codigoPostalService = codigoPostalService;
        this.codigoPostalQueryService = codigoPostalQueryService;
    }

    /**
     * {@code POST  /codigo-postals} : Create a new codigoPostal.
     *
     * @param codigoPostalDTO the codigoPostalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codigoPostalDTO, or with status {@code 400 (Bad Request)} if the codigoPostal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/codigo-postals")
    public ResponseEntity<CodigoPostalDTO> createCodigoPostal(@Valid @RequestBody CodigoPostalDTO codigoPostalDTO) throws URISyntaxException {
        log.debug("REST request to save CodigoPostal : {}", codigoPostalDTO);
        if (codigoPostalDTO.getId() != null) {
            throw new BadRequestAlertException("A new codigoPostal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodigoPostalDTO result = codigoPostalService.save(codigoPostalDTO);
        return ResponseEntity.created(new URI("/api/codigo-postals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /codigo-postals} : Updates an existing codigoPostal.
     *
     * @param codigoPostalDTO the codigoPostalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codigoPostalDTO,
     * or with status {@code 400 (Bad Request)} if the codigoPostalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codigoPostalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/codigo-postals")
    public ResponseEntity<CodigoPostalDTO> updateCodigoPostal(@Valid @RequestBody CodigoPostalDTO codigoPostalDTO) throws URISyntaxException {
        log.debug("REST request to update CodigoPostal : {}", codigoPostalDTO);
        if (codigoPostalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodigoPostalDTO result = codigoPostalService.save(codigoPostalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codigoPostalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /codigo-postals} : get all the codigoPostals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codigoPostals in body.
     */
    @GetMapping("/codigo-postals")
    public ResponseEntity<List<CodigoPostalDTO>> getAllCodigoPostals(CodigoPostalCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CodigoPostals by criteria: {}", criteria);
        Page<CodigoPostalDTO> page = codigoPostalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /codigo-postals/count} : count all the codigoPostals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/codigo-postals/count")
    public ResponseEntity<Long> countCodigoPostals(CodigoPostalCriteria criteria) {
        log.debug("REST request to count CodigoPostals by criteria: {}", criteria);
        return ResponseEntity.ok().body(codigoPostalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /codigo-postals/:id} : get the "id" codigoPostal.
     *
     * @param id the id of the codigoPostalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codigoPostalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/codigo-postals/{id}")
    public ResponseEntity<CodigoPostalDTO> getCodigoPostal(@PathVariable Long id) {
        log.debug("REST request to get CodigoPostal : {}", id);
        Optional<CodigoPostalDTO> codigoPostalDTO = codigoPostalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codigoPostalDTO);
    }

    /**
     * {@code DELETE  /codigo-postals/:id} : delete the "id" codigoPostal.
     *
     * @param id the id of the codigoPostalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/codigo-postals/{id}")
    public ResponseEntity<Void> deleteCodigoPostal(@PathVariable Long id) {
        log.debug("REST request to delete CodigoPostal : {}", id);
        codigoPostalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
