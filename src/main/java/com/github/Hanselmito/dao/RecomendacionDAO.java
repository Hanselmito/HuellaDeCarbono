package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Recomendacion;
import org.hibernate.Session;

import java.util.List;

public class RecomendacionDAO implements DAO<Recomendacion, Integer> {

    @Override
    public Recomendacion save(Recomendacion recomendacion) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Recomendacion update(Recomendacion recomendacion) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Recomendacion delete(Recomendacion recomendacion) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Recomendacion findById(Integer key) {
        Session session = Connection.getInstance().getSession();
        Recomendacion recomendacion = session.get(Recomendacion.class, key);
        return recomendacion;
    }

    public List<Recomendacion> findAll() {
        Session session = Connection.getInstance().getSession();
        List<Recomendacion> recomendaciones = session.createQuery("from Recomendacion", Recomendacion.class).list();
        return recomendaciones;
    }

    @Override
    public void close() {
    }
}