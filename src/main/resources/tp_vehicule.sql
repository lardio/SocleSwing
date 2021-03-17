-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : mer. 17 mars 2021 à 04:58
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tp_vehicule`
--

-- --------------------------------------------------------

--
-- Structure de la table `Adresse`
--

CREATE TABLE `Adresse` (
  `id` bigint(20) NOT NULL,
  `codePostal` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `numeroRue` varchar(255) DEFAULT NULL,
  `rue` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `client` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Adresse`
--

INSERT INTO `Adresse` (`id`, `codePostal`, `mail`, `numeroRue`, `rue`, `tel`, `ville`, `client`) VALUES
(1, '98765', 'lardiere@fz.fr', '2', 'dzvef', '0101010101', 'Paris', NULL),
(2, '09876', 'karo@gpen.fr', '7', 'rue de la gare', '0987654321', 'Katowice', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Camion`
--

CREATE TABLE `Camion` (
  `volumeDisponible` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Camion`
--

INSERT INTO `Camion` (`volumeDisponible`, `id`) VALUES
(9, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Client`
--

CREATE TABLE `Client` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `adresse` bigint(20) DEFAULT NULL,
  `id_permis` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Client`
--

INSERT INTO `Client` (`id`, `nom`, `prenom`, `adresse`, `id_permis`) VALUES
(1, 'Sylvain', 'Lardiere', 1, 1),
(2, 'Karolina', 'Rapala', 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Commentaire`
--

CREATE TABLE `Commentaire` (
  `id` bigint(20) NOT NULL,
  `id_vehicule` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `Entreprise`
--

CREATE TABLE `Entreprise` (
  `id` bigint(20) NOT NULL,
  `compta` double DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Entreprise`
--

INSERT INTO `Entreprise` (`id`, `compta`, `nom`) VALUES
(1, -7545, 'Location Pour les nulles');

-- --------------------------------------------------------

--
-- Structure de la table `Facture`
--

CREATE TABLE `Facture` (
  `id` bigint(20) NOT NULL,
  `cout` double DEFAULT NULL,
  `nbJourLocation` bigint(20) DEFAULT NULL,
  `numero` bigint(20) DEFAULT NULL,
  `statut` int(11) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `paiement_id` bigint(20) DEFAULT NULL,
  `id_reservation` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Facture`
--

INSERT INTO `Facture` (`id`, `cout`, `nbJourLocation`, `numero`, `statut`, `client_id`, `paiement_id`, `id_reservation`) VALUES
(1, 34, 1, 1, 1, 1, 1, 1),
(2, 34, 1, 2, 1, 1, 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `Maintenance`
--

CREATE TABLE `Maintenance` (
  `id` bigint(20) NOT NULL,
  `cout` double DEFAULT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `id_vehicule` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Maintenance`
--

INSERT INTO `Maintenance` (`id`, `cout`, `dateDebut`, `dateFin`, `id_vehicule`) VALUES
(1, 7579, '2021-03-16', '2021-03-17', 1),
(2, NULL, '2021-03-16', NULL, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Marque`
--

CREATE TABLE `Marque` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Marque`
--

INSERT INTO `Marque` (`id`, `nom`) VALUES
(1, 'Peugeot'),
(2, 'Citroen'),
(3, 'Tesla'),
(4, 'Ford');

-- --------------------------------------------------------

--
-- Structure de la table `Modele`
--

CREATE TABLE `Modele` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `Paiement`
--

CREATE TABLE `Paiement` (
  `id` bigint(20) NOT NULL,
  `datePaiement` date DEFAULT NULL,
  `modePaiement` int(11) DEFAULT NULL,
  `facture_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Paiement`
--

INSERT INTO `Paiement` (`id`, `datePaiement`, `modePaiement`, `facture_id`) VALUES
(1, NULL, 0, 1),
(2, NULL, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Permis`
--

CREATE TABLE `Permis` (
  `id` bigint(20) NOT NULL,
  `numero` bigint(20) DEFAULT NULL,
  `obtention` date DEFAULT NULL,
  `id_client` bigint(20) DEFAULT NULL,
  `id_typePermis` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Permis`
--

INSERT INTO `Permis` (`id`, `numero`, `obtention`, `id_client`, `id_typePermis`) VALUES
(1, 23, '2020-03-12', NULL, 1),
(2, 12321, '2018-09-12', NULL, 4);

-- --------------------------------------------------------

--
-- Structure de la table `personnes`
--

CREATE TABLE `personnes` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `Reservation`
--

CREATE TABLE `Reservation` (
  `Id` bigint(20) NOT NULL,
  `KilometrageFin` bigint(20) DEFAULT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `dateRetour` date DEFAULT NULL,
  `kilometrageDebut` bigint(20) DEFAULT NULL,
  `statutReservation` int(11) DEFAULT NULL,
  `id_client` bigint(20) DEFAULT NULL,
  `id_facture` bigint(20) DEFAULT NULL,
  `id_vehicule` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Reservation`
--

INSERT INTO `Reservation` (`Id`, `KilometrageFin`, `dateDebut`, `dateFin`, `dateRetour`, `kilometrageDebut`, `statutReservation`, `id_client`, `id_facture`, `id_vehicule`) VALUES
(1, NULL, '2021-03-17', '2021-03-17', '2021-03-17', 23223223, 2, 1, 2, 1),
(2, NULL, '2021-03-17', '2022-03-19', NULL, 23223223, 1, 2, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `TypePermis`
--

CREATE TABLE `TypePermis` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `TypePermis`
--

INSERT INTO `TypePermis` (`id`, `nom`) VALUES
(1, 'B1'),
(2, 'A1'),
(3, 'A'),
(4, 'A2'),
(5, 'B'),
(6, 'C1'),
(7, 'D1');

-- --------------------------------------------------------

--
-- Structure de la table `TypeVehicule`
--

CREATE TABLE `TypeVehicule` (
  `id` bigint(20) NOT NULL,
  `caution` double DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `sorteVehicule` varchar(255) DEFAULT NULL,
  `tarif` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `TypeVehicule`
--

INSERT INTO `TypeVehicule` (`id`, `caution`, `nom`, `sorteVehicule`, `tarif`) VALUES
(1, 1, 'Berline', NULL, 34),
(2, 20000, 'Sport', NULL, 200),
(3, 2308, 'Van', NULL, 300);

-- --------------------------------------------------------

--
-- Structure de la table `Vehicule`
--

CREATE TABLE `Vehicule` (
  `id` bigint(20) NOT NULL,
  `immatriculation` varchar(255) NOT NULL,
  `kilometrage` bigint(20) DEFAULT NULL,
  `modele` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `statut` int(11) DEFAULT NULL,
  `id_marque` bigint(20) NOT NULL,
  `id_type` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Vehicule`
--

INSERT INTO `Vehicule` (`id`, `immatriculation`, `kilometrage`, `modele`, `nom`, `statut`, `id_marque`, `id_type`) VALUES
(1, 'dg-zrf-ze', 23223223, 'egerbgeg', 'Voiture', 2, 1, 1),
(2, 'ZZ-ZZZ-ZZ', 9, 'z', 'Camion', 1, 1, 1),
(3, 'df-ssq-df', 20, 'vfz', 'Voiture', 0, 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `Voiture`
--

CREATE TABLE `Voiture` (
  `nombrePlaces` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Voiture`
--

INSERT INTO `Voiture` (`nombrePlaces`, `id`) VALUES
(2, 1),
(5, 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Adresse`
--
ALTER TABLE `Adresse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpgcrqjc73b7gq5383usbtarys` (`client`);

--
-- Index pour la table `Camion`
--
ALTER TABLE `Camion`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm055rd59rmoc32sbmootji2p9` (`adresse`),
  ADD KEY `FKjkqs715qetnaoflohy9cn7trx` (`id_permis`);

--
-- Index pour la table `Commentaire`
--
ALTER TABLE `Commentaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5btnaf1c675x3ap10w6skgioo` (`id_vehicule`);

--
-- Index pour la table `Entreprise`
--
ALTER TABLE `Entreprise`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Facture`
--
ALTER TABLE `Facture`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhnasi6n05kllawhofl2f0s1rj` (`client_id`),
  ADD KEY `FK90aw9gn9eck7k0f16bi5q0dlk` (`paiement_id`),
  ADD KEY `FK69beqpp7g7ix0sbalsbm17832` (`id_reservation`);

--
-- Index pour la table `Maintenance`
--
ALTER TABLE `Maintenance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfq4hjmtyhnye6yaei9fcple03` (`id_vehicule`);

--
-- Index pour la table `Marque`
--
ALTER TABLE `Marque`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Modele`
--
ALTER TABLE `Modele`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Paiement`
--
ALTER TABLE `Paiement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh4bfwkcblaoe5xsvyalao486x` (`facture_id`);

--
-- Index pour la table `Permis`
--
ALTER TABLE `Permis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK72qx65pfvdd5qir6rsa6t83al` (`id_client`),
  ADD KEY `FKfmbwkrc5n15kannqwvgbmsqsa` (`id_typePermis`);

--
-- Index pour la table `personnes`
--
ALTER TABLE `personnes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Reservation`
--
ALTER TABLE `Reservation`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FKdheivfvxkhn18rv0hn3du69k8` (`id_client`),
  ADD KEY `FK90xfr0pim9eucu2majjnx8wgt` (`id_facture`),
  ADD KEY `FK3b8koidcerpputfgbab9yqxsi` (`id_vehicule`);

--
-- Index pour la table `TypePermis`
--
ALTER TABLE `TypePermis`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `TypeVehicule`
--
ALTER TABLE `TypeVehicule`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Vehicule`
--
ALTER TABLE `Vehicule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_s2qml6oqe5f09h7emu1vpao0b` (`immatriculation`),
  ADD KEY `FKcw0mskq2eur57fxs08vywpwp2` (`id_marque`),
  ADD KEY `FKsulkay5wi8s7w80gyl3tq8yog` (`id_type`);

--
-- Index pour la table `Voiture`
--
ALTER TABLE `Voiture`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Adresse`
--
ALTER TABLE `Adresse`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Client`
--
ALTER TABLE `Client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Commentaire`
--
ALTER TABLE `Commentaire`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Entreprise`
--
ALTER TABLE `Entreprise`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `Facture`
--
ALTER TABLE `Facture`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Maintenance`
--
ALTER TABLE `Maintenance`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Marque`
--
ALTER TABLE `Marque`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `Modele`
--
ALTER TABLE `Modele`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Paiement`
--
ALTER TABLE `Paiement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `Permis`
--
ALTER TABLE `Permis`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `personnes`
--
ALTER TABLE `personnes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Reservation`
--
ALTER TABLE `Reservation`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `TypePermis`
--
ALTER TABLE `TypePermis`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `TypeVehicule`
--
ALTER TABLE `TypeVehicule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `Vehicule`
--
ALTER TABLE `Vehicule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Adresse`
--
ALTER TABLE `Adresse`
  ADD CONSTRAINT `FKpgcrqjc73b7gq5383usbtarys` FOREIGN KEY (`client`) REFERENCES `Client` (`id`);

--
-- Contraintes pour la table `Camion`
--
ALTER TABLE `Camion`
  ADD CONSTRAINT `FKt0vcbm3ih110yr8ntrcb5dajd` FOREIGN KEY (`id`) REFERENCES `Vehicule` (`id`);

--
-- Contraintes pour la table `Client`
--
ALTER TABLE `Client`
  ADD CONSTRAINT `FKjkqs715qetnaoflohy9cn7trx` FOREIGN KEY (`id_permis`) REFERENCES `Permis` (`id`),
  ADD CONSTRAINT `FKm055rd59rmoc32sbmootji2p9` FOREIGN KEY (`adresse`) REFERENCES `Adresse` (`id`);

--
-- Contraintes pour la table `Commentaire`
--
ALTER TABLE `Commentaire`
  ADD CONSTRAINT `FK5btnaf1c675x3ap10w6skgioo` FOREIGN KEY (`id_vehicule`) REFERENCES `Vehicule` (`id`);

--
-- Contraintes pour la table `Facture`
--
ALTER TABLE `Facture`
  ADD CONSTRAINT `FK69beqpp7g7ix0sbalsbm17832` FOREIGN KEY (`id_reservation`) REFERENCES `Reservation` (`Id`),
  ADD CONSTRAINT `FK90aw9gn9eck7k0f16bi5q0dlk` FOREIGN KEY (`paiement_id`) REFERENCES `Paiement` (`id`),
  ADD CONSTRAINT `FKhnasi6n05kllawhofl2f0s1rj` FOREIGN KEY (`client_id`) REFERENCES `Client` (`id`);

--
-- Contraintes pour la table `Maintenance`
--
ALTER TABLE `Maintenance`
  ADD CONSTRAINT `FKfq4hjmtyhnye6yaei9fcple03` FOREIGN KEY (`id_vehicule`) REFERENCES `Vehicule` (`id`);

--
-- Contraintes pour la table `Paiement`
--
ALTER TABLE `Paiement`
  ADD CONSTRAINT `FKh4bfwkcblaoe5xsvyalao486x` FOREIGN KEY (`facture_id`) REFERENCES `Facture` (`id`);

--
-- Contraintes pour la table `Permis`
--
ALTER TABLE `Permis`
  ADD CONSTRAINT `FK72qx65pfvdd5qir6rsa6t83al` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id`),
  ADD CONSTRAINT `FKfmbwkrc5n15kannqwvgbmsqsa` FOREIGN KEY (`id_typePermis`) REFERENCES `TypePermis` (`id`);

--
-- Contraintes pour la table `Reservation`
--
ALTER TABLE `Reservation`
  ADD CONSTRAINT `FK3b8koidcerpputfgbab9yqxsi` FOREIGN KEY (`id_vehicule`) REFERENCES `Vehicule` (`id`),
  ADD CONSTRAINT `FK90xfr0pim9eucu2majjnx8wgt` FOREIGN KEY (`id_facture`) REFERENCES `Facture` (`id`),
  ADD CONSTRAINT `FKdheivfvxkhn18rv0hn3du69k8` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id`);

--
-- Contraintes pour la table `Vehicule`
--
ALTER TABLE `Vehicule`
  ADD CONSTRAINT `FKcw0mskq2eur57fxs08vywpwp2` FOREIGN KEY (`id_marque`) REFERENCES `Marque` (`id`),
  ADD CONSTRAINT `FKsulkay5wi8s7w80gyl3tq8yog` FOREIGN KEY (`id_type`) REFERENCES `TypeVehicule` (`id`);

--
-- Contraintes pour la table `Voiture`
--
ALTER TABLE `Voiture`
  ADD CONSTRAINT `FK9bxrdi0dalatx9at1di8u90i7` FOREIGN KEY (`id`) REFERENCES `Vehicule` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
