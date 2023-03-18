package edu.bbte.idde.nkim2061.server.service;

import edu.bbte.idde.nkim2061.server.model.BaseEntity;

import java.util.Collection;

public interface Service<T extends BaseEntity> {
    /**
     * @throws ServiceException
     * dummy description
     */
    T save(T entity);

    /**
     * @throws ServiceException
     * dummy description
     */
    void deleteById(Long id);

    /**
     * @throws ServiceException
     * dummy description
     */
    T findById(Long id);

    /**
     * @throws ServiceException
     * dummy description
     */
    Collection<T> findAll();

    /**
     * @throws ServiceException
     * dummy description
     */
    void update(T entity);
}
