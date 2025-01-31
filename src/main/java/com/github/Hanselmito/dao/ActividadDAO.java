package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ActividadDAO implements DAO<Actividad, Integer> {

    @Override
    public Actividad save(Actividad actividad) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Actividad update(Actividad actividad) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Actividad delete(Actividad actividad) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public Actividad findById(Integer key) {
        Session session = Connection.getInstance().getSession();
        Actividad actividad = session.get(Actividad.class, key);
        return actividad;
    }

    public List<Actividad> findAll() {
        Session session = Connection.getInstance().getSession();
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        return actividades;
    }

    @Override
    public void close() {
    }
}