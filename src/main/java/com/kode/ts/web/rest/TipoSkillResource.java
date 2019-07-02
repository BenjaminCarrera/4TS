package com.kode.ts.web.rest;

import com.kode.ts.service.TipoSkillService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.TipoSkillDTO;
import com.kode.ts.service.dto.TipoSkillCriteria;
import com.kode.ts.service.TipoSkillQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.TipoSkill}.
 */
@RestController
@RequestMapping("/api")
public class TipoSkillResource {

    private final Logger log = LoggerFactory.getLogger(TipoSkillResource.class);

    private static final String ENTITY_NAME = "tipoSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoSkillService tipoSkillService;

    private final TipoSkillQueryService tipoSkillQueryService;

    public TipoSkillResource(TipoSkillService tipoSkillService, TipoSkillQueryService tipoSkillQueryService) {
        this.tipoSkillService = tipoSkillService;
        this.tipoSkillQueryService = tipoSkillQueryService;
    }

    /**
     * {@code POST  /tipo-skills} : Create a new tipoSkill.
     *
     * @param tipoSkillDTO the tipoSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoSkillDTO, or with status {@code 400 (Bad Request)} if the tipoSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-skills")
    public ResponseEntity<TipoSkillDTO> createTipoSkill(@Valid @RequestBody TipoSkillDTO tipoSkillDTO) throws URISyntaxException {
        log.debug("REST request to save TipoSkill : {}", tipoSkillDTO);
        if (tipoSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSkillDTO result = tipoSkillService.save(tipoSkillDTO);
        return ResponseEntity.created(new URI("/api/tipo-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-skills} : Updates an existing tipoSkill.
     *
     * @param tipoSkillDTO the tipoSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSkillDTO,
     * or with status {@code 400 (Bad Request)} if the tipoSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-skills")
    public ResponseEntity<TipoSkillDTO> updateTipoSkill(@Valid @RequestBody TipoSkillDTO tipoSkillDTO) throws URISyntaxException {
        log.debug("REST request to update TipoSkill : {}", tipoSkillDTO);
        if (tipoSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoSkillDTO result = tipoSkillService.save(tipoSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-skills} : get all the tipoSkills.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoSkills in body.
     */
    @GetMapping("/tipo-skills")
    public ResponseEntity<List<TipoSkillDTO>> getAllTipoSkills(TipoSkillCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TipoSkills by criteria: {}", criteria);
        Page<TipoSkillDTO> page = tipoSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-skills/count} : count all the tipoSkills.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-skills/count")
    public ResponseEntity<Long> countTipoSkills(TipoSkillCriteria criteria) {
        log.debug("REST request to count TipoSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoSkillQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-skills/:id} : get the "id" tipoSkill.
     *
     * @param id the id of the tipoSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-skills/{id}")
    public ResponseEntity<TipoSkillDTO> getTipoSkill(@PathVariable Long id) {
        log.debug("REST request to get TipoSkill : {}", id);
        Optional<TipoSkillDTO> tipoSkillDTO = tipoSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoSkillDTO);
    }

    /**
     * {@code DELETE  /tipo-skills/:id} : delete the "id" tipoSkill.
     *
     * @param id the id of the tipoSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-skills/{id}")
    public ResponseEntity<Void> deleteTipoSkill(@PathVariable Long id) {
        log.debug("REST request to delete TipoSkill : {}", id);
        tipoSkillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
