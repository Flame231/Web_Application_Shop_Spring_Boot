package org.example.webApplicationShopSpringBoot.repository;

import java.io.Serializable;

public interface DAO <T>{

    void save(T t);

    T get(Serializable id) ;

    void update(T t);

    void delete(Serializable id);

    void refresh(T t);

    void flush();
}
