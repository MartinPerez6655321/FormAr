DROP DATABASE IF EXISTS formar_grupo2;
CREATE DATABASE formar_grupo2;
USE formar_grupo2;

CREATE TABLE `persona_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`apellido` VARCHAR(1024), 
	`telefono` VARCHAR(1024), 
	`email` VARCHAR(1024), 
	`dni` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `interesado_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `diadelasemana_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `horario_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`horaInicio` TIME, 
	`horaFin` TIME, 
	`diaDeLaSemana` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`diaDeLaSemana`) REFERENCES diadelasemana_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `administrador_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `personaladministrativo_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadoevento_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `alumno_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fechaDeCreacion` DATETIME, 
	`legajo` VARCHAR(1024), 
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `inscripcionalumno_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fecha` DATETIME, 
	`alumno` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`alumno`) REFERENCES alumno_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `instructor_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);


CREATE TABLE `instructorHasDisponibilidades`
(
	`idinstructor` INT(11),
	`idHorario` INT(11),
	FOREIGN KEY (`idinstructor`) REFERENCES `instructor_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idHorario`) REFERENCES `horario_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idinstructor`, `idHorario`)
);

CREATE TABLE `supervisor_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`persona` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `usuario_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(1024), 
	`password` VARCHAR(1024), 
	`persona` INT(64), 
	`administrador` BOOLEAN, 
	`supervisor` BOOLEAN, 
	`administrativo` BOOLEAN, 
	`instructor` BOOLEAN, 
	`alumno` BOOLEAN, 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`persona`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadoderecado_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `recado_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`mensaje` VARCHAR(1024), 
	`emisor` INT(64), 
	`receptor` INT(64), 
	`asunto` VARCHAR(1024), 
	`fecha` DATETIME, 
	`hora` TIME, 
	`estado` INT(64), 
	`disponibilidadEmisor` BOOLEAN, 
	`disponibilidadReceptor` BOOLEAN, 
	FOREIGN KEY (`emisor`) REFERENCES usuario_table (id),
	FOREIGN KEY (`receptor`) REFERENCES usuario_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoderecado_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadodeperiododeinscripcion_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `periodoinscripcion_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`inicio` DATETIME, 
	`fin` DATETIME, 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`estado`) REFERENCES estadodeperiododeinscripcion_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `sala_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`alias` VARCHAR(1024), 
	`codigo` VARCHAR(1024), 
	`capacidad` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `curso_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`precio` INT(64), 
	`codigo` VARCHAR(1024), 
	`descripcion` VARCHAR(1024), 
	`horas` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `cuota_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fechaLimite` DATETIME, 
	`monto` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadocursada_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadoasistencia_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `asistencia_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`alumno` INT(64), 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`alumno`) REFERENCES alumno_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoasistencia_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `estadoevaluacion_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `parcial_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`alumno` INT(64), 
	`nota` INT(64), 
	`observaciones` VARCHAR(1024), 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`alumno`) REFERENCES alumno_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoevaluacion_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `planilladeasistencias_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fecha` DATETIME, 
	`horario` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`horario`) REFERENCES horario_table (id),
	PRIMARY KEY (`id`)
);


CREATE TABLE `planilladeasistenciasHasAsistencias`
(
	`idplanilladeasistencias` INT(11),
	`idAsistencia` INT(11),
	FOREIGN KEY (`idplanilladeasistencias`) REFERENCES `planilladeasistencias_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idAsistencia`) REFERENCES `asistencia_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idplanilladeasistencias`, `idAsistencia`)
);

CREATE TABLE `planilladeparciales_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fecha` DATETIME, 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);


CREATE TABLE `planilladeparcialesHasParciales`
(
	`idplanilladeparciales` INT(11),
	`idParcial` INT(11),
	FOREIGN KEY (`idplanilladeparciales`) REFERENCES `planilladeparciales_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idParcial`) REFERENCES `parcial_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idplanilladeparciales`, `idParcial`)
);

CREATE TABLE `cursada_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`vacantes` INT(64), 
	`vacantesMinimas` INT(64), 
	`inicio` DATETIME, 
	`fin` DATETIME, 
	`montoTotal` INT(64), 
	`sala` INT(64), 
	`curso` INT(64), 
	`programa` LONGTEXT, 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`sala`) REFERENCES sala_table (id),
	FOREIGN KEY (`curso`) REFERENCES curso_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadocursada_table (id),
	PRIMARY KEY (`id`)
);


CREATE TABLE `cursadaHasHorarios`
(
	`idcursada` INT(11),
	`idHorario` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idHorario`) REFERENCES `horario_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idHorario`)
);


CREATE TABLE `cursadaHasPeriodosDeInscripcion`
(
	`idcursada` INT(11),
	`idPeriodoinscripcion` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idPeriodoinscripcion`) REFERENCES `periodoinscripcion_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idPeriodoinscripcion`)
);


CREATE TABLE `cursadaHasInstructores`
(
	`idcursada` INT(11),
	`idInstructor` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idInstructor`) REFERENCES `instructor_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idInstructor`)
);


CREATE TABLE `cursadaHasAsistencias`
(
	`idcursada` INT(11),
	`idPlanilladeasistencias` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idPlanilladeasistencias`) REFERENCES `planilladeasistencias_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idPlanilladeasistencias`)
);


CREATE TABLE `cursadaHasParciales`
(
	`idcursada` INT(11),
	`idPlanilladeparciales` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idPlanilladeparciales`) REFERENCES `planilladeparciales_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idPlanilladeparciales`)
);


CREATE TABLE `cursadaHasInscripciones`
(
	`idcursada` INT(11),
	`idInscripcionalumno` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idInscripcionalumno`) REFERENCES `inscripcionalumno_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idInscripcionalumno`)
);


CREATE TABLE `cursadaHasCuotas`
(
	`idcursada` INT(11),
	`idCuota` INT(11),
	FOREIGN KEY (`idcursada`) REFERENCES `cursada_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idCuota`) REFERENCES `cuota_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursada`, `idCuota`)
);

CREATE TABLE `eventoinasistencia_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fechaDeInasistencia` DATETIME, 
	`horaDeCumplimiento` TIME, 
	`alumno` INT(64), 
	`cursada` INT(64), 
	`administrativoAsignado` INT(64), 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`alumno`) REFERENCES alumno_table (id),
	FOREIGN KEY (`cursada`) REFERENCES cursada_table (id),
	FOREIGN KEY (`administrativoAsignado`) REFERENCES personaladministrativo_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoevento_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `eventosupervisor_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`descripcion` VARCHAR(1024), 
	`fechaDeCumplimiento` DATETIME, 
	`horaDeCumplimiento` TIME, 
	`administrativoAsignado` INT(64), 
	`supervisor` INT(64), 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`administrativoAsignado`) REFERENCES personaladministrativo_table (id),
	FOREIGN KEY (`supervisor`) REFERENCES supervisor_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoevento_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `eventointeresado_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`curso` INT(64), 
	`descripcion` VARCHAR(1024), 
	`fechaDeLlamado` DATETIME, 
	`horaDeLlamado` TIME, 
	`fechaDeCumplimiento` DATETIME, 
	`horaDeCumplimiento` TIME, 
	`personalAdministrativo` INT(64), 
	`interesado` INT(64), 
	`administrativoAsignado` INT(64), 
	`supervisor` INT(64), 
	`estado` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`curso`) REFERENCES curso_table (id),
	FOREIGN KEY (`personalAdministrativo`) REFERENCES personaladministrativo_table (id),
	FOREIGN KEY (`interesado`) REFERENCES interesado_table (id),
	FOREIGN KEY (`administrativoAsignado`) REFERENCES personaladministrativo_table (id),
	FOREIGN KEY (`supervisor`) REFERENCES supervisor_table (id),
	FOREIGN KEY (`estado`) REFERENCES estadoevento_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `notificacion_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`titulo` VARCHAR(1024), 
	`descripcion` VARCHAR(1024), 
	`eventoInasistencia` INT(64), 
	`eventoSupervisor` INT(64), 
	`eventoInteresado` INT(64), 
	`usuario` INT(64), 
	`cursada` INT(64), 
	`recado` INT(64), 
	`fecha` DATETIME, 
	`hora` TIME, 
	`codigoVista` VARCHAR(64), 
	`codigoTab` VARCHAR(64), 
	`visto` BOOLEAN, 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`eventoInasistencia`) REFERENCES eventoinasistencia_table (id),
	FOREIGN KEY (`eventoSupervisor`) REFERENCES eventosupervisor_table (id),
	FOREIGN KEY (`eventoInteresado`) REFERENCES eventointeresado_table (id),
	FOREIGN KEY (`usuario`) REFERENCES usuario_table (id),
	FOREIGN KEY (`cursada`) REFERENCES cursada_table (id),
	FOREIGN KEY (`recado`) REFERENCES recado_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `pago_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fecha` DATETIME, 
	`alumno` INT(64), 
	`cuota` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`alumno`) REFERENCES alumno_table (id),
	FOREIGN KEY (`cuota`) REFERENCES cuota_table (id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `pagoempresa_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`fechaLimite` DATETIME, 
	`realizado` DATETIME, 
	`monto` INT(64), 
	`empresa` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	PRIMARY KEY (`id`)
);

CREATE TABLE `cursadaempresa_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`cursada` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`cursada`) REFERENCES cursada_table (id),
	PRIMARY KEY (`id`)
);


CREATE TABLE `cursadaempresaHasPagos`
(
	`idcursadaempresa` INT(11),
	`idPagoempresa` INT(11),
	FOREIGN KEY (`idcursadaempresa`) REFERENCES `cursadaempresa_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idPagoempresa`) REFERENCES `pagoempresa_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idcursadaempresa`, `idPagoempresa`)
);

CREATE TABLE `empresa_table`
(
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(1024), 
	`contacto` INT(64), 
	`disponibleEnSistema` BOOLEAN, 
	FOREIGN KEY (`contacto`) REFERENCES persona_table (id),
	PRIMARY KEY (`id`)
);


CREATE TABLE `empresaHasAlumnos`
(
	`idempresa` INT(11),
	`idAlumno` INT(11),
	FOREIGN KEY (`idempresa`) REFERENCES `empresa_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idAlumno`) REFERENCES `alumno_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idempresa`, `idAlumno`)
);


CREATE TABLE `empresaHasCursadas`
(
	`idempresa` INT(11),
	`idCursadaempresa` INT(11),
	FOREIGN KEY (`idempresa`) REFERENCES `empresa_table` (`id`) ON DELETE CASCADE,
	FOREIGN KEY (`idCursadaempresa`) REFERENCES `cursadaempresa_table` (`id`) ON DELETE NO ACTION,
	PRIMARY KEY (`idempresa`, `idCursadaempresa`)
);

-- Inserts
/*----------------------------------------------------------------------*/
use formar_grupo2;

INSERT INTO curso_table (nombre, descripcion, precio,codigo, horas, disponibleEnSistema)
VALUES 
('Matemática I', 'La primer materia de las que se llaman matemática.', 2500,1, 50,  true),
('Quimica', 'Para aprender a ser como Jesse Pinkman', 500,2, 50,  true),
('física', 'No conozco la diferencia entre física y física elemental', 2500,3, 50,  true),
('física Elemental', 'No conozco la diferencia entre física y física elemental', 500,4, 50,  true),
('Quimica Organica', 'Para aprender a ser como Jesse Pinkman, pero con cosas que se pudren', 2500,5, 50,  true),
('Programacion III', 'Lo más importante de esta materia son las anécdotas de Marenco.', 500,6, 50,  true),
('Organizacion del Cumputador', 'Etc', 2500,7, 50,  true),
('Matematica Discreta', 'Etc', 500,8, 50,  true),
('Logica y teoria de numeros', 'Etc', 2500,9, 50,  true),
('Especificacion de Software', 'Etc', 500,10, 50,  true),
('Electricidad Automotriz', 'Etc', 2500,11, 50,  true),
('Mecanica de fluidos', 'Etc', 500, 50,12,  true),
('Microcontroladores', 'Etc', 2500, 50,13,  true),
('Automatizacion y control I', 'Etc', 500,14, 50,  true),
('Automatizacion y control II', 'Etc', 2500,15, 50,  true),
('Sistemas de control', 'Etc', 500, 50,16,  true),
('Sistemas operativos y redes', 'Etc', 2500,17, 50,  true),
('Introduccion a la matematica', 'Etc', 500,18, 50,  true),
('PSEC', 'Etc', 2500,19, 50,  true),
('Ingles lectocompresion I', 'Etc', 500,20, 50,  true),
('Ingenieria de Software', 'Etc', 2500,21, 50,  true),
('Ingles lectocompresion II', 'Etc', 500,22, 50,  true),
('Introduccion a la programacion', 'Etc', 2500,23, 50,  true),
('Teoria de la computacion', 'Etc', 500,24, 50,  true),
('Probabilidad y estadistica', 'Etc', 2500,25, 50,  true),
('Algebra lineal', 'Etc', 500,26, 50,  true),
('Base de datos I', 'Etc', 2500,27, 50,  true),
('Informatica y sociedad', 'Etc', 500,28, 50,  true),
('Gestion de proyectos', 'Etc', 2500,29, 50,  true),
('Base de datos II', 'Etc', 500, 50,30,  true),
('Ingenieria de software II', 'Etc', 2500,31, 50,  true),
('Organizacion del computador II', 'Etc', 500,32, 50,  true);

INSERT INTO `persona_table` (dni, nombre, apellido, telefono, email, disponibleEnSistema)
VALUES 
('31061612', 'Javier', 'Ramos', '165416461', 'mail1@mail.com', true),
('31645344', 'Pepe', 'Perez', '2435453486', 'mail2@mail.com', true),
('34564328', 'Lalo', 'Lambda', '3453786413', 'mail3@mail.com', true),
('37869569', 'Rodrigo', 'Rodriguez', '445678343', 'mail4@mail.com', true),
('37693428', 'Armando Esteban', 'Quito', '589673140', 'mail5@mail.com', true),
('35442375', 'Facundo', 'Salice', '6456456431', 'mail6@mail.com', true),
('34645378', 'Fabian', 'Juarez', '788898889', 'mail7@mail.com', true),
('35369788', 'Raul', 'Smith', '888898889', 'mail8@mail.com', true),
('45314237', 'Maria', 'Moore', '988898889', 'mail9@mail.com', true),
('33437645', 'Pablo', 'Mason', '88811889', 'mail10@mail.com', true),
('39534574', 'Juan', 'Benitez', '81398889', 'mail11@mail.com', true),
('33275695', 'Martin', 'Lopez', '88891849', 'mail12@mail.com', true),
('34367997', 'Natalia', 'Faust', '88858389', 'mail13@mail.com', true),
('38756340', 'Francisco', 'Mendoza', '86898089', 'mail14@mail.com', true);

INSERT INTO instructor_table (disponibleEnSistema, persona)
VALUES 
(true, 1),
(true, 3),
(true, 6);

INSERT INTO personaladministrativo_table (disponibleEnSistema, persona)
VALUES
(true, 1),
(true, 3);

INSERT INTO `supervisor_table` (disponibleEnSistema, persona)
VALUES
(true, 1),
(true, 2);

INSERT INTO `administrador_table` (disponibleEnSistema, persona)
VALUES
(true, 1),
(true, 2);

INSERT INTO alumno_table (legajo, fechaDeCreacion, disponibleEnSistema, persona)
VALUES 
('31061612/2014','2014-11-06 00:00:00', true, 1),
('31645344/2012','2012-10-22 00:00:00', true, 2),
('34564328/2015','2015-12-20 00:00:00', true, 3),
('35442375/2016','2016-06-15 00:00:00', true, 6);

INSERT INTO usuario_table (email, password, persona, administrador, supervisor, administrativo, instructor, alumno, disponibleEnSistema)
VALUES 
('root','root', 1, true, true, true, true, true, true),
('inst1','inst1', 2, true, true, false, false, true, true),
('inst2','inst2', 3, false, false, true, true, true, true),
('inst3','inst3', 6, false, false, false, true, true, true);


INSERT INTO `curso_table` (`nombre`, `precio`,`codigo`, `horas`, `disponibleEnSistema`)
VALUES 
('Progra3', 550,333, 25, TRUE);

INSERT INTO `sala_table` (`alias`, `codigo`, `capacidad`, `disponibleEnSistema`)
VALUES 
('Sala de labo', '7118', 20, TRUE),
('Alias de sala', '4951', 20, TRUE),
('Oficina de Pepe', '0541', 50, TRUE),
('Oficina de Raul', '9841', 87, TRUE),
('Alias', '1111', 40, TRUE);

INSERT INTO `estadocursada_table` (`nombre`, `disponibleEnSistema`)
VALUES
('En curso', TRUE),
('Aún sin cursar', TRUE),
('Cancelado', TRUE),
('Finalizado', TRUE),
('Pendiente de aprobacion', TRUE);

INSERT INTO `estadodeperiododeinscripcion_table` (`nombre`, `disponibleEnSistema`)
VALUES
('Abierto', TRUE),
('Cerrado', TRUE),
('Cancelado', TRUE),
('Aún no abierto', TRUE);



INSERT INTO `diadelasemana_table` (`nombre`, `disponibleEnSistema`)
VALUES
('Lunes', TRUE),
('Martes', TRUE),
('Miercoles', TRUE),
('Jueves', TRUE),
('Viernes', TRUE),
('Sabado', TRUE),
('Domingo', TRUE);


#Usuario


INSERT INTO `cuota_table` (`fechaLimite`, `monto`, `disponibleEnSistema`) 
VALUES 
('2018-10-20 00:00:00', 110, TRUE),
('2018-11-20 00:00:00', 110, TRUE),
('2018-12-20 00:00:00', 110, TRUE),
('2019-01-20 00:00:00', 110, TRUE),
('2019-02-20 00:00:00', 110, TRUE);



INSERT INTO `estadoevento_table` (`nombre`, `disponibleEnSistema`)
VALUES
('Completado', TRUE),
('Pendiente', TRUE),
('Eliminado', TRUE);


INSERT INTO `estadoasistencia_table` (`nombre`, `disponibleEnSistema`)
VALUES
('-', TRUE),
('Presente', TRUE),
('Tarde', TRUE),
('Ausente', TRUE);

INSERT INTO `estadoevaluacion_table` (`nombre`, `disponibleEnSistema`)
VALUES
('-', TRUE),
('Aprobado', TRUE),
('Desaprobado', TRUE),
('Ausente', TRUE);

INSERT INTO `estadoderecado_table` (`nombre`, `disponibleEnSistema`)
VALUES
('Visto', TRUE),
('No Visto', TRUE);

INSERT INTO `recado_table` (`asunto`,`mensaje`,`fecha`,`hora`,`emisor`,`receptor`,`estado`,`disponibilidadEmisor`, `disponibilidadReceptor`)
VALUES
('Asunto 1','Mensaje 1','2018-10-20 00:00:00','21:20',2,1,2, TRUE, TRUE),
('Asunto 2','Mensaje 2','2018-06-25 00:00:00','15:25',2,1,2, TRUE, TRUE),
('Asunto 3','Mensaje 3','2018-11-20 00:00:00','20:20',2,1,2, TRUE, TRUE),
('Asunto 4','Mensaje 4','2018-06-25 00:00:00','15:25',2,1,1, TRUE, TRUE),
('Asunto 5','Mensaje 5','2018-06-25 00:00:00','15:25',1,2,1, TRUE, TRUE),
('Asunto 10','Mensaje 10','2018-06-25 00:00:00','15:25',2,1,1, FALSE, TRUE);

