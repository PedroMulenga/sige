/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.Enum.TipoEmolumento;
import com.ispi.projectoIspi.model.Emolumento;
import com.ispi.projectoIspi.model.Matricula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface EmolumentoRepository extends JpaRepository<Emolumento, Long> {

    // @Query(value = "select e from emolumento e Where e.tipo_emolumento='TRANSPORTE' ORDER BY data_pagamento DESC limit 1", nativeQuery = true)
    // List<Emolumento> findAllATransporte();
    //@Query("SELECT distinct e FROM Emolumento e JOIN e.matricula m WHERE m.numeroEstudante= :numeroEstudante AND e.tipoEmolumento='TRANSPORTE' ORDER BY e.dataPagamento DESC")
    Iterable<Emolumento> findByMatriculaAndTipoEmolumentoAndAnoAcademicoReferente(Matricula matricula, TipoEmolumento tipoEmolumento, Integer anoAcademicoReferente);

    @Query("SELECT e FROM Emolumento e JOIN e.matricula m WHERE m.numeroEstudante= :numeroEstudante AND e.tipoEmolumento='PROPINA' AND e.mesReferente= :mesReferente AND e.anoAcademicoReferente= :anoAcademicoReferente AND e.situacao='PAGO'")
    Emolumento findByNumeroEstudante(@Param("numeroEstudante") String numeroEstudante, @Param("anoAcademicoReferente") Integer anoAcademicoReferente, @Param("mesReferente") String mesReferente);

    @Query("SELECT e FROM Emolumento e WHERE e.matricula= :matricula AND e.tipoEmolumento= :tipoEmolumento AND e.mesReferente= :mesReferente AND e.anoAcademicoReferente= :anoAcademicoReferente AND e.situacao='PAGO'")
    public Optional<Emolumento> finByPagamento(@Param("matricula") Matricula matricula, @Param("tipoEmolumento") TipoEmolumento tipoEmolumento, @Param("mesReferente") String mesReferente, @Param("anoAcademicoReferente") Integer anoAcademicoReferente);

    @Query("SELECT e FROM Emolumento e JOIN e.matricula m WHERE  e.tipoEmolumento='PROPINA' AND e.mesReferente= :mesReferente AND e.anoAcademicoReferente= :anoAcademicoReferente AND e.situacao='PAGO'")
    List<Emolumento> findAllPay(@Param("anoAcademicoReferente") Integer anoAcademicoReferente, @Param("mesReferente") String mesReferente);


}
