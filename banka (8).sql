-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 15 May 2022, 18:10:18
-- Sunucu sürümü: 8.0.29
-- PHP Sürümü: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `banka`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `banka_bilgileri`
--

CREATE TABLE `banka_bilgileri` (
  `banka_muduru_tc` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `banka_calisan_sayisi` int NOT NULL,
  `toplam_maas` int NOT NULL,
  `banka_bakiye` int NOT NULL,
  `banka_muduru_sifre` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `banka_bilgileri`
--

INSERT INTO `banka_bilgileri` (`banka_muduru_tc`, `banka_calisan_sayisi`, `toplam_maas`, `banka_bakiye`, `banka_muduru_sifre`) VALUES
('12345678910', 1, 1200, 1000, '1234');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `doviz_tablosu`
--

CREATE TABLE `doviz_tablosu` (
  `doviz_turu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kur_orani` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `doviz_tablosu`
--

INSERT INTO `doviz_tablosu` (`doviz_turu`, `kur_orani`) VALUES
('Dolar', 15.39),
('Euro', 16.1),
('Gram Altın', 911.82),
('Sterlin', 18.88),
('Türk Lirası', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hesap_tablosu`
--

CREATE TABLE `hesap_tablosu` (
  `hesap_no` int NOT NULL,
  `doviz_turu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bakiye` float NOT NULL,
  `tc_no` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kullanici_turu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hesap_tablosu`
--

INSERT INTO `hesap_tablosu` (`hesap_no`, `doviz_turu`, `bakiye`, `tc_no`, `kullanici_turu`) VALUES
(1, 'Türk Lirası', 1000, '00000000000', 'Banka'),
(4, 'Türk Lirası', 130, '35468795423', 'Müşteri'),
(5, 'Türk Lirası', 69.83, '35468795423', 'Müşteri'),
(6, 'Türk Lirası', 1579.95, '49785669546', 'Müşteri'),
(7, 'Dolar', 23.675, '35468795423', 'Müşteri'),
(8, 'Türk Lirası', 30, '76354921365', 'Müşteri'),
(10, 'Euro', 0, '49785669546', 'Müşteri'),
(13, 'Türk Lirası', 0, '11055644889', 'Müşteri'),
(14, 'Sterlin', 0, '11055644889', 'Müşteri'),
(17, 'Türk Lirası', 45, '35468795423', 'Müşteri'),
(19, 'Dolar', 0, '76354921365', 'Müşteri'),
(20, 'Türk Lirası', 50, '14207745572', 'Müşteri'),
(21, 'Euro', 203.106, '14207745572', 'Müşteri');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `islem_tablosu`
--

CREATE TABLE `islem_tablosu` (
  `islem_no` int NOT NULL,
  `kaynak` int DEFAULT NULL,
  `hedef` int DEFAULT NULL,
  `islem_turu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tutar` float NOT NULL,
  `kaynak_bakiye` float DEFAULT NULL,
  `hedef_bakiye` float DEFAULT NULL,
  `tarih` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `islem_tablosu`
--

INSERT INTO `islem_tablosu` (`islem_no`, `kaynak`, `hedef`, `islem_turu`, `tutar`, `kaynak_bakiye`, `hedef_bakiye`, `tarih`) VALUES
(1, 1, 2, 'Para Transferi', 100, 300, 400, '2022-05-10'),
(2, 1, 3, 'Para transferi', 200, 400, 700, '2022-05-10'),
(3, 2, 4, 'Borç Ödeme', 1000, 300, 5000, '2022-05-10'),
(4, 4, 5, 'Para Transferi', 10, 204.83, 10, '2022-05-13'),
(5, 4, 5, 'Para Transferi', 14.83, 190, 24.83, '2022-05-13'),
(6, 4, 5, 'Para Transferi', 10, 180, 34.83, '2022-05-13'),
(22, NULL, 5, 'Para Yatırma', 25, NULL, 69.83, '2022-05-14'),
(23, 7, NULL, 'Para Çekme', 15, 23.675, NULL, '2022-05-14'),
(24, NULL, 17, 'Para Yatırma', 20, NULL, 20, '2022-05-14'),
(25, 17, NULL, 'Para Çekme', 5, 15, NULL, '2022-05-14'),
(26, 4, 17, 'Para Transferi', 30, 130, 45, '2022-05-14'),
(27, NULL, 18, 'Para Yatırma', 10, NULL, 10, '2022-05-15'),
(28, 18, NULL, 'Para Çekme', 10, 0, NULL, '2022-05-15'),
(29, NULL, 20, 'Para Yatırma', 100, NULL, 100, '2022-05-15'),
(30, NULL, 21, 'Para Yatırma', 200, NULL, 200, '2022-05-15'),
(31, 20, 21, 'Para Transferi', 3.10559, 96.8944, 203.106, '2022-05-15');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `istek_tablosu`
--

CREATE TABLE `istek_tablosu` (
  `istek_id` int NOT NULL,
  `hesap_no` int DEFAULT NULL,
  `tc_no` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `istek_turu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Hesap açma,hesap silme',
  `doviz_turu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `onay_durumu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Onay Bekliyor' COMMENT 'Reddedildi,Onay Bekliyor,Onaylandı'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `istek_tablosu`
--

INSERT INTO `istek_tablosu` (`istek_id`, `hesap_no`, `tc_no`, `istek_turu`, `doviz_turu`, `onay_durumu`) VALUES
(1, NULL, '35468795423', 'Hesap Açma', 'Türk Lirası', 'Reddedildi'),
(7, NULL, '35468795423', 'Hesap Açma', 'Türk Lirası', 'Reddedildi'),
(10, 18, '35468795423', 'Hesap Silme', 'Türk Lirası', 'Onaylandı'),
(11, NULL, '76354921365', 'Hesap Açma', 'Türk Lirası', 'Reddedildi'),
(12, NULL, '76354921365', 'Hesap Açma', 'Dolar', 'Onaylandı'),
(13, NULL, '14207745572', 'Hesap Açma', 'Türk Lirası', 'Onaylandı'),
(14, NULL, '14207745572', 'Hesap Açma', 'Euro', 'Onaylandı');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kredi_oran_tablosu`
--

CREATE TABLE `kredi_oran_tablosu` (
  `faiz_orani` float NOT NULL,
  `gecikme_faiz_orani` float NOT NULL,
  `is_uptodate` tinyint(1) NOT NULL,
  `kredi_oran_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `kredi_oran_tablosu`
--

INSERT INTO `kredi_oran_tablosu` (`faiz_orani`, `gecikme_faiz_orani`, `is_uptodate`, `kredi_oran_id`) VALUES
(15, 5, 0, 1),
(20, 7, 0, 4),
(25, 7, 0, 5),
(25, 9, 0, 6),
(35, 10, 1, 7);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `müşteri_bilgiler_tablosu`
--

CREATE TABLE `müşteri_bilgiler_tablosu` (
  `ad_soyad` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `telefon` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `tc_no` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `adres` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `e_posta` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `temsilci_tc_no` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `sifre` varchar(16) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `müşteri_bilgiler_tablosu`
--

INSERT INTO `müşteri_bilgiler_tablosu` (`ad_soyad`, `telefon`, `tc_no`, `adres`, `e_posta`, `temsilci_tc_no`, `sifre`) VALUES
('Mahmut Hame', '5545689431', '11055644889', 'Adıyaman', 'mhamer@hotmail.com', '11223344556', '123456'),
('Mahmut Ekler', '5478964453', '11223324556', 'Erzincan', 'mahmutekler@hotmail.com', '11223344556', '12345'),
('Ahmet Kurt', '5522054976', '14207745572', 'Mardin', 'ahmet4721roni@gmail.com', '98765432199', 'Ahmet4721'),
('Ahmet Kurten', '5392941123', '32132945889', 'Adana', 'ahmat@gmail.com', '49785661239', '654123'),
('Deniz Gök', '5469874568', '35468795423', 'İzmir', 'denizgok@hotmail.com', '49785661239', '1234'),
('Bahar Gök', '5054887043', '49785669546', 'İzmir', 'bahargok@gmail.com', '49785661239', '1234'),
('Nevzat Koçak', '5127105632', '54785216334', 'Elazığ', 'nkocak@hotmail.com', '11223344556', '1234'),
('Fikret Kuzu', '5496214783', '76354921365', 'Ordu', 'fikretkuzu@hotmail.com', '98765432199', '1234');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `temsilci_bilgiler_tablosu`
--

CREATE TABLE `temsilci_bilgiler_tablosu` (
  `ad_soyad` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `telefon` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `tc_no` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `adres` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `e_posta` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `maas` int NOT NULL,
  `musteri_sayisi` int NOT NULL,
  `sifre` varchar(16) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `temsilci_bilgiler_tablosu`
--

INSERT INTO `temsilci_bilgiler_tablosu` (`ad_soyad`, `telefon`, `tc_no`, `adres`, `e_posta`, `maas`, `musteri_sayisi`, `sifre`) VALUES
('Okan Serbes', '5469874565', '11223344556', 'Adana', 'okanserbes@gmail.com', 2500, 3, '1234'),
('Barış Kocatürk', '5551112233', '49785661239', 'İstanbul', 'bkocaturk@gmail.com', 2500, 3, '1234'),
('Kaan Boşnak', '5698745564', '98765432199', 'Kocaeli', 'kaanbosnak@gmail.com', 2500, 2, '1234');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `banka_bilgileri`
--
ALTER TABLE `banka_bilgileri`
  ADD PRIMARY KEY (`banka_muduru_tc`);

--
-- Tablo için indeksler `doviz_tablosu`
--
ALTER TABLE `doviz_tablosu`
  ADD PRIMARY KEY (`doviz_turu`);

--
-- Tablo için indeksler `hesap_tablosu`
--
ALTER TABLE `hesap_tablosu`
  ADD PRIMARY KEY (`hesap_no`);

--
-- Tablo için indeksler `islem_tablosu`
--
ALTER TABLE `islem_tablosu`
  ADD PRIMARY KEY (`islem_no`);

--
-- Tablo için indeksler `istek_tablosu`
--
ALTER TABLE `istek_tablosu`
  ADD PRIMARY KEY (`istek_id`),
  ADD UNIQUE KEY `hesap_no` (`hesap_no`);

--
-- Tablo için indeksler `kredi_oran_tablosu`
--
ALTER TABLE `kredi_oran_tablosu`
  ADD PRIMARY KEY (`kredi_oran_id`);

--
-- Tablo için indeksler `müşteri_bilgiler_tablosu`
--
ALTER TABLE `müşteri_bilgiler_tablosu`
  ADD PRIMARY KEY (`tc_no`),
  ADD UNIQUE KEY `e_posta` (`e_posta`),
  ADD UNIQUE KEY `telefon` (`telefon`);

--
-- Tablo için indeksler `temsilci_bilgiler_tablosu`
--
ALTER TABLE `temsilci_bilgiler_tablosu`
  ADD PRIMARY KEY (`tc_no`),
  ADD UNIQUE KEY `e_posta` (`e_posta`),
  ADD UNIQUE KEY `telefon` (`telefon`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `hesap_tablosu`
--
ALTER TABLE `hesap_tablosu`
  MODIFY `hesap_no` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Tablo için AUTO_INCREMENT değeri `islem_tablosu`
--
ALTER TABLE `islem_tablosu`
  MODIFY `islem_no` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Tablo için AUTO_INCREMENT değeri `istek_tablosu`
--
ALTER TABLE `istek_tablosu`
  MODIFY `istek_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Tablo için AUTO_INCREMENT değeri `kredi_oran_tablosu`
--
ALTER TABLE `kredi_oran_tablosu`
  MODIFY `kredi_oran_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
