package com.kode.ts.web.rest;

import com.kode.ts.service.CategoriaSkillService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.CategoriaSkillDTO;
import com.kode.ts.service.dto.CategoriaSkillCriteria;
import com.kode.ts.service.CategoriaSkillQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.CategoriaSkill}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaSkillResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaSkillResource.class);

    private static final String ENTITY_NAME = "categoriaSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaSkillService categoriaSkillService;

    private final CategoriaSkillQueryService categoriaSkillQueryService;

    public CategoriaSkillResource(CategoriaSkillService categoriaSkillService, CategoriaSkillQueryService categoriaSkillQueryService) {
        this.categoriaSkillService = categoriaSkillService;
        this.categoriaSkillQueryService = categoriaSkillQueryService;
    }

    /**
     * {@code POST  /categoria-skills} : Create a new categoriaSkill.
     *
     * @param categoriaSkillDTO the categoriaSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaSkillDTO, or with status {@code 400 (Bad Request)} if the categoriaSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-skills")
    public ResponseEntity<CategoriaSkillDTO> createCategoriaSkill(@Valid @RequestBody CategoriaSkillDTO categoriaSkillDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriaSkill : {}", categoriaSkillDTO);
        if (categoriaSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaSkillDTO result = categoriaSkillService.save(categoriaSkillDTO);
        return ResponseEntity.created(new URI("/api/categoria-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-skills} : Updates an existing categoriaSkill.
     *
     * @param categoriaSkillDTO the categoriaSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaSkillDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-skills")
    public ResponseEntity<CategoriaSkillDTO> updateCategoriaSkill(@Valid @RequestBody CategoriaSkillDTO categoriaSkillDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriaSkill : {}", categoriaSkillDTO);
        if (categoriaSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaSkillDTO result = categoriaSkillService.save(categoriaSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-skills} : get all the categoriaSkills.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaSkills in body.
     */
    @GetMapping("/categoria-skills")
    public ResponseEntity<List<CategoriaSkillDTO>> getAllCategoriaSkills(CategoriaSkillCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CategoriaSkills by criteria: {}", criteria);
        Page<CategoriaSkillDTO> page = categoriaSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /categoria-skills/count} : count all the categoriaSkills.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/categoria-skills/count")
    public ResponseEntity<Long> countCategoriaSkills(CategoriaSkillCriteria criteria) {
        log.debug("REST request to count CategoriaSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-skills/:id} : get the "id" categoriaSkill.
     *
     * @param id the id of the categoriaSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-skills/{id}")
    public ResponseEntity<CategoriaSkillDTO> getCategoriaSkill(@PathVariable Long id) {
        log.debug("REST request to get CategoriaSkill : {}", id);
        Optional<CategoriaSkillDTO> categoriaSkillDTO = categoriaSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaSkillDTO);
    }

    /**
     * {@code DELETE  /categoria-skills/:id} : delete the "id" categoriaSkill.
     *
     * @param id the id of the categoriaSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-skills/{id}")
    public ResponseEntity<Void> deleteCategoriaSkill(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaSkill : {}", id);
        categoriaSkillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
