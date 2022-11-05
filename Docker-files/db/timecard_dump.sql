-- MySQL dump 10.13  Distrib 5.7.30, for macos10.14 (x86_64)
--
-- Host: localhost    Database: timecard
-- ------------------------------------------------------
-- Server version	5.7.30

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
-- Table structure for table `boards`
--

DROP TABLE IF EXISTS `boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `file` varchar(255) DEFAULT NULL,
  `message` longtext NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7p22if134ar49acaob2murh48` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boards`
--

LOCK TABLES `boards` WRITE;
/*!40000 ALTER TABLE `boards` DISABLE KEYS */;
INSERT INTO `boards` VALUES (4,'2021-04-14 13:47:51','6.jpg','あ',6),(5,'2021-04-14 13:50:42','5CD6DDAC072F41118133FAFC58561D2B_LL.jpeg','a',6),(6,'2021-04-14 14:08:34','MagazinePic-07-2.3.001-bigpicture_07_4.jpg','b',6);
/*!40000 ALTER TABLE `boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_flag` int(11) NOT NULL,
  `break_time` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `updated_at` datetime NOT NULL,
  `wage` varchar(255) NOT NULL,
  `delete_flag` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3um79qgwg340lpaw7phtwudtc` (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (5,1,'01:31','555','2021-03-29 13:31:40','shima','65C5DB3C3DB1D6799915ACC77EBF37970AA65062360E00F3615D8556071C18D7','2021-03-29 13:31:40','1500',0),(3,1,'01:57','333','2021-03-25 18:57:38','木村','D9383A2FB0EA8B064C075FF16764C74635094333E0E3FD18111999D9C730F171','2021-04-11 15:20:34','1500',0),(4,1,'00:52','444','2021-03-26 15:53:19','橋本','C7D81B6BF70A042349971BB41EC46DA0D512D5CA1676D45B6049EBF18B528DE7','2021-03-26 15:53:19','1600',0),(6,1,'01:06','222','2021-03-30 00:06:43','松山','33179DA879F59E4F82940ACA5E2B1D57AB13AF5DECF18A457E96DE785A0ADD42','2021-04-11 15:15:08','1000',0),(8,0,'00:25','0','2021-04-11 15:25:19','kohh','1180A10D48FB79B7B91D2237C6B69B7667BA858B37551B0584017F906FCE6308','2021-04-11 15:25:19','1111',0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `message` longtext NOT NULL,
  `get_id` int(11) NOT NULL,
  `send_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtb4c9qlhan9as2lp3hk5ek6dd` (`get_id`),
  KEY `FK6a8ft18o38w9kdiqd00h0j7y0` (`send_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'2021-04-07 11:28:07','sss',5,6),(2,'2021-04-07 11:42:40','a',5,6),(3,'2021-04-07 11:47:59','aaaa',5,6),(4,'2021-04-07 12:12:58','sss',5,6),(5,'2021-04-07 12:40:07','aaa',6,5),(6,'2021-04-07 13:01:22','こんにちは',5,6),(7,'2021-04-07 14:22:36','こんばんは',6,5),(9,'2021-04-07 15:34:16','ddd\r\n\r\n',3,6),(10,'2021-04-07 15:35:57','hhhh\r\n\r\n',6,3),(11,'2021-04-07 15:40:03','Jj',5,6),(12,'2021-04-07 15:42:06','uuu',5,6),(13,'2021-04-07 15:42:52','緊張するよ',5,6),(14,'2021-04-07 15:45:08','Eee',5,6),(15,'2021-04-07 15:47:32','soj\r\n\r\n',5,6),(16,'2021-04-07 15:51:55',';;;;\r\n\r\n',5,6),(17,'2021-04-07 15:52:52','Dddd',5,6),(18,'2021-04-07 15:57:18','sksksk',6,5),(19,'2021-04-08 05:29:59','おはようございます\r\n',5,6),(20,'2021-04-11 16:33:21','ありがとう',8,6),(21,'2021-04-11 16:35:56','どういたしまして',6,8),(22,'2021-04-11 16:39:24','saa',5,6),(23,'2021-04-11 16:39:35','ddd',5,6),(24,'2021-04-11 16:39:38','ddddd',5,6),(25,'2021-04-11 16:39:50','テスト',5,6),(26,'2021-04-11 16:39:55','あ',5,6),(27,'2021-04-11 16:40:56','s',5,6),(28,'2021-04-11 16:43:28','sss',5,6);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relations`
--

DROP TABLE IF EXISTS `relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `boss_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK135i83wlboj3htq1edc4pksjp` (`boss_id`),
  KEY `FKjb1i92a5x4l4yk17rs2jpbqr5` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relations`
--

LOCK TABLES `relations` WRITE;
/*!40000 ALTER TABLE `relations` DISABLE KEYS */;
INSERT INTO `relations` VALUES (6,6,5),(7,5,6);
/*!40000 ALTER TABLE `relations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `times`
--

DROP TABLE IF EXISTS `times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `times` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end` varchar(255) DEFAULT NULL,
  `start` varchar(255) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `wage` bigint(20) NOT NULL,
  `work_date` date NOT NULL,
  `work_minutes` varchar(255) DEFAULT NULL,
  `boss_id` int(11) DEFAULT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmgg9y0eajdwrwad986bjym19` (`boss_id`),
  KEY `FKpbn63pb3vp20f20cy2ngc3cku` (`employee_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `times`
--

LOCK TABLES `times` WRITE;
/*!40000 ALTER TABLE `times` DISABLE KEYS */;
INSERT INTO `times` VALUES (1,'00:29','20:29',0,0,'2021-04-05',NULL,5,6),(2,'00:46','16:46',0,0,'2021-04-11',NULL,5,6),(3,'21:40','16:48',0,0,'2021-03-11',NULL,5,6),(4,'17:22','17:22',0,0,'2021-04-11',NULL,5,6),(5,'16:33','16:33',0,0,'2021-04-12',NULL,5,6),(6,'18:23','13:23',0,0,'2021-04-13',NULL,6,5),(7,'18:23','11:23',0,0,'2021-04-13',NULL,6,5),(8,'18:25','13:25',0,0,'2021-03-13',NULL,6,5),(9,'18:25','11:25',0,0,'2021-03-13',NULL,6,5),(10,'20:03','09:00',0,0,'2021-04-13',NULL,5,6);
/*!40000 ALTER TABLE `times` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-14 14:29:19
