package org.example.webApplicationShopSpringBoot.repository;


import jakarta.persistence.PersistenceContext;
import lombok.Getter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.io.Serializable;

@Getter
public class DAOImpl<T> implements DAO<T> {

    private Class<T> tclass;

    @PersistenceContext
    protected EntityManager entityManager;

    public DAOImpl(Class<T> tclass) {
        this.tclass = tclass;
    }

    @Override
    public void save(T t) throws PersistenceException {
        entityManager.persist(t);
    }

    @Override
    public T get(Serializable id) {
        return entityManager.find(tclass, id);
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    public EntityManager getEm() {
        return entityManager;
    }

    @Override
    public void delete(Serializable id) {
        T t = this.get(id);
        entityManager.remove(t);
    }



    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}
