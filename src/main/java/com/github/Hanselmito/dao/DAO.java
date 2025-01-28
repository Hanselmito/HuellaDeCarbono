package com.github.Hanselmito.dao;

import java.io.Closeable;
import java.sql.SQLException;

public interface DAO<T> extends Closeable {
        T save(T entity);
        T update(T entity);
        T delete(T entity) throws SQLException;
        T findById(int key);
}
