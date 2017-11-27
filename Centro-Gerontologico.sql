CREATE DATABASE  IF NOT EXISTS `centrogerontologico` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `centrogerontologico`;
-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: centrogerontologico
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuestionario`
--

DROP TABLE IF EXISTS `cuestionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuestionario` (
  `idCuestionario` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idCuestionario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuestionario`
--

LOCK TABLES `cuestionario` WRITE;
/*!40000 ALTER TABLE `cuestionario` DISABLE KEYS */;
INSERT INTO `cuestionario` VALUES (1,'Test');
/*!40000 ALTER TABLE `cuestionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuestionario_resuelto`
--

DROP TABLE IF EXISTS `cuestionario_resuelto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuestionario_resuelto` (
  `idCuestionario_Resuelto` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCuestionario_Resuelto`),
  KEY `idUsuario_idx` (`idUsuario`),
  CONSTRAINT `idUsuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuestionario_resuelto`
--

LOCK TABLES `cuestionario_resuelto` WRITE;
/*!40000 ALTER TABLE `cuestionario_resuelto` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuestionario_resuelto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuestionario_resuelto_respuesta`
--

DROP TABLE IF EXISTS `cuestionario_resuelto_respuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuestionario_resuelto_respuesta` (
  `idCuestionarioR` int(11) NOT NULL,
  `idRespuesta` int(11) NOT NULL,
  PRIMARY KEY (`idCuestionarioR`,`idRespuesta`),
  KEY `idRespuesta_idx` (`idRespuesta`),
  CONSTRAINT `idCuestionarioR` FOREIGN KEY (`idCuestionarioR`) REFERENCES `cuestionario_resuelto` (`idCuestionario_Resuelto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idRespuesta` FOREIGN KEY (`idRespuesta`) REFERENCES `respuesta` (`idRespuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuestionario_resuelto_respuesta`
--

LOCK TABLES `cuestionario_resuelto_respuesta` WRITE;
/*!40000 ALTER TABLE `cuestionario_resuelto_respuesta` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuestionario_resuelto_respuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pregunta`
--

DROP TABLE IF EXISTS `pregunta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pregunta` (
  `idPregunta` int(11) NOT NULL AUTO_INCREMENT,
  `Titulo` varchar(200) NOT NULL,
  `idCuestionario` int(11) NOT NULL,
  PRIMARY KEY (`idPregunta`),
  KEY `idCuestionario_idx` (`idCuestionario`),
  CONSTRAINT `idCuestionario` FOREIGN KEY (`idCuestionario`) REFERENCES `cuestionario` (`idCuestionario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pregunta`
--

LOCK TABLES `pregunta` WRITE;
/*!40000 ALTER TABLE `pregunta` DISABLE KEYS */;
INSERT INTO `pregunta` VALUES (1,'Fecha de nacimiento',1),(2,'Años cumplidos',1),(3,'Género',1),(4,'Estado civil',1),(5,'Ocupación actual',1),(6,'Tiene servicio médico de',1),(7,'La casa donde habita es',1),(8,'¿Con quién comparte su hogar?',1),(9,'¿Alguien lo apoya económicamente?',1),(10,'¿Usted padece?',1);
/*!40000 ALTER TABLE `pregunta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respuesta`
--

DROP TABLE IF EXISTS `respuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respuesta` (
  `idRespuesta` int(11) NOT NULL,
  `Tipo_Pregunta` int(11) NOT NULL,
  `Descripcion` varchar(100) NOT NULL,
  `idPregunta` int(11) NOT NULL,
  PRIMARY KEY (`idRespuesta`),
  KEY `idPregunta_idx` (`idPregunta`),
  CONSTRAINT `idPregunta` FOREIGN KEY (`idPregunta`) REFERENCES `pregunta` (`idPregunta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respuesta`
--

LOCK TABLES `respuesta` WRITE;
/*!40000 ALTER TABLE `respuesta` DISABLE KEYS */;
INSERT INTO `respuesta` VALUES (1,1,'Día Mes Año',1),(2,2,'Abierta',2),(3,3,'1)Masculino 2)Femenino 3)Otro',3),(4,3,'1)Casado 2)Viudo(a) 3)Separada 4)Soltera(o)',4),(5,3,'1)empleado 2)negocio propio 3)jubilado 4)hogar',5),(6,3,'1)IMSS 2)ISSTE 3)Seguro popular 4) Hospital militar 5)No tiene ',6),(7,3,'1)Propia 2)Rentada 3)Prestada',7),(8,3,'1)Vive solo 2)Con su pareja 3)Algun hijo',8),(9,4,'1)Su pareja 2)Sus hijos 3)Sus hermanos',9),(10,4,'1)Problemas del corazón 2)Diabetes 3)Enfermedad pulmonar 4)Cáncer',10);
/*!40000 ALTER TABLE `respuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Tipo_Usuario` varchar(14) NOT NULL COMMENT 'Investigador\nTrabajador\nAdministrador',
  `Nombre` varchar(45) NOT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Administrador','administrador','adminpass');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-26 18:43:47
