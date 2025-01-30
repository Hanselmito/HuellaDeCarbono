package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ActividadDAO implements DAO<Actividad> {

    @Override
    public Actividad save(Actividad actividad) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(actividad);
        transaction.commit();
        return actividad;
    }

    @Override
    public Actividad update(Actividad actividad) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(actividad);
        transaction.commit();
        return actividad;
    }

    @Override
    public Actividad delete(Actividad actividad) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(actividad);
        transaction.commit();
        return actividad;
    }

    @Override
    public Actividad findById(int key) {
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
        // Implementación del método close
    }
}