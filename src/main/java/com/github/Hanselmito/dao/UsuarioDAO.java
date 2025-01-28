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
        Connection.getInstance().close();
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(usuario);
        transaction.commit();
        Connection.getInstance().close();
        return usuario;
    }

    @Override
    public Usuario delete(Usuario usuario) throws SQLException {
        Session session = Connection.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(usuario);
        transaction.commit();
        Connection.getInstance().close();
        return usuario;
    }

    @Override
    public Usuario findById(int key) {
        Session session = Connection.getInstance().getSession();
        Usuario usuario = session.get(Usuario.class, key);
        Connection.getInstance().close();
        return usuario;
    }

    public Usuario findByEmailAndPassword(String email, String contrasena) {
        Session session = Connection.getInstance().getSession();
        Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email AND contrasena = :contrasena", Usuario.class);
        query.setParameter("email", email);
        query.setParameter("contrasena", contrasena);
        Usuario usuario = query.uniqueResult();
        Connection.getInstance().close();
        return usuario;
    }

    @Override
    public void close() {
        // Implementación del método close
        // Aquí puedes agregar la lógica para cerrar recursos si es necesario
    }
}