-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: atul
-- ------------------------------------------------------
-- Server version	5.5.31-0ubuntu0.12.04.2

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
-- Table structure for table `pAlert`
--

DROP TABLE IF EXISTS `pAlert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pAlert` (
  `paid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` varchar(60) DEFAULT NULL,
  `alertType` int(11) DEFAULT NULL,
  PRIMARY KEY (`paid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pAlert`
--

LOCK TABLES `pAlert` WRITE;
/*!40000 ALTER TABLE `pAlert` DISABLE KEYS */;
INSERT INTO `pAlert` VALUES (13,'123456789udsfsfddsw',1),(14,'123456789udfsdfsdfddsw',2),(15,'123456789udsfsfdfgdfgdfddsw',1),(16,'123456789udsfsfdfgdfgdfddsw',2),(17,'123456789udsfsfddfgdfgdfgdfddsw',1),(18,'123456789udsfsfddfgdfgddfggdfddsw',1),(19,'123456789udsfsfdfgddgdfdfggdfgdfddsw',2),(20,'123456789',1),(21,'ee5cc8f8-4e72-fbab-7607-435bc3f48ef9',1),(22,'ee5cc8f8-4e72-fbab-7607-435bc3f48ef9',2);
/*!40000 ALTER TABLE `pAlert` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-30  2:11:21
