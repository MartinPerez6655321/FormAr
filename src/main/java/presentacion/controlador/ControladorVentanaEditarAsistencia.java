package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import dto.AsistenciaDTO;
import dto.CursadaDTO;
import dto.EstadoAsistenciaDTO;
import dto.EventoInasistenciaDTO;
import dto.NotificacionDTO;
import dto.PlanillaDeAsistenciasDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaEditarAsistencia;

public class ControladorVentanaEditarAsistencia implements ActionListener {
	private VentanaEditarAsistencia view;

	private AsistenciaDTO asistenciaRecibida;
	private PlanillaDeAsistenciasDTO planillaRecibida;
	private CursadaDTO cursadaRecibida;
	private ModeloNotificaciones modelNotificaciones;
	private ModeloCursos model;
	
	private ModeloEventos modelEventos;

	public ControladorVentanaEditarAsistencia(VentanaEditarAsistencia ventana, AsistenciaDTO asistencia,
			PlanillaDeAsistenciasDTO planillaRecibida, CursadaDTO cursadaRecibida) {
		modelNotificaciones=ModeloNotificaciones.getInstance();
		modelEventos = ModeloEventos.getInstance();
		model=ModeloCursos.getInstance();
		this.view = ventana;
		this.asistenciaRecibida = asistencia;
		this.planillaRecibida=planillaRecibida;
		this.cursadaRecibida=cursadaRecibida;
			
		this.view.getBtnGuardar().addActionListener(this);
		this.view.llenarCombo(model.getEstadoAsistencia());
		this.setearPosicionComboBox();

	}

	private void setearPosicionComboBox() {
		this.view.getComboEstados().setSelectedIndex(asistenciaRecibida.getEstado().getId()-2);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.view.getBtnGuardar()) 
		{

			
			
			
			
			for (EstadoAsistenciaDTO estado : model.getEstadoAsistencia()) 
			{
				if (estado.getNombre().equals(this.view.getComboEstados().getSelectedItem())) 
				{
					
					int idAnterior=asistenciaRecibida.getEstado().getId();
					asistenciaRecibida.setEstado(estado);
					model.modificarAsistencia(asistenciaRecibida);
					
					if (idAnterior==4 && asistenciaRecibida.getEstado().getId()!=4)
					{
						EventoInasistenciaDTO evento=buscarEventoAsociado();
						eliminarNotificacion(evento);
						eliminarEvento(evento);
					}
					else if (idAnterior!=4 && asistenciaRecibida.getEstado().getId().equals(4))
					{
						EventoInasistenciaDTO eventoNuevo = new EventoInasistenciaDTO();
						eventoNuevo.setAlumno(asistenciaRecibida.getAlumno());
						eventoNuevo.setCursada(cursadaRecibida);
						eventoNuevo.setEstado(modelEventos.getEstadosEventos().get(1));
						eventoNuevo.setDisponibleEnSistema(true);
						eventoNuevo.setFechaDeInasistencia(this.planillaRecibida.getFecha());
						
						eventoNuevo.setHoraDeCumplimiento(new Time(10, 0, 0));
						eventoNuevo.setAdministrativoAsignado(null);
	
						modelEventos.agregarEventoInasistencia(eventoNuevo);
	
						crearNotificacion(eventoNuevo);
					}
					break;

					
				}

			}


			model.modificarAsistencia(asistenciaRecibida);
			
			
			
			
			
		}

	}

	private EventoInasistenciaDTO buscarEventoAsociado() {
		for(EventoInasistenciaDTO e:ModeloEventos.getInstance().getEventosInasistencia()) {
			if(e.getAlumno().getId()==asistenciaRecibida.getAlumno().getId() && e.getFechaDeInasistencia().equals(planillaRecibida.getFecha()) && e.getCursada().getId()==cursadaRecibida.getId()) {
				return e;
			}
			
		}
		return null;
	}

	private void eliminarEvento(EventoInasistenciaDTO evento) {
		ModeloEventos.getInstance().eliminarEventoInasistencia(evento);
	}

	private void eliminarNotificacion(EventoInasistenciaDTO eventoAsociado) {
		if(eventoAsociado==null) {
			return;
		}
		for(NotificacionDTO e:ModeloNotificaciones.getInstance().getNotificaciones()) 
		{
			if(e.getEventoInasistencia()!=null && e.getEventoInasistencia().getId()==eventoAsociado.getId()) 
			{
				ModeloNotificaciones.getInstance().eliminarNotificacion(e);
				break;
			}
		}
		
	}

	private void crearNotificacion(EventoInasistenciaDTO evento) {

		NotificacionDTO notificacion = new NotificacionDTO();
		notificacion.setCursada(evento.getCursada());
		notificacion.setDescripcion("El alumno: "+evento.getAlumno().getPersona().getNombre()+" "+evento.getAlumno().getPersona().getApellido()+" falt\u00F3 a: "+evento.getCursada().getNombre());
		notificacion.setEventoInasistencia(evento);
		notificacion.setDisponibleEnSistema(false);
		notificacion.setTitulo("Evento de inasistencia");
		if (evento.getAdministrativoAsignado() != null)
			notificacion.setUsuario(ModeloPersonas.getInstance().getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
		notificacion.setFecha(evento.getFechaDeInasistencia());
		notificacion.setHora(evento.getHoraDeCumplimiento());
		notificacion.setVisto(false);
		notificacion.setCodigoVista("c");
		notificacion.setCodigoTab("7-0");
		modelNotificaciones.agregarNotificacion(notificacion);
	}

}
