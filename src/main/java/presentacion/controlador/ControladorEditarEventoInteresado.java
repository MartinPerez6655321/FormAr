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

import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import dto.PersonaDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.VistaABMInteresado;
import presentacion.vista.VistaRegistrarEventoNuevo;
import presentacion.vista.subcomponentes.DocumentSizeFilter;

public class ControladorEditarEventoInteresado implements DocumentListener {

	private VistaRegistrarEventoNuevo vista;
	private EventoInteresadoDTO eventoInteresado;
	private ModeloCursos modelCursos;
	private ModeloEventos modelEventos;
	private ModeloPersonas modeloPersonas;
	private Modal modal;
	private boolean fechaNotNull;
	private DefaultStyledDocument doc;
	private VistaABMInteresado vistaInteresado;
	private ControladorVistaABMInteresado controladorVistaABMInteresado;

	public ControladorEditarEventoInteresado(VistaRegistrarEventoNuevo vistaRegistrarEventoNuevo, Modal modal, EventoInteresadoDTO selectedItem) {
		
		this.vista = vistaRegistrarEventoNuevo;
		this.eventoInteresado=selectedItem;
		modelCursos = ModeloCursos.getInstance();
		modelEventos=ModeloEventos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista.getBtnAceptar().setEnabled(false);
	
		this.fechaNotNull=false;
		this.modal=modal;
		this.vista.getDateFechaLimite().getDateEditor().addPropertyChangeListener(e->evaluadorFechaNotNull());
		
		this.llenarComboBoxCursos();
		
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
		
		
		this.vista.getTextFieldInteresado().getDocument().addDocumentListener(this);
		this.vista.getTextAreaDescripcion().setDocument(doc);
		this.vista.getTextAreaDescripcion().getDocument().addDocumentListener(this);
		
		
		this.vista.getBtnAceptar().addActionListener(e->registrarEvento());
		this.vista.getBtnAsignarInteresado().addActionListener(e->asignarInteresado());
		
		this.llenarCampos();
	}
	
	
	private void llenarCampos() {
		vista.getTextAreaDescripcion().setText(eventoInteresado.getDescripcion());
		vista.getDateFechaLimite().setDate(eventoInteresado.getFechaDeCumplimiento());
		llenarCampoInteresado(eventoInteresado.getInteresado().getPersona());
		vista.getSpinnerHora().setValue(eventoInteresado.getHoraDeCumplimiento());
		
		if(eventoInteresado.getCurso()!=null) {
			vista.getComboBoxCurso().setSelectedIndex(eventoInteresado.getCurso().getId());}
		
	}


	private void asignarInteresado() 
	{
		vistaInteresado=new VistaABMInteresado();
		eventoInteresado.setFechaDeLlamado(new Date());
		controladorVistaABMInteresado=new ControladorVistaABMInteresado(vistaInteresado,eventoInteresado);
		llenarCampoInteresado(controladorVistaABMInteresado.getInteresadoElegido());
	}


	
	private void llenarCampoInteresado(PersonaDTO interesadoElegido) {
		if(interesadoElegido!=null) {
			this.vista.getTextFieldInteresado().setText(interesadoElegido.getNombre()+" "+interesadoElegido.getApellido());
		}
		
	}
	
	private void registrarEvento() 
	{
		
		System.out.println("aca");
		
		if(eventoInteresado !=null) {
			eventoInteresado.setDescripcion(vista.getTextAreaDescripcion().getText());
			eventoInteresado.setEstado(modelEventos.getEstadosEventos().get(1));
		
			eventoInteresado.setFechaDeCumplimiento(vista.getDateFechaLimite().getDate());
			eventoInteresado.setDisponibleEnSistema(true);
			Date date=new Date();
			eventoInteresado.setFechaDeLlamado(date);
			eventoInteresado.setHoraDeLlamado(new Time(date.getHours(),date.getMinutes(),date.getSeconds()));
			
		}
		
		
		if(!this.vista.getComboBoxCurso().getSelectedItem().equals("")) {
			
			CursoDTO curso=new CursoDTO();
			for(CursoDTO c:modelCursos.getCursos()) {
				
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
			modelEventos.modificarEventoInteresado(eventoInteresado);
			modal.dispose();
		}
		
		
		
	
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
	
	
	private boolean verificarHorario(EventoInteresadoDTO eventoInteresado2) {
		
		if(mismaFecha(eventoInteresado2.getFechaDeCumplimiento(),new Date()) && horarioProximo(eventoInteresado2.getHoraDeCumplimiento())) {
			return false;
		}
		return true;
	}
	
	
	public Time getHorarioCumplimientoElegido() {
		Object value=this.vista.getSpinnerHora().getValue();
		Date date= (Date) value;
		return new Time(date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
	
	private void updateCount()
    {
        vista.getLblCaracteresRestantes().setText((400 -doc.getLength()) + " carácteres restantes");
    }

	private void evaluadorFechaNotNull() {
		if(this.vista.getDateFechaLimite().getDate()!=null) {
			fechaNotNull=true;
			if(!this.vista.getTextAreaDescripcion().getText().isEmpty() && !this.vista.getTextFieldInteresado().getText().isEmpty()) {
				this.vista.getBtnAceptar().setEnabled(true);
			}
		}
	}
	
	private void llenarComboBoxCursos() {
		this.vista.getComboBoxCurso().addItem("");
		for(CursoDTO curso:modelCursos.getCursos()) {
			this.vista.getComboBoxCurso().addItem(curso.getNombre()); 
		}
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
