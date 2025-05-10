package com.chattide.web.Service.generic;

/**
 *
 * @author Juan - Luis
 * @param <T>
 * @param <ID>
 */
public interface IWriteService<T, ID> {

    boolean create(T entity);

    boolean update(T entity);

    void delete(ID id);
}
