/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.torneos.controllers;

import com.rid.controller.report.ReporteResultado;
import com.rid.modelo.controllers.facades.ResultadoFacadeLocal;
import com.rid.modelo.entities.Participacion;
import com.rid.modelo.entities.Resultado;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author karen
 */
@Named(value = "resultadosController")
@ViewScoped
public class ResultadosController implements Serializable{

    /**
     * Creates a new instance of ResultadosController
    } */
    
    @EJB
    private ResultadoFacadeLocal rfl;
    private Resultado resultado;
    
    private List<Resultado> res;   
    
    private Integer idResultado;
    private Integer pesoArranque;
    private Boolean validoArranque;
    private Integer pesoEnvion;
    private Boolean validoEnvion;
    private Participacion idParticipacion;
    
    private Integer sumaPeso;
    
    @Inject
    private TorneoController tc;
    
    public ResultadosController() {
    }
    
    @PostConstruct
    public void init(){
    }

    public Integer getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(Integer idResultado) {
        this.idResultado = idResultado;
    }

    public Integer getPesoArranque() {
        return pesoArranque;
    }

    public void setPesoArranque(Integer pesoArranque) {
        this.pesoArranque = pesoArranque;
    }

    public Integer getPesoEnvion() {
        return pesoEnvion;
    }

    public void setPesoEnvion(Integer pesoEnvion) {
        this.pesoEnvion = pesoEnvion;
    }

    public Boolean getValidoArranque() {
        return validoArranque;
    }

    public void setValidoArranque(Boolean validoArranque) {
        this.validoArranque = validoArranque;
    }

    public Boolean getValidoEnvion() {
        return validoEnvion;
    }

    public void setValidoEnvion(Boolean validoEnvion) {
        this.validoEnvion = validoEnvion;
    }

    public Integer getSumaPeso() {
        return sumaPeso;
    }

    public void setSumaPeso(Integer sumaPeso) {
        this.sumaPeso = resultado.getPesoArranque() + resultado.getPesoEnvion();
    }


    public Participacion getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Participacion idParticipacion) {
        this.idParticipacion = idParticipacion;
    }
    
    public List<Resultado> getResultado(){
        if (res == null || res.isEmpty()) {
            res = rfl.findAll();
        }
        return res;
    }    
    
    public String registrarResultado(){
        System.out.println("Id: " +idResultado);
        System.out.println("id: " +pesoArranque);
        System.out.println("id: " +validoArranque);
        System.out.println("id: " +pesoEnvion);
        System.out.println("id: " +validoEnvion);
        System.out.println("id: " +idParticipacion);
        
        try {
            Resultado r = new Resultado(null);
            r.setPesoArranque(pesoArranque);
            r.setValidoArranque(validoArranque);
            r.setPesoEnvion(pesoEnvion);
            r.setValidoEnvion(validoEnvion);
            r.setIdParticipacion(idParticipacion);
            rfl.create(r);
            r = null;
            return "Registrar.resultados.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public void exportarResultados(){
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            //Lista
            List<ReporteResultado> repr = ReporteResultado.reportesResultado(res);

            Map<String, Object> p = new HashMap<>();
            p.put("nombreTorneo", tc.getNombre());
            p.put("sumaPeso", getSumaPeso());

            File f = new File(ec.getRealPath("/WEB-INF/classes/com/rid/modelo/reportes/resultado.jasper"));
            JasperPrint jp = JasperFillManager.fillReport(f.getPath(), p, new JRBeanCollectionDataSource(repr));
            
            HttpServletResponse hsr = (HttpServletResponse) ec.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=resultado.pdf");
            OutputStream os = hsr.getOutputStream();
        
            JasperExportManager.exportReportToPdfStream(jp, os);
            
            os.flush();
            os.close();
            
            fc.responseComplete();
        } catch (JRException ex) {
            Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResultadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
