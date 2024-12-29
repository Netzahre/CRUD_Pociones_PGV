-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-12-2024 a las 23:49:41
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

-- CREATE DATABASE pgvpociones
-- USE pgvpociones

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pgvpociones`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingredientes`
--
CREATE TABLE `ingredientes` (
  `idIngrediente` int(11) NOT NULL,
  `nombreIngrediente` varchar(255) NOT NULL,
  `tipoIngrediente` enum('MAGICO','MINERAL','ORGANICO','VEGETAL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ingredientes`
--

INSERT INTO `ingredientes` (`idIngrediente`, `nombreIngrediente`, `tipoIngrediente`) VALUES
(1, 'Hoja de Mandragora', 'VEGETAL'),
(2, 'Raiz de Belladona', 'VEGETAL'),
(3, 'Flor de Luna', 'VEGETAL'),
(4, 'Hierba de San Juan', 'VEGETAL'),
(5, 'Alga Espectral', 'VEGETAL'),
(6, 'Musgo Brillante', 'VEGETAL'),
(7, 'Corteza de Roble Ancestral', 'VEGETAL'),
(8, 'Semillas de Heliotropo', 'VEGETAL'),
(9, 'Espinas de Zarza Negra', 'VEGETAL'),
(10, 'Fruto de Serbal', 'VEGETAL'),
(11, 'Polvo de Estrella', 'MINERAL'),
(12, 'Cristal de Cuarzo Puro', 'MINERAL'),
(13, 'Fragmento de Obsidiana', 'MINERAL'),
(14, 'Piedra Filosofal (Imperfecta)', 'MINERAL'),
(15, 'Esquirla de Jade Encantado', 'MINERAL'),
(16, 'Minerales de Hierro Enriquecido', 'MINERAL'),
(17, 'Polvo de Pirita', 'MINERAL'),
(18, 'Carbon Vivo', 'MINERAL'),
(19, 'Rocas de Basalto Infernal', 'MINERAL'),
(20, 'Sal de las Profundidades', 'MINERAL'),
(21, 'Sangre de Dragon (Sustituto)', 'ORGANICO'),
(22, 'Huesos de Serpiente Voladora', 'ORGANICO'),
(23, 'Piel de Salamandra Escarlata', 'ORGANICO'),
(24, 'Escamas de Triton', 'ORGANICO'),
(25, 'Diente de Ogro', 'ORGANICO'),
(26, 'Plumas de Cuervo Nocturno', 'ORGANICO'),
(27, 'Corazon de Rana Gigante', 'ORGANICO'),
(28, 'Glandula de Veneno de Escorpion', 'ORGANICO'),
(29, 'Colmillo de Lobo Fantasma', 'ORGANICO'),
(30, 'Musculo de Golem de Barro', 'ORGANICO'),
(31, 'Polvo de Hadas', 'MAGICO'),
(32, 'Lagrimas de Unicornio', 'MAGICO'),
(33, 'Fragmento de Runa Antigua', 'MAGICO'),
(34, 'Esencia de Elemental de Agua', 'MAGICO'),
(35, 'Cenizas de Fenix', 'MAGICO'),
(36, 'Energia Residual de un Portal', 'MAGICO'),
(37, 'Fragmento de Cristal Onirico', 'MAGICO'),
(38, 'Nectar de Arbol del Mundo', 'MAGICO'),
(39, 'Resplandor de Llama Eterna', 'MAGICO'),
(40, 'Fragmento de Corazon de Dragon', 'MAGICO'),
(41, 'Aceite de Serpiente de Oro', 'ORGANICO'),
(42, 'Salvia Negra', 'VEGETAL'),
(43, 'Polvo de Carbon Encantado', 'MINERAL'),
(44, 'Polvo de Mariposa de Ebano', 'ORGANICO'),
(45, 'Resina de Arbol de Fuego', 'VEGETAL'),
(46, 'Perla de Agua Clara', 'MINERAL'),
(47, 'Arena Dorada de las Dunas Eternas', 'MINERAL'),
(48, 'Esencia de Niebla Arcana', 'MAGICO'),
(49, 'Plumas de Ave Fenix Menor', 'ORGANICO'),
(50, 'Sangre de Basilisco', 'ORGANICO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pociones`
--

CREATE TABLE `pociones` (
  `idPocion` int(11) NOT NULL,
  `precio` double NOT NULL,
  `efectoPocion` varchar(255) NOT NULL,
  `nombrePocion` varchar(255) NOT NULL,
  `escuela` enum('ABJURACION','CONJURACION','DIVINACION','ENCANTAMIENTO','EVOCACION','ILUSION','NIGROMANCIA','TRANSMUTACION','UNIVERSAL') NOT NULL,
  `tamanio` enum('GRANDE','MEDIANO','PEQUEÑO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pociones`
--

INSERT INTO `pociones` (`idPocion`, `precio`, `efectoPocion`, `nombrePocion`, `escuela`, `tamanio`) VALUES
(205, 50, 'Restaura salud al instante', 'Elixir de Vida', 'CONJURACION', 'MEDIANO'),
(206, 120, 'Te vuelve invisible por un minuto', 'Poción de Invisibilidad', 'ILUSION', 'PEQUEÑO'),
(207, 80, 'Produce una llama que nunca se apaga', 'Llama Perpetua', 'EVOCACION', 'PEQUEÑO'),
(208, 200, 'Permite hablar con los muertos', 'Esencia del Alma', 'NIGROMANCIA', 'GRANDE'),
(209, 100, 'Crea un escudo mágico protector', 'Barrera Arcana', 'ABJURACION', 'MEDIANO'),
(210, 150, 'Incrementa temporalmente la inteligencia', 'Elixir de Sabiduría', 'DIVINACION', 'PEQUEÑO'),
(211, 75, 'Aumenta el carisma temporalmente', 'Poción de Encanto', 'ENCANTAMIENTO', 'MEDIANO'),
(212, 90, 'Transforma objetos pequeños', 'Transformación Menor', 'TRANSMUTACION', 'MEDIANO'),
(213, 500, 'Revive a una criatura recientemente fallecida', 'Poción de Resurrección', 'CONJURACION', 'GRANDE'),
(214, 60, 'Permite moverse sigilosamente', 'Polvo de Sombras', 'ILUSION', 'PEQUEÑO'),
(215, 110, 'Duplica la fuerza física temporalmente', 'Fuerza de Titán', 'EVOCACION', 'MEDIANO'),
(216, 140, 'Permite ver y tocar seres etéreos', 'Vínculo Etéreo', 'NIGROMANCIA', 'GRANDE'),
(217, 100, 'Reduce el daño recibido', 'Capa Protectora', 'ABJURACION', 'PEQUEÑO'),
(218, 180, 'Otorga visiones del futuro', 'Visión Profética', 'DIVINACION', 'MEDIANO'),
(219, 95, 'Aumenta la persuasión y atracción', 'Aura de Atracción', 'ENCANTAMIENTO', 'PEQUEÑO'),
(220, 130, 'Transforma temporalmente la materia', 'Rayo Cambiante', 'TRANSMUTACION', 'MEDIANO'),
(221, 300, 'Restaura una gran cantidad de salud', 'Curación Mayor', 'CONJURACION', 'GRANDE'),
(222, 70, 'Crea un área cubierta de niebla ilusoria', 'Nieblas Ilusorias', 'ILUSION', 'MEDIANO'),
(223, 90, 'Lanza una bola de fuego a un enemigo', 'Piedra de Fuego', 'EVOCACION', 'PEQUEÑO'),
(224, 250, 'Convoca un espíritu protector', 'Esencia Fantasmal', 'NIGROMANCIA', 'GRANDE'),
(225, 150, 'Proporciona resistencia mágica', 'Armadura Arcana', 'ABJURACION', 'MEDIANO'),
(226, 130, 'Permite ver a grandes distancias', 'Ojo de Águila', 'DIVINACION', 'PEQUEÑO'),
(227, 110, 'Aumenta la capacidad de manipulación', 'Encanto de la Sirena', 'ENCANTAMIENTO', 'PEQUEÑO'),
(228, 200, 'Convierte hierro en oro durante un tiempo limitado', 'Elixir de Metales', 'TRANSMUTACION', 'GRANDE'),
(229, 180, 'Restaura salud lentamente con el tiempo', 'Aura de Vitalidad', 'CONJURACION', 'MEDIANO'),
(230, 85, 'Crea un doble ilusorio', 'Espejo Ilusorio', 'ILUSION', 'PEQUEÑO'),
(231, 120, 'Libera una onda de energía mágica', 'Explosión Controlada', 'EVOCACION', 'MEDIANO'),
(232, 350, 'Permite drenar vida de un objetivo', 'Toque de la Muerte', 'NIGROMANCIA', 'GRANDE'),
(233, 160, 'Bloquea ataques oscuros', 'Barrera de Luz', 'ABJURACION', 'MEDIANO'),
(234, 300, 'Otorga visión del pasado y el futuro', 'Clarividencia Suprema', 'DIVINACION', 'GRANDE'),
(235, 399.9, 'Te vuelve un esqueleto chiquitico', 'Elixir de TragoNublo', 'TRANSMUTACION', 'PEQUEÑO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recetas`
--

CREATE TABLE `recetas` (
  `IdIngrediente` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `idPocion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recetas`
--

INSERT INTO `recetas` (`IdIngrediente`, `cantidad`, `idPocion`) VALUES
(1, 2, 205),
(1, 3, 213),
(1, 3, 221),
(1, 1, 229),
(1, 2, 234),
(2, 1, 206),
(2, 1, 231),
(3, 1, 207),
(3, 1, 215),
(3, 1, 221),
(3, 2, 223),
(3, 2, 232),
(4, 2, 208),
(4, 1, 216),
(4, 2, 224),
(4, 2, 229),
(5, 1, 209),
(5, 1, 214),
(5, 1, 217),
(5, 1, 222),
(5, 1, 225),
(5, 2, 233),
(6, 1, 210),
(6, 2, 215),
(6, 1, 218),
(6, 1, 226),
(6, 2, 228),
(6, 1, 234),
(7, 1, 211),
(7, 2, 216),
(7, 1, 219),
(7, 1, 226),
(7, 2, 227),
(8, 1, 209),
(8, 1, 212),
(8, 2, 217),
(8, 1, 220),
(8, 2, 225),
(8, 1, 230),
(9, 1, 208),
(9, 1, 219),
(9, 1, 232),
(10, 1, 205),
(10, 1, 212),
(10, 1, 223),
(10, 1, 229),
(10, 2, 235),
(11, 2, 209),
(11, 2, 221),
(11, 1, 230),
(12, 2, 206),
(12, 1, 213),
(12, 1, 222),
(12, 1, 232),
(13, 1, 208),
(13, 1, 211),
(13, 2, 231),
(14, 1, 207),
(14, 1, 220),
(14, 1, 231),
(14, 1, 233),
(15, 1, 210),
(15, 2, 214),
(15, 1, 224),
(15, 1, 230),
(16, 1, 234),
(17, 2, 211),
(17, 1, 215),
(17, 2, 219),
(17, 2, 220),
(17, 1, 225),
(17, 1, 227),
(17, 1, 235),
(18, 1, 207),
(18, 1, 223),
(19, 1, 212),
(19, 2, 218),
(19, 1, 228),
(20, 1, 205),
(20, 1, 216),
(20, 1, 227),
(20, 1, 233),
(21, 1, 217),
(22, 1, 206),
(22, 1, 213),
(22, 1, 226),
(23, 1, 214),
(23, 1, 218),
(23, 1, 224),
(23, 1, 228),
(23, 1, 235),
(24, 2, 210),
(24, 1, 222);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ingredientes`
--
ALTER TABLE `ingredientes`
  ADD PRIMARY KEY (`idIngrediente`);

--
-- Indices de la tabla `pociones`
--
ALTER TABLE `pociones`
  ADD PRIMARY KEY (`idPocion`);

--
-- Indices de la tabla `recetas`
--
ALTER TABLE `recetas`
  ADD PRIMARY KEY (`IdIngrediente`,`idPocion`),
  ADD KEY `FKj7iln4wpfl7s77u38vfvbgkiv` (`idPocion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ingredientes`
--
ALTER TABLE `ingredientes`
  MODIFY `idIngrediente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `pociones`
--
ALTER TABLE `pociones`
  MODIFY `idPocion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=236;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `recetas`
--
ALTER TABLE `recetas`
  ADD CONSTRAINT `FK9j5ecj3j3kplm7yxxnqjt67y8` FOREIGN KEY (`IdIngrediente`) REFERENCES `ingredientes` (`idIngrediente`),
  ADD CONSTRAINT `FKj7iln4wpfl7s77u38vfvbgkiv` FOREIGN KEY (`idPocion`) REFERENCES `pociones` (`idPocion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
