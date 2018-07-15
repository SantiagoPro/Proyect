/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controllers.report;

import com.rid.modelo.entities.Resultado;
import com.rid.controllers.torneos.ResultadosController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Santiago
 */
public class ReporteResultado implements Serializable{
    
    private static String torneo;
    private String puesto;
    private Long idUsuarios;
    private String apellido;
    private String nombre;
    private static Integer sumaPeso;
    
    @Inject
    private ResultadosController rc;

    public ReporteResultado() {
    }
    
    public ReporteResultado(Resultado r) {
        this.torneo = r.getIdParticipacion().getIdTorneo().getNombre();
        this.puesto = r.getIdParticipacion().getPuesto();
        this.idUsuarios = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getIdDeportista();
        this.apellido = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getUsuario().getApellido();
        this.nombre = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getUsuario().getNombre();
        this.sumaPeso = r.getPesoArranque() + r.getPesoEnvion();
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Long idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static Integer getSumaPeso() {
        return sumaPeso;
    }

    public static void setSumaPeso(Integer sumaPeso) {
        ReporteResultado.sumaPeso = sumaPeso;
    }

    public static String getTorneo() {
        return torneo;
    }

    public static void setTorneo(String torneo) {
        ReporteResultado.torneo = torneo;
    }

    
    public static List<ReporteResultado> reportesResultado(List<Resultado> rs){
        
        List<ReporteResultado> repr = new ArrayList<>();
        for (Resultado res : rs) {
            repr.add(new ReporteResultado(res));
        }
        return repr;
    }
}
