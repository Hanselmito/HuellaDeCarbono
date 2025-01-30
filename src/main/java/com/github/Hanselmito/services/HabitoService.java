package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.HabitoDAO;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.HabitoId;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.entities.Actividad;

import java.time.LocalDate;

public class HabitoService {
    private HabitoDAO habitoDAO = new HabitoDAO();

    public void asignarActividad(Usuario usuario, Actividad actividad, int frecuencia, LocalDate ultimaFecha) throws Exception {
        HabitoId habitoId = new HabitoId();
        habitoId.setIdUsuario(usuario.getId());
        habitoId.setIdActividad(actividad.getId());

        Habito habito = new Habito();
        habito.setId(habitoId);
        habito.setIdUsuario(usuario);
        habito.setIdActividad(actividad);
        habito.setFrecuencia(frecuencia);
        habito.setUltimaFecha(ultimaFecha);

        habitoDAO.save(habito);
    }
}