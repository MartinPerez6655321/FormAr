package modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import dto.AlumnoDTO;
import dto.AsistenciaDTO;
import dto.CuotaDTO;
import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.CursoDTO;
import dto.DiaDeLaSemanaDTO;
import dto.EmpresaDTO;
import dto.EstadoAsistenciaDTO;
import dto.EstadoCursadaDTO;
import dto.EstadoDePeriodoDeInscripcionDTO;
import dto.EstadoEvaluacionDTO;
import dto.EventoInasistenciaDTO;
import dto.EventoInteresadoDTO;
import dto.HorarioDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import dto.NotificacionDTO;
import dto.PagoDTO;
import dto.PagoEmpresaDTO;
import dto.ParcialDTO;
import dto.PeriodoInscripcionDTO;
import dto.PersonaDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.PlanillaDeParcialesDTO;
import dto.SalaDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.AsistenciaDAOMYSQL;
import persistencia.dao.mysql.CuotaDAOMYSQL;
import persistencia.dao.mysql.CursadaDAOMYSQL;
import persistencia.dao.mysql.CursadaEmpresaDAOMYSQL;
import persistencia.dao.mysql.CursoDAOMYSQL;
import persistencia.dao.mysql.DiaDeLaSemanaDAOMYSQL;
import persistencia.dao.mysql.EmpresaDAOMYSQL;
import persistencia.dao.mysql.EstadoAsistenciaDAOMYSQL;
import persistencia.dao.mysql.EstadoCursadaDAOMYSQL;
import persistencia.dao.mysql.EstadoDePeriodoDeInscripcionDAOMYSQL;
import persistencia.dao.mysql.EstadoEvaluacionDAOMYSQL;
import persistencia.dao.mysql.HorarioDAOMYSQL;
import persistencia.dao.mysql.InscripcionAlumnoDAOMYSQL;
import persistencia.dao.mysql.PagoDAOMYSQL;
import persistencia.dao.mysql.PagoEmpresaDAOMYSQL;
import persistencia.dao.mysql.ParcialDAOMYSQL;
import persistencia.dao.mysql.PeriodoInscripcionDAOMYSQL;
import persistencia.dao.mysql.PlanillaDeAsistenciasDAOMYSQL;
import persistencia.dao.mysql.PlanillaDeParcialesDAOMYSQL;

public class ModeloCursos extends AbstractObservable {
	private CursoDAOMYSQL cursoDao;
	private CursadaDAOMYSQL cursadaDao;
	private InscripcionAlumnoDAOMYSQL inscripcionAlumnoDao;
	private EmpresaDAOMYSQL empresaDao;
	private EstadoDePeriodoDeInscripcionDAOMYSQL estadodeperiododeinscripcionDao;
	private EstadoCursadaDAOMYSQL estadocursadaDao;
	private PagoDAOMYSQL pagoDao;
	private DiaDeLaSemanaDAOMYSQL diasDao;
	private HorarioDAOMYSQL horariosDao;
	private EstadoAsistenciaDAOMYSQL estadoAsistenciaDao;
	private PlanillaDeAsistenciasDAOMYSQL planillaDeAsistenciasDao;
	private EstadoEvaluacionDAOMYSQL estadoEvaluacionDao;
	private PlanillaDeParcialesDAOMYSQL planillaDeParcialesDao;
	private AsistenciaDAOMYSQL asistenciasDao;
	private ParcialDAOMYSQL parcialesDao;
	private CuotaDAOMYSQL cuotasDao;
	private CursadaEmpresaDAOMYSQL cursadaEmpresaDao;
	private PagoEmpresaDAOMYSQL pagosEmpresaDao;
	private PeriodoInscripcionDAOMYSQL periodoDeInscripcionDao;
	private List<CursoDTO> cursos;
	private List<CursadaDTO> cursadas;
	private List<EmpresaDTO> empresas;
	private List<InscripcionAlumnoDTO> inscripcionesAlumnos;
	private List<EstadoDePeriodoDeInscripcionDTO> estadosdeperiododeinscripcion;
	private List<EstadoCursadaDTO> estadoscursada;
	private List<PagoDTO> pagos;
	private List<DiaDeLaSemanaDTO> dias;
	private List<HorarioDTO> horarios;
	private List<EstadoAsistenciaDTO> estadosAsistencia;
	private List<PlanillaDeAsistenciasDTO> planillaDeAsistencias;
	private List<EstadoEvaluacionDTO> estadoEvaluacion;
	private List<AsistenciaDTO> asistencias;
	private List<ParcialDTO> parciales;
	private List<CuotaDTO> cuotas;
	private List<CursadaEmpresaDTO> cursadaEmpresa;
	private List<PagoEmpresaDTO> pagoEmpresas;
	private List<PeriodoInscripcionDTO> periodosDeInscripcion;
	private List<PlanillaDeParcialesDTO> planillaDeParciales;

	private static ModeloCursos instance;

	public static ModeloCursos getInstance() {
		if (instance == null)
			instance = new ModeloCursos();
		return instance;
	}

	private ModeloCursos() {
		cursoDao = CursoDAOMYSQL.getInstance();
		cursadaDao = CursadaDAOMYSQL.getInstance();
		inscripcionAlumnoDao = InscripcionAlumnoDAOMYSQL.getInstance();

		empresaDao = EmpresaDAOMYSQL.getInstance();
		estadodeperiododeinscripcionDao = EstadoDePeriodoDeInscripcionDAOMYSQL.getInstance();
		estadocursadaDao = EstadoCursadaDAOMYSQL.getInstance();
		pagoDao = PagoDAOMYSQL.getInstance();
		diasDao = DiaDeLaSemanaDAOMYSQL.getInstance();
		horariosDao = HorarioDAOMYSQL.getInstance();

		estadoAsistenciaDao = EstadoAsistenciaDAOMYSQL.getInstance();
		planillaDeAsistenciasDao = PlanillaDeAsistenciasDAOMYSQL.getInstance();
		estadoEvaluacionDao = EstadoEvaluacionDAOMYSQL.getInstance();
		planillaDeParcialesDao = PlanillaDeParcialesDAOMYSQL.getInstance();

		asistenciasDao = AsistenciaDAOMYSQL.getInstance();
		parcialesDao = ParcialDAOMYSQL.getInstance();
		cuotasDao = CuotaDAOMYSQL.getInstance();
		cursadaEmpresaDao = CursadaEmpresaDAOMYSQL.getInstance();
		pagosEmpresaDao = PagoEmpresaDAOMYSQL.getInstance();
		periodoDeInscripcionDao = PeriodoInscripcionDAOMYSQL.getInstance();

		cursos = cursoDao.readAll();
		cursadas = cursadaDao.readAll();
		inscripcionesAlumnos = inscripcionAlumnoDao.readAll();
		estadosdeperiododeinscripcion = estadodeperiododeinscripcionDao.readAll();
		estadoscursada = estadocursadaDao.readAll();
		pagos = pagoDao.readAll();
		dias = diasDao.readAll();
		horarios = horariosDao.readAll();

		estadosAsistencia = estadoAsistenciaDao.readAll();
		planillaDeAsistencias = planillaDeAsistenciasDao.readAll();
		planillaDeParciales = this.planillaDeParcialesDao.readAll();
		estadoEvaluacion = estadoEvaluacionDao.readAll();

		asistencias = asistenciasDao.readAll();
		parciales = parcialesDao.readAll();
		cuotas = cuotasDao.readAll();
		this.pagoEmpresas = this.pagosEmpresaDao.readAll();
		this.periodosDeInscripcion = this.periodoDeInscripcionDao.readAll();
	}

	public List<PlanillaDeParcialesDTO> getPlanillaDeParciales() {
		return this.planillaDeParciales;
	}

	public List<CursadaEmpresaDTO> getCursadasEmpresas() {
		if(cursadaEmpresa == null)
			cursadaEmpresa = this.cursadaEmpresaDao.readAll();
		return cursadaEmpresa;

	}

	public List<CuotaDTO> getCuotas() {
		return cuotas;
	}

	public List<PagoEmpresaDTO> getPagosEmpresas() {
		return this.pagoEmpresas;
	}

	public List<InscripcionAlumnoDTO> getInscripcionesAlumnos() {
		return inscripcionesAlumnos;
	}

	public List<EstadoEvaluacionDTO> getEstadoEvaluacion() {
		return estadoEvaluacion;
	}

	public List<PlanillaDeAsistenciasDTO> getPlanillasDeAsistencia() {
		return planillaDeAsistencias;
	}

	public List<EstadoAsistenciaDTO> getEstadoAsistencia() {
		return estadosAsistencia;
	}

	public List<CursoDTO> getCursos() {
		return cursos;
	}

	public List<CursadaDTO> getCursadas() {
		return cursadas;
	}

	public List<PeriodoInscripcionDTO> getPeriodosDeInscripcion() {
		return this.periodosDeInscripcion;
	}

	public List<EmpresaDTO> getEmpresas() {
		if(empresas == null)
			empresas = empresaDao.readAll();
		return empresas;
	}

	public List<PersonaDTO> getAlumnosPorCursada(CursadaDTO cursada) {

		List<PersonaDTO> personas = new ArrayList<>();

		List<InscripcionAlumnoDTO> inscripciones = cursada.getInscripciones();

		for (InscripcionAlumnoDTO inscripcionCursada : cursada.getInscripciones()) {
			for (InscripcionAlumnoDTO inscripcion : inscripciones) {
				if (inscripcion.equals(inscripcionCursada)) {
					personas.add(inscripcion.getAlumno().getPersona());
					break;
				}
			}
		}

		return personas;

	}

	public List<PeriodoInscripcionDTO> getPeriodoInscripcion(CursadaDTO cursada) {
		return cursada.getPeriodosDeInscripcion();
	}

	public List<CursadaDTO> getCursadasPorInstructorYFechas(InstructorDTO instructor, Calendar fechaFiltroA,
			Calendar fechaFiltroB) {
		return getCursadasPorInstructorYFechas(instructor, fechaFiltroA.getTime(), fechaFiltroB.getTime());
	}

	public List<CursadaDTO> getCursadasPorInstructorYFechas(InstructorDTO instructor, Date fechaFiltroA,
			Date fechaFiltroB) {
		List<CursadaDTO> ret = new LinkedList<>();

		for (CursadaDTO cursada : getCursadasPorInstructor(instructor)) {
			if (!cursada.getInicio().after(fechaFiltroB) && !cursada.getFin().before(fechaFiltroA)) {
				ret.add(cursada);
			}
		}

		return ret;
	}

	public List<CursadaDTO> getCursadasPorInstructor(InstructorDTO instructor) {
		List<CursadaDTO> ret = new LinkedList<>();

		for (CursadaDTO cursada : getCursadas()) {
			if (cursada.getInstructores().contains(instructor))
				ret.add(cursada);
		}

		return ret;
	}

	public List<SalaDTO> salasDisponiblesParaCursada(CursadaDTO cursada) {
		List<SalaDTO> ret = new LinkedList<>();

		for (SalaDTO sala : ModeloSala.getInstance().getSalas()) {
			if (sala.getDisponibleEnSistema()) {
				boolean disponible = true;
				for (HorarioDTO horario : cursada.getHorarios()) {
					disponible = disponible && salaDisponibleEnHorario(cursada, sala, horario);
				}
				if (disponible)
					ret.add(sala);

			}

		}

		return ret;
	}

	private boolean salaDisponibleEnHorario(CursadaDTO cursada, SalaDTO sala, HorarioDTO horario) {
		for (CursadaDTO cursadaAsignada : getCursadasPorSala(sala)) {
			for (HorarioDTO horarioOcupada : cursadaAsignada.getHorarios()) {
				if (intersecan(cursada, cursadaAsignada) && intersecan(horario, horarioOcupada)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Devuelve true si los horarios tienen al menos un momento en común y coinciden
	 * en algún día de la semana.
	 */
	private static boolean intersecan(HorarioDTO horario, HorarioDTO horarioOcupada) {
		return (horarioOcupada.getDiaDeLaSemana().equals(horario.getDiaDeLaSemana())
				&& horarioOcupada.getHoraInicio().before(horario.getHoraFin())
				&& horario.getHoraInicio().before(horarioOcupada.getHoraFin()));
	}

	/**
	 * Devuelve true si las cursadas se dictan al mismo tiempo aunque sea por una
	 * parte del período de dictado.
	 */
	private static boolean intersecan(CursadaDTO cursadaA, CursadaDTO cursadaB) {
		return (cursadaA.getInicio().before(cursadaB.getFin()) && cursadaB.getInicio().before(cursadaA.getFin()));
	}

	private List<CursadaDTO> getCursadasPorSala(SalaDTO sala) {
		List<CursadaDTO> ret = new LinkedList<>();
		for (CursadaDTO cursada : getCursadas())
			if (cursada.getSala() != null && cursada.getSala().equals(sala))
				ret.add(cursada);
		return ret;
	}

	public List<CursadaDTO> getCursadasPorSalaYFechas(SalaDTO sala, CursadaDTO cursadaB) {
		List<CursadaDTO> ret = new LinkedList<>();

		for (CursadaDTO cursada : getCursadasPorSala(sala))
			if (intersecan(cursada, cursadaB))
				ret.add(cursada);

		return ret;
	}

	public List<InscripcionAlumnoDTO> getInscripciones() {
		List<InscripcionAlumnoDTO> ret = new LinkedList<>();

		for (CursadaDTO c : getCursadas())
			ret.addAll(c.getInscripciones());

		return ret;
	}

	public List<InscripcionAlumnoDTO> getInscripcionesPorAlumno(AlumnoDTO alumno) {
		List<InscripcionAlumnoDTO> ret = new LinkedList<>();

		for (InscripcionAlumnoDTO inscripcion : getInscripciones())
			if (inscripcion.getAlumno().equals(alumno))
				ret.add(inscripcion);

		return ret;
	}

	public List<CursadaDTO> getCursadasPorAlumno(AlumnoDTO alumno) {
		List<CursadaDTO> ret = new LinkedList<>();
		for (CursadaDTO cursada : getCursadas()) {

			for (InscripcionAlumnoDTO inscripcion : cursada.getInscripciones()) {
				if (inscripcion != null && inscripcion.getAlumno().getId() == alumno.getId()) {
					ret.add(cursada);
				}
			}
		}
		return ret;
	}

	public boolean agregarCurso(CursoDTO curso) {
		if (cursoDao.create(curso)) {
			cursos.add(curso);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarCurso(CursoDTO curso) {
		if (cursoDao.delete(curso)) {
			cursos.remove(curso);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarCurso(CursoDTO curso) {
		if (cursoDao.update(curso)) {

			for (CursadaDTO cursada : this.cursadas) {
				if (cursada.getCurso().getId().equals(curso.getId())) {
					cursada.setCurso(curso);
					//this.modificarCursada(cursada);
				}
			}

			for (EventoInteresadoDTO eventoInteresado: ModeloEventos.getInstance().getEventosInteresados())
			{
				if (eventoInteresado.getCurso().getId().equals(curso.getId()))
					eventoInteresado.setCurso(curso);
			}
		
			invalidateObservers();
			return false;
		}

		else {
			invalidateObservers();
			return true;
		}
	}

	public boolean agregarCursada(CursadaDTO cursada) {
		if (cursadaDao.create(cursada)) {
			cursadas.add(cursada);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarCursada(CursadaDTO cursada) {
		if (cursadaDao.delete(cursada)) {
			cursadas.remove(cursada);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarCursada(CursadaDTO cursada) {

		if (cursadaDao.update(cursada)) {

			for (NotificacionDTO notificacion : ModeloNotificaciones.getInstance().getNotificaciones()) {
				if (notificacion.getCursada() != null && notificacion.getCursada().getId().equals(cursada.getId())) {
					notificacion.setCursada(cursada);
					//ModeloNotificaciones.getInstance().modificarNotificacion(notificacion);

					break;
				}
			}
			for (CursadaEmpresaDTO cursadaEmpresaDto : this.cursadaEmpresa) {
				if (cursadaEmpresaDto.getCursada().getId().equals(cursada.getId()))

				{

					cursadaEmpresaDto.setCursada(cursada);

					//this.modificarCursadaEmpresa(cursadaEmpresaDto);

				}
			}
			for(EventoInasistenciaDTO eventoInasistencia: ModeloEventos.getInstance().getEventosInasistencia())
			{
				if (eventoInasistencia.getCursada().getId().equals(eventoInasistencia.getId()))
					eventoInasistencia.setCursada(cursada);
			}
			

			invalidateObservers();
			return false;
		}

		else {
			invalidateObservers();
			return true;
		}
	}

	public boolean modificarPlanillaDeParciales(PlanillaDeParcialesDTO planilla) {

		if (planillaDeParcialesDao.update(planilla)) {
			for (PlanillaDeParcialesDTO planillaDTO : this.planillaDeParciales) {
				if (planillaDTO.getId().equals(planilla.getId())) {

					planillaDTO = planilla;
					break;
				}

			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public List<PagoDTO> getPagos() {
		return pagos;
	}

	public List<CuotaDTO> cuotasPagasPorAlumno(AlumnoDTO alumno) {
		List<CuotaDTO> cuotasPagas = new LinkedList<>();

		for (PagoDTO pago : getPagos())
			if (pago.getAlumno().equals(alumno))
				cuotasPagas.add(pago.getCuota());

		return cuotasPagas;
	}

	public List<DiaDeLaSemanaDTO> getDias() {
		return dias;
	}

	public boolean agregarPago(PagoDTO pago) {
		if (pagoDao.create(pago)) {
			pagos.add(pago);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarPago(PagoDTO pago) {
		if (pagoDao.update(pago)) 
		{
			

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}

	public boolean eliminarPago(PagoDTO pago) {
		if (pagoDao.delete(pago)) {
			pagos.remove(pago);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<HorarioDTO> getHorarios() {
		return horarios;
	}

	public boolean agregarHorario(HorarioDTO horario) {
		if (horariosDao.create(horario)) {
			horarios.add(horario);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarHorario(HorarioDTO horario) {
		if (horariosDao.update(horario)) {
			for (HorarioDTO horarioDto : this.horarios) {
				if (horarioDto.getId().equals(horario.getId())) {
					horarioDto = horario;
					break;
				}
			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}

	public boolean agregarEstadoDePeriodoDeInscripcion(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripcion) {
		if (estadodeperiododeinscripcionDao.create(estadodeperiododeinscripcion)) {
			estadosdeperiododeinscripcion.add(estadodeperiododeinscripcion);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<EstadoDePeriodoDeInscripcionDTO> getEstadosDePeriodoDeInscripcion() {
		return estadosdeperiododeinscripcion;
	}

	public boolean modificarEstadoDePeriodoDeInscripcion(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripcion) {
		if (estadodeperiododeinscripcionDao.update(estadodeperiododeinscripcion)) {
			for (EstadoDePeriodoDeInscripcionDTO estado : this.estadosdeperiododeinscripcion) {
				if (estadodeperiododeinscripcion.getId().equals(estado.getId())) {
					estado = estadodeperiododeinscripcion;
					break;
				}
			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}

	public boolean eliminarEstadoDePeriodoDeInscripcion(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripcion) {
		if (estadodeperiododeinscripcionDao.delete(estadodeperiododeinscripcion)) {
			estadosdeperiododeinscripcion.remove(estadodeperiododeinscripcion);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean agregarEstadoCursada(EstadoCursadaDTO estadocursada) {
		if (estadocursadaDao.create(estadocursada)) {
			estadoscursada.add(estadocursada);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<EstadoCursadaDTO> getEstadosCursada() {
		return estadoscursada;
	}

	public boolean modificarEstadoCursada(EstadoCursadaDTO estadocursada) {
		if (estadocursadaDao.update(estadocursada)) 
		{


			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}

	public boolean eliminarEstadoCursada(EstadoCursadaDTO estadocursada) {
		if (estadocursadaDao.delete(estadocursada)) {
			estadoscursada.remove(estadocursada);
			invalidateObservers();
			return true;
		}
		return false;
	}

	private boolean mismoTiempo(CursadaDTO cursadaA, CursadaDTO cursadaB) {
		if (!intersecan(cursadaA, cursadaB))
			return false;

		boolean ret = false;

		for (HorarioDTO horarioA : cursadaA.getHorarios())
			for (HorarioDTO horarioB : cursadaB.getHorarios())
				ret = ret || intersecan(horarioA, horarioB);

		return ret;
	}

	public List<InstructorDTO> getInstructoresDisponiblesPara(CursadaDTO cursada) {
		List<InstructorDTO> ret = new LinkedList<>();

		for (InstructorDTO instructor : ModeloPersonas.getInstance().getInstructores()) {
			boolean disponible = true;

			for (CursadaDTO cursadaDeInstructor : getCursadasPorInstructorYFechas(instructor, cursada.getInicio(),
					cursada.getFin()))
				disponible = disponible && !mismoTiempo(cursadaDeInstructor, cursada);

			if (disponible && !esAlumno(instructor,cursada))
				ret.add(instructor);
		}

		return ret;
	}

	private boolean esAlumno(InstructorDTO instructor, CursadaDTO cursada) {

		for(InscripcionAlumnoDTO e:cursada.getInscripciones()) {
			if(e.getAlumno().getPersona().getId()==instructor.getPersona().getId()) {
				return true;
			}
		}
		
		return false;
	}

	public void agregarPlanillaDeAsistencias(PlanillaDeAsistenciasDTO planillaRecibida) {
		this.planillaDeAsistenciasDao.create(planillaRecibida);
		this.planillaDeAsistencias.add(planillaRecibida);
		invalidateObservers();
	}

	public boolean modificarPlanillaDeAsistencias(PlanillaDeAsistenciasDTO planillaRecibida) 
	{
		if (this.planillaDeAsistenciasDao.update(planillaRecibida)) 
		{
			

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public List<CursadaDTO> getCursadasPorCurso(CursoDTO curso) {
		List<CursadaDTO> ret = new LinkedList<>();

		for (CursadaDTO cursada : getCursadas()) {
			if (cursada.getCurso().getId() == curso.getId())
				ret.add(cursada);
		}

		return ret;
	}

	public boolean modificarAsistencia(AsistenciaDTO asistencia) {

		if (asistenciasDao.update(asistencia)) 
		{

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}
	
	public void agregarAsistencia(AsistenciaDTO asistencia)
	{
		this.asistenciasDao.create(asistencia);
		this.asistencias.add(asistencia);
		invalidateObservers();
		
	}

	public boolean modificarParcial(ParcialDTO parcial) 
	{
		if (parcialesDao.update(parcial)) 
		{


			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public List<AsistenciaDTO> getAsistencias() {
		return asistencias;
	}

	public List<ParcialDTO> getParciales() {
		return parciales;
	}

	public boolean agregarEmpresa(EmpresaDTO empresa) {
		if (empresaDao.create(empresa)) {
			empresas.add(empresa);
			invalidateObservers();
			return true;
		}
		return false;
	}


	public boolean modificarEmpresa(EmpresaDTO empresaRecibida) {
		if (empresaDao.update(empresaRecibida)) {
			for (EmpresaDTO empresa : this.empresas) {
				if (empresa.getId().equals(empresaRecibida.getId())) {

					empresa = empresaRecibida;

					break;
				}
			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public void agregarCursadaEmpresa(CursadaEmpresaDTO cursadaEmpresa) {
		cursadaEmpresaDao.create(cursadaEmpresa);
		this.cursadaEmpresa.add(cursadaEmpresa);
		invalidateObservers();

	}

	public boolean modificarCursadaEmpresa(CursadaEmpresaDTO cursadaEmpresa) {

		if (cursadaEmpresaDao.update(cursadaEmpresa)) {

			for (EmpresaDTO empresa : this.empresas) 
			{
				List<CursadaEmpresaDTO> ret = new ArrayList<>();
				for (CursadaEmpresaDTO cursada : empresa.getCursadas()) 
				{

					if (cursada.getId().equals(cursadaEmpresa.getId())) 
					{
						ret.add(cursadaEmpresa);
					} else {
						ret.add(cursada);
					}

					empresa.setCursadas(ret);
					
				}

			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public List<AlumnoDTO> getAlumnosDeEmpresas() {
		List<AlumnoDTO> ret = new ArrayList<>();
		for (EmpresaDTO empresa : getEmpresas()) {
			ret.addAll(empresa.getAlumnos());
		}
		return ret;
	}

	public void agregarPagoEmpresa(PagoEmpresaDTO pago, CursadaEmpresaDTO cursadaEmpresa) {
		this.pagosEmpresaDao.create(pago);
		this.pagoEmpresas.add(pago);

		for (CursadaEmpresaDTO cursada : this.cursadaEmpresa) {
			if (cursada.getId().equals(cursadaEmpresa.getId())) {
				cursada.getPagos().add(pago);
				this.modificarCursadaEmpresa(cursada);
				break;
			}
		}

		invalidateObservers();
	}

	public boolean modificarPeriodoDeInscripcion(PeriodoInscripcionDTO periodo) {

		if (this.periodoDeInscripcionDao.update(periodo)) {



			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public boolean modificarPagoEmpresa(PagoEmpresaDTO pagoEmpresa) {

		if (this.pagosEmpresaDao.update(pagoEmpresa)) {

			for (PagoEmpresaDTO pagoEmpresaDto : this.pagoEmpresas) {
				if (pagoEmpresaDto.getId().equals(pagoEmpresa.getId())) {
					pagoEmpresaDto = pagoEmpresa;
					break;
				}

			}

			for (CursadaEmpresaDTO cursadaEmpresDTO : this.cursadaEmpresa) {
				for (PagoEmpresaDTO pagoEmpresaCursada : cursadaEmpresDTO.getPagos()) {
					if (pagoEmpresa.getId().equals(pagoEmpresaCursada.getId())) {

						pagoEmpresaCursada = pagoEmpresa;
						this.modificarCursadaEmpresa(cursadaEmpresDTO);
						break;
					}

				}
			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public CursadaDTO getCursadaPorPagoEmpresa(PagoEmpresaDTO pago) {

		for (CursadaEmpresaDTO cursada : getCursadasEmpresas()) {
			if (cursada.getPagos().contains(pago)) {
				return cursada.getCursada();
			}
		}
		return null;
	}

	public CursadaDTO getCursadaPorCuota(CuotaDTO cuota) {

		for (CursadaDTO cursada : getCursadas()) {
			if (cursada.getCuotas().contains(cuota)) {
				return cursada;
			}
		}

		return null;

	}

	public PagoDTO getPagoPorCuota(CuotaDTO cuota) {

		for (PagoDTO pago : getPagos()) {
			if (pago.getCuota().getId() == cuota.getId()) {
				return pago;
			}
		}

		return null;
	}

	public List<AlumnoDTO> getAlumnosporInscripciones(List<InscripcionAlumnoDTO> inscripciones) {
		List<AlumnoDTO> ret = new ArrayList<>();
		for (InscripcionAlumnoDTO inscripcion : inscripciones) {
			ret.add(inscripcion.getAlumno());
		}
		return ret;
	}

	public List<CursadaEmpresaDTO> getCursadasPorEmpresa(EmpresaDTO empresaRecibida) {

		for (EmpresaDTO EmpresaDTO : this.empresas) {

			if (empresaRecibida.getId().equals(EmpresaDTO.getId())) {

				return EmpresaDTO.getCursadas();
			}

		}
		return new ArrayList<>();
	}

	public boolean modificarInscripcionAlumno(InscripcionAlumnoDTO inscripcion) {
		if (this.inscripcionAlumnoDao.update(inscripcion)) {

			for (InscripcionAlumnoDTO inscripcionDto : this.inscripcionesAlumnos) {
				if (inscripcionDto.getId().equals(inscripcion.getId())) {
					inscripcionDto = inscripcion;
					break;
				}
			}

			for (CursadaDTO cursada : this.cursadas)
			{
				for (InscripcionAlumnoDTO inscripcionAlumno : cursada.getInscripciones())
				{
					if (inscripcionAlumno.getId().equals(inscripcion.getId()))
					{
						inscripcionAlumno = inscripcion;
					}
				}
			}

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}
	
	public CursadaDTO cursadaPorId(int id)
	{
		for(CursadaDTO cursada : getCursadas())
			if(cursada.getId() == id)
				return cursada;
		return null;
	}

	public List<PagoEmpresaDTO> getPagosCursada(CursadaEmpresaDTO cursadaRecibida, EmpresaDTO empresa) {

		List<PagoEmpresaDTO> pagos=new ArrayList<>();
		for (CursadaEmpresaDTO cursada : this.cursadaEmpresa) 
		{
			for (PagoEmpresaDTO pago: cursada.getPagos())
			{
				if (pago.getEmpresa().equals(empresa.getId()))
					pagos.add(pago);
					
			}
		}
		return pagos;

	}

	@Deprecated
	public static int cantidadDeMeses(Date inicio, Date fin)
	{

	            Calendar inicioCalendar = new GregorianCalendar();
	            Calendar finCalendar = new GregorianCalendar();
	            inicioCalendar.setTime(inicio);
	            finCalendar.setTime(fin);
	            int difA = finCalendar.get(Calendar.YEAR) - inicioCalendar.get(Calendar.YEAR);
	            int difM = difA * 12 + finCalendar.get(Calendar.MONTH) - inicioCalendar.get(Calendar.MONTH);
	           return (difM);

		
	}
}
