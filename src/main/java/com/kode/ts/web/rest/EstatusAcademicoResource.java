package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusAcademicoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusAcademicoDTO;
import com.kode.ts.service.dto.EstatusAcademicoCriteria;
import com.kode.ts.service.EstatusAcademicoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusAcademico}.
 */
@RestController
@RequestMapping("/api")
public class EstatusAcademicoResource {

    private final Logger log = LoggerFactory.getLogger(EstatusAcademicoResource.class);

    private static final String ENTITY_NAME = "estatusAcademico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusAcademicoService estatusAcademicoService;

    private final EstatusAcademicoQueryService estatusAcademicoQueryService;

    public EstatusAcademicoResource(EstatusAcademicoService estatusAcademicoService, EstatusAcademicoQueryService estatusAcademicoQueryService) {
        this.estatusAcademicoService = estatusAcademicoService;
        this.estatusAcademicoQueryService = estatusAcademicoQueryService;
    }

    /**
     * {@code POST  /estatus-academicos} : Create a new estatusAcademico.
     *
     * @param estatusAcademicoDTO the estatusAcademicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusAcademicoDTO, or with status {@code 400 (Bad Request)} if the estatusAcademico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-academicos")
    public ResponseEntity<EstatusAcademicoDTO> createEstatusAcademico(@Valid @RequestBody EstatusAcademicoDTO estatusAcademicoDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusAcademico : {}", estatusAcademicoDTO);
        if (estatusAcademicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusAcademico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusAcademicoDTO result = estatusAcademicoService.save(estatusAcademicoDTO);
        return ResponseEntity.created(new URI("/api/estatus-academicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-academicos} : Updates an existing estatusAcademico.
     *
     * @param estatusAcademicoDTO the estatusAcademicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusAcademicoDTO,
     * or with status {@code 400 (Bad Request)} if the estatusAcademicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusAcademicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-academicos")
    public ResponseEntity<EstatusAcademicoDTO> updateEstatusAcademico(@Valid @RequestBody EstatusAcademicoDTO estatusAcademicoDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusAcademico : {}", estatusAcademicoDTO);
        if (estatusAcademicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusAcademicoDTO result = estatusAcademicoService.save(estatusAcademicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusAcademicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-academicos} : get all the estatusAcademicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusAcademicos in body.
     */
    @GetMapping("/estatus-academicos")
    public ResponseEntity<List<EstatusAcademicoDTO>> getAllEstatusAcademicos(EstatusAcademicoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusAcademicos by criteria: {}", criteria);
        Page<EstatusAcademicoDTO> page = estatusAcademicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-academicos/count} : count all the estatusAcademicos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-academicos/count")
    public ResponseEntity<Long> countEstatusAcademicos(EstatusAcademicoCriteria criteria) {
        log.debug("REST request to count EstatusAcademicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusAcademicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-academicos/:id} : get the "id" estatusAcademico.
     *
     * @param id the id of the estatusAcademicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusAcademicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-academicos/{id}")
    public ResponseEntity<EstatusAcademicoDTO> getEstatusAcademico(@PathVariable Long id) {
        log.debug("REST request to get EstatusAcademico : {}", id);
        Optional<EstatusAcademicoDTO> estatusAcademicoDTO = estatusAcademicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusAcademicoDTO);
    }

    /**
     * {@code DELETE  /estatus-academicos/:id} : delete the "id" estatusAcademico.
     *
     * @param id the id of the estatusAcademicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-academicos/{id}")
    public ResponseEntity<Void> deleteEstatusAcademico(@PathVariable Long id) {
        log.debug("REST request to delete EstatusAcademico : {}", id);
        estatusAcademicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
