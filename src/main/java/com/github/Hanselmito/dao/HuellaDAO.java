package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HuellaDAO implements DAO<Huella, Integer> {

    @Override
    public Huella save(Huella huella) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(huella);
        transaction.commit();
        return huella;
    }

    @Override
    public Huella update(Huella huella) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(huella);
        transaction.commit();
        return huella;
    }

    @Override
    public Huella delete(Huella huella) throws SQLException {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(huella);
        transaction.commit();
        return huella;
    }

    @Override
    public Huella findById(Integer key) {
        Session session = Connection.getInstance().getSession();
        Huella huella = session.get(Huella.class, key);
        return huella;
    }

    public List<Huella> findByUsuario(Usuario usuario) {
        Session session = Connection.getInstance().getSession();
        List<Huella> huellas = session.createQuery("FROM Huella WHERE idUsuario = :usuario", Huella.class)
                .setParameter("usuario", usuario)
                .list();
        return huellas;
    }

    public List<Huella> findByUsuarioAndDateRange(Usuario usuario, LocalDate startDate, LocalDate endDate) {
        Session session = Connection.getInstance().getSession();
        List<Huella> huellas = session.createQuery("FROM Huella WHERE idUsuario = :usuario AND fecha BETWEEN :startDate AND :endDate", Huella.class)
                .setParameter("usuario", usuario)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .list();
        return huellas;
    }

    public double calcularMediaPorCategoria(String categoria) {
        Session session = Connection.getInstance().getSession();
        Double media = session.createQuery("SELECT AVG(valor) FROM Huella WHERE idActividad.idCategoria.nombre = :categoria", Double.class)
                .setParameter("categoria", categoria)
                .uniqueResult();
        return media != null ? media : 0.0;
    }

    @Override
    public void close() {

    }
}