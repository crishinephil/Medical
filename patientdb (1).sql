-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 26, 2025 at 07:18 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `patientdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `address` text DEFAULT NULL,
  `contact` varchar(20) NOT NULL,
  `examination_date` date NOT NULL,
  `slot_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `fullname`, `address`, `contact`, `examination_date`, `slot_number`) VALUES
(1, 'Crishinephil Rosal', 'Ormoc', '09738472837', '2025-03-10', 195),
(2, 'Cecile Salilin', 'Ormoc', '09563392341', '2025-03-14', 200);

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL CHECK (`age` >= 0),
  `address` text DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `patient_condition` text DEFAULT NULL,
  `purpose` varchar(100) NOT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `status` varchar(100) NOT NULL,
  `examination_date` date NOT NULL,
  `certificate_date` date NOT NULL,
  `slot_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`id`, `fullname`, `age`, `address`, `email`, `patient_condition`, `purpose`, `doctor_id`, `status`, `examination_date`, `certificate_date`, `slot_number`) VALUES
(1, 'Randy Endolos', 23, 'Ormoc', 'randy@gmail.com', 'asthma', 'educ tour', 1, 'Pending', '2025-03-10', '2025-03-07', 1),
(2, 'Neal Gasal', 22, 'ormoc', 'cecithjaked@gmail.com', 'none', 'educational tour', 1, 'Pending', '2025-03-10', '2025-03-07', 3),
(5, 'aviv sumile', 20, 'ormoc', 'sumileavivyarden@gmail.com', 'none', 'educational tour', 1, 'Pending', '2025-03-10', '2025-03-11', 4),
(6, 'Randy Endolos', 23, 'Green Valley Ormoc City', 'randy.endolos@evsu.educ.ph', 'Astma', 'Flight Medical Certificate', 1, 'Pending', '2025-05-15', '2025-05-13', 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `patients_ibfk_1` (`doctor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
