package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.HabitoDAO;
import com.github.Hanselmito.entities.Enums.TipoEnum;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.HabitoId;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.entities.Actividad;

import java.time.LocalDate;
import java.util.List;

public class HabitoService {
    private HabitoDAO habitoDAO = new HabitoDAO();

    public void asignarActividad(Usuario usuario, Actividad actividad, int frecuencia, TipoEnum tipo, LocalDate ultimaFecha) throws Exception {
        HabitoId habitoId = new HabitoId();
        habitoId.setIdUsuario(usuario.getId());
        habitoId.setIdActividad(actividad.getId());

        Habito habito = new Habito();
        habito.setId(habitoId);
        habito.setIdUsuario(usuario);
        habito.setIdActividad(actividad);
        habito.setFrecuencia(frecuencia);
        habito.setTipo(tipo);
        habito.setUltimaFecha(ultimaFecha);

        habitoDAO.save(habito);
    }

    public void addHabito(Habito habito) throws Exception {
        if (habito == null) {
            throw new Exception("El hábito no puede ser nulo");
        }
        habitoDAO.save(habito);
    }

    public String getNombreActividadById(int idActividad) throws Exception {
        Actividad actividad = new ActividadService().findActividadById(idActividad);
        if (actividad == null) {
            throw new Exception("Actividad no encontrada");
        }
        return actividad.getNombre();
    }

    public Habito findHabitoById(HabitoId id) throws Exception {
        Habito habito = habitoDAO.findById(id);
        if (habito == null) {
            throw new Exception("Hábito no encontrado");
        }
        return habito;
    }

    public List<Habito> findAll() throws Exception {
        return habitoDAO.findAll();
    }

    public List<Habito> findHabitosByUsuario(Usuario usuario) throws Exception {
        return habitoDAO.findHabitosByUsuario(usuario);
    }

    public void updateHabito(Habito habito) throws Exception {
        habitoDAO.update(habito);
    }

    public void deleteHabito(Habito habito) throws Exception {
        habitoDAO.delete(habito);
    }
}