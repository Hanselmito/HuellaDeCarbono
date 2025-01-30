package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.ActividadDAO;
import com.github.Hanselmito.entities.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDAO actividadDAO = new ActividadDAO();

    public Actividad findActividadById(int id) throws Exception {
        Actividad actividad = actividadDAO.findById(id);
        if (actividad == null) {
            throw new Exception("Actividad no encontrada");
        }
        return actividad;
    }

    public List<Actividad> findAll() throws Exception {
        return actividadDAO.findAll();
    }
}