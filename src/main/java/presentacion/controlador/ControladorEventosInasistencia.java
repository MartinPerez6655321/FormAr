package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.EventoInasistenciaDTO;
import dto.NotificacionDTO;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VistaEventos;
import util.Strings;

public class ControladorEventosInasistencia {
	private VistaEventos vista;
	private ModeloEventos modeloEventos;
	private ModeloPersonas modeloPersonas;
	private ModeloNotificaciones modelo;
	private EventoInasistenciaDTO selectedItem;
	private boolean completados;
	

	public ControladorEventosInasistencia(VistaEventos vistaEventosInteresadoPendientes,boolean completados ) {
		this.vista=vistaEventosInteresadoPendientes;
		this.modeloEventos=ModeloEventos.getInstance();
		this.modelo=ModeloNotificaciones.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.completados=completados;
		this.vista.setTable(crearTabla());
		
		
		if(completados) {
			vista.getBtnMarcarCumplido().setVisible(false);
		}
		vista.getBtnCrearEvento().setVisible(false);
		vista.getBtnMarcarCumplido().addActionListener(e->marcarComoCumplido());
		this.setSelectedItem(null);
		
	}


	private TablaGenerica<EventoInasistenciaDTO> crearTabla() {
		Transformer<EventoInasistenciaDTO> transformer = elem -> new String[] 
				{ 
					elem.getAlumno().getPersona().getNombre(),
					elem.getCursada().getNombre()+" ("+elem.getCursada().getCurso().getNombre()+")",
					elem.getAlumno().getPersona().getTelefono(),
					elem.getAlumno().getPersona().getEmail(),
					Strings.formatoFecha(elem.getFechaDeInasistencia()),
					Strings.formatoHorario(elem.getHoraDeCumplimiento())
					};
	
		String[] columnNames = new String[] 
				{ 
					"Alumno ausente","Cursada","Tel\u00E9fono","Email","Fecha de inasistencia","Hora de cumplimiento"
				};
	
		TablaGenerica<EventoInasistenciaDTO> table = new TablaGenerica<>(
			columnNames, 
			transformer,
			() -> filtro(modeloEventos.getEventosInasistenciasPorAdministrativo(modeloPersonas.getPersonalAdministrativoActual(modeloPersonas.getUsuarioActual()))),
			FilterPanel.stringFilterPanel(columnNames, transformer));
	
		modeloEventos.addListener(table);
		modelo.addListener(table);
		modeloPersonas.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
	
		return table;
	}
	
	private List<EventoInasistenciaDTO> filtro (List<EventoInasistenciaDTO> lista){
		List<EventoInasistenciaDTO> ret=new ArrayList<>();
		
		if(completados) {
			for(EventoInasistenciaDTO e:lista) {
				if(e.getEstado().getId().equals(1)) {
					ret.add(e);
				}
			}
		}
		else {
			for(EventoInasistenciaDTO e:lista) {
				if(e.getEstado().getId().equals(2)) {
					ret.add(e);
				}
			}
		}
		
		return ret;
	}
	
	private void setSelectedItem(EventoInasistenciaDTO evento) 
	{
		selectedItem = evento;
		vista.getBtnMarcarCumplido().setEnabled(selectedItem!=null );

	}
	
	
	private void marcarComoCumplido() 
	{
		for (NotificacionDTO notificacion: modelo.getNotificacionesPorUsuarioActual())
		{
			if (notificacion.getEventoInasistencia()!=null && notificacion.getEventoInasistencia().equals(selectedItem))
			{
				String[] partes=notificacion.getCodigoTab().split("-");
				notificacion.setCodigoTab(partes[0]+"-1");
				modelo.modificarNotificacion(notificacion);
			}
		}
		EventoInasistenciaDTO e=selectedItem;
		e.setEstado(modeloEventos.getEstadosEventos().get(0));
		modeloEventos.modificarEventoInasistencia(e);
		
		
		
	}


}
