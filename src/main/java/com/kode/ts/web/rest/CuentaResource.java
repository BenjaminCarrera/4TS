package com.kode.ts.web.rest;

import com.kode.ts.service.CuentaService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.CuentaDTO;
import com.kode.ts.service.dto.CuentaCriteria;
import com.kode.ts.service.CuentaQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Cuenta}.
 */
@RestController
@RequestMapping("/api")
public class CuentaResource {

    private final Logger log = LoggerFactory.getLogger(CuentaResource.class);

    private static final String ENTITY_NAME = "cuenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuentaService cuentaService;

    private final CuentaQueryService cuentaQueryService;

    public CuentaResource(CuentaService cuentaService, CuentaQueryService cuentaQueryService) {
        this.cuentaService = cuentaService;
        this.cuentaQueryService = cuentaQueryService;
    }

    /**
     * {@code POST  /cuentas} : Create a new cuenta.
     *
     * @param cuentaDTO the cuentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuentaDTO, or with status {@code 400 (Bad Request)} if the cuenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cuentas")
    public ResponseEntity<CuentaDTO> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) throws URISyntaxException {
        log.debug("REST request to save Cuenta : {}", cuentaDTO);
        if (cuentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CuentaDTO result = cuentaService.save(cuentaDTO);
        return ResponseEntity.created(new URI("/api/cuentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuentas} : Updates an existing cuenta.
     *
     * @param cuentaDTO the cuentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentaDTO,
     * or with status {@code 400 (Bad Request)} if the cuentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuentas")
    public ResponseEntity<CuentaDTO> updateCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) throws URISyntaxException {
        log.debug("REST request to update Cuenta : {}", cuentaDTO);
        if (cuentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CuentaDTO result = cuentaService.save(cuentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cuentas} : get all the cuentas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuentas in body.
     */
    @GetMapping("/cuentas")
    public ResponseEntity<List<CuentaDTO>> getAllCuentas(CuentaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Cuentas by criteria: {}", criteria);
        Page<CuentaDTO> page = cuentaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cuentas/count} : count all the cuentas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cuentas/count")
    public ResponseEntity<Long> countCuentas(CuentaCriteria criteria) {
        log.debug("REST request to count Cuentas by criteria: {}", criteria);
        return ResponseEntity.ok().body(cuentaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cuentas/:id} : get the "id" cuenta.
     *
     * @param id the id of the cuentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id) {
        log.debug("REST request to get Cuenta : {}", id);
        Optional<CuentaDTO> cuentaDTO = cuentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuentaDTO);
    }

    /**
     * {@code DELETE  /cuentas/:id} : delete the "id" cuenta.
     *
     * @param id the id of the cuentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        log.debug("REST request to delete Cuenta : {}", id);
        cuentaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
