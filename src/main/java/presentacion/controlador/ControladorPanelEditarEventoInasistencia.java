package presentacion.controlador;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import dto.EventoInasistenciaDTO;
import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.PanelEditarEventoInasistencia;
import presentacion.vista.PanelVistaAsignarEventos;

public class ControladorPanelEditarEventoInasistencia {

	private PanelEditarEventoInasistencia vista;
	private EventoInasistenciaDTO evento;
	private Modal modal;

	private ControladorPanelAsignarEventosInasistencia controladorPanelAsignarEventos;
	private ModeloEventos model;

	public ControladorPanelEditarEventoInasistencia(PanelEditarEventoInasistencia panelEditar, EventoInasistenciaDTO selectedItem, Modal modal) {
		vista=panelEditar;
		evento=selectedItem;
		this.modal=modal;
		model=ModeloEventos.getInstance();
		this.llenarCampos();
		
		this.vista.getBtnAsignarResponsable().addActionListener(e->asignarResponsable());
		
		
		this.vista.getBtnAceptar().addActionListener(e->modificarEvento());
		
	}
	
	
	private void modificarEvento() 
	{
		
		
		evento.setDisponibleEnSistema(true);
		
		if(controladorPanelAsignarEventos!=null && controladorPanelAsignarEventos.getPersonalElegido()!=null) {
				evento.setAdministrativoAsignado(controladorPanelAsignarEventos.getPersonalElegido());
		}
		
		evento.setHoraDeCumplimiento(getHorarioCumplimientoElegido());
		
		if(!verificarHorario(evento)) {
			JOptionPane.showMessageDialog(null, "El horario ingresado no es válido.");
			return;
		}
		
		model.modificarEventoInasistencia(evento);
		modal.dispose();
		
	}
	
	
	
	
	private boolean horarioProximo(Time horaDeCumplimiento) {
		final long unMinutoEnMilis=60000;

		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		Date dateMinMas=new Date(t + (1 * unMinutoEnMilis));
		Date dateComp=new Date();
		dateComp.setHours(horaDeCumplimiento.getHours());
		dateComp.setMinutes(horaDeCumplimiento.getMinutes());
		dateComp.setSeconds(horaDeCumplimiento.getSeconds());
		
		
		
		return (dateComp.before(dateMinMas));
	}


	private boolean verificarHorario(EventoInasistenciaDTO evento) {
		
		if(horarioProximo(evento.getHoraDeCumplimiento())) {
			return false;
		}
		return true;
	}
	


	public Time getHorarioCumplimientoElegido() {
		Object value=this.vista.getSpinnerHora().getValue();
		Date date= (Date) value;
		return new Time(date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
	
	private void llenarCampos() {
		this.vista.getTextFieldAusente().setText(evento.getAlumno().getPersona().getNombre()+" "+evento.getAlumno().getPersona().getApellido());
		this.vista.getTextFieldCurso().setText(evento.getCursada().getNombre()+" ("+evento.getCursada().getCurso().getNombre()+")");
		
		if(evento.getAdministrativoAsignado()!=null) {
			llenarCampoResponsable(evento.getAdministrativoAsignado());}
		
		this.vista.getSpinnerHora().setValue(evento.getHoraDeCumplimiento());
		
	}
	
	private void asignarResponsable() 
	{
		
		PanelVistaAsignarEventos vistaAsignador = new PanelVistaAsignarEventos();
		controladorPanelAsignarEventos = new ControladorPanelAsignarEventosInasistencia(vistaAsignador,evento);
		Modal.showDialog(vistaAsignador, "Asignar Personal Administrativo a evento", vistaAsignador.getAceptarButton());
		llenarCampoResponsable(controladorPanelAsignarEventos.getPersonalElegido());
		

	}
	
	private void llenarCampoResponsable(PersonalAdministrativoDTO PersonalAdminElegido) {
		if(PersonalAdminElegido!=null) {
			this.vista.getTextFieldResponsable().setText(PersonalAdminElegido.getPersona().getNombre()+" "+PersonalAdminElegido.getPersona().getApellido());
		}
		
	}
	

	
	


}
