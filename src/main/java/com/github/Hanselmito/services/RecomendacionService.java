package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.RecomendacionDAO;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Recomendacion;
import com.github.Hanselmito.entities.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionService {
    private RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    public Recomendacion findRecomendacionById(int id) throws Exception {
        Recomendacion recomendacion = recomendacionDAO.findById(id);
        if (recomendacion == null) {
            throw new Exception("Recomendación no encontrada");
        }
        return recomendacion;
    }

    public List<Recomendacion> findAll() throws Exception {
        return recomendacionDAO.findAll();
    }

    public List<Recomendacion> obtenerRecomendacionesPorUsuario(Usuario usuario) throws Exception {
        List<Recomendacion> todasRecomendaciones = recomendacionDAO.findAll();
        return todasRecomendaciones.stream()
                .filter(recomendacion -> usuario.getHabitos().stream()
                        .anyMatch(habito -> habito.getIdActividad().getIdCategoria().equals(recomendacion.getIdCategoria())))
                .collect(Collectors.toList());
    }

    public List<Recomendacion> obtenerRecomendacionesPorHuellas(Usuario usuario) throws Exception {
        List<Huella> huellas = new HuellaService().findHuellasByUsuario(usuario);
        List<Recomendacion> todasRecomendaciones = recomendacionDAO.findAll();

        return todasRecomendaciones.stream()
                .filter(recomendacion -> huellas.stream()
                        .anyMatch(huella -> huella.getIdActividad().getIdCategoria().equals(recomendacion.getIdCategoria())))
                .collect(Collectors.toList());
    }

    public List<Recomendacion> obtenerRecomendacionesPorActividades(Usuario usuario) throws Exception {
        List<Recomendacion> todasRecomendaciones = recomendacionDAO.findAll();
        return todasRecomendaciones.stream()
                .filter(recomendacion -> usuario.getHabitos().stream()
                        .anyMatch(habito -> habito.getIdActividad().getIdCategoria().equals(recomendacion.getIdCategoria())))
                .collect(Collectors.toList());
    }
}