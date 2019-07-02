package com.kode.ts.web.rest;

import com.kode.ts.service.ReqCanService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.ReqCanDTO;
import com.kode.ts.service.dto.ReqCanCriteria;
import com.kode.ts.service.ReqCanQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.ReqCan}.
 */
@RestController
@RequestMapping("/api")
public class ReqCanResource {

    private final Logger log = LoggerFactory.getLogger(ReqCanResource.class);

    private static final String ENTITY_NAME = "reqCan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReqCanService reqCanService;

    private final ReqCanQueryService reqCanQueryService;

    public ReqCanResource(ReqCanService reqCanService, ReqCanQueryService reqCanQueryService) {
        this.reqCanService = reqCanService;
        this.reqCanQueryService = reqCanQueryService;
    }

    /**
     * {@code POST  /req-cans} : Create a new reqCan.
     *
     * @param reqCanDTO the reqCanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reqCanDTO, or with status {@code 400 (Bad Request)} if the reqCan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/req-cans")
    public ResponseEntity<ReqCanDTO> createReqCan(@RequestBody ReqCanDTO reqCanDTO) throws URISyntaxException {
        log.debug("REST request to save ReqCan : {}", reqCanDTO);
        if (reqCanDTO.getId() != null) {
            throw new BadRequestAlertException("A new reqCan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReqCanDTO result = reqCanService.save(reqCanDTO);
        return ResponseEntity.created(new URI("/api/req-cans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /req-cans} : Updates an existing reqCan.
     *
     * @param reqCanDTO the reqCanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reqCanDTO,
     * or with status {@code 400 (Bad Request)} if the reqCanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reqCanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/req-cans")
    public ResponseEntity<ReqCanDTO> updateReqCan(@RequestBody ReqCanDTO reqCanDTO) throws URISyntaxException {
        log.debug("REST request to update ReqCan : {}", reqCanDTO);
        if (reqCanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReqCanDTO result = reqCanService.save(reqCanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reqCanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /req-cans} : get all the reqCans.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reqCans in body.
     */
    @GetMapping("/req-cans")
    public ResponseEntity<List<ReqCanDTO>> getAllReqCans(ReqCanCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ReqCans by criteria: {}", criteria);
        Page<ReqCanDTO> page = reqCanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /req-cans/count} : count all the reqCans.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/req-cans/count")
    public ResponseEntity<Long> countReqCans(ReqCanCriteria criteria) {
        log.debug("REST request to count ReqCans by criteria: {}", criteria);
        return ResponseEntity.ok().body(reqCanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /req-cans/:id} : get the "id" reqCan.
     *
     * @param id the id of the reqCanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reqCanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/req-cans/{id}")
    public ResponseEntity<ReqCanDTO> getReqCan(@PathVariable Long id) {
        log.debug("REST request to get ReqCan : {}", id);
        Optional<ReqCanDTO> reqCanDTO = reqCanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reqCanDTO);
    }

    /**
     * {@code DELETE  /req-cans/:id} : delete the "id" reqCan.
     *
     * @param id the id of the reqCanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/req-cans/{id}")
    public ResponseEntity<Void> deleteReqCan(@PathVariable Long id) {
        log.debug("REST request to delete ReqCan : {}", id);
        reqCanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
