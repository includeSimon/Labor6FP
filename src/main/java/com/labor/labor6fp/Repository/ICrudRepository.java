package com.labor.labor6fp.Repository;
import com.labor.labor6fp.Exceptions.NullException;

import java.util.UUID;

public interface ICrudRepository<T> {
    /**
     * finds the object with id 'id'
     * @param id id of searched object
     * @return object with id or null if it doesn't exist
     */
    T findOne(int id);


    /**
     * returns all stored objects
     * @return all stored objects
     */
    Iterable<T> findAll();


    /**
     * saves(adds) an object to repository
     * @param obj obj different from null
     * @return null if object is saved otherwise obje if id exists
     */
    T save(T obj);


    /**
     * modifies an object
     * @param obj obj different from null
     * @return null - null if object is updated otherwise obj if id doesn't exist
     */
    T update(T obj);


    /**
     * removes object with id 'id'
     * @param id id must be not null
     * @return obj or null obj id doesn't exist
     */
    T delete(int id);

}
