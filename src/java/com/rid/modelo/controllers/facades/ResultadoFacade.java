/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.modelo.controllers.facades;

import com.rid.modelo.entities.Resultado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author karen
 */
@Stateless
public class ResultadoFacade extends AbstractFacade<Resultado> implements ResultadoFacadeLocal {


    public ResultadoFacade() {
        super(Resultado.class);
    }

    @Override
    public List<Resultado> resultados(int id) {
        try {
            TypedQuery<Resultado> q = getEntityManager().createQuery("SELECT r.idParticipacion.puesto, r.idParticipacion.idCategoriaDeportista.idDeportista.usuario.idUsuarios, r.idParticipacion.idCategoriaDeportista.idDeportista.usuario.apellido, r.idParticipacion.idCategoriaDeportista.idDeportista.usuario.nombre FROM Resultado r WHERE r.idResultado=:id", Resultado.class);
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
