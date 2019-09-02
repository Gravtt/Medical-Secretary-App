CREATE DATABASE  IF NOT EXISTS `medsec` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `medsec`;
-- MySQL dump 10.13  Distrib 5.7.22, for osx10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: medsec
-- ------------------------------------------------------
-- Server version	5.7.22

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
-- Table structure for table `Appointment`
--

DROP TABLE IF EXISTS `Appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Appointment` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `date_create` datetime NOT NULL,
  `date_change` datetime DEFAULT NULL,
  `date` datetime NOT NULL,
  `duration` int(45) NOT NULL,
  `detail` longtext,
  `note` longtext,
  `user_note` longtext,
  `status` enum('UNCONFIRMED','CONFIRMED','CANCELLED') DEFAULT 'UNCONFIRMED',
  PRIMARY KEY (`id`),
  KEY `fk_Appointment_Patient1_idx` (`uid`),
  CONSTRAINT `fk_Appointment_User` FOREIGN KEY (`uid`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Appointment`
--

LOCK TABLES `Appointment` WRITE;
/*!40000 ALTER TABLE `Appointment` DISABLE KEYS */;
INSERT INTO `Appointment` VALUES (1,1,'Day Oncology Unit','2018-05-16 05:23:41','2018-05-16 05:23:41','2018-06-12 10:30:00',60,'Education Session','Looking after yourself during chemotherapy - Watch\nPatient Health History Sheet - Please fill in and email back to daychemo.wrD@ramsayhealth.com.au\nPharmacy Medication Sheet - Please fill in and email back to daychemo.wrp@ramsayhealth.com.au\nParking Information - ReadQuestions Sheet - Read','Remember to bring scan result.','CONFIRMED'),(2,1,'Warringal Private Hospital / Epworth Eastern','2018-05-14 10:17:40','2018-05-14 10:17:40','2018-06-08 08:00:00',10,'Inflisaport Insertion','Warringal Private Hospital will contact you the day before to confirm admission and fasting times.\\nInfusaport Questionnaire - Please fill in and send back to reception@.66darebinst.com.au\\nDoctor Information - Read\\nProcedure Information - Read\\nAnaesthetists Information - Read',NULL,'CONFIRMED'),(3,1,'Warringal Private Hospital / Epworth Eastern','2018-05-14 10:17:40','2018-05-14 10:17:40','2018-06-08 08:00:00',10,NULL,NULL,NULL,'UNCONFIRMED');
/*!40000 ALTER TABLE `Appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Doctor`
--

DROP TABLE IF EXISTS `Doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Doctor` (
  `id` int(11) NOT NULL,
  `contact` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `fax` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `website` varchar(45) DEFAULT NULL,
  `expertise` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Doctor`
--

LOCK TABLES `Doctor` WRITE;
/*!40000 ALTER TABLE `Doctor` DISABLE KEYS */;
/*!40000 ALTER TABLE `Doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `File`
--

DROP TABLE IF EXISTS `File`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `File` (
  `id` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `link` varchar(255) NOT NULL,
  `pid` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `File`
--

LOCK TABLES `File` WRITE;
/*!40000 ALTER TABLE `File` DISABLE KEYS */;
/*!40000 ALTER TABLE `File` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hospital`
--

DROP TABLE IF EXISTS `Hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hospital` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `contact` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `fax` varchar(45) NOT NULL,
  `website` varchar(45) DEFAULT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hospital`
--

LOCK TABLES `Hospital` WRITE;
/*!40000 ALTER TABLE `Hospital` DISABLE KEYS */;
/*!40000 ALTER TABLE `Hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pathology`
--

DROP TABLE IF EXISTS `Pathology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pathology` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `contact` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `fax` varchar(45) NOT NULL,
  `website` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pathology`
--

LOCK TABLES `Pathology` WRITE;
/*!40000 ALTER TABLE `Pathology` DISABLE KEYS */;
/*!40000 ALTER TABLE `Pathology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Radiology`
--

DROP TABLE IF EXISTS `Radiology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Radiology` (
  `RadiologyId` int(11) NOT NULL,
  `RadiologyName` varchar(255) NOT NULL,
  `RadiologyContact` varchar(45) NOT NULL,
  `RadiologyAddress` varchar(45) NOT NULL,
  `RadiologyFax` varchar(45) NOT NULL,
  `RadiologyWebsite` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`RadiologyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Radiology`
--

LOCK TABLES `Radiology` WRITE;
/*!40000 ALTER TABLE `Radiology` DISABLE KEYS */;
/*!40000 ALTER TABLE `Radiology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `password` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middlename` varchar(45) DEFAULT NULL,
  `dob` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `street` varchar(45) DEFAULT NULL,
  `suburb` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_expire_date` datetime DEFAULT NULL,
  `token_valid_from` datetime DEFAULT CURRENT_TIMESTAMP,
  `role` enum('PATIENT','ADMIN') DEFAULT 'PATIENT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('123',1,'Williamson','Alex','Mileston','1986-08-07','williamson@example.com','97 Masthead Drive','ROCKHAMPTON','QLD',NULL,NULL,'2018-08-05 04:15:10','PATIENT'),('1230',2,'Maggard','Arnold','Logan','1968-02-10','arnold@example.com','42 Edgewater Close','HUSKISSON','NSW',NULL,NULL,'2018-08-09 13:09:29','ADMIN'),(NULL,3,'Sharpe','Chad',NULL,'1979-08-03','chad@example.com','41 Ross Street',NULL,NULL,NULL,NULL,'2018-08-04 14:05:19','PATIENT'),(NULL,4,'Haggerty','Susan',NULL,'1994-01-08','susan@example.com',NULL,NULL,NULL,NULL,NULL,'2018-08-04 14:05:19','PATIENT');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserNotificationToken`
--

DROP TABLE IF EXISTS `NotificationToken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NotificationToken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `fcm_token` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_NotificationToken_User_idx` (`uid`),
  CONSTRAINT `fk_NotificationToken_User` FOREIGN KEY (`uid`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-14 18:11:57
