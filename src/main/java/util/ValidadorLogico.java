package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dto.AlumnoDTO;
import dto.AsistenciaDTO;
import dto.CursadaDTO;
import dto.CursoDTO;
import dto.HorarioDTO;
import dto.NotificacionDTO;
import dto.PeriodoInscripcionDTO;
import dto.PersonaDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.SalaDTO;
import dto.UsuarioDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import modelo.ModeloSala;

public class ValidadorLogico {
	private static ModeloCursos modeloCursos = ModeloCursos.getInstance();
	private static ModeloPersonas modeloPersonas = ModeloPersonas.getInstance();
	private static ModeloSala modeloSala=ModeloSala.getInstance();

	public static boolean esProximoAHoy(Date fecha) {
		return !fecha.before(new Date());
	}

	public static boolean esFechaAnterior(Date inicio, Date fin) {
		return inicio.before(fin);
	}

	public static boolean validarCrearCurso(String string) {
		List<CursoDTO> cursos = modeloCursos.getCursos();

		for (CursoDTO cursoDTO : cursos) {

			if (cursoDTO.getCodigo().equals(string))
				return false;
		}
		return true;

	}

	public static boolean validarComisionRepetidaCursada(String comision) {
		List<CursadaDTO> cursadas = modeloCursos.getCursadas();
		for (CursadaDTO cursadaDTO : cursadas) {
			if (cursadaDTO.getNombre().equals(comision)) {
				return false;
			}
		}
		return true;
	}

	public static boolean validarInscripcionAlumno(AlumnoDTO alumno, CursadaDTO cursada) 
	{
		
		
		// Quedarme con las cursadas que esta inscripto el alumno

		List<CursadaDTO> cursadasQueCursaAlumno = modeloCursos.getCursadasPorAlumno(alumno);

		// Quedarme con los horarios de la cursada
		List<HorarioDTO> horariosCursadaAlumno = new ArrayList<>();
		List<HorarioDTO> horariosCursadaNueva = cursada.getHorarios();

		for (CursadaDTO cursadaDTO : cursadasQueCursaAlumno) {
			horariosCursadaAlumno.addAll(cursadaDTO.getHorarios());
		}

		for (HorarioDTO HorarioDTO : horariosCursadaNueva) {

			for (HorarioDTO horarioCursoAlumnoDTO : horariosCursadaAlumno) {
				if (HorarioDTO.getDiaDeLaSemana().equals(horarioCursoAlumnoDTO.getDiaDeLaSemana())) {
					if (horarioCursoAlumnoDTO.getHoraInicio().equals(HorarioDTO.getHoraInicio()))
						return false;
					if (HorarioDTO.getHoraInicio().equals(horarioCursoAlumnoDTO.getHoraFin())
							|| HorarioDTO.getHoraFin().equals(horarioCursoAlumnoDTO.getHoraInicio())) {
						return false;

					}
					if (HorarioDTO.getHoraInicio().before(horarioCursoAlumnoDTO.getHoraFin())
							&& HorarioDTO.getHoraInicio().after(horarioCursoAlumnoDTO.getHoraInicio()))
						return false;

					if (HorarioDTO.getHoraInicio().before(horarioCursoAlumnoDTO.getHoraInicio())
							&& HorarioDTO.getHoraFin().after(horarioCursoAlumnoDTO.getHoraFin()))
						return false;

				}
			}
		}

		return true;
	}

	public static boolean validarPeriodoInscripcion(Date inicio, Date fin)

	{

		Date fechaHoy = new java.util.Date();

		if (fechaHoy.getDate() == inicio.getDate() && fechaHoy.getMonth() == inicio.getMonth()
				&& fechaHoy.getYear() == inicio.getYear()) {
			return true;
		}

		if (inicio.before(fechaHoy))
			return false;
		return true;

	}

	public static boolean validarEditarCurso(CursoDTO cursoEditado, String nombre) {
		List<CursoDTO> cursos = modeloCursos.getCursos();

		for (CursoDTO cursoDTO : cursos) {
			if (!cursoEditado.equals(cursoDTO)) {
				if (cursoDTO.getCodigo().equals(nombre))
					return false;
			}
		}

		return true;
	}

	public static boolean validarInscripcionAbierta(CursadaDTO cursada) {
		List<PeriodoInscripcionDTO> periodoInscripcion = modeloCursos.getPeriodoInscripcion(cursada);

		Date fechaHoy = new java.util.Date();

		for (PeriodoInscripcionDTO periodoInscripcionDTO : periodoInscripcion) {

			if (fechaHoy.after(periodoInscripcionDTO.getInicio()) && fechaHoy.before(periodoInscripcionDTO.getFin())) {

				return true;
			}
		}

		return false;
	}

	public static boolean validarComisionVaciaCursada(String comision) {
		if (comision == null || comision.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean validarNombreUsuario(String usuario) 
	{
		
		List<UsuarioDTO> usuarios = modeloPersonas.getUsuarios();
		for (UsuarioDTO user : usuarios) {
			if (user.getEmail().equals(usuario)) 
			{
				return false;
			}
		}
		return true;
	}

	public static boolean legajoValido(String legajo) {
		List<AlumnoDTO> alumnos = modeloPersonas.getAlumnos();
		for (AlumnoDTO alumno : alumnos) {
			if (alumno.getLegajo().equals(legajo)) {
				return false;
			}
		}
		return true;
	}


	public static boolean dniValido(String dni) 
	{
		for (PersonaDTO persona : modeloPersonas.getPersonas())
		{
			if(persona.getDni()!=null && persona.getDni().equals(dni))
			{
				return false;
			}
		}
		return true;
	}

	public static boolean telefonoValido(String telefono) 
	{
		List<UsuarioDTO> usuarios = modeloPersonas.getUsuarios();

		for (UsuarioDTO usuarioDTO : usuarios) {

			if (usuarioDTO.getPersona().getTelefono().equals(telefono)) 
			{

				return false;
			}

		}
		return true;

	}

	public static boolean emailValido(String email) {
		
		List<UsuarioDTO> usuarios = modeloPersonas.getUsuarios();

		for (UsuarioDTO usuarioDTO : usuarios) {

			if (usuarioDTO.getPersona().getEmail().equalsIgnoreCase(email)) 
			{

				return false;
			}

		}
		return true;

	}

	public static List<PlanillaDeAsistenciasDTO> getPlanillas(CursadaDTO cursadaRecibida) {

		CursadaDTO cursada = cursadaRecibida;

		List<HorarioDTO> horarios = cursada.getHorarios();

		Date diaInicio = cursada.getInicio();

		List<PlanillaDeAsistenciasDTO> planillasAsistencia = new ArrayList<>();

		Calendar calendarioInicio = Calendar.getInstance();
		calendarioInicio.setTime(diaInicio);

		Calendar calendarioFinal = Calendar.getInstance();
		calendarioFinal.setTime(cursada.getFin());

		while (calendarioInicio.before(calendarioFinal) || calendarioInicio.equals(calendarioFinal)) {
			int diaSemana = calendarioInicio.get(Calendar.DAY_OF_WEEK);

			for (HorarioDTO horario : horarios) {

				switch (diaSemana) {

				case 1:
					if (horario.getDiaDeLaSemana().getId().equals(7))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 2:

					if (horario.getDiaDeLaSemana().getId().equals(1))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 3:

					if (horario.getDiaDeLaSemana().getId().equals(2))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 4:

					if (horario.getDiaDeLaSemana().getId().equals(3))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 5:

					if (horario.getDiaDeLaSemana().getId().equals(4))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 6:

					if (horario.getDiaDeLaSemana().getId().equals(5))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				case 7:

					if (horario.getDiaDeLaSemana().getId().equals(6))

						llenarPlanilla(planillasAsistencia, calendarioInicio, horario, cursada);
					break;

				}

			}
			calendarioInicio.add(Calendar.DAY_OF_MONTH, 1);

		}

		return planillasAsistencia;
	}

	private static void llenarPlanilla(List<PlanillaDeAsistenciasDTO> planillasAsistencia, Calendar diaCalendario,
			HorarioDTO horario, CursadaDTO cursada) {

		PlanillaDeAsistenciasDTO planilla = new PlanillaDeAsistenciasDTO();
		planilla.setDisponibleEnSistema(true);

		planilla.setFecha(diaCalendario.getTime());
		planilla.setAsistencias(new ArrayList<AsistenciaDTO>());
		planilla.setHorario(horario);

		planillasAsistencia.add(planilla);

	}

	public static boolean validarMailPersona(String email) 
	{
		List<PersonaDTO> personas = modeloPersonas.getPersonas();
		for (PersonaDTO personaDTO : personas) 
		{
			if (personaDTO.getEmail().equals(email))
				return false;
		}
		return true;
	}

	public static boolean validarTelefonoPersona(String telefono) 
	{
		List<PersonaDTO> personas = modeloPersonas.getPersonas();
		for (PersonaDTO personaDTO : personas) 
		{
			if (personaDTO.getTelefono().equals(telefono))
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean fechaValidaNotificacion(Date fechaHoy, NotificacionDTO notificacionDTO) {
		return (notificacionDTO.getFecha().getYear() <= fechaHoy.getYear()
				&& notificacionDTO.getFecha().getMonth() <= fechaHoy.getMonth()
				&& notificacionDTO.getFecha().getDate() < fechaHoy.getDate())
				|| (notificacionDTO.getFecha().getYear() == fechaHoy.getYear()
						&& notificacionDTO.getFecha().getMonth() == fechaHoy.getMonth()
						&& notificacionDTO.getFecha().getDate() == fechaHoy.getDate()
						&& fechaHoy.getHours() >= notificacionDTO.getHora().getHours()
						&& fechaHoy.getMinutes()>=notificacionDTO.getHora().getMinutes());
	}

	public static boolean validarCodigoSala(String text) 
	{
		for(SalaDTO e:modeloSala.getSalas()) {
			if(e.getCodigo().equals(text)) {
				return false;
			}
		}	
		return true;
	}

	public static boolean validarNombreCurso(String nombre) {
		for(CursoDTO c:modeloCursos.getCursos()) {
			if(c.getNombre().equals(nombre)) {
				return false;
			}
		}
		return true;
	}

}
