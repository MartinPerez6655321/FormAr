package presentacion.controlador;



import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;


import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.PanelVistaAsignarEventos;

import presentacion.vista.VistaRegistrarEventoSupervisor;
import presentacion.vista.subcomponentes.DocumentSizeFilter;

public class ControladorRegistrarEventoSupervisor implements DocumentListener  
{
	
	private ModeloEventos modelEventos;
	private ModeloPersonas modeloPersonas;
	private VistaRegistrarEventoSupervisor vista ;
	private EventoSupervisorDTO evento;
	private ModeloNotificaciones model;
	
	private PanelVistaAsignarEventos vistaAsignador;
	private ControladorPanelAsignarEventosSupervisor controladorPanelAsignarEventos;
	private int creador;
	private boolean fechaNotNull;
	private Modal modal;
	private DefaultStyledDocument doc = new DefaultStyledDocument();


	public ControladorRegistrarEventoSupervisor(VistaRegistrarEventoSupervisor vista,int numeroCreador,Modal modal)
	{	this.model=ModeloNotificaciones.getInstance();
		this.vista = vista;
		this.evento=new EventoSupervisorDTO();
		modelEventos = ModeloEventos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista.getBtnAceptar().setEnabled(false);
		this.creador=numeroCreador; //asigno 0 si el creador es un administrativo o 1 si el creador es un supervisor
		this.fechaNotNull=false;
		this.modal=modal;
		this.modal.setTitle("Crear Evento");
		this.vista.getDateFechaLimite().getDateEditor().addPropertyChangeListener(e->evaluadorFechaNotNull());
		doc.setDocumentFilter(new DocumentSizeFilter(400));
	    doc.addDocumentListener(new DocumentListener(){
	            @Override
	        public void changedUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void insertUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void removeUpdate(DocumentEvent e) { updateCount();}
	        });
		
	    this.vista.getTextAreaDescripcion().setDocument(doc);
		this.vista.getTextAreaDescripcion().getDocument().addDocumentListener(this);
	
		this.vista.getBtnAceptar().addActionListener(e->registrarEvento());
		this.vista.getBtnAsignarResponsable().addActionListener(e->asignarResponsable());
		
	}

	private void updateCount()
    {
        vista.getLblCaracteresRestantes().setText((400 -doc.getLength()) + " carácteres restantes");
    }
	
	private void evaluadorFechaNotNull() {
		if(this.vista.getDateFechaLimite().getDate()!=null) {
			fechaNotNull=true;
			if(!this.vista.getTextAreaDescripcion().getText().isEmpty() ) {
				this.vista.getBtnAceptar().setEnabled(true);
			}
		}
	}



	private void asignarResponsable() 
	{
		
		vistaAsignador = new PanelVistaAsignarEventos();
		controladorPanelAsignarEventos = new ControladorPanelAsignarEventosSupervisor(vistaAsignador,evento);
		Modal.showDialog(vistaAsignador, "Asignar Personal Administrativo a evento", vistaAsignador.getAceptarButton());
		llenarCampoResponsable(controladorPanelAsignarEventos.getPersonalElegido());
		

		
		
		
	}
	

	

	
	private void llenarCampoResponsable(PersonalAdministrativoDTO PersonalAdminElegido) {
		if(PersonalAdminElegido!=null) {
			this.vista.getTextFieldResponsable().setText(PersonalAdminElegido.getPersona().getNombre()+" "+PersonalAdminElegido.getPersona().getApellido());
		}
		
	}

	private void registrarEvento() 
	{
		
		evento.setDescripcion(vista.getTextAreaDescripcion().getText());
		evento.setEstado(modelEventos.getEstadosEventos().get(1));
	
		evento.setFechaDeCumplimiento(vista.getDateFechaLimite().getDate());
		evento.setDisponibleEnSistema(true);
		
		if(creador==0) {
			PersonalAdministrativoDTO administrativoActual=modeloPersonas.getPersonalAdministrativoActual(modeloPersonas.getUsuarioActual());
			
			evento.setAdministrativoAsignado(administrativoActual);}
		else {
			SupervisorDTO supervisorActual=modeloPersonas.getSupervisorActual(modeloPersonas.getUsuarioActual());
			evento.setSupervisor(supervisorActual);
			
		}

		
		if(controladorPanelAsignarEventos!=null) {
			if(controladorPanelAsignarEventos.getPersonalElegido()!=null)
			{
				evento.setAdministrativoAsignado(controladorPanelAsignarEventos.getPersonalElegido());
			}
		}
		
		evento.setHoraDeCumplimiento(getHorarioCumplimientoElegido());
		
		if(!verificarHorario(evento)) {
			JOptionPane.showMessageDialog(null, "El horario ingresado no es válido.");
			return;
		}
		if(eventoValido()) {
			modelEventos.agregarEventoSupervisor(evento);
			modal.dispose();
			crearNotificacion(evento);
		}
		
		

	}

	private boolean verificarHorario(EventoSupervisorDTO eventoSupervisor) {
		
		if(mismaFecha(eventoSupervisor.getFechaDeCumplimiento(),new Date()) && horarioProximo(eventoSupervisor.getHoraDeCumplimiento())) {
			return false;
		}
		return true;
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

	private boolean mismaFecha(Date date1,Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean mismoDia = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
		                  cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		
		return mismoDia;
	}
	private void crearNotificacion(EventoSupervisorDTO evento) 
	{
		
			NotificacionDTO notificacion=new NotificacionDTO();
			notificacion.setDescripcion("Se le asigno la tarea de: "+evento.getDescripcion());
			notificacion.setEventoSupervisor(evento);
			notificacion.setDisponibleEnSistema(false);
			notificacion.setTitulo("Evento supervisor");
			if (evento.getAdministrativoAsignado()!=null)
				notificacion.setUsuario(modeloPersonas.getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
			notificacion.setFecha(evento.getFechaDeCumplimiento());
			notificacion.setHora(evento.getHoraDeCumplimiento());
			notificacion.setVisto(false);
			notificacion.setCodigoVista("c");
			notificacion.setCodigoTab("6-0");
			model.agregarNotificacion(notificacion);
	}
	

	
	
	
	public Time getHorarioCumplimientoElegido() {
		Object value=this.vista.getSpinnerHora().getValue();
		Date date= (Date) value;
		return new Time(date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
	private boolean eventoValido() {
		Date in = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		Date actual = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		actual.setHours(0);actual.setMinutes(0);actual.setSeconds(0);//lo seteo asi porque sino toma la fecha del sistema y gralmente hay errores (salvo que sean las 00:00:00)
	
		
		if (evento.getFechaDeCumplimiento().getYear()<actual.getYear() ||
				evento.getFechaDeCumplimiento().getMonth()<actual.getMonth() ||
				evento.getFechaDeCumplimiento().getDate()<actual.getDate() 
			)
		{
			JOptionPane.showMessageDialog(null,"Ingresa una fecha valida.");
			return false;
		}
		return true;
	}

	
	@Override
	public void changedUpdate(DocumentEvent e) {
		
		if(this.vista.getTextAreaDescripcion().getText().isEmpty()  || !this.fechaNotNull) {
			this.vista.getBtnAceptar().setEnabled(false);
		}
		else {
			this.vista.getBtnAceptar().setEnabled(true);
		}
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
		
	}

	
}
