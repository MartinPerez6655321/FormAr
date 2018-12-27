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

import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import dto.NotificacionDTO;
import dto.PersonaDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.VistaABMInteresado;
import presentacion.vista.VistaRegistrarEventoNuevo;
import presentacion.vista.subcomponentes.DocumentSizeFilter;

public class ControladorRegistrarEventoInteresado implements DocumentListener
{
	private ModeloEventos modelEventos;
	private ModeloNotificaciones modelNotificaciones;
	private ModeloCursos model;
	private ModeloPersonas modeloPersonas;
	private VistaRegistrarEventoNuevo vista ;
	private EventoInteresadoDTO eventoInteresado;
	private ControladorVistaABMInteresado controladorVistaABMInteresado;
	private VistaABMInteresado vistaInteresado;
	private int creador;
	private boolean fechaNotNull;
	private Modal modal;
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	
	public ControladorRegistrarEventoInteresado(VistaRegistrarEventoNuevo vista, int numeroCreador, Modal modal)
	{
		this.vista = vista;
		this.model=ModeloCursos.getInstance();
		this.modelNotificaciones=ModeloNotificaciones.getInstance();
		this.eventoInteresado=new EventoInteresadoDTO();
		modelEventos = ModeloEventos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista.getBtnAceptar().setEnabled(false);
		this.creador=numeroCreador; //asigno 0 si el creador es un administrativo o 1 si el creador es un supervisor
		this.fechaNotNull=false;
		this.modal=modal;
		this.modal.setTitle("Crear Evento");
		this.vista.getDateFechaLimite().getDateEditor().addPropertyChangeListener(e->evaluadorFechaNotNull());
		this.llenarCampoInteresadoSupervisor();
		this.llenarComboBoxCursos();
		
		
		doc.setDocumentFilter(new DocumentSizeFilter(400));
	    doc.addDocumentListener(new DocumentListener(){
	            @Override
	        public void changedUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void insertUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void removeUpdate(DocumentEvent e) { updateCount();}
	        });
		
		
		this.vista.getTextFieldInteresado().getDocument().addDocumentListener(this);
		this.vista.getTextAreaDescripcion().setDocument(doc);
		this.vista.getTextAreaDescripcion().getDocument().addDocumentListener(this);
		
		
		this.vista.getBtnAceptar().addActionListener(e->registrarEvento());
		this.vista.getBtnAsignarInteresado().addActionListener(e->asignarInteresado());
	}
	
	private void updateCount()
    {
        vista.getLblCaracteresRestantes().setText((400 -doc.getLength()) + " carácteres restantes");
    }

	private void llenarComboBoxCursos() {
		this.vista.getComboBoxCurso().addItem("");
		for(CursoDTO curso:model.getCursos()) {
			this.vista.getComboBoxCurso().addItem(curso.getNombre()); 
		}
	}

	private void evaluadorFechaNotNull() {
		if(this.vista.getDateFechaLimite().getDate()!=null) {
			fechaNotNull=true;
			if(!this.vista.getTextAreaDescripcion().getText().isEmpty() && !this.vista.getTextFieldInteresado().getText().isEmpty()) {
				this.vista.getBtnAceptar().setEnabled(true);
			}
		}
	}

	private void asignarInteresado() 
	{
		vistaInteresado=new VistaABMInteresado();
		eventoInteresado.setFechaDeLlamado(new Date());
		controladorVistaABMInteresado=new ControladorVistaABMInteresado(vistaInteresado,eventoInteresado);
		llenarCampoInteresado(controladorVistaABMInteresado.getInteresadoElegido());
	}

	private void llenarCampoInteresadoSupervisor() {
		if(this.creador==1) {
			SupervisorDTO supervisorActual = modeloPersonas.getSupervisorActual(ModeloPersonas.getInstance().getUsuarioActual());
			this.vista.getTextFieldInteresado().setText(supervisorActual.getPersona().getNombre()+" "+supervisorActual.getPersona().getApellido()+" (Supervisor actual)" );

		}
	}
	
	private void llenarCampoInteresado(PersonaDTO interesadoElegido) {
		if(interesadoElegido!=null) {
			this.vista.getTextFieldInteresado().setText(interesadoElegido.getNombre()+" "+interesadoElegido.getApellido());
		}
		
	}

	private void registrarEvento() 
	{
		
		
		
		if(eventoInteresado !=null) {
			eventoInteresado.setDescripcion(vista.getTextAreaDescripcion().getText());
			eventoInteresado.setEstado(modelEventos.getEstadosEventos().get(1));
		
			eventoInteresado.setFechaDeCumplimiento(vista.getDateFechaLimite().getDate());
			eventoInteresado.setDisponibleEnSistema(true);
			Date date=new Date();
			eventoInteresado.setFechaDeLlamado(date);
			eventoInteresado.setHoraDeLlamado(new Time(date.getHours(),date.getMinutes(),date.getSeconds()));
			
		}
		if(creador==0) {
			PersonalAdministrativoDTO administrativoActual=modeloPersonas.getPersonalAdministrativoActual(ModeloPersonas.getInstance().getUsuarioActual());
			eventoInteresado.setPersonalAdministrativo(administrativoActual);
			eventoInteresado.setAdministrativoAsignado(administrativoActual);}
		
		if(!this.vista.getComboBoxCurso().getSelectedItem().equals("")) {
			
			CursoDTO curso=new CursoDTO();
			for(CursoDTO c:model.getCursos()) {
				
				if(c.getNombre().equals(this.vista.getComboBoxCurso().getSelectedItem())) {
					curso=c;
					break;
				}
			}
			
			eventoInteresado.setCurso(curso);
		}
		
		
		if(controladorVistaABMInteresado!=null) {
			eventoInteresado.setInteresado(modeloPersonas.getInteresadoPorPersona(controladorVistaABMInteresado.getInteresadoElegido()));
			
		}
		
		eventoInteresado.setHoraDeCumplimiento(getHorarioCumplimientoElegido());
		
		if(!verificarHorario(eventoInteresado)) {
			JOptionPane.showMessageDialog(null, "El horario ingresado no es válido.");
			return;
		}
		
		
		if(eventoValido() && eventoInteresado!=null) {
			modelEventos.agregarEventoInteresado(eventoInteresado);
			modal.dispose();
			crearNotificacion(eventoInteresado);
		}
		
		
		
	
	}
	
	private boolean verificarHorario(EventoInteresadoDTO eventoInteresado2) {
		
		if(mismaFecha(eventoInteresado2.getFechaDeCumplimiento(),new Date()) && horarioProximo(eventoInteresado2.getHoraDeCumplimiento())) {
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
	
	
	private void crearNotificacion(EventoInteresadoDTO evento) 
	{
		
			NotificacionDTO notificacion=new NotificacionDTO();
			notificacion.setDescripcion("se te  asigno la tarea de:"+evento.getDescripcion());
			notificacion.setEventoInteresado(evento);
			notificacion.setDisponibleEnSistema(false);
			notificacion.setTitulo("Evento de interesado");
			if (evento.getAdministrativoAsignado()!=null)
				notificacion.setUsuario(modeloPersonas.getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
			notificacion.setFecha(evento.getFechaDeCumplimiento());
			notificacion.setHora(evento.getHoraDeCumplimiento());
			notificacion.setVisto(false);
			notificacion.setCodigoVista("c");
			notificacion.setCodigoTab("5-0");
			modelNotificaciones.agregarNotificacion(notificacion);
	}
	
	private boolean eventoValido() {
		Date in = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		Date actual = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		actual.setHours(0);actual.setMinutes(0);actual.setSeconds(0);//lo seteo asi porque sino toma la fecha del sistema y gralmente hay errores (salvo que sean las 00:00:00)
		
		if (eventoInteresado.getFechaDeCumplimiento().getYear()<actual.getYear() ||
				eventoInteresado.getFechaDeCumplimiento().getMonth()<actual.getMonth() ||
				eventoInteresado.getFechaDeCumplimiento().getDate()<actual.getDate() 
			)
		{
			
			JOptionPane.showMessageDialog(null,"Ingresa una fecha valida.");
			return false;
		}
		return true;
	}

	
	public Time getHorarioCumplimientoElegido() {
		Object value=this.vista.getSpinnerHora().getValue();
		Date date= (Date) value;
		return new Time(date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
	
	
	@Override
	public void changedUpdate(DocumentEvent e)
	{
		if(this.vista.getTextAreaDescripcion().getText().isEmpty() || this.vista.getTextFieldInteresado().getText().isEmpty() || !this.fechaNotNull) {
			this.vista.getBtnAceptar().setEnabled(false);
		}
		else {
			this.vista.getBtnAceptar().setEnabled(true);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		changedUpdate(e);
	}
}
