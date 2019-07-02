package com.kode.ts.web.rest;

import com.kode.ts.service.DocumentoService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.DocumentoDTO;
import com.kode.ts.service.dto.DocumentoCriteria;
import com.kode.ts.service.DocumentoQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Documento}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);

    private static final String ENTITY_NAME = "documento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoService documentoService;

    private final DocumentoQueryService documentoQueryService;

    public DocumentoResource(DocumentoService documentoService, DocumentoQueryService documentoQueryService) {
        this.documentoService = documentoService;
        this.documentoQueryService = documentoQueryService;
    }

    /**
     * {@code POST  /documentos} : Create a new documento.
     *
     * @param documentoDTO the documentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoDTO, or with status {@code 400 (Bad Request)} if the documento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos")
    public ResponseEntity<DocumentoDTO> createDocumento(@RequestBody DocumentoDTO documentoDTO) throws URISyntaxException {
        log.debug("REST request to save Documento : {}", documentoDTO);
        if (documentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new documento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentoDTO result = documentoService.save(documentoDTO);
        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentos} : Updates an existing documento.
     *
     * @param documentoDTO the documentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoDTO,
     * or with status {@code 400 (Bad Request)} if the documentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentos")
    public ResponseEntity<DocumentoDTO> updateDocumento(@RequestBody DocumentoDTO documentoDTO) throws URISyntaxException {
        log.debug("REST request to update Documento : {}", documentoDTO);
        if (documentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentoDTO result = documentoService.save(documentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentos} : get all the documentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentos in body.
     */
    @GetMapping("/documentos")
    public ResponseEntity<List<DocumentoDTO>> getAllDocumentos(DocumentoCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Documentos by criteria: {}", criteria);
        Page<DocumentoDTO> page = documentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /documentos/count} : count all the documentos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/documentos/count")
    public ResponseEntity<Long> countDocumentos(DocumentoCriteria criteria) {
        log.debug("REST request to count Documentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /documentos/:id} : get the "id" documento.
     *
     * @param id the id of the documentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos/{id}")
    public ResponseEntity<DocumentoDTO> getDocumento(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        Optional<DocumentoDTO> documentoDTO = documentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoDTO);
    }

    /**
     * {@code DELETE  /documentos/:id} : delete the "id" documento.
     *
     * @param id the id of the documentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        log.debug("REST request to delete Documento : {}", id);
        documentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
