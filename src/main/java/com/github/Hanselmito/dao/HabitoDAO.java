package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.HabitoId;
import com.github.Hanselmito.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HabitoDAO implements DAO<Habito, HabitoId> {

    @Override
    public Habito save(Habito habito) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(habito);
        transaction.commit();
        return habito;
    }

    @Override
    public Habito update(Habito habito) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(habito);
        transaction.commit();
        return habito;
    }

    @Override
    public Habito delete(Habito habito) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(habito);
        transaction.commit();
        return habito;
    }

    @Override
    public Habito findById(HabitoId key) {
        Session session = Connection.getInstance().getSession();
        Habito habito = session.get(Habito.class, key);
        return habito;
    }

    public List<Habito> findHabitosByUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSession();
        List<Habito> habitos = session.createQuery("FROM Habito WHERE idUsuario = :usuario", Habito.class)
                .setParameter("usuario", usuario).list();
        return habitos;
    }

    public List<Habito> findAll() {
        Session session = Connection.getInstance().getSession();
        List<Habito> habitos = session.createQuery("from Habito", Habito.class).list();
        return habitos;
    }

    @Override
    public void close() {
    }
}