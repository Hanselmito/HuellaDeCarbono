package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.RecomendacionDAO;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.Recomendacion;
import com.github.Hanselmito.entities.Usuario;

import java.util.List;
import java.util.Map;
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

    public List<Recomendacion> obtenerRecomendacionesPorUsuario(Usuario usuario){
        List<Recomendacion> todasRecomendaciones = recomendacionDAO.findAll();
        return todasRecomendaciones.stream()
                .filter(recomendacion -> usuario.getHabitos().stream()
                        .anyMatch(habito -> habito.getIdActividad().getIdCategoria().equals(recomendacion.getIdCategoria())))
                .collect(Collectors.toList());
    }

    public List<Recomendacion> obtenerRecomendacionesPorHabitoPorCategoria(Usuario usuario) {
        List<Habito> habitos = new HabitoService().findHabitosByUsuario(usuario);

        Map<Integer, List<Habito>> habitosPorCategoria = habitos.stream()
                .collect(Collectors.groupingBy(h -> h.getIdActividad().getIdCategoria().getId()));

        List<Recomendacion> todasRecomendaciones = recomendacionDAO.findAll();
        return todasRecomendaciones.stream()
                .filter(recomendacion -> habitosPorCategoria.containsKey(recomendacion.getIdCategoria().getId()))
                .collect(Collectors.toList());
    }
}