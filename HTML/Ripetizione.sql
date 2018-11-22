-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Nov 16, 2018 alle 11:38
-- Versione del server: 10.1.36-MariaDB
-- Versione PHP: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Ripetizione`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Corso`
--

CREATE TABLE `Corso` (
  `Titolo` varchar(50) NOT NULL,
  `Descrizione` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Corso`
--

INSERT INTO `Corso` (`Titolo`, `Descrizione`) VALUES
('Inglese', 'corso di inglese'),
('Italiano', 'corso di italiano'),
('Matematica', 'corso di matematica ');

-- --------------------------------------------------------

--
-- Struttura della tabella `CorsoDocente`
--

CREATE TABLE `CorsoDocente` (
  `Docente` int(11) NOT NULL,
  `Corso` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `CorsoDocente`
--

INSERT INTO `CorsoDocente` (`Docente`, `Corso`) VALUES
(1, 'Inglese'),
(1, 'Italiano'),
(2, 'Italiano'),
(2, 'Matematica');

-- --------------------------------------------------------

--
-- Struttura della tabella `Docente`
--

CREATE TABLE `Docente` (
  `ID_Docente` int(11) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `DataNascita` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Docente`
--

INSERT INTO `Docente` (`ID_Docente`, `Cognome`, `Nome`, `Email`, `DataNascita`) VALUES
(1, 'Uno', 'Docente1', 'doc1@gmail.com', '2018-09-18'),
(2, 'Due', 'Docente2', 'doc2@gmail.com', '2018-08-14');

-- --------------------------------------------------------

--
-- Struttura della tabella `Prenotazione`
--

CREATE TABLE `Prenotazione` (
  `ID_Prenotazione` int(11) NOT NULL,
  `Studente` int(11) NOT NULL,
  `Docente` int(11) NOT NULL,
  `Corso` varchar(50) NOT NULL,
  `DTInizio` datetime NOT NULL,
  `DTFine` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Prenotazione`
--

INSERT INTO `Prenotazione` (`ID_Prenotazione`, `Studente`, `Docente`, `Corso`, `DTInizio`, `DTFine`) VALUES
(2, 2, 1, 'Italiano', '2018-11-16 18:00:00', '2018-11-16 19:00:00');

-- --------------------------------------------------------

--
-- Struttura della tabella `Ruolo`
--

CREATE TABLE `Ruolo` (
  `Nome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Ruolo`
--

INSERT INTO `Ruolo` (`Nome`) VALUES
('Admin'),
('Studente');

-- --------------------------------------------------------

--
-- Struttura della tabella `Utente`
--

CREATE TABLE `Utente` (
  `ID_Utente` int(11) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Pwd` varchar(50) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Ruolo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Utente`
--

INSERT INTO `Utente` (`ID_Utente`, `Username`, `Pwd`, `Nome`, `Cognome`, `Email`, `Ruolo`) VALUES
(1, 'mikelian', 'mikelian', 'Michele', 'Gazulli', 'mikeliangazgaz@gmail.com', 'Studente'),
(2, 'fabio', 'fabio', 'Fabio', 'Federico', 'fedefavoloso@gmail.com', 'Studente'),
(4, 'admin', 'admin', 'Admin', 'Admin', 'admin@gmail.com', 'Admin');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `Corso`
--
ALTER TABLE `Corso`
  ADD PRIMARY KEY (`Titolo`);

--
-- Indici per le tabelle `CorsoDocente`
--
ALTER TABLE `CorsoDocente`
  ADD PRIMARY KEY (`Docente`,`Corso`),
  ADD KEY `Corso` (`Corso`);

--
-- Indici per le tabelle `Docente`
--
ALTER TABLE `Docente`
  ADD PRIMARY KEY (`ID_Docente`);

--
-- Indici per le tabelle `Prenotazione`
--
ALTER TABLE `Prenotazione`
  ADD PRIMARY KEY (`ID_Prenotazione`),
  ADD KEY `CorsoRipetizione` (`Corso`),
  ADD KEY `CorsoStudente` (`Studente`),
  ADD KEY `CorsoDocente` (`Docente`);

--
-- Indici per le tabelle `Ruolo`
--
ALTER TABLE `Ruolo`
  ADD PRIMARY KEY (`Nome`);

--
-- Indici per le tabelle `Utente`
--
ALTER TABLE `Utente`
  ADD PRIMARY KEY (`ID_Utente`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD KEY `Ruolo-Utente` (`Ruolo`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `Docente`
--
ALTER TABLE `Docente`
  MODIFY `ID_Docente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `Prenotazione`
--
ALTER TABLE `Prenotazione`
  MODIFY `ID_Prenotazione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `Utente`
--
ALTER TABLE `Utente`
  MODIFY `ID_Utente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `CorsoDocente`
--
ALTER TABLE `CorsoDocente`
  ADD CONSTRAINT `Corso` FOREIGN KEY (`Corso`) REFERENCES `Corso` (`Titolo`),
  ADD CONSTRAINT `Docente` FOREIGN KEY (`Docente`) REFERENCES `Docente` (`ID_Docente`);

--
-- Limiti per la tabella `Prenotazione`
--
ALTER TABLE `Prenotazione`
  ADD CONSTRAINT `CorsoDocente` FOREIGN KEY (`Docente`) REFERENCES `CorsoDocente` (`Docente`),
  ADD CONSTRAINT `CorsoRipetizione` FOREIGN KEY (`Corso`) REFERENCES `CorsoDocente` (`Corso`),
  ADD CONSTRAINT `CorsoStudente` FOREIGN KEY (`Studente`) REFERENCES `Utente` (`ID_Utente`);

--
-- Limiti per la tabella `Utente`
--
ALTER TABLE `Utente`
  ADD CONSTRAINT `Ruolo-Utente` FOREIGN KEY (`Ruolo`) REFERENCES `Ruolo` (`Nome`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
