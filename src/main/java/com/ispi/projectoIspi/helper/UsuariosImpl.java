/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.helper;

import com.ispi.projectoIspi.model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author PEDRO P MULENGA
 */
@Configuration
public class UsuariosImpl{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Usuario buscarComGrupos(Long codigo) {
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
        criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("codigo", codigo));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Usuario) criteria.uniqueResult();
    }

}
