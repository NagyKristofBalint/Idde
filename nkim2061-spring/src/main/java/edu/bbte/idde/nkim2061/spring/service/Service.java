package edu.bbte.idde.nkim2061.spring.service;

import edu.bbte.idde.nkim2061.spring.model.BaseEntity;

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
}
