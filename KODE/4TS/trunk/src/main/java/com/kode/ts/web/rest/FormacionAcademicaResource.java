package com.kode.ts.web.rest;

import com.kode.ts.service.FormacionAcademicaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.FormacionAcademicaDTO;
import com.kode.ts.service.dto.FormacionAcademicaCriteria;
import com.kode.ts.service.FormacionAcademicaQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kode.ts.domain.FormacionAcademica}.
 */
@RestController
@RequestMapping("/api")
public class FormacionAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(FormacionAcademicaResource.class);

    private static final String ENTITY_NAME = "formacionAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormacionAcademicaService formacionAcademicaService;

    private final FormacionAcademicaQueryService formacionAcademicaQueryService;

    public FormacionAcademicaResource(FormacionAcademicaService formacionAcademicaService, FormacionAcademicaQueryService formacionAcademicaQueryService) {
        this.formacionAcademicaService = formacionAcademicaService;
        this.formacionAcademicaQueryService = formacionAcademicaQueryService;
    }

    /**
     * {@code POST  /formacion-academicas} : Create a new formacionAcademica.
     *
     * @param formacionAcademicaDTO the formacionAcademicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formacionAcademicaDTO, or with status {@code 400 (Bad Request)} if the formacionAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formacion-academicas")
    public ResponseEntity<FormacionAcademicaDTO> createFormacionAcademica(@RequestBody FormacionAcademicaDTO formacionAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to save FormacionAcademica : {}", formacionAcademicaDTO);
        if (formacionAcademicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new formacionAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormacionAcademicaDTO result = formacionAcademicaService.save(formacionAcademicaDTO);
        return ResponseEntity.created(new URI("/api/formacion-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formacion-academicas} : Updates an existing formacionAcademica.
     *
     * @param formacionAcademicaDTO the formacionAcademicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formacionAcademicaDTO,
     * or with status {@code 400 (Bad Request)} if the formacionAcademicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formacionAcademicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formacion-academicas")
    public ResponseEntity<FormacionAcademicaDTO> updateFormacionAcademica(@RequestBody FormacionAcademicaDTO formacionAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to update FormacionAcademica : {}", formacionAcademicaDTO);
        if (formacionAcademicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormacionAcademicaDTO result = formacionAcademicaService.save(formacionAcademicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formacionAcademicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formacion-academicas} : get all the formacionAcademicas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formacionAcademicas in body.
     */
    @GetMapping("/formacion-academicas")
    public ResponseEntity<List<FormacionAcademicaDTO>> getAllFormacionAcademicas(FormacionAcademicaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FormacionAcademicas by criteria: {}", criteria);
        Page<FormacionAcademicaDTO> page = formacionAcademicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /formacion-academicas/count} : count all the formacionAcademicas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/formacion-academicas/count")
    public ResponseEntity<Long> countFormacionAcademicas(FormacionAcademicaCriteria criteria) {
        log.debug("REST request to count FormacionAcademicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(formacionAcademicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /formacion-academicas/:id} : get the "id" formacionAcademica.
     *
     * @param id the id of the formacionAcademicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formacionAcademicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formacion-academicas/{id}")
    public ResponseEntity<FormacionAcademicaDTO> getFormacionAcademica(@PathVariable Long id) {
        log.debug("REST request to get FormacionAcademica : {}", id);
        Optional<FormacionAcademicaDTO> formacionAcademicaDTO = formacionAcademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formacionAcademicaDTO);
    }

    /**
     * {@code DELETE  /formacion-academicas/:id} : delete the "id" formacionAcademica.
     *
     * @param id the id of the formacionAcademicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formacion-academicas/{id}")
    public ResponseEntity<Void> deleteFormacionAcademica(@PathVariable Long id) {
        log.debug("REST request to delete FormacionAcademica : {}", id);
        formacionAcademicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
