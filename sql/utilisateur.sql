-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 29 déc. 2023 à 21:48
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `locar_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roles` varchar(90) COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT 'ROLE_USER',
  `nom` varchar(120) COLLATE utf8mb3_unicode_ci NOT NULL,
  `prenom` varchar(120) COLLATE utf8mb3_unicode_ci NOT NULL,
  `adresse` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `code_postal` int NOT NULL,
  `ville` varchar(150) COLLATE utf8mb3_unicode_ci NOT NULL,
  `telephone` varchar(13) COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `is_verified` tinyint(1) DEFAULT '0',
  `is_verified_by_admin` tinyint(1) DEFAULT '0',
  `permis_path` varchar(255) COLLATE utf8mb3_unicode_ci NOT NULL,
  `age` int NOT NULL,
  `created_at` date NOT NULL DEFAULT (curdate()),
  `updated_at` date DEFAULT (curdate()),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `roles`, `nom`, `prenom`, `adresse`, `code_postal`, `ville`, `telephone`, `password`, `email`, `is_verified`, `is_verified_by_admin`, `permis_path`, `age`, `created_at`, `updated_at`) VALUES
(1, 'ROLE_USER', 'Mob', 'Sami', '9 rue', 78300, 'Poisy', '0623216330', '$2a$10$NkGjOh1Uedxy71xDMew7PuqURT0AwmyDuyqClV3LivyXGxmvtFAfW', '43012239@parisnanterre.fr', 0, 0, 'ssaasa', 21, '2023-12-26', '2023-12-26'),
(3, 'ROLE_USER', 'Mob', 'Sami', '9 rue', 78300, 'Poisy', '0623216330', '$2a$10$Uc0C/5fVcU1qqvIP3Gbe3enJERvfwrACsGSILBT.zFQ3p1V6MZ8B.', 'test@gmail.com', 0, 0, 'zer', 21, '2023-12-27', '2023-12-27');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
