package edu.bbte.idde.nkim2061.spring.repository;

import edu.bbte.idde.nkim2061.spring.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

public interface DAO<T extends BaseEntity> {

    /**
     * @throws RepositoryException
     * dummy description
     */
    T save(T entity);

    /**
     * @throws RepositoryException
     * dummy description
     */
    void deleteById(Long id);

    /**
     * @throws RepositoryException
     * dummy description
     */
    Optional<T> findById(Long id);

    /**
     * @throws RepositoryException
     * dummy description
     */
    Collection<T> findAll();
}
