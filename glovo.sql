-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 04, 2023 at 12:20 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `glovo`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`username`, `password`, `type`) VALUES
('admin', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `id_cmd` int(50) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `quantityProd` varchar(100) NOT NULL,
  `price` varchar(100) NOT NULL,
  `date_cmd` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `commande_receipt`
--

CREATE TABLE `commande_receipt` (
  `id` int(50) NOT NULL,
  `total` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `id_delivery` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `commande_receipt`
--

INSERT INTO `commande_receipt` (`id`, `total`, `date`, `id_delivery`) VALUES
(1, '510.0', '2023/03/31', NULL),
(2, '510.0', '2023/03/31', NULL),
(3, '510.0', '2023/03/31', NULL),
(11, '80.0', '2023/03/31', NULL),
(12, '100.0', '2023/04/01', NULL),
(23, '20.0', '2023/04/01', NULL),
(28, '40.0', '2023/04/02', NULL),
(29, '40.0', '2023/04/02', NULL),
(30, '100.0', '2023/04/02', NULL),
(31, '150.0', '2023/04/02', NULL),
(32, '1050.0', '2023/04/02', NULL),
(33, '30.0', '2023/04/02', NULL),
(34, '20.0', '2023/04/02', NULL),
(35, '20.0', '2023/04/02', NULL),
(36, '20.0', '2023/04/02', NULL),
(37, '30.0', '2023/04/02', NULL),
(39, '6.0', '2023/04/03', NULL),
(40, '49.0', '2023/04/03', NULL),
(41, '30.0', '2023/04/03', NULL),
(42, '220.0', '2023/04/03', NULL),
(43, '200.0', '2023/04/03', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `id_delivery` int(50) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `id_livreur` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `livreur`
--

CREATE TABLE `livreur` (
  `id_livreur` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `livreur`
--

INSERT INTO `livreur` (`id_livreur`, `nom`, `telephone`, `status`) VALUES
(13, 'Oussama', '0676877687', 'Available'),
(14, 'Amine', '0678876545', 'Available'),
(15, 'Anass', '0787009988', 'Available'),
(16, 'Hassan', '0762633567', 'Available'),
(17, 'Zineb', '0677665543', 'Available'),
(18, 'Manal', '0765678763', 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id_products` int(50) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id_products`, `product_name`, `brand`, `status`, `price`) VALUES
(1, 'Pizza', 'PizzaHat', 'Available', 50),
(7, 'Hamburger', 'Macdonald', 'Available', 20),
(9, 'Nuggets', 'KFC', 'Available', 30),
(11, 'Pasta', 'FastFood', 'Not Available', 30),
(12, 'IceCream', 'Macdonald', 'Not Available', 13);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `gendre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `password`, `gendre`) VALUES
(1, 'user', 'user', 'male');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_cmd`);

--
-- Indexes for table `commande_receipt`
--
ALTER TABLE `commande_receipt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commande_receipt_delivery` (`id_delivery`);

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id_delivery`),
  ADD KEY `fk_delivery_livreur` (`id_livreur`);

--
-- Indexes for table `livreur`
--
ALTER TABLE `livreur`
  ADD PRIMARY KEY (`id_livreur`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id_products`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_cmd` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT for table `commande_receipt`
--
ALTER TABLE `commande_receipt`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id_delivery` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `livreur`
--
ALTER TABLE `livreur`
  MODIFY `id_livreur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id_products` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `commande_receipt`
--
ALTER TABLE `commande_receipt`
  ADD CONSTRAINT `fk_commande_receipt_delivery` FOREIGN KEY (`id_delivery`) REFERENCES `delivery` (`id_delivery`);

--
-- Constraints for table `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `fk_delivery_livreur` FOREIGN KEY (`id_livreur`) REFERENCES `livreur` (`id_livreur`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
