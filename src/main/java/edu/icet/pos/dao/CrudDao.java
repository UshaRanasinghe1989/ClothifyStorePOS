package edu.icet.pos.dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao{
    boolean save(T entity);
    List<T> retrieveAll();
    List<T> retrieveById(String id);
    List<T> retrieveAllId();
    int update(T entity);
}
