-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: chicural
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cultivo`
--

DROP TABLE IF EXISTS `cultivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cultivo` (
  `idcultivo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idcultivo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cultivo`
--

LOCK TABLES `cultivo` WRITE;
/*!40000 ALTER TABLE `cultivo` DISABLE KEYS */;
INSERT INTO `cultivo` VALUES (2,'Chile'),(3,'Aguacate'),(4,'Maiz');
/*!40000 ALTER TABLE `cultivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordencompra`
--

DROP TABLE IF EXISTS `ordencompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordencompra` (
  `idordenCompra` int NOT NULL AUTO_INCREMENT,
  `proveedor` int NOT NULL,
  `cultivo` int NOT NULL,
  `folio` int NOT NULL,
  `fecha` date NOT NULL,
  `hectareas` int NOT NULL,
  `block` int NOT NULL,
  `montoTotal` double DEFAULT NULL,
  PRIMARY KEY (`idordenCompra`),
  KEY `idproveedor_fk_idx` (`proveedor`),
  KEY `idcultivo_fk_idx` (`cultivo`),
  CONSTRAINT `idcultivo_fk` FOREIGN KEY (`cultivo`) REFERENCES `cultivo` (`idcultivo`),
  CONSTRAINT `idproveedorOrden` FOREIGN KEY (`proveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordencompra`
--

LOCK TABLES `ordencompra` WRITE;
/*!40000 ALTER TABLE `ordencompra` DISABLE KEYS */;
INSERT INTO `ordencompra` VALUES (23,2,3,23,'2021-04-24',0,2,13),(24,2,3,24,'2021-04-21',0,3,31);
/*!40000 ALTER TABLE `ordencompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(70) NOT NULL,
  `costo` float NOT NULL,
  `presentacion` varchar(100) NOT NULL,
  `idProveedor` int NOT NULL,
  `proveedor` int NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `idProveedor` (`idProveedor`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'semillas',10,' ',1,1),(2,'fertilizante',12,' ',1,1),(3,'Tomate',11,' ',1,1),(4,'Aguacate',43,'',1,1),(5,'Chile verde',52,'',1,1),(6,'Sorgo',16,'',1,1),(7,'Insecticidas',16,'',2,2),(8,'Nematicidas',13,'',2,2),(9,'Fitorreguladores',17,'',2,2),(10,'Fungicidas',18,'',2,2);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `idProductos` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `idOrdenCompra` int NOT NULL,
  `idProducto` int NOT NULL,
  `ordenCompra` int NOT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`idProductos`),
  KEY `idProducto` (`idProducto`),
  KEY `idOrdenCompra` (`idOrdenCompra`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`idOrdenCompra`) REFERENCES `ordencompra` (`idordenCompra`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (24,1,23,8,23,13),(25,1,24,10,24,18),(26,1,24,8,24,13);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `idProveedor` int NOT NULL AUTO_INCREMENT,
  `rfc` varchar(15) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idProveedor`),
  UNIQUE KEY `rfc_UNIQUE` (`rfc`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'MELM8305','Panfilo'),(2,'AZJL99221','Pancracio');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idusuarios` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `contrasenia` varchar(50) NOT NULL,
  PRIMARY KEY (`idusuarios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-25 12:18:27
