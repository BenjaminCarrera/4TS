package com.kode.ts.web.rest;

import com.kode.ts.service.EstatusReqCanRecService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EstatusReqCanRecDTO;
import com.kode.ts.service.dto.EstatusReqCanRecCriteria;
import com.kode.ts.service.EstatusReqCanRecQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EstatusReqCanRec}.
 */
@RestController
@RequestMapping("/api")
public class EstatusReqCanRecResource {

    private final Logger log = LoggerFactory.getLogger(EstatusReqCanRecResource.class);

    private static final String ENTITY_NAME = "estatusReqCanRec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusReqCanRecService estatusReqCanRecService;

    private final EstatusReqCanRecQueryService estatusReqCanRecQueryService;

    public EstatusReqCanRecResource(EstatusReqCanRecService estatusReqCanRecService, EstatusReqCanRecQueryService estatusReqCanRecQueryService) {
        this.estatusReqCanRecService = estatusReqCanRecService;
        this.estatusReqCanRecQueryService = estatusReqCanRecQueryService;
    }

    /**
     * {@code POST  /estatus-req-can-recs} : Create a new estatusReqCanRec.
     *
     * @param estatusReqCanRecDTO the estatusReqCanRecDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusReqCanRecDTO, or with status {@code 400 (Bad Request)} if the estatusReqCanRec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-req-can-recs")
    public ResponseEntity<EstatusReqCanRecDTO> createEstatusReqCanRec(@Valid @RequestBody EstatusReqCanRecDTO estatusReqCanRecDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusReqCanRec : {}", estatusReqCanRecDTO);
        if (estatusReqCanRecDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusReqCanRec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusReqCanRecDTO result = estatusReqCanRecService.save(estatusReqCanRecDTO);
        return ResponseEntity.created(new URI("/api/estatus-req-can-recs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-req-can-recs} : Updates an existing estatusReqCanRec.
     *
     * @param estatusReqCanRecDTO the estatusReqCanRecDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusReqCanRecDTO,
     * or with status {@code 400 (Bad Request)} if the estatusReqCanRecDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusReqCanRecDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-req-can-recs")
    public ResponseEntity<EstatusReqCanRecDTO> updateEstatusReqCanRec(@Valid @RequestBody EstatusReqCanRecDTO estatusReqCanRecDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusReqCanRec : {}", estatusReqCanRecDTO);
        if (estatusReqCanRecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusReqCanRecDTO result = estatusReqCanRecService.save(estatusReqCanRecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusReqCanRecDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estatus-req-can-recs} : get all the estatusReqCanRecs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusReqCanRecs in body.
     */
    @GetMapping("/estatus-req-can-recs")
    public ResponseEntity<List<EstatusReqCanRecDTO>> getAllEstatusReqCanRecs(EstatusReqCanRecCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EstatusReqCanRecs by criteria: {}", criteria);
        Page<EstatusReqCanRecDTO> page = estatusReqCanRecQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /estatus-req-can-recs/count} : count all the estatusReqCanRecs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/estatus-req-can-recs/count")
    public ResponseEntity<Long> countEstatusReqCanRecs(EstatusReqCanRecCriteria criteria) {
        log.debug("REST request to count EstatusReqCanRecs by criteria: {}", criteria);
        return ResponseEntity.ok().body(estatusReqCanRecQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estatus-req-can-recs/:id} : get the "id" estatusReqCanRec.
     *
     * @param id the id of the estatusReqCanRecDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusReqCanRecDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-req-can-recs/{id}")
    public ResponseEntity<EstatusReqCanRecDTO> getEstatusReqCanRec(@PathVariable Long id) {
        log.debug("REST request to get EstatusReqCanRec : {}", id);
        Optional<EstatusReqCanRecDTO> estatusReqCanRecDTO = estatusReqCanRecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusReqCanRecDTO);
    }

    /**
     * {@code DELETE  /estatus-req-can-recs/:id} : delete the "id" estatusReqCanRec.
     *
     * @param id the id of the estatusReqCanRecDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-req-can-recs/{id}")
    public ResponseEntity<Void> deleteEstatusReqCanRec(@PathVariable Long id) {
        log.debug("REST request to delete EstatusReqCanRec : {}", id);
        estatusReqCanRecService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
