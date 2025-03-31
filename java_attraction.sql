-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 31 mars 2025 à 15:05
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `java_attraction`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int DEFAULT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `id_utilisateur` (`id_utilisateur`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
CREATE TABLE IF NOT EXISTS `attraction` (
  `id_attraction` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `description` text,
  `prix` decimal(10,2) DEFAULT NULL,
  `capacite` int DEFAULT NULL,
  `type_attraction` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_attraction`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `type_client` enum('nouveau','ancien') DEFAULT NULL,
  `type_membre` enum('régulier','vieux','enfant') DEFAULT NULL,
  `reduction` int DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `id_utilisateur` (`id_utilisateur`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `historique_reservation`
--

DROP TABLE IF EXISTS `historique_reservation`;
CREATE TABLE IF NOT EXISTS `historique_reservation` (
  `id_historique` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `date_achat` date DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `date_reservation` date DEFAULT NULL,
  `id_attraction` int DEFAULT NULL,
  `prix_total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_historique`),
  KEY `id_client` (`id_client`),
  KEY `id_attraction` (`id_attraction`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
CREATE TABLE IF NOT EXISTS `reduction` (
  `id_reduction` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `pourcentage` decimal(5,2) DEFAULT NULL,
  `description` text,
  `id_attraction` int DEFAULT NULL,
  `id_client` int DEFAULT NULL,
  PRIMARY KEY (`id_reduction`),
  KEY `id_attraction` (`id_attraction`),
  KEY `id_client` (`id_client`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `date_reservation` date DEFAULT NULL,
  `date_achat` date DEFAULT NULL,
  `id_attraction` int DEFAULT NULL,
  `prix_total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_reservation`),
  KEY `id_client` (`id_client`),
  KEY `id_attraction` (`id_attraction`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `mdp` varchar(255) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `id_client` int DEFAULT NULL,
  `id_admin` int DEFAULT NULL,
  PRIMARY KEY (`id_utilisateur`),
  UNIQUE KEY `mail` (`mail`),
  KEY `id_client` (`id_client`),
  KEY `id_admin` (`id_admin`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
