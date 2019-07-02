package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusLaboralService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusLaboralDTO;
import com.kode.ts.service.dto.EstatusLaboralCriteria;
import com.kode.ts.service.EstatusLaboralQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusLaboral}.
 */
@RestController
@RequestMapping("/api")
public class EstatusLaboralResource {

    private final Logger log = LoggerFactory.getLogger(EstatusLaboralResource.class);

    private static final String ENTITY_NAME = "estatusLaboral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusLaboralService estatusLaboralService;

    private final EstatusLaboralQueryService estatusLaboralQueryService;

    public EstatusLaboralResource(EstatusLaboralService estatusLaboralService, EstatusLaboralQueryService estatusLaboralQueryService) {
        this.estatusLaboralService = estatusLaboralService;
        this.estatusLaboralQueryService = estatusLaboralQueryService;
    }

    /**
     * {@code POST  /estatus-laborals} : Create a new estatusLaboral.
     *
     * @param estatusLaboralDTO the estatusLaboralDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusLaboralDTO, or with status {@code 400 (Bad Request)} if the estatusLaboral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-laborals")
    public ResponseEntity<EstatusLaboralDTO> createEstatusLaboral(@Valid @RequestBody EstatusLaboralDTO estatusLaboralDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusLaboral : {}", estatusLaboralDTO);
        if (estatusLaboralDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusLaboral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusLaboralDTO result = estatusLaboralService.save(estatusLaboralDTO);
        return ResponseEntity.created(new URI("/api/estatus-laborals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-laborals} : Updates an existing estatusLaboral.
     *
     * @param estatusLaboralDTO the estatusLaboralDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusLaboralDTO,
     * or with status {@code 400 (Bad Request)} if the estatusLaboralDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusLaboralDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-laborals")
    public ResponseEntity<EstatusLaboralDTO> updateEstatusLaboral(@Valid @RequestBody EstatusLaboralDTO estatusLaboralDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusLaboral : {}", estatusLaboralDTO);
        if (estatusLaboralDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusLaboralDTO result = estatusLaboralService.save(estatusLaboralDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusLaboralDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-laborals} : get all the estatusLaborals.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusLaborals in body.
     */
    @GetMapping("/estatus-laborals")
    public ResponseEntity<List<EstatusLaboralDTO>> getAllEstatusLaborals(EstatusLaboralCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusLaborals by criteria: {}", criteria);
        Page<EstatusLaboralDTO> page = estatusLaboralQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-laborals/count} : count all the estatusLaborals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-laborals/count")
    public ResponseEntity<Long> countEstatusLaborals(EstatusLaboralCriteria criteria) {
        log.debug("REST request to count EstatusLaborals by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusLaboralQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-laborals/:id} : get the "id" estatusLaboral.
     *
     * @param id the id of the estatusLaboralDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusLaboralDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-laborals/{id}")
    public ResponseEntity<EstatusLaboralDTO> getEstatusLaboral(@PathVariable Long id) {
        log.debug("REST request to get EstatusLaboral : {}", id);
        Optional<EstatusLaboralDTO> estatusLaboralDTO = estatusLaboralService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusLaboralDTO);
    }

    /**
     * {@code DELETE  /estatus-laborals/:id} : delete the "id" estatusLaboral.
     *
     * @param id the id of the estatusLaboralDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-laborals/{id}")
    public ResponseEntity<Void> deleteEstatusLaboral(@PathVariable Long id) {
        log.debug("REST request to delete EstatusLaboral : {}", id);
        estatusLaboralService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
