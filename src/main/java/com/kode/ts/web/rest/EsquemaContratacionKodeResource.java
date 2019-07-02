package com.kode.ts.web.rest;

import com.kode.ts.service.EsquemaContratacionKodeService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.EsquemaContratacionKodeDTO;
import com.kode.ts.service.dto.EsquemaContratacionKodeCriteria;
import com.kode.ts.service.EsquemaContratacionKodeQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.EsquemaContratacionKode}.
 */
@RestController
@RequestMapping("/api")
public class EsquemaContratacionKodeResource {

    private final Logger log = LoggerFactory.getLogger(EsquemaContratacionKodeResource.class);

    private static final String ENTITY_NAME = "esquemaContratacionKode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsquemaContratacionKodeService esquemaContratacionKodeService;

    private final EsquemaContratacionKodeQueryService esquemaContratacionKodeQueryService;

    public EsquemaContratacionKodeResource(EsquemaContratacionKodeService esquemaContratacionKodeService, EsquemaContratacionKodeQueryService esquemaContratacionKodeQueryService) {
        this.esquemaContratacionKodeService = esquemaContratacionKodeService;
        this.esquemaContratacionKodeQueryService = esquemaContratacionKodeQueryService;
    }

    /**
     * {@code POST  /esquema-contratacion-kodes} : Create a new esquemaContratacionKode.
     *
     * @param esquemaContratacionKodeDTO the esquemaContratacionKodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esquemaContratacionKodeDTO, or with status {@code 400 (Bad Request)} if the esquemaContratacionKode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/esquema-contratacion-kodes")
    public ResponseEntity<EsquemaContratacionKodeDTO> createEsquemaContratacionKode(@Valid @RequestBody EsquemaContratacionKodeDTO esquemaContratacionKodeDTO) throws URISyntaxException {
        log.debug("REST request to save EsquemaContratacionKode : {}", esquemaContratacionKodeDTO);
        if (esquemaContratacionKodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new esquemaContratacionKode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EsquemaContratacionKodeDTO result = esquemaContratacionKodeService.save(esquemaContratacionKodeDTO);
        return ResponseEntity.created(new URI("/api/esquema-contratacion-kodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /esquema-contratacion-kodes} : Updates an existing esquemaContratacionKode.
     *
     * @param esquemaContratacionKodeDTO the esquemaContratacionKodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esquemaContratacionKodeDTO,
     * or with status {@code 400 (Bad Request)} if the esquemaContratacionKodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esquemaContratacionKodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/esquema-contratacion-kodes")
    public ResponseEntity<EsquemaContratacionKodeDTO> updateEsquemaContratacionKode(@Valid @RequestBody EsquemaContratacionKodeDTO esquemaContratacionKodeDTO) throws URISyntaxException {
        log.debug("REST request to update EsquemaContratacionKode : {}", esquemaContratacionKodeDTO);
        if (esquemaContratacionKodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EsquemaContratacionKodeDTO result = esquemaContratacionKodeService.save(esquemaContratacionKodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esquemaContratacionKodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /esquema-contratacion-kodes} : get all the esquemaContratacionKodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esquemaContratacionKodes in body.
     */
    @GetMapping("/esquema-contratacion-kodes")
    public ResponseEntity<List<EsquemaContratacionKodeDTO>> getAllEsquemaContratacionKodes(EsquemaContratacionKodeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get EsquemaContratacionKodes by criteria: {}", criteria);
        Page<EsquemaContratacionKodeDTO> page = esquemaContratacionKodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /esquema-contratacion-kodes/count} : count all the esquemaContratacionKodes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/esquema-contratacion-kodes/count")
    public ResponseEntity<Long> countEsquemaContratacionKodes(EsquemaContratacionKodeCriteria criteria) {
        log.debug("REST request to count EsquemaContratacionKodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(esquemaContratacionKodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /esquema-contratacion-kodes/:id} : get the "id" esquemaContratacionKode.
     *
     * @param id the id of the esquemaContratacionKodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esquemaContratacionKodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/esquema-contratacion-kodes/{id}")
    public ResponseEntity<EsquemaContratacionKodeDTO> getEsquemaContratacionKode(@PathVariable Long id) {
        log.debug("REST request to get EsquemaContratacionKode : {}", id);
        Optional<EsquemaContratacionKodeDTO> esquemaContratacionKodeDTO = esquemaContratacionKodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esquemaContratacionKodeDTO);
    }

    /**
     * {@code DELETE  /esquema-contratacion-kodes/:id} : delete the "id" esquemaContratacionKode.
     *
     * @param id the id of the esquemaContratacionKodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/esquema-contratacion-kodes/{id}")
    public ResponseEntity<Void> deleteEsquemaContratacionKode(@PathVariable Long id) {
        log.debug("REST request to delete EsquemaContratacionKode : {}", id);
        esquemaContratacionKodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
