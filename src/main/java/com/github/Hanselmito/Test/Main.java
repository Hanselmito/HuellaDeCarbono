package com.github.Hanselmito.Test;

import com.github.Hanselmito.connection.Connection;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {

            Session session = Connection.getInstance().getSession();
            if (session != null){
                System.out.println("Conexión establecida");
            } else {
                System.out.println("Conexión fallida");
            }
            //consultar los registros de la tabla alumno.
            //opcion º1.
    }
}
