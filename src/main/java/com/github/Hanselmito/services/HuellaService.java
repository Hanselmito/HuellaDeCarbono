package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.HuellaDAO;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;

import java.util.List;

public class HuellaService {
    private HuellaDAO huellaDAO = new HuellaDAO();

    public Huella addHuella(Huella huella) throws Exception {
        if (huella.getIdUsuario() == null) {
            throw new Exception("El usuario no puede estar vacío");
        }
        if (huella.getIdActividad() == null) {
            throw new Exception("La actividad no puede estar vacía");
        }
        return huellaDAO.save(huella);
    }

    public Huella updateHuella(Huella huella) throws Exception {
        if (huella.getId() == null) {
            throw new Exception("El ID de la huella no puede estar vacío");
        }
        return huellaDAO.update(huella);
    }

    public void deleteHuella(Huella huella) throws Exception {
        if (huella.getId() == null) {
            throw new Exception("El ID de la huella no puede estar vacío");
        }
        huellaDAO.delete(huella);
    }

    public Huella findHuellaById(int id) throws Exception {
        Huella huella = huellaDAO.findById(id);
        if (huella == null) {
            throw new Exception("Huella no encontrada");
        }
        return huella;
    }

    public List<Huella> findHuellasByUsuario(Usuario usuario) throws Exception {
        return huellaDAO.findByUsuario(usuario);
    }
}