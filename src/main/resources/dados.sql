INSERT IGNORE INTO `provincia` (`codigo`, `nome`, `sigla`) VALUES
(1, 'Huíla', 'HL');
INSERT IGNORE INTO `municipio` (`codigo`, `nome`, `provincia_codigo`) VALUES
(1, 'Lubango', 1);
insert ignore into Funcionario (codigo, bairro, bi, data_nascimento, data_registo, email, estado, genero, nome, nome_mae, nome_pai, numcrm, sobrenome, telefone, tipo_funcionario, `municipio_codigo`, `provincia_codigo`) VALUES
(1, 'Ferrovia', '000000001HA044', '1996-05-05', '2021-07-07', 'easymull@gmail.com', b'1', 'MASCULINO', 'Pedro Paciência', 'Leonor Ngueve', 'Pedro Mulenga', '01245', 'Mulenga', 929182705, 'SECRETARIA', 1, 1);
INSERT IGNORE INTO `grupo` (`codigo`, `estado`, `nome`) VALUES
(1, b'1', 'Secretaria'),
(2, b'1', 'Gestor_Financeiro'),
(3, b'1', 'Administrador');
INSERT IGNORE INTO `permissao` (`codigo`, `estado`, `nome`) VALUES
(1, b'1', 'EFECTUAR_INSCRICOES'),
(3, b'1', 'EFECTUAR_MATRICULAS'),
(4, b'1', 'REGISTAR_PAGAMENTOS'),
(5, b'1', 'VERIFICAR_PAGAMENTOS'),
(6, b'1', 'CADASTRAR_FUNCIONARIOS'),
(7, b'1', 'CADASTRAR_ACESSORIOS'),
(8, b'1', 'VERIFICAR_RELATORIOS'),
(9, b'1', 'CADASTRAR_USUARIOS');
INSERT IGNORE INTO `grupo_permissao` (`codigo_grupo`, `codigo_permissao`) VALUES
(1, 3),
(1, 4),
(1, 5),
(2, 5),
(2, 8),
(3, 1),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9);
INSERT IGNORE INTO `usuario` (`codigo`, `bi`, `data_registo`, `email`, `estado`, `nome_imagen`, `nome_usuario`, `senha`, `funcionario_codigo`) VALUES
(1, '000000001HA044', '2022-08-04', 'easymull@gmail.com', b'1', 'userDefault.png', 'Pedro Mulenga', '$2a$10$vyyWzcTJYhOGPyGPsauM7.RSE5ghsYj2tnXZeRFSZWCkPPb9gEReS', 1);
INSERT IGNORE INTO `usuario_grupo` (`codigo_usuario`, `codigo_grupo`) VALUES
(1, 3);