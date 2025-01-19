-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-01-2025 a las 14:25:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

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
(1, 50, 'Restaura salud al instante', 'Elixir de Vida', 'CONJURACION', 'MEDIANO'),
(2, 120, 'Te vuelve invisible por un minuto', 'Poción de Invisibilidad', 'ILUSION', 'PEQUEÑO'),
(3, 80, 'Produce una llama que nunca se apaga', 'Llama Perpetua', 'EVOCACION', 'PEQUEÑO'),
(4, 200, 'Permite hablar con los muertos', 'Esencia del Alma', 'NIGROMANCIA', 'GRANDE'),
(5, 100, 'Crea un escudo mágico protector', 'Barrera Arcana', 'ABJURACION', 'MEDIANO'),
(6, 150, 'Incrementa temporalmente la inteligencia', 'Elixir de Sabiduría', 'DIVINACION', 'PEQUEÑO'),
(7, 75, 'Aumenta el carisma temporalmente', 'Poción de Encanto', 'ENCANTAMIENTO', 'MEDIANO'),
(8, 90, 'Transforma objetos pequeños', 'Transformación Menor', 'TRANSMUTACION', 'MEDIANO'),
(9, 500, 'Revive a una criatura recientemente fallecida', 'Poción de Resurrección', 'CONJURACION', 'GRANDE'),
(10, 60, 'Permite moverse sigilosamente', 'Polvo de Sombras', 'ILUSION', 'PEQUEÑO'),
(11, 110, 'Duplica la fuerza física temporalmente', 'Fuerza de Titán', 'EVOCACION', 'MEDIANO'),
(12, 140, 'Permite ver y tocar seres etéreos', 'Vínculo Etéreo', 'NIGROMANCIA', 'GRANDE'),
(13, 100, 'Reduce el daño recibido', 'Capa Protectora', 'ABJURACION', 'PEQUEÑO'),
(14, 180, 'Otorga visiones del futuro', 'Visión Profética', 'DIVINACION', 'MEDIANO'),
(15, 95, 'Aumenta la persuasión y atracción', 'Aura de Atracción', 'ENCANTAMIENTO', 'PEQUEÑO'),
(16, 130, 'Transforma temporalmente la materia', 'Rayo Cambiante', 'TRANSMUTACION', 'MEDIANO'),
(17, 300, 'Restaura una gran cantidad de salud', 'Curación Mayor', 'CONJURACION', 'GRANDE'),
(18, 70, 'Crea un área cubierta de niebla ilusoria', 'Nieblas Ilusorias', 'ILUSION', 'MEDIANO'),
(19, 90, 'Lanza una bola de fuego a un enemigo', 'Piedra de Fuego', 'EVOCACION', 'PEQUEÑO'),
(20, 250, 'Convoca un espíritu protector', 'Esencia Fantasmal', 'NIGROMANCIA', 'GRANDE'),
(21, 150, 'Proporciona resistencia mágica', 'Armadura Arcana', 'ABJURACION', 'MEDIANO'),
(22, 130, 'Permite ver a grandes distancias', 'Ojo de Águila', 'DIVINACION', 'PEQUEÑO'),
(23, 110, 'Aumenta la capacidad de manipulación', 'Encanto de la Sirena', 'ENCANTAMIENTO', 'PEQUEÑO'),
(24, 200, 'Convierte hierro en oro durante un tiempo limitado', 'Elixir de Metales', 'TRANSMUTACION', 'GRANDE'),
(25, 180, 'Restaura salud lentamente con el tiempo', 'Aura de Vitalidad', 'CONJURACION', 'MEDIANO'),
(26, 85, 'Crea un doble ilusorio', 'Espejo Ilusorio', 'ILUSION', 'PEQUEÑO'),
(27, 120, 'Libera una onda de energía mágica', 'Explosión Controlada', 'EVOCACION', 'MEDIANO'),
(28, 350, 'Permite drenar vida de un objetivo', 'Toque de la Muerte', 'NIGROMANCIA', 'GRANDE'),
(29, 160, 'Bloquea ataques oscuros', 'Barrera de Luz', 'ABJURACION', 'MEDIANO'),
(30, 300, 'Otorga visión del pasado y el futuro', 'Clarividencia Suprema', 'DIVINACION', 'GRANDE'),
(31, 399.9, 'Te vuelve un esqueleto chiquitico', 'Elixir de TragoNublo', 'TRANSMUTACION', 'PEQUEÑO');

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
(1, 2, 1),
(1, 3, 9),
(1, 3, 17),
(1, 1, 25),
(1, 2, 30),
(2, 1, 2),
(2, 1, 27),
(3, 1, 3),
(3, 1, 11),
(3, 1, 17),
(3, 2, 19),
(3, 2, 28),
(4, 2, 4),
(4, 1, 12),
(4, 2, 20),
(4, 2, 25),
(5, 1, 5),
(5, 1, 10),
(5, 1, 13),
(5, 1, 18),
(5, 1, 21),
(5, 2, 29),
(6, 1, 6),
(6, 2, 11),
(6, 1, 14),
(6, 1, 22),
(6, 2, 24),
(6, 1, 30),
(7, 1, 7),
(7, 2, 12),
(7, 1, 15),
(7, 1, 22),
(7, 2, 23),
(8, 1, 5),
(8, 1, 8),
(8, 2, 13),
(8, 1, 16),
(8, 2, 21),
(8, 1, 26),
(9, 1, 4),
(9, 1, 15),
(9, 1, 28),
(10, 1, 1),
(10, 1, 8),
(10, 1, 19),
(10, 1, 25),
(10, 2, 31),
(11, 2, 5),
(11, 2, 17),
(11, 1, 26),
(12, 2, 2),
(12, 1, 9),
(12, 1, 18),
(12, 1, 28),
(13, 1, 4),
(13, 1, 7),
(13, 2, 27),
(14, 1, 3),
(14, 1, 16),
(14, 1, 27),
(14, 1, 29),
(15, 1, 6),
(15, 2, 10),
(15, 1, 20),
(15, 1, 26),
(16, 1, 30),
(17, 2, 7),
(17, 1, 11),
(17, 2, 15),
(17, 2, 16),
(17, 1, 21),
(17, 1, 23),
(17, 1, 31),
(18, 1, 3),
(18, 1, 19),
(19, 1, 8),
(19, 2, 14),
(19, 1, 24),
(20, 1, 1),
(20, 1, 12),
(20, 1, 23),
(20, 1, 29),
(21, 1, 13),
(22, 1, 2),
(22, 1, 9),
(22, 1, 22),
(23, 1, 10),
(23, 1, 14),
(23, 1, 20),
(23, 1, 24),
(23, 1, 31),
(24, 2, 6),
(24, 1, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(255) DEFAULT NULL,
  `contrasena` varbinary(255) DEFAULT NULL,
  `esAdmin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `nombreUsuario`, `contrasena`, `esAdmin`) VALUES
(1, 'admin', 0x21232f297a57a5a743894a0e4a801fc3, 1),
(2, 'usuario', 0xf8032d5cae3de20fcec887f395ec9a6a, 0);

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
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`);

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
  MODIFY `idPocion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
