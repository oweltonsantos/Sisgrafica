/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.60-log : Database - sisgrafica
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sisgrafica` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `sisgrafica`;

/*Table structure for table `tbcliente` */

DROP TABLE IF EXISTS `tbcliente`;

CREATE TABLE `tbcliente` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `CPF` varchar(14) NOT NULL,
  `DataNascimento` date NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `tbcliente` */

insert  into `tbcliente`(`Codigo`,`Nome`,`CPF`,`DataNascimento`) values (1,'Welton Santos','077.133.784-00','2018-11-27'),(3,'Maria Luiza','123.456.789-01','2018-11-27'),(4,'Pablo Suaréz','098.765.432-10','2018-11-27'),(5,'Alicia Lucena','123.456.789-00','2018-11-27'),(6,'Clientes','010.101.010-10','2018-11-27'),(7,'Fernando Henrique','123.456.789-00','2018-11-27');

/*Table structure for table `tbcompra` */

DROP TABLE IF EXISTS `tbcompra`;

CREATE TABLE `tbcompra` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `CodigoFornecedor` int(11) NOT NULL,
  `DataCompra` date NOT NULL,
  `ValorTotal` decimal(10,2) NOT NULL,
  `Situacao` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `FK_CodigoFornecedor_idx` (`CodigoFornecedor`),
  CONSTRAINT `FK_CodigoFornecedor` FOREIGN KEY (`CodigoFornecedor`) REFERENCES `tbfornecedor` (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `tbcompra` */

insert  into `tbcompra`(`Codigo`,`CodigoFornecedor`,`DataCompra`,`ValorTotal`,`Situacao`) values (1,1,'2018-11-27','10000.00',2),(2,2,'2018-11-27','10000.00',2),(3,2,'2018-11-27','10000.00',2),(4,2,'2018-11-27','35150.00',2),(5,1,'2018-11-27','49050.00',2);

/*Table structure for table `tbfornecedor` */

DROP TABLE IF EXISTS `tbfornecedor`;

CREATE TABLE `tbfornecedor` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `CNPJ` varchar(18) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tbfornecedor` */

insert  into `tbfornecedor`(`Codigo`,`Nome`,`CNPJ`) values (1,'M DE L DA S GOMES','26.393.740/0001-29'),(2,'TB DE LIMA QUEIROZ','17.489.572/0001-89');

/*Table structure for table `tbitemcompra` */

DROP TABLE IF EXISTS `tbitemcompra`;

CREATE TABLE `tbitemcompra` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `CodigoProduto` int(11) NOT NULL,
  `CodigoCompra` int(11) NOT NULL,
  `Quantidade` int(11) NOT NULL,
  `ValorUnitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `FK_CodigoCompra_idx2` (`CodigoCompra`),
  KEY `FK_CodigoProduto_idx2` (`CodigoProduto`),
  CONSTRAINT `FK_CodigoProduto2` FOREIGN KEY (`CodigoProduto`) REFERENCES `tbproduto` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CodigoCompra2` FOREIGN KEY (`CodigoCompra`) REFERENCES `tbcompra` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `tbitemcompra` */

insert  into `tbitemcompra`(`Codigo`,`CodigoProduto`,`CodigoCompra`,`Quantidade`,`ValorUnitario`) values (1,1,1,100,'100.00'),(2,1,2,100,'100.00'),(3,2,3,200,'50.00'),(4,2,4,541,'50.00'),(5,1,4,81,'100.00'),(6,2,5,981,'50.00');

/*Table structure for table `tbitemvenda` */

DROP TABLE IF EXISTS `tbitemvenda`;

CREATE TABLE `tbitemvenda` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `CodigoProduto` int(11) NOT NULL,
  `CodigoVenda` int(11) NOT NULL,
  `Quantidade` int(11) NOT NULL,
  `ValorUnitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `FK_CodigoVenda_idx` (`CodigoVenda`),
  KEY `FK_CodigoProduto_idx` (`CodigoProduto`),
  CONSTRAINT `FK_CodigoProduto` FOREIGN KEY (`CodigoProduto`) REFERENCES `tbproduto` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CodigoVenda` FOREIGN KEY (`CodigoVenda`) REFERENCES `tbvenda` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tbitemvenda` */

/*Table structure for table `tbproduto` */

DROP TABLE IF EXISTS `tbproduto`;

CREATE TABLE `tbproduto` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `PrecoCompra` decimal(10,2) NOT NULL,
  `PrecoVenda` decimal(10,2) NOT NULL,
  `QuantidadeEstoque` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tbproduto` */

insert  into `tbproduto`(`Codigo`,`Nome`,`PrecoCompra`,`PrecoVenda`,`QuantidadeEstoque`) values (1,'Papel A4','100.00','200.00',281),(2,'Lona Qualquer','50.00','75.00',1722);

/*Table structure for table `tbvenda` */

DROP TABLE IF EXISTS `tbvenda`;

CREATE TABLE `tbvenda` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `CodigoCliente` int(11) NOT NULL,
  `DataVenda` date NOT NULL,
  `ValorTotal` decimal(10,2) NOT NULL,
  `Situacao` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `FK_CodigoCliente_idx` (`CodigoCliente`),
  CONSTRAINT `FK_CodigoCliente` FOREIGN KEY (`CodigoCliente`) REFERENCES `tbcliente` (`Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tbvenda` */

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `foneuser` varchar(15) DEFAULT NULL,
  `tipo_usuario` varchar(15) NOT NULL DEFAULT '0',
  `login` varchar(15) NOT NULL,
  `senha` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Data for the table `usuario` */

insert  into `usuario`(`iduser`,`usuario`,`foneuser`,`tipo_usuario`,`login`,`senha`) values (1,'welton santos','83998311871','','weltonsantos','123456'),(2,'Administrador','8399999-9991','','admin','admin'),(17,'Fernando','123456','user','fernando','1234567890'),(19,'Teste','123','user','teste','E5857B335AFDF35CA81A110BC81F38682F8A89892CC597F5398DFEF82D42B513'),(20,'Teste2','1234','admin','teste2','B24331B1A138CDE62AA1F679164FC62F'),(21,'Teste3','12345','admin','teste3','B24331B1A138CDE62AA1F679164FC62F'),(22,'weltonSantos','(83)99831-1871','Administrador','santos1','0EEE46AE42BE1318F19BC6EF94E5F42BF650789987CC8427B955F2DEB29FB076'),(23,'Funcinario1','(00)00000-0000','Usuario','func1','EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),(24,'Funcionario2','(00)00000-0000','Usuario','func2','E24DF920078C3DD4E7E8D2442F00E5C9AB2A231BB3918D65CC50906E49ECAEF4'),(25,'Funcionario3','(00)00000-0000','Usuario','func3','15E2B0D3C33891EBB0F1EF609EC419420C20E320CE94C65FBC8C3312448EB225'),(26,'Funcionario4','(00)00000-0001','Usuario','func4','C9620C7FD832FB5345632B0D828706F736A41F7AF9EDCFBF2D7D0DDBFFE75F42');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
