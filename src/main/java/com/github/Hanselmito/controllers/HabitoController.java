package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.HabitoService;
import com.github.Hanselmito.services.RecomendacionService;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Enums.TipoEnum;
import com.github.Hanselmito.entities.Recomendacion;

import java.time.LocalDate;
import java.util.List;

public class HabitoController {
    private HabitoService habitoService = new HabitoService();
    private RecomendacionService recomendacionService = new RecomendacionService();

    public void asignarActividad(Usuario usuario, Actividad actividad, int frecuencia, TipoEnum tipo, LocalDate ultimaFecha) {
        try {
            habitoService.asignarActividad(usuario, actividad, frecuencia, tipo, ultimaFecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recomendacion> obtenerRecomendaciones(Usuario usuario) {
        try {
            return recomendacionService.obtenerRecomendacionesPorUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}