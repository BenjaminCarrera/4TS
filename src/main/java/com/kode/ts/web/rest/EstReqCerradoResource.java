package com.kode.ts.web.rest;

import com.kode.ts.service.EstReqCerradoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstReqCerradoDTO;
import com.kode.ts.service.dto.EstReqCerradoCriteria;
import com.kode.ts.service.EstReqCerradoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstReqCerrado}.
 */
@RestController
@RequestMapping("/api")
public class EstReqCerradoResource {

    private final Logger log = LoggerFactory.getLogger(EstReqCerradoResource.class);

    private static final String ENTITY_NAME = "estReqCerrado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstReqCerradoService estReqCerradoService;

    private final EstReqCerradoQueryService estReqCerradoQueryService;

    public EstReqCerradoResource(EstReqCerradoService estReqCerradoService, EstReqCerradoQueryService estReqCerradoQueryService) {
        this.estReqCerradoService = estReqCerradoService;
        this.estReqCerradoQueryService = estReqCerradoQueryService;
    }

    /**
     * {@code POST  /est-req-cerrados} : Create a new estReqCerrado.
     *
     * @param estReqCerradoDTO the estReqCerradoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estReqCerradoDTO, or with status {@code 400 (Bad Request)} if the estReqCerrado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/est-req-cerrados")
    public ResponseEntity<EstReqCerradoDTO> createEstReqCerrado(@Valid @RequestBody EstReqCerradoDTO estReqCerradoDTO) throws URISyntaxException {
        log.debug("REST request to save EstReqCerrado : {}", estReqCerradoDTO);
        if (estReqCerradoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estReqCerrado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstReqCerradoDTO result = estReqCerradoService.save(estReqCerradoDTO);
        return ResponseEntity.created(new URI("/api/est-req-cerrados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /est-req-cerrados} : Updates an existing estReqCerrado.
     *
     * @param estReqCerradoDTO the estReqCerradoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estReqCerradoDTO,
     * or with status {@code 400 (Bad Request)} if the estReqCerradoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estReqCerradoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/est-req-cerrados")
    public ResponseEntity<EstReqCerradoDTO> updateEstReqCerrado(@Valid @RequestBody EstReqCerradoDTO estReqCerradoDTO) throws URISyntaxException {
        log.debug("REST request to update EstReqCerrado : {}", estReqCerradoDTO);
        if (estReqCerradoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstReqCerradoDTO result = estReqCerradoService.save(estReqCerradoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estReqCerradoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /est-req-cerrados} : get all the estReqCerrados.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estReqCerrados in body.
     */
    @GetMapping("/est-req-cerrados")
    public ResponseEntity<List<EstReqCerradoDTO>> getAllEstReqCerrados(EstReqCerradoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstReqCerrados by criteria: {}", criteria);
        Page<EstReqCerradoDTO> page = estReqCerradoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /est-req-cerrados/count} : count all the estReqCerrados.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/est-req-cerrados/count")
    public ResponseEntity<Long> countEstReqCerrados(EstReqCerradoCriteria criteria) {
        log.debug("REST request to count EstReqCerrados by criteria: {}", criteria);
        return ResponseEntity.ok().body(estReqCerradoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /est-req-cerrados/:id} : get the "id" estReqCerrado.
     *
     * @param id the id of the estReqCerradoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estReqCerradoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/est-req-cerrados/{id}")
    public ResponseEntity<EstReqCerradoDTO> getEstReqCerrado(@PathVariable Long id) {
        log.debug("REST request to get EstReqCerrado : {}", id);
        Optional<EstReqCerradoDTO> estReqCerradoDTO = estReqCerradoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estReqCerradoDTO);
    }

    /**
     * {@code DELETE  /est-req-cerrados/:id} : delete the "id" estReqCerrado.
     *
     * @param id the id of the estReqCerradoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/est-req-cerrados/{id}")
    public ResponseEntity<Void> deleteEstReqCerrado(@PathVariable Long id) {
        log.debug("REST request to delete EstReqCerrado : {}", id);
        estReqCerradoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
