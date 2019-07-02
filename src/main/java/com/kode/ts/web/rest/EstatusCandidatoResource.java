package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusCandidatoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusCandidatoDTO;
import com.kode.ts.service.dto.EstatusCandidatoCriteria;
import com.kode.ts.service.EstatusCandidatoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusCandidato}.
 */
@RestController
@RequestMapping("/api")
public class EstatusCandidatoResource {

    private final Logger log = LoggerFactory.getLogger(EstatusCandidatoResource.class);

    private static final String ENTITY_NAME = "estatusCandidato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusCandidatoService estatusCandidatoService;

    private final EstatusCandidatoQueryService estatusCandidatoQueryService;

    public EstatusCandidatoResource(EstatusCandidatoService estatusCandidatoService, EstatusCandidatoQueryService estatusCandidatoQueryService) {
        this.estatusCandidatoService = estatusCandidatoService;
        this.estatusCandidatoQueryService = estatusCandidatoQueryService;
    }

    /**
     * {@code POST  /estatus-candidatoes} : Create a new estatusCandidato.
     *
     * @param estatusCandidatoDTO the estatusCandidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusCandidatoDTO, or with status {@code 400 (Bad Request)} if the estatusCandidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-candidatoes")
    public ResponseEntity<EstatusCandidatoDTO> createEstatusCandidato(@Valid @RequestBody EstatusCandidatoDTO estatusCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusCandidato : {}", estatusCandidatoDTO);
        if (estatusCandidatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusCandidato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusCandidatoDTO result = estatusCandidatoService.save(estatusCandidatoDTO);
        return ResponseEntity.created(new URI("/api/estatus-candidatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-candidatoes} : Updates an existing estatusCandidato.
     *
     * @param estatusCandidatoDTO the estatusCandidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusCandidatoDTO,
     * or with status {@code 400 (Bad Request)} if the estatusCandidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusCandidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-candidatoes")
    public ResponseEntity<EstatusCandidatoDTO> updateEstatusCandidato(@Valid @RequestBody EstatusCandidatoDTO estatusCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusCandidato : {}", estatusCandidatoDTO);
        if (estatusCandidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusCandidatoDTO result = estatusCandidatoService.save(estatusCandidatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusCandidatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-candidatoes} : get all the estatusCandidatoes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusCandidatoes in body.
     */
    @GetMapping("/estatus-candidatoes")
    public ResponseEntity<List<EstatusCandidatoDTO>> getAllEstatusCandidatoes(EstatusCandidatoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusCandidatoes by criteria: {}", criteria);
        Page<EstatusCandidatoDTO> page = estatusCandidatoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-candidatoes/count} : count all the estatusCandidatoes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-candidatoes/count")
    public ResponseEntity<Long> countEstatusCandidatoes(EstatusCandidatoCriteria criteria) {
        log.debug("REST request to count EstatusCandidatoes by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusCandidatoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-candidatoes/:id} : get the "id" estatusCandidato.
     *
     * @param id the id of the estatusCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-candidatoes/{id}")
    public ResponseEntity<EstatusCandidatoDTO> getEstatusCandidato(@PathVariable Long id) {
        log.debug("REST request to get EstatusCandidato : {}", id);
        Optional<EstatusCandidatoDTO> estatusCandidatoDTO = estatusCandidatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusCandidatoDTO);
    }

    /**
     * {@code DELETE  /estatus-candidatoes/:id} : delete the "id" estatusCandidato.
     *
     * @param id the id of the estatusCandidatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-candidatoes/{id}")
    public ResponseEntity<Void> deleteEstatusCandidato(@PathVariable Long id) {
        log.debug("REST request to delete EstatusCandidato : {}", id);
        estatusCandidatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
