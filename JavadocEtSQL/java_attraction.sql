-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 27 avr. 2025 à 20:37
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id_admin`, `id_utilisateur`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

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
  `mois` int NOT NULL,
  PRIMARY KEY (`id_attraction`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `attraction`
--

INSERT INTO `attraction` (`id_attraction`, `nom`, `description`, `prix`, `capacite`, `type_attraction`, `mois`) VALUES
(1, 'Titan\'s Fury', 'Affronte la fureur des titans avec des loopings renversants !', 8.00, 24, 'Sensations fortes', 0),
(2, 'Colère de Zeus', 'Une chute libre foudroyante qui te fera défier les cieux', 6.00, 16, 'Sensations fortes', 1),
(3, 'Hydra Coaster', 'Montagnes russes aquatiques plongeant sous l’eau', 7.00, 20, 'Sensations fortes', 0),
(4, 'Labyrinthe du Minotaure', 'Course folle à grande vitesse dans un labyrinthe mouvant', 8.00, 18, 'Sensations fortes', 0),
(5, 'Odyssée des mers', 'Descente en bouée sur une rivière mythologique', 5.00, 6, 'Aquatique', 0),
(6, 'Cascade d\'Atlantide', 'Un splash final spectaculaire avec des effets de lumière magique', 6.00, 12, 'Aquatique', 0),
(7, 'Sirènes en furie', 'Rafting déchaîné avec illusions sonores envoûtantes', 6.00, 8, 'Aquatique', 0),
(8, 'Carrousel d\'Olympe', 'Tour féérique avec chevaux ailés, licornes et créatures mythiques', 4.00, 30, 'Famille', 0),
(9, 'Petite aventure de Pégase', 'Mini montagnes russes pour les petits héros', 4.00, 12, 'Famille', 0),
(10, 'Arène d\'Arès', 'Combat laser dans une arène antique enflammée', 6.00, 10, 'Interactif', 0),
(11, 'Mission Oracles', 'Escape game divin où tu décryptes les messages des dieux', 7.00, 6, 'Interactif', 0),
(12, 'La malédiction de Méduse', 'Maison hantée remplie de miroirs et de statues pétrifiantes', 5.00, 10, 'Horreur', 0),
(13, 'Les portes d\'Hadès', 'Train fantôme souterrain vers le royaume des morts', 6.00, 16, 'Horreur', 0);

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
  `type_membre` enum('adulte','etudiant','senior','enfant') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `id_utilisateur` (`id_utilisateur`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_client`, `id_utilisateur`, `age`, `type_client`, `type_membre`) VALUES
(1, 5, 76, 'nouveau', 'senior');

-- --------------------------------------------------------

--
-- Structure de la table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
CREATE TABLE IF NOT EXISTS `reduction` (
  `id_reduction` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `pourcentage` int DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id_reduction`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reduction`
--

INSERT INTO `reduction` (`id_reduction`, `nom`, `pourcentage`, `description`) VALUES
(1, 'Première visite', 15, 'Pour les nouveaux utilisateurs qui réservent leur 1ère attraction'),
(2, 'Pack Famille', 25, 'Pour les familles avec 2 adultes et 2 enfants'),
(3, 'Fidélité', 15, 'Pour les clients ayant réservé 5 attractions ou plus'),
(4, 'Client enfant', 25, 'Pour les enfants de moins de 18 ans'),
(5, 'Client sénior', 15, 'Pour les adultes de plus de 60 ans'),
(6, 'Client étudiant', 20, 'Pour les étudiants entre 18 et 25 ans'),
(7, 'Famille nombreuse', 30, 'Pour les familles de 2 adultes et plus de 3 enfants'),
(8, 'Pâques', 10, 'Pour les fêtes, profitez de -10% sur certaines attractions');

-- --------------------------------------------------------

--
-- Structure de la table `reduction_attraction`
--

DROP TABLE IF EXISTS `reduction_attraction`;
CREATE TABLE IF NOT EXISTS `reduction_attraction` (
  `id_RA` int NOT NULL AUTO_INCREMENT,
  `id_attraction` int DEFAULT NULL,
  `id_reduction` int DEFAULT NULL,
  PRIMARY KEY (`id_RA`),
  KEY `id_attraction` (`id_attraction`),
  KEY `id_reduction` (`id_reduction`)
) ENGINE=MyISAM AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reduction_attraction`
--

INSERT INTO `reduction_attraction` (`id_RA`, `id_attraction`, `id_reduction`) VALUES
(2, 9, 8),
(3, 10, 8),
(1, 8, 8),
(4, 11, 8);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `date_reservation` date DEFAULT NULL,
  `date_achat` date DEFAULT NULL,
  `id_attraction` int DEFAULT NULL,
  `prix_total` decimal(10,2) DEFAULT NULL,
  `nb_personne` int DEFAULT NULL,
  `est_archivee` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_reservation`),
  KEY `id_client` (`id_client`),
  KEY `id_attraction` (`id_attraction`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_client`, `nom`, `prenom`, `mail`, `date_reservation`, `date_achat`, `id_attraction`, `prix_total`, `nb_personne`, `est_archivee`) VALUES
(1, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-26', '2025-04-25', 4, 12.24, 2, 1),
(12, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-27', '2025-04-26', 10, 13.50, 3, 0),
(13, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-27', '2025-04-26', 10, 29.40, 7, 0),
(11, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-26', '2025-04-26', 11, 31.50, 6, 1),
(14, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-25', '2025-04-26', 9, 10.20, 3, 1),
(23, 1, 'Filly', 'Danielle', 'danielle@gmail.com', '2025-04-10', '2025-04-27', 12, 26.25, 7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `mdp` varchar(255) NOT NULL,
  PRIMARY KEY (`id_utilisateur`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `email`, `nom`, `prenom`, `mdp`) VALUES
(3, 'Maeli@gmail.com', 'Orain', 'Maeli', 'Maeli'),
(4, 'Hind@gmail.com', 'Kherbouche', 'Hind', 'Hind'),
(2, 'Anna@gmail.com', 'Putod', 'Anna', 'Anna'),
(1, 'marie@gmail.com', 'Labous-Filly', 'Marie', 'Marie'),
(5, 'danielle@gmail.com', 'Filly', 'Danielle', 'Danielle');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
