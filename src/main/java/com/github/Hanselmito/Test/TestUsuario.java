package com.github.Hanselmito.Test;

import com.github.Hanselmito.connection.Connection;
import com.github.Hanselmito.entities.Usuario;
import org.hibernate.Session;

import java.time.LocalDate;

public class TestUsuario {
    public static void main(String[] args) {
        /*
        Session session = Connection.getInstance().getSession();
        if (session != null) {
            System.out.println("Conexión establecida");
            session.getTransaction();
            Usuario usuario = new Usuario();
            usuario.setNombre("Hansel");
            usuario.setEmail("Hansel@gmail.com");
            usuario.setContrasena("hola");
            usuario.setFechaRegistro(LocalDate.now());
            session.save(usuario);
            session.close();
            System.out.println(usuario);
        } else {
            System.out.println("Conexión fallida");
        }
        */
    }
}
