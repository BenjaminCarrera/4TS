package com.kode.ts.service.impl;

import com.kode.ts.service.SkillCandidatoService;
import com.kode.ts.domain.SkillCandidato;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.repository.SkillCandidatoRepository;
import com.kode.ts.service.dto.ListaSkillCandidatoDTO;
import com.kode.ts.service.dto.ListaSkillRequerimientoDTO;
import com.kode.ts.service.dto.SkillCandidatoDTO;
import com.kode.ts.service.dto.SkillRequerimientoDTO;
import com.kode.ts.service.mapper.SkillCandidatoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.persistence.RollbackException;

/**
 * Service Implementation for managing {@link SkillCandidato}.
 */
@Service
@Transactional
public class SkillCandidatoServiceImpl implements SkillCandidatoService {

    private final Logger log = LoggerFactory.getLogger(SkillCandidatoServiceImpl.class);

    private final SkillCandidatoRepository skillCandidatoRepository;

    private final SkillCandidatoMapper skillCandidatoMapper;

    public SkillCandidatoServiceImpl(SkillCandidatoRepository skillCandidatoRepository, SkillCandidatoMapper skillCandidatoMapper) {
        this.skillCandidatoRepository = skillCandidatoRepository;
        this.skillCandidatoMapper = skillCandidatoMapper;
    }

    /**
     * Save a skillCandidato.
     *
     * @param skillCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SkillCandidatoDTO save(SkillCandidatoDTO skillCandidatoDTO) {
        log.debug("Request to save SkillCandidato : {}", skillCandidatoDTO);
        SkillCandidato skillCandidato = skillCandidatoMapper.toEntity(skillCandidatoDTO);
        skillCandidato = skillCandidatoRepository.save(skillCandidato);
        return skillCandidatoMapper.toDto(skillCandidato);
    }
    
    /**
     * Save a skillCandidato.
     *
     * @param skillCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public ListaSkillCandidatoDTO saveLista(ListaSkillCandidatoDTO listaSkillCandidatoDTO) {
        log.debug("Request to save SkillRequerimiento : {}", listaSkillCandidatoDTO);
        if(listaSkillCandidatoDTO.getLista().size() > 0) {
        	loopSkillCandidatos(listaSkillCandidatoDTO.getLista());
        }
        return listaSkillCandidatoDTO;
    }

    /**
     * Get all the skillCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkillCandidatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillCandidatoes");
        return skillCandidatoRepository.findAll(pageable)
            .map(skillCandidatoMapper::toDto);
    }


    /**
     * Get one skillCandidato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillCandidatoDTO> findOne(Long id) {
        log.debug("Request to get SkillCandidato : {}", id);
        return skillCandidatoRepository.findById(id)
            .map(skillCandidatoMapper::toDto);
    }

    /**
     * Delete the skillCandidato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillCandidato : {}", id);
        skillCandidatoRepository.deleteById(id);
    }
    
    /**
     * Save the skillRequerimientos by list.
     *
     * @param list of SkillRequerimientoDTO entity.
     */
    @Transactional
    private void loopSkillCandidatos(List<SkillCandidatoDTO> lista) {
    	
        for(SkillCandidatoDTO item : lista) {
        	try {
        		SkillCandidato skillCandidato = skillCandidatoMapper.toEntity(item);
        		skillCandidato = skillCandidatoRepository.save(skillCandidato);
        	} catch (RollbackException e) {
				
			} catch (Exception e) {
				
			}	
        }
        
    }
}
