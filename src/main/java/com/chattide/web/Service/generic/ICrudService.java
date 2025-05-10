package com.chattide.web.Service.generic;

/**
 *
 * @author Juan - Luis
 * @param <T>
 * @param <ID>
 */
public interface ICrudService<T, ID> extends IReadService<T, ID>, IWriteService<T, ID> {

}
