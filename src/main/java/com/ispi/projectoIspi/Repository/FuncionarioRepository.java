/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ispi.projectoIspi.Repository;

import com.ispi.projectoIspi.Enum.TipoFuncionario;
import com.ispi.projectoIspi.model.Funcionario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PEDRO P MULENGA
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByBiIgnoreCaseAndTipoFuncionarioAndEstadoIsTrue(String bi, TipoFuncionario tipoFuncionario);

    public Optional<Funcionario> findByEmailIgnoreCase(String email);

    public Optional<Funcionario> findByTelefone(Integer telefone);

    public Optional<Funcionario> findByBiIgnoreCase(String bi);

}
