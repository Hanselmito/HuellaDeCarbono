package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Categoria;
import org.hibernate.Session;

import java.util.List;

public class CategoriaDAO implements DAO<Categoria, Integer> {

    @Override
    public Categoria save(Categoria categoria) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Categoria update(Categoria categoria) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Categoria delete(Categoria categoria) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Categoria findById(Integer key) {
        Session session = Connection.getInstance().getSession();
        Categoria categoria = session.get(Categoria.class, key);
        return categoria;
    }

    public List<Categoria> findAll() {
        Session session = Connection.getInstance().getSession();
        List<Categoria> categorias = session.createQuery("from Categoria", Categoria.class).list();
        return categorias;
    }

    @Override
    public void close() {
    }
}