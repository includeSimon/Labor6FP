package com.labor.labor6fp.Repository;
import java.util.List;

public abstract class InMemoryRepository<T> implements com.labor.labor6fp.Repository.ICrudRepository<T> {
    protected List<T> objectList;

    public InMemoryRepository(List<T> objectList) {

        this.objectList = objectList;
    }

    @Override
    public Iterable<T> findAll() {

        return this.objectList;
    }

}
