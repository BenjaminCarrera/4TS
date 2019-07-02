package com.kode.ts.web.rest;

import com.kode.ts.service.NivelPerfilService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.NivelPerfilDTO;
import com.kode.ts.service.dto.NivelPerfilCriteria;
import com.kode.ts.service.NivelPerfilQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.NivelPerfil}.
 */
@RestController
@RequestMapping("/api")
public class NivelPerfilResource {

    private final Logger log = LoggerFactory.getLogger(NivelPerfilResource.class);

    private static final String ENTITY_NAME = "nivelPerfil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelPerfilService nivelPerfilService;

    private final NivelPerfilQueryService nivelPerfilQueryService;

    public NivelPerfilResource(NivelPerfilService nivelPerfilService, NivelPerfilQueryService nivelPerfilQueryService) {
        this.nivelPerfilService = nivelPerfilService;
        this.nivelPerfilQueryService = nivelPerfilQueryService;
    }

    /**
     * {@code POST  /nivel-perfils} : Create a new nivelPerfil.
     *
     * @param nivelPerfilDTO the nivelPerfilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelPerfilDTO, or with status {@code 400 (Bad Request)} if the nivelPerfil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-perfils")
    public ResponseEntity<NivelPerfilDTO> createNivelPerfil(@Valid @RequestBody NivelPerfilDTO nivelPerfilDTO) throws URISyntaxException {
        log.debug("REST request to save NivelPerfil : {}", nivelPerfilDTO);
        if (nivelPerfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new nivelPerfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelPerfilDTO result = nivelPerfilService.save(nivelPerfilDTO);
        return ResponseEntity.created(new URI("/api/nivel-perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-perfils} : Updates an existing nivelPerfil.
     *
     * @param nivelPerfilDTO the nivelPerfilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelPerfilDTO,
     * or with status {@code 400 (Bad Request)} if the nivelPerfilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelPerfilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-perfils")
    public ResponseEntity<NivelPerfilDTO> updateNivelPerfil(@Valid @RequestBody NivelPerfilDTO nivelPerfilDTO) throws URISyntaxException {
        log.debug("REST request to update NivelPerfil : {}", nivelPerfilDTO);
        if (nivelPerfilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelPerfilDTO result = nivelPerfilService.save(nivelPerfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelPerfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nivel-perfils} : get all the nivelPerfils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelPerfils in body.
     */
    @GetMapping("/nivel-perfils")
    public ResponseEntity<List<NivelPerfilDTO>> getAllNivelPerfils(NivelPerfilCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get NivelPerfils by criteria: {}", criteria);
        Page<NivelPerfilDTO> page = nivelPerfilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /nivel-perfils/count} : count all the nivelPerfils.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/nivel-perfils/count")
    public ResponseEntity<Long> countNivelPerfils(NivelPerfilCriteria criteria) {
        log.debug("REST request to count NivelPerfils by criteria: {}", criteria);
        return ResponseEntity.ok().body(nivelPerfilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nivel-perfils/:id} : get the "id" nivelPerfil.
     *
     * @param id the id of the nivelPerfilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelPerfilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-perfils/{id}")
    public ResponseEntity<NivelPerfilDTO> getNivelPerfil(@PathVariable Long id) {
        log.debug("REST request to get NivelPerfil : {}", id);
        Optional<NivelPerfilDTO> nivelPerfilDTO = nivelPerfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelPerfilDTO);
    }

    /**
     * {@code DELETE  /nivel-perfils/:id} : delete the "id" nivelPerfil.
     *
     * @param id the id of the nivelPerfilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-perfils/{id}")
    public ResponseEntity<Void> deleteNivelPerfil(@PathVariable Long id) {
        log.debug("REST request to delete NivelPerfil : {}", id);
        nivelPerfilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
