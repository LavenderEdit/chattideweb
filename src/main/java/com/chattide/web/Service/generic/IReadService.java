package com.chattide.web.Service.generic;

import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 * @param <T>
 * @param <ID>
 */
public interface IReadService<T, ID> {

    T findById(ID id);

    ArrayList<T> findAll();
}
