package com.kode.ts.service.impl;

import com.kode.ts.service.SkillRequerimientoService;
import com.kode.ts.domain.SkillRequerimiento;
import com.kode.ts.repository.SkillRequerimientoRepository;
import com.kode.ts.service.dto.ListaSkillRequerimientoDTO;
import com.kode.ts.service.dto.SkillRequerimientoDTO;
import com.kode.ts.service.mapper.SkillRequerimientoMapper;
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
 * Service Implementation for managing {@link SkillRequerimiento}.
 */
@Service
@Transactional
public class SkillRequerimientoServiceImpl implements SkillRequerimientoService {

    private final Logger log = LoggerFactory.getLogger(SkillRequerimientoServiceImpl.class);

    private final SkillRequerimientoRepository skillRequerimientoRepository;

    private final SkillRequerimientoMapper skillRequerimientoMapper;

    public SkillRequerimientoServiceImpl(SkillRequerimientoRepository skillRequerimientoRepository, SkillRequerimientoMapper skillRequerimientoMapper) {
        this.skillRequerimientoRepository = skillRequerimientoRepository;
        this.skillRequerimientoMapper = skillRequerimientoMapper;
    }

    /**
     * Save a skillRequerimiento.
     *
     * @param skillRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SkillRequerimientoDTO save(SkillRequerimientoDTO skillRequerimientoDTO) {
        log.debug("Request to save SkillRequerimiento : {}", skillRequerimientoDTO);
        SkillRequerimiento skillRequerimiento = skillRequerimientoMapper.toEntity(skillRequerimientoDTO);
        skillRequerimiento = skillRequerimientoRepository.save(skillRequerimiento);
        return skillRequerimientoMapper.toDto(skillRequerimiento);
    }
    
    /**
     * Save a skillRequerimiento.
     *
     * @param skillRequerimientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public ListaSkillRequerimientoDTO saveLista(ListaSkillRequerimientoDTO listaSkillRequerimientoDTO) {
        log.debug("Request to save SkillRequerimiento : {}", listaSkillRequerimientoDTO);
        if(listaSkillRequerimientoDTO.getLista().size() > 0) {
        	loopSkillRequerimientos(listaSkillRequerimientoDTO.getLista());
        }
        return listaSkillRequerimientoDTO;
    }

    /**
     * Get all the skillRequerimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkillRequerimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillRequerimientos");
        return skillRequerimientoRepository.findAll(pageable)
            .map(skillRequerimientoMapper::toDto);
    }


    /**
     * Get one skillRequerimiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillRequerimientoDTO> findOne(Long id) {
        log.debug("Request to get SkillRequerimiento : {}", id);
        return skillRequerimientoRepository.findById(id)
            .map(skillRequerimientoMapper::toDto);
    }

    /**
     * Delete the skillRequerimiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillRequerimiento : {}", id);
        skillRequerimientoRepository.deleteById(id);
    }
    
    /**
     * Save the skillRequerimientos by list.
     *
     * @param list of SkillRequerimientoDTO entity.
     */
    @Transactional
    private void loopSkillRequerimientos(List<SkillRequerimientoDTO> lista) {
    	
        for(SkillRequerimientoDTO item : lista) {
        	try {
        		SkillRequerimiento skillRequerimiento = skillRequerimientoMapper.toEntity(item);
            	skillRequerimiento = skillRequerimientoRepository.save(skillRequerimiento);
        	} catch (RollbackException e) {
				
			} catch (Exception e) {
				
			}	
        }
        
    }
}
