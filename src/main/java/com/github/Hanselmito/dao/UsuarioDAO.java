package com.github.Hanselmito.dao;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Usuario;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public Usuario save(Usuario usuario){
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(usuario);
        transaction.commit();
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(usuario);
        transaction.commit();
        return usuario;
    }

    @Override
    public Usuario delete(Usuario usuario) throws SQLException {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(usuario);
        transaction.commit();
        return usuario;
    }

    @Override
    public Usuario findById(int key) {
        Session session = Connection.getInstance().getSession();
        Usuario usuario = session.get(Usuario.class, key);
        return usuario;
    }

    public Usuario findByEmailOrUsernameAndPassword(String identifier, String contrasena) {
        try (Session session = Connection.getInstance().getSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE (email = :identifier OR nombre = :identifier) AND contrasena = :contrasena", Usuario.class);
            query.setParameter("identifier", identifier);
            query.setParameter("contrasena", contrasena);
            return query.uniqueResult();
        }
    }

    public Usuario findByEmail(String email) {
        try (Session session = Connection.getInstance().getSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        // Implementación del método close
        // Aquí puedes agregar la lógica para cerrar recursos si es necesario
    }
}