package com.kode.ts.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.kode.ts.domain.User;
import com.kode.ts.domain.*; // for static metamodels
import com.kode.ts.repository.UserRepository;
import com.kode.ts.service.dto.UserCriteria;
import com.kode.ts.service.dto.UserDTO;
import com.kode.ts.service.mapper.UserMapper;

/**
 * Service for executing complex queries for {@link Candidato} entities in the database.
 * The main input is a {@link CandidatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CandidatoDTO} or a {@link Page} of {@link CandidatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Return a {@link List} of {@link CandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userMapper.usersToUserDTOs(userRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CandidatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page)
            .map(userMapper::userToUserDTO);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getSecondName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondName(), User_.secondName));
            }
            if (criteria.getIniciales() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIniciales(), User_.iniciales));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), User_.imageUrl));
            }
            if (criteria.getLangKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangKey(), User_.langKey));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), User_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), User_.lastModifiedBy));
            }
            if (criteria.getAuthority() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthority(),
                    root -> root.join(User_.authorities, JoinType.LEFT).get(Authority_.name)));
            }
        }
        return specification;
    }
}
