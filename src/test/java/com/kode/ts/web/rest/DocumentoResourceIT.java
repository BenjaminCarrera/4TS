package com.kode.ts.web.rest;

import com.kode.ts.Application;
import com.kode.ts.domain.Documento;
import com.kode.ts.repository.DocumentoRepository;
import com.kode.ts.service.DocumentoService;
import com.kode.ts.service.dto.DocumentoDTO;
import com.kode.ts.service.mapper.DocumentoMapper;
import com.kode.ts.web.rest.errors.ExceptionTranslator;
import com.kode.ts.service.dto.DocumentoCriteria;
import com.kode.ts.service.DocumentoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kode.ts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DocumentoResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class DocumentoResourceIT {

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private DocumentoQueryService documentoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoResource documentoResource = new DocumentoResource(documentoService, documentoQueryService);
        this.restDocumentoMockMvc = MockMvcBuilders.standaloneSetup(documentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
            .documento(DEFAULT_DOCUMENTO);
        return documento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity(EntityManager em) {
        Documento documento = new Documento()
            .documento(UPDATED_DOCUMENTO);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
    }

    @Test
    @Transactional
    public void createDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento with an existing ID
        documento.setId(1L);
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()));
    }

    @Test
    @Transactional
    public void getAllDocumentosByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList where documento equals to DEFAULT_DOCUMENTO
        defaultDocumentoShouldBeFound("documento.equals=" + DEFAULT_DOCUMENTO);

        // Get all the documentoList where documento equals to UPDATED_DOCUMENTO
        defaultDocumentoShouldNotBeFound("documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllDocumentosByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList where documento in DEFAULT_DOCUMENTO or UPDATED_DOCUMENTO
        defaultDocumentoShouldBeFound("documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO);

        // Get all the documentoList where documento equals to UPDATED_DOCUMENTO
        defaultDocumentoShouldNotBeFound("documento.in=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllDocumentosByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList where documento is not null
        defaultDocumentoShouldBeFound("documento.specified=true");

        // Get all the documentoList where documento is null
        defaultDocumentoShouldNotBeFound("documento.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentoShouldBeFound(String filter) throws Exception {
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)));

        // Check, that the count call also returns 1
        restDocumentoMockMvc.perform(get("/api/documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentoShouldNotBeFound(String filter) throws Exception {
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentoMockMvc.perform(get("/api/documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .documento(UPDATED_DOCUMENTO);
        DocumentoDTO documentoDTO = documentoMapper.toDto(updatedDocumento);

        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc.perform(delete("/api/documentos/{id}", documento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documento.class);
        Documento documento1 = new Documento();
        documento1.setId(1L);
        Documento documento2 = new Documento();
        documento2.setId(documento1.getId());
        assertThat(documento1).isEqualTo(documento2);
        documento2.setId(2L);
        assertThat(documento1).isNotEqualTo(documento2);
        documento1.setId(null);
        assertThat(documento1).isNotEqualTo(documento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoDTO.class);
        DocumentoDTO documentoDTO1 = new DocumentoDTO();
        documentoDTO1.setId(1L);
        DocumentoDTO documentoDTO2 = new DocumentoDTO();
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
        documentoDTO2.setId(documentoDTO1.getId());
        assertThat(documentoDTO1).isEqualTo(documentoDTO2);
        documentoDTO2.setId(2L);
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
        documentoDTO1.setId(null);
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentoMapper.fromId(null)).isNull();
    }
}
