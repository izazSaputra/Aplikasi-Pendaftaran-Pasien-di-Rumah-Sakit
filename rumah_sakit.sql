-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 10, 2026 at 02:19 AM
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
-- Database: `rumah_sakit`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nama_admin` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES
(1, 'admin', '123', 'Admin1');

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` int(11) NOT NULL,
  `nama_kamar` varchar(50) DEFAULT NULL,
  `tipe_kamar` varchar(20) DEFAULT NULL,
  `status_kamar` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nama_kamar`, `tipe_kamar`, `status_kamar`) VALUES
(5, 'BPJS-101', 'BPJS', 'kosong'),
(6, 'BPJS-102', 'BPJS', 'kosong'),
(7, 'BPJS-103', 'BPJS', 'kosong'),
(8, 'BPJS-104', 'BPJS', 'kosong'),
(9, 'BPJS-105', 'BPJS', 'kosong'),
(10, 'BPJS-106', 'BPJS', 'kosong'),
(11, 'BPJS-107', 'BPJS', 'kosong'),
(12, 'BPJS-108', 'BPJS', 'kosong'),
(13, 'BPJS-109', 'BPJS', 'kosong'),
(14, 'BPJS-110', 'BPJS', 'kosong'),
(15, 'BPJS-111', 'BPJS', 'kosong'),
(16, 'BPJS-112', 'BPJS', 'kosong'),
(17, 'BPJS-113', 'BPJS', 'kosong'),
(18, 'BPJS-114', 'BPJS', 'kosong'),
(19, 'BPJS-115', 'BPJS', 'kosong'),
(20, 'BPJS-116', 'BPJS', 'kosong'),
(21, 'BPJS-117', 'BPJS', 'kosong'),
(22, 'BPJS-118', 'BPJS', 'kosong'),
(23, 'BPJS-119', 'BPJS', 'kosong'),
(24, 'BPJS-120', 'BPJS', 'kosong'),
(25, 'REG-201', 'REGULER', 'terisi'),
(26, 'REG-202', 'REGULER', 'kosong'),
(27, 'REG-203', 'REGULER', 'kosong'),
(28, 'REG-204', 'REGULER', 'kosong'),
(29, 'REG-205', 'REGULER', 'kosong'),
(30, 'REG-206', 'REGULER', 'kosong'),
(31, 'REG-207', 'REGULER', 'kosong'),
(32, 'REG-208', 'REGULER', 'kosong'),
(33, 'REG-209', 'REGULER', 'kosong'),
(34, 'REG-210', 'REGULER', 'kosong'),
(35, 'REG-211', 'REGULER', 'kosong'),
(36, 'REG-212', 'REGULER', 'kosong'),
(37, 'REG-213', 'REGULER', 'kosong'),
(38, 'REG-214', 'REGULER', 'kosong'),
(39, 'REG-215', 'REGULER', 'kosong'),
(40, 'REG-216', 'REGULER', 'kosong'),
(41, 'REG-217', 'REGULER', 'kosong'),
(42, 'REG-218', 'REGULER', 'kosong'),
(43, 'REG-219', 'REGULER', 'kosong'),
(44, 'REG-220', 'REGULER', 'kosong'),
(45, 'VIP-301', 'VIP', 'kosong'),
(46, 'VIP-302', 'VIP', 'kosong'),
(47, 'VIP-303', 'VIP', 'kosong'),
(48, 'VIP-304', 'VIP', 'kosong'),
(49, 'VIP-305', 'VIP', 'kosong'),
(50, 'VIP-306', 'VIP', 'kosong'),
(51, 'VIP-307', 'VIP', 'kosong'),
(52, 'VIP-308', 'VIP', 'kosong'),
(53, 'VIP-309', 'VIP', 'kosong'),
(54, 'VIP-310', 'VIP', 'kosong');

-- --------------------------------------------------------

--
-- Table structure for table `pendaftaran`
--

CREATE TABLE `pendaftaran` (
  `id_pendaftaran` int(11) NOT NULL,
  `nama_pasien` varchar(100) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `level_kronis` varchar(20) DEFAULT NULL,
  `bpjs` tinyint(1) DEFAULT NULL,
  `tanggal_daftar` date DEFAULT NULL,
  `id_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pendaftaran`
--

INSERT INTO `pendaftaran` (`id_pendaftaran`, `nama_pasien`, `alamat`, `no_hp`, `level_kronis`, `bpjs`, `tanggal_daftar`, `id_admin`) VALUES
(3, 'ijas', 'jalan sini', '0895622242960', 'Berat', 0, '2026-04-06', 1),
(4, 'lali', 'siapa ya', '112312312312', 'Sedang', 1, '2026-04-06', 1),
(5, 'anjay gurinjay', 'dwasdwa', '61254234', 'Berat', 0, '2026-04-06', 1),
(6, 'tadi', 'gtw			', '12312312', 'Ringan', 0, '2026-04-06', 1),
(7, 'Eka Revo', 'Jl. Suhat', '0811166667', 'Berat', 0, '2026-04-06', 1),
(8, 'Darya', 'JL. BEKASI', '1231231231231', 'Sedang', 0, '2026-04-06', 1),
(9, 'Izaz Saputra', 'Jl. dimana hayo', '1231231231', 'Sedang', 1, '2026-04-07', 1),
(10, 'Ijad', 'Jl. ini itu', '0895123', 'Sedang', 0, '2026-04-07', 1),
(11, 'Farhan', 'Jl. Simpang Janti Barat I', '089519669110', 'Sedang', 0, '2026-04-07', 1),
(12, 'Farhans', 'Jl. Janti Sukun', '089591165044', 'Berat', 1, '2026-04-07', 1),
(13, 'Alvito', 'JL. jalan', '69420', 'Sedang', 0, '2026-04-07', 1),
(14, 'Blebleble', '...', '100', 'Sedang', 0, '2026-04-07', 1),
(15, 'irenk', 'Jl. aseli', '56568', 'Ringan', 0, '2026-04-07', 1),
(16, 'Jastin', 'JL. Popo', '89547', 'Ringan', 0, '2026-04-07', 1),
(17, 'Namaku', 'Jl. jalannya', '784512', 'Ringan', 0, '2026-04-07', 1),
(18, 'Nama orang', 'Jl. supriadi', '96581', 'Ringan', 0, '2026-04-07', 1),
(19, 'pasien 2', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(20, 'pasin 2', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(21, 'Izaz Saputra', 'Jl. Singosari', '0895', 'Ringan', 0, '2026-04-07', 1),
(22, 'Farhan Yuval', 'Jl. Sukun', '089519669110', 'Ringan', 0, '2026-04-07', 1),
(23, 'Farhan Yuval', 'Jl. Simpang', '08951', 'Ringan', 0, '2026-04-07', 1),
(24, 'Farhan', 'Jl.', '08951', 'Ringan', 0, '2026-04-07', 1),
(25, 'Farhan', 'jjjj', '123', 'Ringan', 0, '2026-04-07', 1),
(26, 'Farhan', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(27, 'Farhan', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(28, 'Farhan', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(29, 'Farhan', 'JL', '123', 'Ringan', 0, '2026-04-09', 1),
(30, 'farhan', 'JL', '123123', 'Ringan', 0, '2026-04-09', 1),
(31, 'Farhan', 'jl', '123', 'Ringan', 0, '2026-04-09', 1),
(32, 'farhan', 'jl', '123', 'Ringan', 0, '2026-04-09', 1),
(33, 'Hantu', 'JL. Tidak Terlihat', '888888', 'Berat', 1, '2026-04-07', 1),
(34, 'Farhan', 'JL', '123', 'Ringan', 0, '2026-04-07', 1),
(35, 'miya', 'JL. ML', '1234', 'Ringan', 0, '2026-04-07', 1),
(36, 'Pasien Sakit', 'JL...', '5000', 'Ringan', 0, '2026-04-07', 1),
(37, 'Pasien BPJS', 'JL', '1234', 'Berat', 1, '2026-04-07', 1),
(38, 'mara', '...', '123', 'Ringan', 0, '2026-04-07', 1),
(39, 'mara', '...', '123', 'Ringan', 0, '2026-04-07', 1),
(40, 'Rasha', 'JL. Sidoarjo', '1000', 'Berat', 0, '2026-04-07', 1),
(41, 'Farhan Yuval Susanto', 'JL. Simpang Janti Barat I B/8', '089519669110', 'Sedang', 0, '2026-04-08', 1);

-- --------------------------------------------------------

--
-- Table structure for table `rawat_inap`
--

CREATE TABLE `rawat_inap` (
  `id_rawat` int(11) NOT NULL,
  `id_pendaftaran` int(11) DEFAULT NULL,
  `id_kamar` int(11) DEFAULT NULL,
  `tanggal_masuk` date DEFAULT NULL,
  `tanggal_keluar` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `tanggal_dikeluarkan` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rawat_inap`
--

INSERT INTO `rawat_inap` (`id_rawat`, `id_pendaftaran`, `id_kamar`, `tanggal_masuk`, `tanggal_keluar`, `status`, `tanggal_dikeluarkan`) VALUES
(2, 3, 45, '2026-04-06', '2026-04-09', 'selesai', NULL),
(3, 4, 5, '2026-04-06', '2026-04-09', 'selesai', NULL),
(4, 5, 46, '2026-04-06', '2026-04-09', 'selesai', NULL),
(5, 6, 25, '2026-04-06', '2026-04-09', 'selesai', NULL),
(6, 7, 47, '2026-04-06', '2026-04-11', 'selesai', NULL),
(7, 8, 26, '2026-04-06', '2026-04-09', 'selesai', NULL),
(9, 10, 25, '2026-04-07', '2026-04-10', 'selesai', NULL),
(10, 11, 26, '2026-04-07', '2026-04-10', 'selesai', NULL),
(11, 12, 5, '2026-04-07', '2026-04-09', 'selesai', NULL),
(12, 13, 26, '2026-04-07', '2026-04-10', 'selesai', NULL),
(13, 14, 27, '2026-04-07', '2026-04-10', 'selesai', NULL),
(14, 15, 28, '2026-04-07', '2026-04-10', 'selesai', NULL),
(15, 16, 29, '2026-04-07', '2026-04-10', 'selesai', NULL),
(16, 17, 30, '2026-04-07', '2026-04-10', 'selesai', NULL),
(17, 18, 26, '2026-04-07', '2026-04-10', 'selesai', NULL),
(18, 19, 26, '2026-04-07', '2026-04-10', 'selesai', NULL),
(19, 20, 31, '2026-04-07', '2026-04-10', 'selesai', NULL),
(20, 21, 25, '2026-04-07', '2026-04-10', 'selesai', NULL),
(21, 22, 26, '2026-04-07', '2026-04-07', 'selesai', NULL),
(22, 23, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(23, 24, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(24, 25, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(25, 26, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(26, 27, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(27, 28, 25, '2026-04-07', '2026-04-09', 'selesai', NULL),
(28, 29, 25, '2026-04-09', '2026-04-09', 'selesai', NULL),
(29, 30, 25, '2026-04-09', '2026-04-09', 'selesai', NULL),
(30, 31, 25, '2026-04-09', '2026-04-12', 'selesai', '2026-04-09 16:45:48'),
(31, 32, 26, '2026-04-09', '2026-04-12', 'selesai', '2026-04-09 16:46:28'),
(32, 33, 5, '2026-04-07', '2026-04-07', 'selesai', NULL),
(33, 34, 25, '2026-04-07', '2026-04-07', 'selesai', NULL),
(34, 35, 25, '2026-04-07', '2026-04-10', 'selesai', '2026-04-07 19:32:10'),
(35, 36, 25, '2026-04-07', '2026-04-10', 'selesai', '2026-04-07 19:34:48'),
(36, 37, 5, '2026-04-07', '2026-04-09', 'selesai', '2026-04-07 19:35:16'),
(37, 38, 25, '2026-04-07', '2026-04-10', 'selesai', '2026-04-07 19:36:46'),
(38, 39, 26, '2026-04-07', '2026-04-10', 'selesai', '2026-04-07 19:37:44'),
(39, 40, 45, '2026-04-07', '2026-04-12', 'selesai', '2026-04-07 19:30:26'),
(40, 41, 25, '2026-04-08', '2026-04-11', 'aktif', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indexes for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD PRIMARY KEY (`id_pendaftaran`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indexes for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  ADD PRIMARY KEY (`id_rawat`),
  ADD KEY `id_pendaftaran` (`id_pendaftaran`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  MODIFY `id_pendaftaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  MODIFY `id_rawat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD CONSTRAINT `pendaftaran_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);

--
-- Constraints for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  ADD CONSTRAINT `rawat_inap_ibfk_1` FOREIGN KEY (`id_pendaftaran`) REFERENCES `pendaftaran` (`id_pendaftaran`),
  ADD CONSTRAINT `rawat_inap_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
