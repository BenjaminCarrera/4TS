package com.kode.ts.web.rest;

import com.kode.ts.service.SkillRequerimientoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.SkillRequerimientoDTO;
import com.kode.ts.service.dto.SkillRequerimientoCriteria;
import com.kode.ts.service.SkillRequerimientoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.SkillRequerimiento}.
 */
@RestController
@RequestMapping("/api")
public class SkillRequerimientoResource {

    private final Logger log = LoggerFactory.getLogger(SkillRequerimientoResource.class);

    private static final String ENTITY_NAME = "skillRequerimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillRequerimientoService skillRequerimientoService;

    private final SkillRequerimientoQueryService skillRequerimientoQueryService;

    public SkillRequerimientoResource(SkillRequerimientoService skillRequerimientoService, SkillRequerimientoQueryService skillRequerimientoQueryService) {
        this.skillRequerimientoService = skillRequerimientoService;
        this.skillRequerimientoQueryService = skillRequerimientoQueryService;
    }

    /**
     * {@code POST  /skill-requerimientos} : Create a new skillRequerimiento.
     *
     * @param skillRequerimientoDTO the skillRequerimientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillRequerimientoDTO, or with status {@code 400 (Bad Request)} if the skillRequerimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-requerimientos")
    public ResponseEntity<SkillRequerimientoDTO> createSkillRequerimiento(@RequestBody SkillRequerimientoDTO skillRequerimientoDTO) throws URISyntaxException {
        log.debug("REST request to save SkillRequerimiento : {}", skillRequerimientoDTO);
        if (skillRequerimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillRequerimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillRequerimientoDTO result = skillRequerimientoService.save(skillRequerimientoDTO);
        return ResponseEntity.created(new URI("/api/skill-requerimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-requerimientos} : Updates an existing skillRequerimiento.
     *
     * @param skillRequerimientoDTO the skillRequerimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillRequerimientoDTO,
     * or with status {@code 400 (Bad Request)} if the skillRequerimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillRequerimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-requerimientos")
    public ResponseEntity<SkillRequerimientoDTO> updateSkillRequerimiento(@RequestBody SkillRequerimientoDTO skillRequerimientoDTO) throws URISyntaxException {
        log.debug("REST request to update SkillRequerimiento : {}", skillRequerimientoDTO);
        if (skillRequerimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillRequerimientoDTO result = skillRequerimientoService.save(skillRequerimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillRequerimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-requerimientos} : get all the skillRequerimientos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillRequerimientos in body.
     */
    @GetMapping("/skill-requerimientos")
    public ResponseEntity<List<SkillRequerimientoDTO>> getAllSkillRequerimientos(SkillRequerimientoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SkillRequerimientos by criteria: {}", criteria);
        Page<SkillRequerimientoDTO> page = skillRequerimientoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /skill-requerimientos/count} : count all the skillRequerimientos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/skill-requerimientos/count")
    public ResponseEntity<Long> countSkillRequerimientos(SkillRequerimientoCriteria criteria) {
        log.debug("REST request to count SkillRequerimientos by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillRequerimientoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-requerimientos/:id} : get the "id" skillRequerimiento.
     *
     * @param id the id of the skillRequerimientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillRequerimientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-requerimientos/{id}")
    public ResponseEntity<SkillRequerimientoDTO> getSkillRequerimiento(@PathVariable Long id) {
        log.debug("REST request to get SkillRequerimiento : {}", id);
        Optional<SkillRequerimientoDTO> skillRequerimientoDTO = skillRequerimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillRequerimientoDTO);
    }

    /**
     * {@code DELETE  /skill-requerimientos/:id} : delete the "id" skillRequerimiento.
     *
     * @param id the id of the skillRequerimientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-requerimientos/{id}")
    public ResponseEntity<Void> deleteSkillRequerimiento(@PathVariable Long id) {
        log.debug("REST request to delete SkillRequerimiento : {}", id);
        skillRequerimientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
