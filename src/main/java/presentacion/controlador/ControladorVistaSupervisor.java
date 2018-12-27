
package presentacion.controlador;


import presentacion.vista.PanelEventosSupervisorCompletados;
import presentacion.vista.VistaEventosInasistenciaCompletados;
import presentacion.vista.PanelEventosSupervisorAsignar;
import presentacion.vista.VistaEventosInasistencias;
import presentacion.vista.VistaEventosInasistenciasSupervisorAsignar;
import presentacion.vista.VistaEventosInteresados;
import presentacion.vista.VistaEventosSupervisor;
import presentacion.vista.VistaSupervisor;
import presentacion.vista.VistaEventosInteresadosPendientesCompletados;

public class ControladorVistaSupervisor {
	private VistaEventosSupervisor vistaEventosSupervisor;
	private VistaEventosInasistencias vistaEventosInasistencias;
	private VistaEventosInteresados vistaEventosInteresados;
	
	private PanelEventosSupervisorAsignar vistaEventosPendientesSupervisorAsignar;
	private PanelEventosSupervisorCompletados vistaEventosCompletadosSupervisor;
	private VistaEventosInasistenciasSupervisorAsignar vistaEventosInasistenciasSupervisorAsignar;
	private VistaEventosInasistenciaCompletados vistaEventosInasistenciaCompletados;
	private VistaEventosInteresadosPendientesCompletados vistaEventosInteresadosPendientes;
	private VistaEventosInteresadosPendientesCompletados vistaEventosInteresadosCompletados;

	public ControladorVistaSupervisor(VistaSupervisor vistaSupervisor) {
		
		vistaEventosSupervisor=new VistaEventosSupervisor();
		vistaEventosPendientesSupervisorAsignar = new PanelEventosSupervisorAsignar();
		new ControladorPanelEventosSupervisorAsignar(vistaEventosPendientesSupervisorAsignar);
		vistaEventosCompletadosSupervisor= new PanelEventosSupervisorCompletados();
		new ControladorPanelEventosSupervisorCompletados(vistaEventosCompletadosSupervisor);
		
		
		vistaEventosInasistencias=new VistaEventosInasistencias();
		vistaEventosInasistenciasSupervisorAsignar=new VistaEventosInasistenciasSupervisorAsignar();
		new ControladorVistaEventosInasistenciasSupervisorAsignar(vistaEventosInasistenciasSupervisorAsignar);
		vistaEventosInasistenciaCompletados=new VistaEventosInasistenciaCompletados();
		new ControladorVistaEventosInasistenciaCompletados(vistaEventosInasistenciaCompletados);
		
		
		vistaEventosInteresados=new VistaEventosInteresados();
		vistaEventosInteresadosPendientes=new VistaEventosInteresadosPendientesCompletados();
		vistaEventosInteresadosCompletados=new VistaEventosInteresadosPendientesCompletados();
		new ControladorVistaEventosInteresadosPendientes(vistaEventosInteresadosPendientes);
		new ControladorVistaEventosInteresadosCompletados(vistaEventosInteresadosCompletados);
		
		vistaEventosSupervisor.agregarVista("Eventos Pendientes", vistaEventosPendientesSupervisorAsignar);
		vistaEventosSupervisor.agregarVista("Eventos Completados", vistaEventosCompletadosSupervisor);
		
		vistaEventosInasistencias.agregarVista("Eventos Pendientes", vistaEventosInasistenciasSupervisorAsignar);
		vistaEventosInasistencias.agregarVista("Eventos Completados", vistaEventosInasistenciaCompletados);
		
		vistaEventosInteresados.agregarVista("Eventos Pendientes", vistaEventosInteresadosPendientes);
		vistaEventosInteresados.agregarVista("Eventos Completados", vistaEventosInteresadosCompletados);
	
		vistaSupervisor.agregarVista("Eventos Supervisor", vistaEventosSupervisor);
		vistaSupervisor.agregarVista("Eventos Inasistencias", vistaEventosInasistencias);
		vistaSupervisor.agregarVista("Eventos Interesados", vistaEventosInteresados);
		
		}

}

