/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.usuarios.controllers;

import com.rid.modelo.entities.Usuario;
import com.rid.modelo.entities.TipoDocumento;
import com.rid.modelo.controllers.facades.TipoDocumentoFacadeLocal;
import com.rid.modelo.controllers.facades.UsuarioFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author Santiago
 */
@Named(value = "documentoController")
@SessionScoped
public class DocumentoController implements Serializable {

    /**
     * Creates a new instance of UsuarioController
     */
    @EJB
    private UsuarioFacadeLocal ufl;
    
    @EJB
    private TipoDocumentoFacadeLocal tdfl;

    private List<TipoDocumento> tipoDocumento;
  
            
    public DocumentoController() {
    }
    
    @PostConstruct
    public void init(){
    
    }
    
        public List<TipoDocumento> getTipoDocumento(){
    
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            tipoDocumento = tdfl.findAll();
        }
        return tipoDocumento;
    }

}
