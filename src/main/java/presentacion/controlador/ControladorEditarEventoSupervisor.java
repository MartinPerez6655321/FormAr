package presentacion.controlador;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;

import dto.EventoSupervisorDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.PanelVistaAsignarEventos;
import presentacion.vista.VistaRegistrarEventoSupervisor;
import presentacion.vista.subcomponentes.DocumentSizeFilter;

public class ControladorEditarEventoSupervisor implements DocumentListener  {

	private VistaRegistrarEventoSupervisor vista;
	
	private ModeloEventos modelEventos;
	private EventoSupervisorDTO evento;
	private ModeloPersonas modeloPersonas;
	private boolean fechaNotNull;
	private Modal modal;
	private DefaultStyledDocument doc;
	private ControladorPanelAsignarEventosSupervisor controladorPanelAsignarEventos;
	private PanelVistaAsignarEventos vistaAsignador;

	public ControladorEditarEventoSupervisor(VistaRegistrarEventoSupervisor vistaRegistrarEventoSupervisor,
			EventoSupervisorDTO selectedItem, Modal modal) {
		this.vista = vistaRegistrarEventoSupervisor;
		this.evento=selectedItem;
		modelEventos = ModeloEventos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista.getBtnAceptar().setEnabled(false);
		this.fechaNotNull=false;
		this.modal=modal;
		this.vista.getDateFechaLimite().getDateEditor().addPropertyChangeListener(e->evaluadorFechaNotNull());
		doc = new DefaultStyledDocument();
		
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
		
		this.llenarCampos();
	}
	private void llenarCampos() {
		this.vista.getTextAreaDescripcion().setText(evento.getDescripcion());
		this.vista.getSpinnerHora().setValue(evento.getHoraDeCumplimiento());
		this.vista.getDateFechaLimite().setDate(evento.getFechaDeCumplimiento());
		this.llenarCampoResponsable(evento.getAdministrativoAsignado());
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
		
		
		SupervisorDTO supervisorActual=modeloPersonas.getSupervisorActual(modeloPersonas.getUsuarioActual());
		evento.setSupervisor(supervisorActual);
			
	

		
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
			modelEventos.modificarEventoSupervisor(evento);
			modal.dispose();
		}
		
		

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


	private boolean verificarHorario(EventoSupervisorDTO eventoSupervisor) {
		
		if(mismaFecha(eventoSupervisor.getFechaDeCumplimiento(),new Date()) && horarioProximo(eventoSupervisor.getHoraDeCumplimiento())) {
			return false;
		}
		return true;
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
