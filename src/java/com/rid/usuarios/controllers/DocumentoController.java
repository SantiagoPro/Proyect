/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santiago
 */
@Named(value = "documentoController")
@ViewScoped
public class DocumentoController implements Serializable{

    @EJB
    private TipoDocumentoFacadeLocal tdfl;
    
    private List<TipoDocumento> tiposDocumentos;
    /**
     * Creates a new instance of TipoDocumentoController
     */
    public DocumentoController() {
    }
    
    @PostConstruct
    public void init(){
    }
    
    public List<TipoDocumento> getTiposDocumentos(){
        if (tiposDocumentos == null || tiposDocumentos.isEmpty()) {
            tiposDocumentos = tdfl.findAll();
        }
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<TipoDocumento> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }
    
    
}
