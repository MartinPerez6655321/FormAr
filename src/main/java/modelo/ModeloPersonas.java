package modelo;

import java.util.ArrayList;
import java.util.List;

import dto.AdministradorDTO;
import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.EmpresaDTO;
import dto.EventoInasistenciaDTO;
import dto.EventoInteresadoDTO;
import dto.EventoSupervisorDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import dto.InteresadoDTO;
import dto.PersonaDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import dto.UsuarioDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.AdministradorDAOMYSQL;
import persistencia.dao.mysql.AlumnoDAOMYSQL;
import persistencia.dao.mysql.InstructorDAOMYSQL;
import persistencia.dao.mysql.InteresadoDAOMYSQL;
import persistencia.dao.mysql.PersonaDAOMYSQL;
import persistencia.dao.mysql.PersonalAdministrativoDAOMYSQL;
import persistencia.dao.mysql.SupervisorDAOMYSQL;
import persistencia.dao.mysql.UsuarioDAOMYSQL;

public class ModeloPersonas extends AbstractObservable {
	private UsuarioDAOMYSQL usuarioDAO;
	private PersonaDAOMYSQL personaDao;
	private AlumnoDAOMYSQL alumnoDao;
	private InstructorDAOMYSQL instructorDao;
	private PersonalAdministrativoDAOMYSQL personaladministrativoDao;
	private SupervisorDAOMYSQL supervisorDao;
	private AdministradorDAOMYSQL administradorDao;
	private InteresadoDAOMYSQL interesadoDao;

	private List<UsuarioDTO> usuarios;
	private List<PersonaDTO> personas;
	private List<AlumnoDTO> alumnos;
	private List<InstructorDTO> instructores;
	private List<PersonalAdministrativoDTO> personalesAdministrativo;
	private List<SupervisorDTO> supervisores;
	private List<AdministradorDTO> administradores;
	private List<InteresadoDTO> interesados;

	private UsuarioDTO usuarioActual;

	private static ModeloPersonas instance;

	public static ModeloPersonas getInstance() {
		if (instance == null)
			instance = new ModeloPersonas();
		return instance;
	}

	private ModeloPersonas() {
		usuarioDAO = UsuarioDAOMYSQL.getInstance();
		personaDao = PersonaDAOMYSQL.getInstance();
		alumnoDao = AlumnoDAOMYSQL.getInstance();
		instructorDao = InstructorDAOMYSQL.getInstance();
		personaladministrativoDao = PersonalAdministrativoDAOMYSQL.getInstance();
		administradorDao = AdministradorDAOMYSQL.getInstance();
		supervisorDao = SupervisorDAOMYSQL.getInstance();
		interesadoDao = InteresadoDAOMYSQL.getInstance();

		usuarios = usuarioDAO.readAll();
		personas = personaDao.readAll();
		alumnos = alumnoDao.readAll();
		instructores = instructorDao.readAll();
		personalesAdministrativo = personaladministrativoDao.readAll();
		administradores = administradorDao.readAll();
		supervisores = supervisorDao.readAll();
		interesados = interesadoDao.readAll();
	}

	public List<SupervisorDTO> getSupervisores() {
		return supervisores;
	}

	public List<AdministradorDTO> getAdministradores() {
		return administradores;
	}

	public List<InteresadoDTO> getInteresados() {
		return interesados;
	}

	public boolean agregarUsuario(UsuarioDTO usuario) {
		if (usuarioDAO.create(usuario)) {
			usuarios.add(usuario);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarUsuario(UsuarioDTO usuario) 
	{
		if (usuario.getAdministrador()) {
			AdministradorDTO admin = this.getAdministrador(usuario);
			usuario.setAdministrador(false);
			this.modificarPersonaDTO(usuario.getPersona());
			this.administradorDao.delete(admin);
			administradores.remove(admin);
		}
		if (usuario.getSupervisor()) {
			SupervisorDTO supervisor = this.getSupervisor(usuario);
			usuario.setSupervisor(false);
			this.modificarPersonaDTO(usuario.getPersona());
			this.supervisorDao.delete(supervisor);
			supervisores.remove(supervisor);
		}
		if (usuario.getAdministrativo()) {
			PersonalAdministrativoDTO personalAdmin = this.getAdministrativo(usuario);
			usuario.setAdministrativo(false);
			this.modificarPersonaDTO(usuario.getPersona());
			this.personaladministrativoDao.delete(personalAdmin);
			personalesAdministrativo.remove(personalAdmin);
		}
		if (usuario.getInstructor()) {
			InstructorDTO instructor = this.getInstructor(usuario);
			usuario.setInstructor(false);
			this.modificarPersonaDTO(usuario.getPersona());
			this.instructorDao.delete(instructor);
			instructores.remove(instructor);
		}
		if (usuario.getAlumno()) {
			AlumnoDTO alumno = this.getAlumno(usuario);
			usuario.setAlumno(false);
			this.modificarPersonaDTO(usuario.getPersona());
			this.alumnoDao.delete(alumno);
			alumnos.remove(alumno);
		}
		
		PersonaDTO persona = usuario.getPersona();
		usuario.setPersona(null);

		if (this.usuarioDAO.update(usuario))
			usuarios.remove(usuario);
		if (this.personaDao.delete(persona))
			personas.remove(persona);
		if (this.usuarioDAO.delete(usuario))
			usuarios.remove(usuario);

		invalidateObservers();

		return true;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public boolean createPersonaDTO(PersonaDTO personadto) {
		if (personaDao.create(personadto)) {
			personas.add(personadto);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarPersonaDTO(PersonaDTO personadto) {

		if (personaDao.update(personadto)) {

					for (AlumnoDTO alumno : this.alumnos) 
					{
						if (alumno.getPersona().getId().equals(personadto.getId())) 
						{
							alumno.setPersona(personadto);
							this.modificarAlumno(alumno);
							break;

						}
					}
					for (InstructorDTO instructor : this.instructores) {
						if (instructor.getPersona().getId().equals(personadto.getId())) {
							instructor.setPersona(personadto);
							this.modificarInstructor(instructor);
							break;
						}
					}
					for (PersonalAdministrativoDTO personal : this.personalesAdministrativo) {

						if (personal.getPersona().getId().equals(personadto.getId())) {
							personal.setPersona(personadto);
							this.modificarPersonalAdministrativo(personal);
							break;
						}

					}
					for (InteresadoDTO interesado : this.interesados) {
						if (interesado.getPersona().getId().equals(personadto.getId())) {
							interesado.setPersona(personadto);
							modificarInteresado(interesado);
							break;

						}
					}
					for (SupervisorDTO supervisor: this.supervisores)
					{
						if (supervisor.getPersona().getId().equals(personadto.getId()))
						{
							supervisor.setPersona(personadto);
							modificarSupervisor(supervisor);
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

	private boolean modificarSupervisor(SupervisorDTO supervisor) 
	{
		if (supervisorDao.update(supervisor))
		{
			
			for (EventoSupervisorDTO eventoSupervisor : ModeloEventos.getInstance().getEventosSupervisor()) 
			{
				if (eventoSupervisor.getSupervisor().getId().equals(supervisor.getId()))
					eventoSupervisor.setSupervisor(supervisor);
			}

				
			invalidateObservers();
			return false;
			}
			
		 else {
			invalidateObservers();
			return true;
		}
		
	}

	public boolean eliminarPersonaDTO(PersonaDTO personadto) {
		if (personaDao.delete(personadto)) {
			personas.remove(personadto);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<AlumnoDTO> getAlumnos() {
		return alumnos;
	}

	public boolean agregarAlumno(AlumnoDTO alumno) {
		if (alumnoDao.create(alumno)) {
			alumnos.add(alumno);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarAlumno(AlumnoDTO alumno) 
	{
		if (alumnoDao.update(alumno)) {

					for (InscripcionAlumnoDTO inscripcion : ModeloCursos.getInstance().getInscripciones())
					{
						if (inscripcion.getAlumno().getId().equals(alumno.getId()))
						{
							inscripcion.setAlumno(alumno);
							ModeloCursos.getInstance().modificarInscripcionAlumno(inscripcion);
							
						}
					}
					
					for (EmpresaDTO empresa : ModeloCursos.getInstance().getEmpresas()) 
					{
						
						for (AlumnoDTO empleado : empresa.getAlumnos()) 
						{
							if (empleado.getId().equals(alumno.getId()))							
							{
								empleado=alumno;
								ModeloCursos.getInstance().modificarEmpresa(empresa);
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

	public boolean eliminarAlumno(AlumnoDTO alumno) {
		if (alumnoDao.delete(alumno)) {
			alumnos.remove(alumno);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<InstructorDTO> getInstructores() {
		return instructores;
	}

	public boolean agregarInstructor(InstructorDTO instructor) {
		if (instructorDao.create(instructor)) {
			instructores.add(instructor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarInstructor(InstructorDTO instructor) {
		if (instructorDao.delete(instructor)) {
			instructores.remove(instructor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarInstructor(InstructorDTO instructor) {

		if (instructorDao.update(instructor))
		{
			

					for (CursadaDTO cursada : ModeloCursos.getInstance().getCursadas()) 
					{
						
						List<InstructorDTO> instructores=new ArrayList<>();
						for (InstructorDTO instructorCursada : cursada.getInstructores()) 
						{
							if(instructorCursada.getId().equals(instructor.getId()))
							{
								instructores.add(instructor);	
							}
							else
							{
								instructores.add(instructorCursada);
							}
							cursada.setInstructores(instructores);
							ModeloCursos.getInstance().modificarCursada(cursada);
							
						}
					}
					invalidateObservers();
			return false;
			}
			
		 else {
			invalidateObservers();
			return true;
		}

	}

	public List<PersonalAdministrativoDTO> getListaPersonalAdministrativo() {
		return personalesAdministrativo;
	}

	public boolean agregarPersonalAdministrativo(PersonalAdministrativoDTO personaladministrativo) 
	{
		if (personaladministrativoDao.create(personaladministrativo)) {
			personalesAdministrativo.add(personaladministrativo);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarPersonalAdministrativo(PersonalAdministrativoDTO personaladministrativo) {
		if (!personaladministrativoDao.update(personaladministrativo)) 
		{
			
			for(EventoInteresadoDTO eventoInteresado: ModeloEventos.getInstance().getEventosInteresados())
			{
				if (eventoInteresado.getPersonalAdministrativo()!=null && eventoInteresado.getPersonalAdministrativo().getId().equals(personaladministrativo.getId()))
					eventoInteresado.setPersonalAdministrativo(personaladministrativo);
			}
			for (EventoSupervisorDTO eventoSupervisor : ModeloEventos.getInstance().getEventosSupervisor()) 
			{
				if (eventoSupervisor.getAdministrativoAsignado()!=null && eventoSupervisor.getAdministrativoAsignado().getId().equals(personaladministrativo.getId()))
					eventoSupervisor.setAdministrativoAsignado(personaladministrativo);
			}
			
			for (EventoInasistenciaDTO eventoInasistencia : ModeloEventos.getInstance().getEventosInasistencia()) 
			{
				if (eventoInasistencia.getAdministrativoAsignado()!=null && eventoInasistencia.getAdministrativoAsignado().getId().equals(personaladministrativo.getId()))
					eventoInasistencia.setAdministrativoAsignado(personaladministrativo);
			}
			
			
			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public boolean eliminarPersonalAdministrativo(PersonalAdministrativoDTO personaladministrativo) {
		if (personaladministrativoDao.delete(personaladministrativo)) {
			personalesAdministrativo.remove(personaladministrativo);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean agregarInteresado(InteresadoDTO interesado) {
		if (interesadoDao.create(interesado)) {
			interesados.add(interesado);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarInteresado(InteresadoDTO interesado) {
		if (interesadoDao.delete(interesado)) {
			interesados.remove(interesado);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarInteresado(InteresadoDTO interesado) {
		if (interesadoDao.update(interesado)) 
		{
			for (EventoInteresadoDTO eventoInteresado : ModeloEventos.getInstance().getEventosInteresados()) 
			{
				if (eventoInteresado.getInteresado().getId().equals(interesado.getId()))
					eventoInteresado.setInteresado(interesado);
			}
			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}

	}

	public UsuarioDTO getUsuarioPersona(PersonaDTO persona) {
		if (persona == null)
			return null;
		for (UsuarioDTO user : usuarios)
			if (persona.equals(user.getPersona()))
				return user;
		return null;
	}

	public AlumnoDTO getAlumnoPorPersona(PersonaDTO persona) {
		for (AlumnoDTO alumno : getAlumnos()) {
			if (alumno.getPersona().equals(persona))
				return alumno;
		}
		return null;
	}

	public AdministradorDTO getAdministrador(UsuarioDTO usuario) {
		for (AdministradorDTO administrador : getAdministradores())
			if (administrador.getPersona().equals(usuario.getPersona()))
				return administrador;
		return null;
	}

	public SupervisorDTO getSupervisor(UsuarioDTO usuario) {
		for (SupervisorDTO supervisor : getSupervisores())
			if (supervisor.getPersona().equals(usuario.getPersona()))
				return supervisor;
		return null;
	}

	public InstructorDTO getInstructor(UsuarioDTO usuario) {
		for (InstructorDTO instructor : getInstructores())
			if (instructor.getPersona().equals(usuario.getPersona()))
				return instructor;
		return null;
	}

	public AlumnoDTO getAlumno(UsuarioDTO usuario) {
		for (AlumnoDTO alumno : getAlumnos())
			if (alumno.getPersona().equals(usuario.getPersona()))
				return alumno;
		return null;
	}

	public InteresadoDTO getInteresadoPorPersona(PersonaDTO persona) {
		for (InteresadoDTO e : this.getInteresados()) {
			if (persona!=null && e.getPersona().getId() == persona.getId()) {
				return e;
			}
		}
		return null;
	}

	public PersonalAdministrativoDTO getAdministrativo(UsuarioDTO usuario) {
		for (PersonalAdministrativoDTO personalAdministrativo : getListaPersonalAdministrativo())
			if (personalAdministrativo.getPersona().equals(usuario.getPersona()))
				return personalAdministrativo;
		return null;
	}

	public PersonalAdministrativoDTO getPersonalAdministrativoActual(UsuarioDTO usuario) {
		if (usuario.getAdministrativo()) {
			for (PersonalAdministrativoDTO e : getListaPersonalAdministrativo()) {
				if (usuario.getPersona().getId() == e.getPersona().getId()) {
					return e;
				}
			}
		}
		return null;
	}

	public SupervisorDTO getSupervisorActual(UsuarioDTO usuario) {
		if (usuario.getSupervisor()) {
			for (SupervisorDTO e : getSupervisores()) {
				if (usuario.getPersona().getId() == e.getPersona().getId()) {
					return e;
				}
			}
		}
		return null;
	}

	public boolean agregarAdministrador(AdministradorDTO admin) {
		if (administradorDao.create(admin)) {
			administradores.add(admin);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean agregarSupervisor(SupervisorDTO supervisor) {
		if (supervisorDao.create(supervisor)) {
			supervisores.add(supervisor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean agregarPersona(PersonaDTO persona) {
		if (personaDao.create(persona)) {
			personas.add(persona);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarUsuarioDTO(UsuarioDTO usuario) 
	{
		if (!usuarioDAO.update(usuario)) 
		{
			
			for (UsuarioDTO usuarioDto: this.usuarios)
			{
				if (usuarioDto.getId().equals(usuario.getId()))
				{
					usuarioDto=usuario;
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

	public boolean eliminarAdministrador(AdministradorDTO admin) {
		if (administradorDao.delete(admin)) {
			administradores.remove(admin);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarSupervisor(SupervisorDTO supervisor) {
		if (supervisorDao.delete(supervisor)) {
			supervisores.remove(supervisor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public List<UsuarioDTO> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarioActual(UsuarioDTO usuario) {
		this.usuarioActual = usuario;
	}

	public UsuarioDTO getUsuarioActual() {
		return usuarioActual;
	}
}
