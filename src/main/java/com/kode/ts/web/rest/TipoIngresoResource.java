package com.kode.ts.web.rest;

import com.kode.ts.service.TipoIngresoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TipoIngresoDTO;
import com.kode.ts.service.dto.TipoIngresoCriteria;
import com.kode.ts.service.TipoIngresoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.TipoIngreso}.
 */
@RestController
@RequestMapping("/api")
public class TipoIngresoResource {

    private final Logger log = LoggerFactory.getLogger(TipoIngresoResource.class);

    private static final String ENTITY_NAME = "tipoIngreso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoIngresoService tipoIngresoService;

    private final TipoIngresoQueryService tipoIngresoQueryService;

    public TipoIngresoResource(TipoIngresoService tipoIngresoService, TipoIngresoQueryService tipoIngresoQueryService) {
        this.tipoIngresoService = tipoIngresoService;
        this.tipoIngresoQueryService = tipoIngresoQueryService;
    }

    /**
     * {@code POST  /tipo-ingresos} : Create a new tipoIngreso.
     *
     * @param tipoIngresoDTO the tipoIngresoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoIngresoDTO, or with status {@code 400 (Bad Request)} if the tipoIngreso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-ingresos")
    public ResponseEntity<TipoIngresoDTO> createTipoIngreso(@Valid @RequestBody TipoIngresoDTO tipoIngresoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoIngreso : {}", tipoIngresoDTO);
        if (tipoIngresoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoIngreso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoIngresoDTO result = tipoIngresoService.save(tipoIngresoDTO);
        return ResponseEntity.created(new URI("/api/tipo-ingresos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-ingresos} : Updates an existing tipoIngreso.
     *
     * @param tipoIngresoDTO the tipoIngresoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoIngresoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoIngresoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoIngresoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-ingresos")
    public ResponseEntity<TipoIngresoDTO> updateTipoIngreso(@Valid @RequestBody TipoIngresoDTO tipoIngresoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoIngreso : {}", tipoIngresoDTO);
        if (tipoIngresoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoIngresoDTO result = tipoIngresoService.save(tipoIngresoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoIngresoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-ingresos} : get all the tipoIngresos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoIngresos in body.
     */
    @GetMapping("/tipo-ingresos")
    public ResponseEntity<List<TipoIngresoDTO>> getAllTipoIngresos(TipoIngresoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TipoIngresos by criteria: {}", criteria);
        Page<TipoIngresoDTO> page = tipoIngresoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-ingresos/count} : count all the tipoIngresos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-ingresos/count")
    public ResponseEntity<Long> countTipoIngresos(TipoIngresoCriteria criteria) {
        log.debug("REST request to count TipoIngresos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoIngresoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-ingresos/:id} : get the "id" tipoIngreso.
     *
     * @param id the id of the tipoIngresoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoIngresoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-ingresos/{id}")
    public ResponseEntity<TipoIngresoDTO> getTipoIngreso(@PathVariable Long id) {
        log.debug("REST request to get TipoIngreso : {}", id);
        Optional<TipoIngresoDTO> tipoIngresoDTO = tipoIngresoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoIngresoDTO);
    }

    /**
     * {@code DELETE  /tipo-ingresos/:id} : delete the "id" tipoIngreso.
     *
     * @param id the id of the tipoIngresoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-ingresos/{id}")
    public ResponseEntity<Void> deleteTipoIngreso(@PathVariable Long id) {
        log.debug("REST request to delete TipoIngreso : {}", id);
        tipoIngresoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
