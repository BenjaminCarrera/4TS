package com.kode.ts.web.rest;

import com.kode.ts.service.InstitucionAcademicaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.InstitucionAcademicaDTO;
import com.kode.ts.service.dto.InstitucionAcademicaCriteria;
import com.kode.ts.service.InstitucionAcademicaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.InstitucionAcademica}.
 */
@RestController
@RequestMapping("/api")
public class InstitucionAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(InstitucionAcademicaResource.class);

    private static final String ENTITY_NAME = "institucionAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstitucionAcademicaService institucionAcademicaService;

    private final InstitucionAcademicaQueryService institucionAcademicaQueryService;

    public InstitucionAcademicaResource(InstitucionAcademicaService institucionAcademicaService, InstitucionAcademicaQueryService institucionAcademicaQueryService) {
        this.institucionAcademicaService = institucionAcademicaService;
        this.institucionAcademicaQueryService = institucionAcademicaQueryService;
    }

    /**
     * {@code POST  /institucion-academicas} : Create a new institucionAcademica.
     *
     * @param institucionAcademicaDTO the institucionAcademicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institucionAcademicaDTO, or with status {@code 400 (Bad Request)} if the institucionAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institucion-academicas")
    public ResponseEntity<InstitucionAcademicaDTO> createInstitucionAcademica(@Valid @RequestBody InstitucionAcademicaDTO institucionAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to save InstitucionAcademica : {}", institucionAcademicaDTO);
        if (institucionAcademicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new institucionAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitucionAcademicaDTO result = institucionAcademicaService.save(institucionAcademicaDTO);
        return ResponseEntity.created(new URI("/api/institucion-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institucion-academicas} : Updates an existing institucionAcademica.
     *
     * @param institucionAcademicaDTO the institucionAcademicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institucionAcademicaDTO,
     * or with status {@code 400 (Bad Request)} if the institucionAcademicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institucionAcademicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institucion-academicas")
    public ResponseEntity<InstitucionAcademicaDTO> updateInstitucionAcademica(@Valid @RequestBody InstitucionAcademicaDTO institucionAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to update InstitucionAcademica : {}", institucionAcademicaDTO);
        if (institucionAcademicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstitucionAcademicaDTO result = institucionAcademicaService.save(institucionAcademicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institucionAcademicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institucion-academicas} : get all the institucionAcademicas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institucionAcademicas in body.
     */
    @GetMapping("/institucion-academicas")
    public ResponseEntity<List<InstitucionAcademicaDTO>> getAllInstitucionAcademicas(InstitucionAcademicaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get InstitucionAcademicas by criteria: {}", criteria);
        Page<InstitucionAcademicaDTO> page = institucionAcademicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /institucion-academicas/count} : count all the institucionAcademicas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/institucion-academicas/count")
    public ResponseEntity<Long> countInstitucionAcademicas(InstitucionAcademicaCriteria criteria) {
        log.debug("REST request to count InstitucionAcademicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(institucionAcademicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /institucion-academicas/:id} : get the "id" institucionAcademica.
     *
     * @param id the id of the institucionAcademicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institucionAcademicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institucion-academicas/{id}")
    public ResponseEntity<InstitucionAcademicaDTO> getInstitucionAcademica(@PathVariable Long id) {
        log.debug("REST request to get InstitucionAcademica : {}", id);
        Optional<InstitucionAcademicaDTO> institucionAcademicaDTO = institucionAcademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(institucionAcademicaDTO);
    }

    /**
     * {@code DELETE  /institucion-academicas/:id} : delete the "id" institucionAcademica.
     *
     * @param id the id of the institucionAcademicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institucion-academicas/{id}")
    public ResponseEntity<Void> deleteInstitucionAcademica(@PathVariable Long id) {
        log.debug("REST request to delete InstitucionAcademica : {}", id);
        institucionAcademicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
