/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controller.report;

import com.rid.modelo.entities.Resultado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class ReporteResultado implements Serializable{
    
    private String puesto;
    private Long idUsuarios;
    private String apellido;
    private String nombre;
    private Integer sumaPeso;

    public ReporteResultado() {
    }
    
    public ReporteResultado(Resultado r) {
        this.puesto = r.getIdParticipacion().getPuesto();
        this.idUsuarios = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getIdDeportista();
        this.apellido = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getUsuario().getApellido();
        this.nombre = r.getIdParticipacion().getIdCategoriaDeportista().getIdDeportista().getUsuario().getNombre();
        this.sumaPeso = r.getPesoEnvion() + r.getPesoArranque();
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

    public Integer getSumaPeso() {
        return sumaPeso;
    }

    public void setSumaPeso(Integer sumaPeso) {
        this.sumaPeso = sumaPeso;
    }
    
    public static List<ReporteResultado> reportesResultado(List<Resultado> rs){
        List<ReporteResultado> repr = new ArrayList<>();
        for (Resultado res : rs) {
            repr.add(new ReporteResultado(res));
        }
        return repr;
    }
}
