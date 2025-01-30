package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.RecomendacionService;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.entities.Recomendacion;

import java.util.List;

public class RecomendacionController {
    private RecomendacionService recomendacionService = new RecomendacionService();

    public List<Recomendacion> obtenerRecomendacionesPorHuellas(Usuario usuario) {
        try {
            return recomendacionService.obtenerRecomendacionesPorHuellas(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recomendacion> obtenerRecomendacionesPorActividades(Usuario usuario) {
        try {
            return recomendacionService.obtenerRecomendacionesPorActividades(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}