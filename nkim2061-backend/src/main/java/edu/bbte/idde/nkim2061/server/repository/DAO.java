package edu.bbte.idde.nkim2061.server.repository;

import edu.bbte.idde.nkim2061.server.model.BaseEntity;

import java.util.Collection;

public interface DAO<T extends BaseEntity> {

    /**
     * @throws RepositoryException
     * dummy description
     */
    T create(T entity);

    /**
     * @throws RepositoryException
     * dummy description
     */
    void deleteById(Long id);

    /**
     * @throws RepositoryException
     * dummy description
     */
    T getById(Long id);

    /**
     * @throws RepositoryException
     * dummy description
     */
    Collection<T> getAll();

    /**
     * @throws RepositoryException
     * dummy description
     */
    void update(T entity);
}
