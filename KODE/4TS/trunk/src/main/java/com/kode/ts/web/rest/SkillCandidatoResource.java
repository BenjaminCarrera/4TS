package com.kode.ts.web.rest;

import com.kode.ts.service.SkillCandidatoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.SkillCandidatoDTO;
import com.kode.ts.service.dto.ListaSkillCandidatoDTO;
import com.kode.ts.service.dto.ListaSkillRequerimientoDTO;
import com.kode.ts.service.dto.SkillCandidatoCriteria;
import com.kode.ts.service.SkillCandidatoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.SkillCandidato}.
 */
@RestController
@RequestMapping("/api")
public class SkillCandidatoResource {

    private final Logger log = LoggerFactory.getLogger(SkillCandidatoResource.class);

    private static final String ENTITY_NAME = "skillCandidato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillCandidatoService skillCandidatoService;

    private final SkillCandidatoQueryService skillCandidatoQueryService;

    public SkillCandidatoResource(SkillCandidatoService skillCandidatoService, SkillCandidatoQueryService skillCandidatoQueryService) {
        this.skillCandidatoService = skillCandidatoService;
        this.skillCandidatoQueryService = skillCandidatoQueryService;
    }

    /**
     * {@code POST  /skill-candidatoes} : Create a new skillCandidato.
     *
     * @param skillCandidatoDTO the skillCandidatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillCandidatoDTO, or with status {@code 400 (Bad Request)} if the skillCandidato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-candidatoes")
    public ResponseEntity<SkillCandidatoDTO> createSkillCandidato(@RequestBody SkillCandidatoDTO skillCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to save SkillCandidato : {}", skillCandidatoDTO);
        if (skillCandidatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillCandidato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillCandidatoDTO result = skillCandidatoService.save(skillCandidatoDTO);
        return ResponseEntity.created(new URI("/api/skill-candidatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-candidatoes} : Updates an existing skillCandidato.
     *
     * @param skillCandidatoDTO the skillCandidatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillCandidatoDTO,
     * or with status {@code 400 (Bad Request)} if the skillCandidatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillCandidatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-candidatoes")
    public ResponseEntity<SkillCandidatoDTO> updateSkillCandidato(@RequestBody SkillCandidatoDTO skillCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to update SkillCandidato : {}", skillCandidatoDTO);
        if (skillCandidatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillCandidatoDTO result = skillCandidatoService.save(skillCandidatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillCandidatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-candidatoes} : get all the skillCandidatoes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillCandidatoes in body.
     */
    @GetMapping("/skill-candidatoes")
    public ResponseEntity<List<SkillCandidatoDTO>> getAllSkillCandidatoes(SkillCandidatoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SkillCandidatoes by criteria: {}", criteria);
        Page<SkillCandidatoDTO> page = skillCandidatoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /skill-candidatoes/count} : count all the skillCandidatoes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/skill-candidatoes/count")
    public ResponseEntity<Long> countSkillCandidatoes(SkillCandidatoCriteria criteria) {
        log.debug("REST request to count SkillCandidatoes by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillCandidatoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-candidatoes/:id} : get the "id" skillCandidato.
     *
     * @param id the id of the skillCandidatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillCandidatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-candidatoes/{id}")
    public ResponseEntity<SkillCandidatoDTO> getSkillCandidato(@PathVariable Long id) {
        log.debug("REST request to get SkillCandidato : {}", id);
        Optional<SkillCandidatoDTO> skillCandidatoDTO = skillCandidatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillCandidatoDTO);
    }
    
    /**
     * {@code PATCH  /skill-requerimientos} : Updates an existing skillRequerimientos.
     *
     * @param Array of skillRequerimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillRequerimientoDTO,
     * or with status {@code 400 (Bad Request)} if the skillRequerimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillRequerimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping("/skill-candidatoes")
    public ResponseEntity<ListaSkillCandidatoDTO> patchSkillCandidato(@RequestBody ListaSkillCandidatoDTO listaSkillCandidatoDTO) throws URISyntaxException {
        log.debug("REST request to update SkillRequerimiento : {}", listaSkillCandidatoDTO);
        
        ListaSkillCandidatoDTO result = skillCandidatoService.saveLista(listaSkillCandidatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaSkillCandidatoDTO.getLista().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /skill-candidatoes/:id} : delete the "id" skillCandidato.
     *
     * @param id the id of the skillCandidatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-candidatoes/{id}")
    public ResponseEntity<Void> deleteSkillCandidato(@PathVariable Long id) {
        log.debug("REST request to delete SkillCandidato : {}", id);
        skillCandidatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
