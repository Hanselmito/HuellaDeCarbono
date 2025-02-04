package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.HuellaDAO;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HuellaService {
    private HuellaDAO huellaDAO = new HuellaDAO();

    public Huella addHuella(Huella huella) throws Exception {
        validarHuella(huella);
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

    public BigDecimal calcularImpactoHuellaCarbono(Huella huella) {
        BigDecimal factorEmision = huella.getIdActividad().getIdCategoria().getFactorEmision();
        BigDecimal valor = huella.getValor();
        return valor.multiply(factorEmision);
    }

    public BigDecimal calcularImpactoDiario(Usuario usuario, LocalDate fecha) throws Exception {
        List<Huella> huellas = huellaDAO.findByUsuarioAndDateRange(usuario, fecha, fecha);
        return huellas.stream()
                .map(this::calcularImpactoHuellaCarbono)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularImpactoSemanal(Usuario usuario, LocalDate fechaInicio) throws Exception {
        LocalDate fechaFin = fechaInicio.plusDays(6);
        List<Huella> huellas = huellaDAO.findByUsuarioAndDateRange(usuario, fechaInicio, fechaFin);
        return huellas.stream()
                .map(this::calcularImpactoHuellaCarbono)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularImpactoMensual(Usuario usuario, LocalDate fechaInicio) throws Exception {
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        List<Huella> huellas = huellaDAO.findByUsuarioAndDateRange(usuario, fechaInicio, fechaFin);
        return huellas.stream()
                .map(this::calcularImpactoHuellaCarbono)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularImpactoPorCategoria(Usuario usuario, String categoria, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        List<Huella> huellas = huellaDAO.findByUsuarioAndDateRange(usuario, fechaInicio, fechaFin);
        return huellas.stream()
                .filter(huella -> huella.getIdActividad().getIdCategoria().getNombre().equalsIgnoreCase(categoria))
                .map(this::calcularImpactoHuellaCarbono)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularMediaPorCategoria(String categoria) {
        double media = huellaDAO.calcularMediaPorCategoria(categoria);
        return BigDecimal.valueOf(media);
    }

    private void validarHuella(Huella huella) throws Exception {
        if (huella.getIdActividad() == null) {
            throw new Exception("La actividad no puede estar vacía");
        }
        if (huella.getIdActividad() == null) {
            throw new Exception("La actividad no puede estar vacía");
        }
    }
}