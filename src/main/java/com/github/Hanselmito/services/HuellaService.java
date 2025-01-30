package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.HuellaDAO;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public String getNombreActividadById(int idActividad) throws Exception {
        Actividad actividad = new ActividadService().findActividadById(idActividad);
        if (actividad == null) {
            throw new Exception("Actividad no encontrada");
        }
        return actividad.getNombre();
    }

    public BigDecimal calcularHuellaCarbono(Usuario usuario) throws Exception {
        List<Huella> huellas = huellaDAO.findByUsuario(usuario);
        BigDecimal huellaTotal = BigDecimal.ZERO;

        for (Huella huella : huellas) {
            BigDecimal impacto = huella.getValor().multiply(huella.getIdActividad().getIdCategoria().getFactorEmision());
            huellaTotal = huellaTotal.add(impacto);
        }

        return huellaTotal;
    }

    public Map<LocalDate, BigDecimal> obtenerHistorialHuellaCarbono(Usuario usuario, ChronoUnit periodo) throws Exception {
        List<Huella> huellas = huellaDAO.findByUsuario(usuario);
        return huellas.stream()
                .collect(Collectors.groupingBy(
                        huella -> ajustarFecha(huella.getFecha(), periodo),
                        Collectors.reducing(BigDecimal.ZERO, huella -> huella.getValor().multiply(huella.getIdActividad().getIdCategoria().getFactorEmision()), BigDecimal::add)
                ));
    }

    public Map<LocalDate, BigDecimal> compararImpactoAmbiental(Usuario usuario, ChronoUnit periodo) throws Exception {
        return obtenerHistorialHuellaCarbono(usuario, periodo);
    }

    private LocalDate ajustarFecha(LocalDate fecha, ChronoUnit periodo) {
        switch (periodo) {
            case WEEKS:
                return fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case MONTHS:
                return fecha.with(TemporalAdjusters.firstDayOfMonth());
            case YEARS:
                return fecha.with(TemporalAdjusters.firstDayOfYear());
            default:
                throw new IllegalArgumentException("Período no soportado: " + periodo);
        }
    }
}