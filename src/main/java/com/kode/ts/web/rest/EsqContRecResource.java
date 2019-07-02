package com.kode.ts.web.rest;

import com.kode.ts.service.EsqContRecService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EsqContRecDTO;
import com.kode.ts.service.dto.EsqContRecCriteria;
import com.kode.ts.service.EsqContRecQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EsqContRec}.
 */
@RestController
@RequestMapping("/api")
public class EsqContRecResource {

    private final Logger log = LoggerFactory.getLogger(EsqContRecResource.class);

    private static final String ENTITY_NAME = "esqContRec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsqContRecService esqContRecService;

    private final EsqContRecQueryService esqContRecQueryService;

    public EsqContRecResource(EsqContRecService esqContRecService, EsqContRecQueryService esqContRecQueryService) {
        this.esqContRecService = esqContRecService;
        this.esqContRecQueryService = esqContRecQueryService;
    }

    /**
     * {@code POST  /esq-cont-recs} : Create a new esqContRec.
     *
     * @param esqContRecDTO the esqContRecDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esqContRecDTO, or with status {@code 400 (Bad Request)} if the esqContRec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/esq-cont-recs")
    public ResponseEntity<EsqContRecDTO> createEsqContRec(@Valid @RequestBody EsqContRecDTO esqContRecDTO) throws URISyntaxException {
        log.debug("REST request to save EsqContRec : {}", esqContRecDTO);
        if (esqContRecDTO.getId() != null) {
            throw new BadRequestAlertException("A new esqContRec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EsqContRecDTO result = esqContRecService.save(esqContRecDTO);
        return ResponseEntity.created(new URI("/api/esq-cont-recs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /esq-cont-recs} : Updates an existing esqContRec.
     *
     * @param esqContRecDTO the esqContRecDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esqContRecDTO,
     * or with status {@code 400 (Bad Request)} if the esqContRecDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esqContRecDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/esq-cont-recs")
    public ResponseEntity<EsqContRecDTO> updateEsqContRec(@Valid @RequestBody EsqContRecDTO esqContRecDTO) throws URISyntaxException {
        log.debug("REST request to update EsqContRec : {}", esqContRecDTO);
        if (esqContRecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EsqContRecDTO result = esqContRecService.save(esqContRecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esqContRecDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /esq-cont-recs} : get all the esqContRecs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esqContRecs in body.
     */
    @GetMapping("/esq-cont-recs")
    public ResponseEntity<List<EsqContRecDTO>> getAllEsqContRecs(EsqContRecCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EsqContRecs by criteria: {}", criteria);
        Page<EsqContRecDTO> page = esqContRecQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /esq-cont-recs/count} : count all the esqContRecs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/esq-cont-recs/count")
    public ResponseEntity<Long> countEsqContRecs(EsqContRecCriteria criteria) {
        log.debug("REST request to count EsqContRecs by criteria: {}", criteria);
        return ResponseEntity.ok().body(esqContRecQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /esq-cont-recs/:id} : get the "id" esqContRec.
     *
     * @param id the id of the esqContRecDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esqContRecDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/esq-cont-recs/{id}")
    public ResponseEntity<EsqContRecDTO> getEsqContRec(@PathVariable Long id) {
        log.debug("REST request to get EsqContRec : {}", id);
        Optional<EsqContRecDTO> esqContRecDTO = esqContRecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esqContRecDTO);
    }

    /**
     * {@code DELETE  /esq-cont-recs/:id} : delete the "id" esqContRec.
     *
     * @param id the id of the esqContRecDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/esq-cont-recs/{id}")
    public ResponseEntity<Void> deleteEsqContRec(@PathVariable Long id) {
        log.debug("REST request to delete EsqContRec : {}", id);
        esqContRecService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
