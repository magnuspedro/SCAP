-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Tempo de geração: 17/05/2017 às 21:47
-- Versão do servidor: 5.7.17-0ubuntu0.16.04.2
-- Versão do PHP: 7.0.15-0ubuntu0.16.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sati`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno`
--

CREATE TABLE `aluno` (
  `idaluno` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `ra` varchar(20) DEFAULT NULL,
  `CPF` varchar(15) DEFAULT NULL,
  `RG` varchar(45) DEFAULT NULL,
  `orgao_expeditor` varchar(20) DEFAULT NULL,
  `externo` tinyint(1) DEFAULT NULL,
  `curso` varchar(60) DEFAULT NULL,
  `instituicao` varchar(70) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Fazendo dump de dados para tabela `aluno`
--

INSERT INTO `aluno` (`idaluno`, `nome`, `ra`, `CPF`, `RG`, `orgao_expeditor`, `externo`, `curso`, `instituicao`) VALUES
(1, 'a', 'a', 'a', 'a', 'a', 0, 'a', 'a'),
(2, 'Matheus', '1700243', '396548855', '4528826-8', 'SSP', 0, 'BCC', 'UTFPR');

-- --------------------------------------------------------

--
-- Estrutura para tabela `chamada`
--

CREATE TABLE `chamada` (
  `idchamada` int(11) NOT NULL,
  `iddata_evento` int(11) NOT NULL,
  `idaluno` int(11) NOT NULL,
  `hora` time DEFAULT NULL,
  `situacao` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `data_evento`
--

CREATE TABLE `data_evento` (
  `iddata_evento` int(11) NOT NULL,
  `idevento` int(11) NOT NULL,
  `data` date DEFAULT NULL,
  `aberto` tinyint(1) DEFAULT NULL,
  `hora_aberto` time DEFAULT NULL,
  `hora_fechamento` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `evento`
--

CREATE TABLE `evento` (
  `idevento` int(11) NOT NULL,
  `nome` varchar(200) DEFAULT NULL,
  `tipo` varchar(60) DEFAULT NULL,
  `descricao` longtext,
  `vagas_totais` int(11) DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_termino` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `eventos_instrutores`
--

CREATE TABLE `eventos_instrutores` (
  `ideventos_instrutor` int(11) NOT NULL,
  `idevento` int(11) NOT NULL,
  `idinstrutor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `instrutor`
--

CREATE TABLE `instrutor` (
  `idinstrutor` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `tipo` tinyint(1) DEFAULT NULL,
  `RG` varchar(45) DEFAULT NULL,
  `orgao_expeditor` varchar(20) DEFAULT NULL,
  `CPF` varchar(15) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `administrador` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura para tabela `matricula`
--

CREATE TABLE `matricula` (
  `idmatricula` int(11) NOT NULL,
  `idevento` int(11) NOT NULL,
  `idaluno` int(11) NOT NULL,
  `pago` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices de tabelas apagadas
--

--
-- Índices de tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`idaluno`);

--
-- Índices de tabela `chamada`
--
ALTER TABLE `chamada`
  ADD PRIMARY KEY (`idchamada`),
  ADD KEY `fk_chamada_aluno1_idx` (`idaluno`),
  ADD KEY `fk_chamada_data_evento1_idx` (`iddata_evento`);

--
-- Índices de tabela `data_evento`
--
ALTER TABLE `data_evento`
  ADD PRIMARY KEY (`iddata_evento`),
  ADD KEY `fk_data_evento_evento1_idx` (`idevento`);

--
-- Índices de tabela `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`idevento`);

--
-- Índices de tabela `eventos_instrutores`
--
ALTER TABLE `eventos_instrutores`
  ADD PRIMARY KEY (`ideventos_instrutor`),
  ADD KEY `fk_eventos_instrutores_instrutor1_idx` (`idinstrutor`),
  ADD KEY `fk_eventos_instrutores_evento1_idx` (`idevento`);

--
-- Índices de tabela `instrutor`
--
ALTER TABLE `instrutor`
  ADD PRIMARY KEY (`idinstrutor`);

--
-- Índices de tabela `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`idmatricula`),
  ADD KEY `fk_matricula_evento1_idx` (`idevento`),
  ADD KEY `fk_matricula_aluno1_idx` (`idaluno`);

--
-- AUTO_INCREMENT de tabelas apagadas
--

--
-- AUTO_INCREMENT de tabela `aluno`
--
ALTER TABLE `aluno`
  MODIFY `idaluno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de tabela `chamada`
--
ALTER TABLE `chamada`
  MODIFY `idchamada` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `data_evento`
--
ALTER TABLE `data_evento`
  MODIFY `iddata_evento` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `evento`
--
ALTER TABLE `evento`
  MODIFY `idevento` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `eventos_instrutores`
--
ALTER TABLE `eventos_instrutores`
  MODIFY `ideventos_instrutor` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `instrutor`
--
ALTER TABLE `instrutor`
  MODIFY `idinstrutor` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de tabela `matricula`
--
ALTER TABLE `matricula`
  MODIFY `idmatricula` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restrições para dumps de tabelas
--

--
-- Restrições para tabelas `chamada`
--
ALTER TABLE `chamada`
  ADD CONSTRAINT `fk_chamada_aluno1` FOREIGN KEY (`idaluno`) REFERENCES `aluno` (`idaluno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_chamada_data_evento1` FOREIGN KEY (`iddata_evento`) REFERENCES `data_evento` (`iddata_evento`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `data_evento`
--
ALTER TABLE `data_evento`
  ADD CONSTRAINT `fk_data_evento_evento1` FOREIGN KEY (`idevento`) REFERENCES `evento` (`idevento`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `eventos_instrutores`
--
ALTER TABLE `eventos_instrutores`
  ADD CONSTRAINT `fk_eventos_instrutores_evento1` FOREIGN KEY (`idevento`) REFERENCES `evento` (`idevento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_eventos_instrutores_instrutor1` FOREIGN KEY (`idinstrutor`) REFERENCES `instrutor` (`idinstrutor`) ON UPDATE CASCADE;

--
-- Restrições para tabelas `matricula`
--
ALTER TABLE `matricula`
  ADD CONSTRAINT `fk_matricula_aluno1` FOREIGN KEY (`idaluno`) REFERENCES `aluno` (`idaluno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_matricula_evento1` FOREIGN KEY (`idevento`) REFERENCES `evento` (`idevento`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
