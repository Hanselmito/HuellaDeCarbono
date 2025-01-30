package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.ActividadDAO;
import com.github.Hanselmito.entities.Actividad;

import java.util.List;

public class ActividadService {
    private ActividadDAO actividadDAO = new ActividadDAO();

    public Actividad addActividad(Actividad actividad) throws Exception {
        if (actividad.getNombre() == null || actividad.getNombre().isEmpty()) {
            throw new Exception("El nombre de la actividad no puede estar vacío");
        }
        if (actividad.getIdCategoria() == null) {
            throw new Exception("La categoría de la actividad no puede estar vacía");
        }
        return actividadDAO.save(actividad);
    }

    public Actividad updateActividad(Actividad actividad) throws Exception {
        if (actividad.getId() == null) {
            throw new Exception("El ID de la actividad no puede estar vacío");
        }
        return actividadDAO.update(actividad);
    }

    public void deleteActividad(Actividad actividad) throws Exception {
        if (actividad.getId() == null) {
            throw new Exception("El ID de la actividad no puede estar vacío");
        }
        actividadDAO.delete(actividad);
    }

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