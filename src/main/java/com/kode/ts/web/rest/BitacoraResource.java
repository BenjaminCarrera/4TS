package com.kode.ts.web.rest;

import com.kode.ts.service.BitacoraService;
import com.kode.ts.web.rest.errors.BadRequestAlertException;
import com.kode.ts.service.dto.BitacoraDTO;
import com.kode.ts.service.dto.BitacoraCriteria;
import com.kode.ts.service.BitacoraQueryService;

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
 * REST controller for managing {@link com.kode.ts.domain.Bitacora}.
 */
@RestController
@RequestMapping("/api")
public class BitacoraResource {

    private final Logger log = LoggerFactory.getLogger(BitacoraResource.class);

    private static final String ENTITY_NAME = "bitacora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BitacoraService bitacoraService;

    private final BitacoraQueryService bitacoraQueryService;

    public BitacoraResource(BitacoraService bitacoraService, BitacoraQueryService bitacoraQueryService) {
        this.bitacoraService = bitacoraService;
        this.bitacoraQueryService = bitacoraQueryService;
    }

    /**
     * {@code POST  /bitacoras} : Create a new bitacora.
     *
     * @param bitacoraDTO the bitacoraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bitacoraDTO, or with status {@code 400 (Bad Request)} if the bitacora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bitacoras")
    public ResponseEntity<BitacoraDTO> createBitacora(@Valid @RequestBody BitacoraDTO bitacoraDTO) throws URISyntaxException {
        log.debug("REST request to save Bitacora : {}", bitacoraDTO);
        if (bitacoraDTO.getId() != null) {
            throw new BadRequestAlertException("A new bitacora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BitacoraDTO result = bitacoraService.save(bitacoraDTO);
        return ResponseEntity.created(new URI("/api/bitacoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bitacoras} : Updates an existing bitacora.
     *
     * @param bitacoraDTO the bitacoraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bitacoraDTO,
     * or with status {@code 400 (Bad Request)} if the bitacoraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bitacoraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bitacoras")
    public ResponseEntity<BitacoraDTO> updateBitacora(@Valid @RequestBody BitacoraDTO bitacoraDTO) throws URISyntaxException {
        log.debug("REST request to update Bitacora : {}", bitacoraDTO);
        if (bitacoraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BitacoraDTO result = bitacoraService.save(bitacoraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bitacoraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bitacoras} : get all the bitacoras.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bitacoras in body.
     */
    @GetMapping("/bitacoras")
    public ResponseEntity<List<BitacoraDTO>> getAllBitacoras(BitacoraCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Bitacoras by criteria: {}", criteria);
        Page<BitacoraDTO> page = bitacoraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /bitacoras/count} : count all the bitacoras.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/bitacoras/count")
    public ResponseEntity<Long> countBitacoras(BitacoraCriteria criteria) {
        log.debug("REST request to count Bitacoras by criteria: {}", criteria);
        return ResponseEntity.ok().body(bitacoraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bitacoras/:id} : get the "id" bitacora.
     *
     * @param id the id of the bitacoraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bitacoraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bitacoras/{id}")
    public ResponseEntity<BitacoraDTO> getBitacora(@PathVariable Long id) {
        log.debug("REST request to get Bitacora : {}", id);
        Optional<BitacoraDTO> bitacoraDTO = bitacoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bitacoraDTO);
    }

    /**
     * {@code DELETE  /bitacoras/:id} : delete the "id" bitacora.
     *
     * @param id the id of the bitacoraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bitacoras/{id}")
    public ResponseEntity<Void> deleteBitacora(@PathVariable Long id) {
        log.debug("REST request to delete Bitacora : {}", id);
        bitacoraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
