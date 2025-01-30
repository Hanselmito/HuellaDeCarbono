package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.CategoriaDAO;
import com.github.Hanselmito.entities.Categoria;

import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public Categoria findCategoriaById(int id) throws Exception {
        Categoria categoria = categoriaDAO.findById(id);
        if (categoria == null) {
            throw new Exception("Categoría no encontrada");
        }
        return categoria;
    }

    public List<Categoria> findAll() throws Exception {
        return categoriaDAO.findAll();
    }
}