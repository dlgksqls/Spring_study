package com.example.memorydb.db;

import java.util.List;
import java.util.Optional;

public interface DataRepository <T, ID> extends Repository<T, ID>{

    // create, update
    T save(T data);

    // read
    Optional<T> findById(ID id); // return 있을 수도 있고 없을 수도 있음
    List<T> findAll();

    // delete
    void delete(ID id);

}
