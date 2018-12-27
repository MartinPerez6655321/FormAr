package presentacion.controlador;

import presentacion.vista.PanelEventosGeneral;
import presentacion.vista.PanelGestionAlumnos;
import presentacion.vista.PanelGestionCursada;
import presentacion.vista.PanelGestionCursos;
import presentacion.vista.PanelGestionEmpresa;
import presentacion.vista.PanelGestionInscripcionAlumno;
import presentacion.vista.PanelGestionInstructor;
import presentacion.vista.PanelInteresados;
import presentacion.vista.PanelEstadoCursada;
import presentacion.vista.VistaAdministrativo;
import presentacion.vista.VistaEventos;

public class ControladorVistaAdministrativo
{

	private PanelGestionInscripcionAlumno vistaGestionInscripcionAlumno;
	private PanelGestionCursos vistaGestionCursos;
	private PanelGestionCursada vistaGestionCursada;
	private PanelGestionInstructor vistaGestionInstructor;
	private PanelGestionAlumnos vistaGestionAlumnos;

	private PanelGestionEmpresa vistaGestionEmpresa;

	private PanelEventosGeneral vistaEventosInteresado;
	private PanelEventosGeneral vistaEventosSupervisor;
	private PanelEventosGeneral vistaEventosInasistencia;
	
	private PanelInteresados vistaInteresados;	
	
	private PanelEstadoCursada vistaEstadoCursada;
	
	//Sistema notificacion
	private ControladorPanelEstadoCursada controladorEstadoCursada;
	private ControladorPanelGestionCursada controladorGestionCursada;
	private VistaEventos vistaEventosInteresadoPendientes;
	private VistaEventos vistaEventosInteresadoCompletados;
	private VistaEventos vistaEventosSupervisorPendientes;
	private VistaEventos vistaEventosSupervisorCompletados;
	private VistaEventos vistaEventosInasistenciaPendientes;
	private VistaEventos vistaEventosInasistenciaCompletados;
	
	public ControladorVistaAdministrativo(VistaAdministrativo vista)
	{
		vistaGestionInscripcionAlumno = new PanelGestionInscripcionAlumno();
		new ControladorPanelGestionInscripcionAlumno(vistaGestionInscripcionAlumno);
		
		vistaGestionCursos = new PanelGestionCursos();
		new ControladorPanelGestionCursos(vistaGestionCursos);
		
		vistaGestionCursada = new PanelGestionCursada();
		this.controladorGestionCursada =new ControladorPanelGestionCursada(vistaGestionCursada);
		
		vistaGestionInstructor = new PanelGestionInstructor();
		new ControladorPanelGestionInstructor(vistaGestionInstructor);
		
		vistaGestionAlumnos = new PanelGestionAlumnos();
		new ControladorPanelGestionAlumnos(vistaGestionAlumnos);

		vistaGestionEmpresa = new PanelGestionEmpresa();
		new ControladorPanelGestionEmpresa(vistaGestionEmpresa);
		
		vistaEventosInteresado=new PanelEventosGeneral();
	
		
		vistaEventosInteresadoPendientes=new VistaEventos();
		vistaEventosInteresadoCompletados=new VistaEventos();
		
		
		new ControladorEventosInteresados(vistaEventosInteresadoPendientes,false);
		new ControladorEventosInteresados(vistaEventosInteresadoCompletados,true);
		
		vistaEventosInteresado.agregarVista("Eventos Pendientes", vistaEventosInteresadoPendientes);
		vistaEventosInteresado.agregarVista("Eventos Completados", vistaEventosInteresadoCompletados);
		
		vistaEventosSupervisor=new PanelEventosGeneral();
		
		vistaEventosSupervisorPendientes=new VistaEventos();
		vistaEventosSupervisorCompletados=new VistaEventos();
		
		new ControladorEventosSupervisor(vistaEventosSupervisorPendientes,false);
		new ControladorEventosSupervisor(vistaEventosSupervisorCompletados,true);
		
		vistaEventosSupervisor.agregarVista("Eventos Pendientes", vistaEventosSupervisorPendientes);
		vistaEventosSupervisor.agregarVista("Eventos Completados", vistaEventosSupervisorCompletados);
		
		vistaEventosInasistencia=new PanelEventosGeneral();
		
		vistaEventosInasistenciaPendientes=new VistaEventos();
		vistaEventosInasistenciaCompletados=new VistaEventos();
		
		new ControladorEventosInasistencia(vistaEventosInasistenciaPendientes,false);
		new ControladorEventosInasistencia(vistaEventosInasistenciaCompletados,true);
		
		vistaEventosInasistencia.agregarVista("Eventos Pendientes", vistaEventosInasistenciaPendientes);
		vistaEventosInasistencia.agregarVista("Eventos Completados", vistaEventosInasistenciaCompletados);
		
		
		vistaInteresados=new PanelInteresados();
		new ControladorPanelInteresados(vistaInteresados);
		
		
		vistaEstadoCursada=new PanelEstadoCursada();
		this.controladorEstadoCursada=new ControladorPanelEstadoCursada(vistaEstadoCursada);
		
		
		
		vista.agregarVista("Inscripciones", vistaGestionInscripcionAlumno);
		vista.agregarVista("Cursos", vistaGestionCursos);
		vista.agregarVista("Cursadas", vistaGestionCursada);
		vista.agregarVista("Instructor", vistaGestionInstructor);
		vista.agregarVista("Alumnos", vistaGestionAlumnos);
		vista.agregarVista("Empresas", vistaGestionEmpresa);
		vista.agregarVista("Eventos Interesado", vistaEventosInteresado);
		vista.agregarVista("Eventos Supervisor", vistaEventosSupervisor);
		vista.agregarVista("Eventos Inasistencia", vistaEventosInasistencia);
		
		vista.agregarVista("Interesados", vistaInteresados);
		vista.agregarVista("Cursadas pendientes", vistaEstadoCursada);
		
		
		
	}



	public ControladorPanelEstadoCursada getControladorEstadoCursada() {
		return controladorEstadoCursada;
	}



	public ControladorPanelGestionCursada getControladorGestionCursada() {
		return controladorGestionCursada;
	}



	public void setControladorGestionCursada(ControladorPanelGestionCursada controladorGestionCursada) {
		this.controladorGestionCursada = controladorGestionCursada;
	}



	public PanelEventosGeneral getVistaEventosInteresado() {
		return vistaEventosInteresado;
	}



	public PanelEventosGeneral getVistaEventosSupervisor() {
		return vistaEventosSupervisor;
	}



	public PanelEventosGeneral getVistaEventosInasistencia() {
		return vistaEventosInasistencia;
	}



	public VistaEventos getVistaEventosInteresadoPendientes() {
		return vistaEventosInteresadoPendientes;
	}



	public VistaEventos getVistaEventosInteresadoCompletados() {
		return vistaEventosInteresadoCompletados;
	}



	public VistaEventos getVistaEventosSupervisorPendientes() {
		return vistaEventosSupervisorPendientes;
	}



	public VistaEventos getVistaEventosSupervisorCompletados() {
		return vistaEventosSupervisorCompletados;
	}



	public VistaEventos getVistaEventosInasistenciaPendientes() {
		return vistaEventosInasistenciaPendientes;
	}



	public VistaEventos getVistaEventosInasistenciaCompletados() {
		return vistaEventosInasistenciaCompletados;
	}

	
	
}
