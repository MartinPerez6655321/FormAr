package presentacion.controlador;


import java.sql.Time;
import java.util.Date;
import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.EstadoEventoDTO;
import dto.EventoInteresadoDTO;
import dto.InteresadoDTO;
import dto.NotificacionDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.reportes.ReporteAlumno;
import presentacion.vista.PanelTramitesAlumno;

public class ControladorPanelTramitesAlumno {
	
	private PanelTramitesAlumno panel;
	private ModeloEventos modeloEventos;
	private ModeloNotificaciones modelNotificaciones;
	private ModeloCursos model;
	private ModeloPersonas modeloPersonas;
	private AlumnoDTO alumno;

	public ControladorPanelTramitesAlumno(PanelTramitesAlumno panelTramites,AlumnoDTO alumno) {
		panel=panelTramites;
		model=ModeloCursos.getInstance();
		modeloEventos=ModeloEventos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.alumno=alumno;
		this.modelNotificaciones=ModeloNotificaciones.getInstance();
		
		this.panel.getBtnGenerarReporteAcadmico().addActionListener(e->generarReporte());
		this.panel.getBtnSolicitarReporte().addActionListener(e->solicitarReporte());
	}

	private void solicitarReporte() {
		if(this.hayCursadasFinalizadas()) 
		{
			EventoInteresadoDTO eventoSolicitud=new EventoInteresadoDTO();
			eventoSolicitud.setDescripcion(alumno.getPersona().getNombre()+" "+alumno.getPersona().getApellido()+" solicita su reporte académico para retirarlo en el instituto.");
			Date date=new Date();
			eventoSolicitud.setFechaDeLlamado(date);
			eventoSolicitud.setHoraDeLlamado(new Time(date.getHours(),date.getMinutes(),date.getSeconds()));
			
			eventoSolicitud.setFechaDeCumplimiento(date);
			eventoSolicitud.setHoraDeCumplimiento(new Time(date.getHours()+5,date.getMinutes(),date.getSeconds()));
			
			eventoSolicitud.setInteresado(generarInteresado());
			
			EstadoEventoDTO estado=modeloEventos.getEstadosEventos().get(1);//estado pendiente
			eventoSolicitud.setEstado(estado);
			eventoSolicitud.setDisponibleEnSistema(true);
			
			eventoSolicitud.setAdministrativoAsignado(null);
			eventoSolicitud.setPersonalAdministrativo(null);
			
			modeloEventos.agregarEventoInteresado(eventoSolicitud);
			
			crearNotificacion(eventoSolicitud);
			
			JOptionPane.showMessageDialog(null, "Puede pasar a retirar su reporte académico en el instituto cuando desee.", "Reporte solicitado", JOptionPane.PLAIN_MESSAGE);}
		else 
		{
			JOptionPane.showMessageDialog(null, "Usted no posee cursadas finalizadas");
		}
		


	}
	
	private void crearNotificacion(EventoInteresadoDTO evento) 
	{
		
			NotificacionDTO notificacion=new NotificacionDTO();
			notificacion.setDescripcion("se te  asigno la tarea de:"+evento.getDescripcion());
			notificacion.setEventoInteresado(evento);
			notificacion.setDisponibleEnSistema(false);
			notificacion.setTitulo("no visto-c-5");
			if (evento.getAdministrativoAsignado()!=null)
				notificacion.setUsuario(modeloPersonas.getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
			notificacion.setFecha(evento.getFechaDeCumplimiento());
			notificacion.setHora(evento.getHoraDeCumplimiento());

			modelNotificaciones.agregarNotificacion(notificacion);

			notificacion.setVisto(false);
			this.modelNotificaciones.agregarNotificacion(notificacion);

	}

	private InteresadoDTO generarInteresado() {
		InteresadoDTO ret=modeloPersonas.getInteresadoPorPersona(alumno.getPersona());
		if(ret==null){
			ret=new InteresadoDTO();
			ret.setPersona(alumno.getPersona());
			ret.setDisponibleEnSistema(true);
			modeloPersonas.agregarInteresado(ret);
			return ret;
			}
		else {
			return ret;
		}
		
	}

	private void generarReporte() {
		if(this.hayCursadasFinalizadas()) {
			ReporteAlumno reporteAlumno = new ReporteAlumno(alumno);
			reporteAlumno.mostrar();}
		else {
			JOptionPane.showMessageDialog(null, "Usted no posee cursadas finalizadas");
		}
	}
	
	private boolean hayCursadasFinalizadas() {
		
		for(CursadaDTO e: model.getCursadasPorAlumno(alumno)) {
			if(e.getEstado().getNombre().equals("Finalizado")) { 
				return true;
				}
		}
		return false;
	}

}
