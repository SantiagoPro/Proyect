/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.categorias;

import com.rid.modelo.facades.CategoriaDeportistaFacadeLocal;
import com.rid.modelo.entities.CategoriaDeportista;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author karen
 */
@Named(value = "categoriaDeportistaController")
@ViewScoped
public class CategoriaDeportistaController implements Serializable{

    /**
     * Creates a new instance of CategoriaDeportistaController
     */
    @EJB
    private CategoriaDeportistaFacadeLocal cdfl;
    
    private List<CategoriaDeportista> categoriasDeportista;
    
    public CategoriaDeportistaController() {
    
    }
    
    @PostConstruct
    public void init(){}

    public List<CategoriaDeportista> getCategoriasDeportista() {
        if (categoriasDeportista == null || categoriasDeportista.isEmpty()) {
            categoriasDeportista = cdfl.findAll();
        }
        return categoriasDeportista;
    }

    public void setCategoriasDeportista(List<CategoriaDeportista> categoriasDeportista) {
        this.categoriasDeportista = categoriasDeportista;
    }

    
}
