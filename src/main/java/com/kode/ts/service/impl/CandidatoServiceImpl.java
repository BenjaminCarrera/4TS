package com.kode.ts.service.impl;

import com.kode.ts.service.CandidatoService;
import com.kode.ts.config.ApplicationProperties;
import com.kode.ts.domain.Candidato;
import com.kode.ts.repository.CandidatoRepository;
import com.kode.ts.service.dto.CandidatoDTO;
import com.kode.ts.service.mapper.CandidatoMapper;
import com.kode.ts.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Candidato}.
 */
@Service
@Transactional
public class CandidatoServiceImpl implements CandidatoService {

	private final Logger log = LoggerFactory.getLogger(CandidatoServiceImpl.class);

	private final CandidatoRepository candidatoRepository;

	private final CandidatoMapper candidatoMapper;

	private final ApplicationProperties applicationProperties;

	public CandidatoServiceImpl(CandidatoRepository candidatoRepository, CandidatoMapper candidatoMapper,
			ApplicationProperties applicationProperties) {
		this.candidatoRepository = candidatoRepository;
		this.candidatoMapper = candidatoMapper;
		this.applicationProperties = applicationProperties;
	}

	/**
	 * Save a candidato.
	 *
	 * @param candidatoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public CandidatoDTO save(CandidatoDTO candidatoDTO) {
		log.debug("Request to save Candidato : {}", candidatoDTO);
		Candidato candidato = candidatoMapper.toEntity(candidatoDTO);
		candidato = candidatoRepository.save(candidato);
		return candidatoMapper.toDto(candidato);
	}

	/**
	 * Save a candidato.
	 *
	 * @param candidatoDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public String saveImage(MultipartFile file, Long candidatoId) {
		log.debug("Request to save Candidato Image : {}", candidatoId);
		BufferedImage image = null;
		byte[] bytes = null;
		String fineName = null;
		try {

			// Get the file and save it somewhere
			bytes = file.getBytes();
			Optional<String> fileExt = getExtensionByStringHandling(file.getOriginalFilename());
			fineName = "candidato_" + candidatoId + "." + fileExt.get();
			Path path = Paths.get(applicationProperties.getUserImagePath() + fineName);
			Files.write(path, bytes);
			
			Optional<CandidatoDTO> candidatoDTO = this.findOne(candidatoId);
			CandidatoDTO candidatoSave = candidatoDTO.get(); 
			candidatoSave.setFoto(fineName);
			this.save(candidatoSave);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fineName;
	}

	/**
	 * Get all the candidatoes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<CandidatoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Candidatoes");
		return candidatoRepository.findAll(pageable).map(candidatoMapper::toDto);
	}

	/**
	 * Get all the candidatoes with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<CandidatoDTO> findAllWithEagerRelationships(Pageable pageable) {
		return candidatoRepository.findAllWithEagerRelationships(pageable).map(candidatoMapper::toDto);
	}

	/**
	 * Get one candidato by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CandidatoDTO> findOne(Long id) {
		log.debug("Request to get Candidato : {}", id);
		return candidatoRepository.findOneWithEagerRelationships(id).map(candidatoMapper::toDto);
	}

	/**
	 * Delete the candidato by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Candidato : {}", id);
		candidatoRepository.deleteById(id);
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
