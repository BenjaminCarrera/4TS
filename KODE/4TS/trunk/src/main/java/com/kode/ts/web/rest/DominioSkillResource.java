package com.kode.ts.web.rest;

import com.kode.ts.service.DominioSkillService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.DominioSkillDTO;
import com.kode.ts.service.dto.DominioSkillCriteria;
import com.kode.ts.service.DominioSkillQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.DominioSkill}.
 */
@RestController
@RequestMapping("/api")
public class DominioSkillResource {

    private final Logger log = LoggerFactory.getLogger(DominioSkillResource.class);

    private static final String ENTITY_NAME = "dominioSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DominioSkillService dominioSkillService;

    private final DominioSkillQueryService dominioSkillQueryService;

    public DominioSkillResource(DominioSkillService dominioSkillService, DominioSkillQueryService dominioSkillQueryService) {
        this.dominioSkillService = dominioSkillService;
        this.dominioSkillQueryService = dominioSkillQueryService;
    }

    /**
     * {@code POST  /dominio-skills} : Create a new dominioSkill.
     *
     * @param dominioSkillDTO the dominioSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dominioSkillDTO, or with status {@code 400 (Bad Request)} if the dominioSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dominio-skills")
    public ResponseEntity<DominioSkillDTO> createDominioSkill(@Valid @RequestBody DominioSkillDTO dominioSkillDTO) throws URISyntaxException {
        log.debug("REST request to save DominioSkill : {}", dominioSkillDTO);
        if (dominioSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new dominioSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DominioSkillDTO result = dominioSkillService.save(dominioSkillDTO);
        return ResponseEntity.created(new URI("/api/dominio-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dominio-skills} : Updates an existing dominioSkill.
     *
     * @param dominioSkillDTO the dominioSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dominioSkillDTO,
     * or with status {@code 400 (Bad Request)} if the dominioSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dominioSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dominio-skills")
    public ResponseEntity<DominioSkillDTO> updateDominioSkill(@Valid @RequestBody DominioSkillDTO dominioSkillDTO) throws URISyntaxException {
        log.debug("REST request to update DominioSkill : {}", dominioSkillDTO);
        if (dominioSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DominioSkillDTO result = dominioSkillService.save(dominioSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dominioSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dominio-skills} : get all the dominioSkills.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dominioSkills in body.
     */
    @GetMapping("/dominio-skills")
    public ResponseEntity<List<DominioSkillDTO>> getAllDominioSkills(DominioSkillCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DominioSkills by criteria: {}", criteria);
        Page<DominioSkillDTO> page = dominioSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /dominio-skills/count} : count all the dominioSkills.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/dominio-skills/count")
    public ResponseEntity<Long> countDominioSkills(DominioSkillCriteria criteria) {
        log.debug("REST request to count DominioSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(dominioSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dominio-skills/:id} : get the "id" dominioSkill.
     *
     * @param id the id of the dominioSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dominioSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dominio-skills/{id}")
    public ResponseEntity<DominioSkillDTO> getDominioSkill(@PathVariable Long id) {
        log.debug("REST request to get DominioSkill : {}", id);
        Optional<DominioSkillDTO> dominioSkillDTO = dominioSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dominioSkillDTO);
    }

    /**
     * {@code DELETE  /dominio-skills/:id} : delete the "id" dominioSkill.
     *
     * @param id the id of the dominioSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dominio-skills/{id}")
    public ResponseEntity<Void> deleteDominioSkill(@PathVariable Long id) {
        log.debug("REST request to delete DominioSkill : {}", id);
        dominioSkillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
