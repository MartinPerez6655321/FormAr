package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VistaEventos;
import util.Strings;

public class ControladorEventosSupervisor {
	
	private VistaEventos vista;
	private ModeloEventos modeloEventos;
	private ModeloPersonas modeloPersonas;
	private ModeloNotificaciones modelo;
	private EventoSupervisorDTO selectedItem;
	private boolean completados;
	

	public ControladorEventosSupervisor(VistaEventos vistaEventosInteresadoPendientes,boolean completados ) {
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


	private TablaGenerica<EventoSupervisorDTO> crearTabla() {
		Transformer<EventoSupervisorDTO> transformer = elem -> new String[] 
				{ 
					elem.getDescripcion(), 
					Strings.formatoFecha(elem.getFechaDeCumplimiento())+" "+Strings.formatoHorario(elem.getHoraDeCumplimiento()),
					elem.getSupervisor().getPersona().getNombre()+" "+elem.getSupervisor().getPersona().getApellido()
					};
	
		String[] columnNames = new String[] 
				{ 
					"Descripci\u00F3n", "Fecha y hora de cumplimiento","Supervisor"
				};
	
		TablaGenerica<EventoSupervisorDTO> table = new TablaGenerica<>(
			columnNames, 
			transformer,
			() -> filtro(modeloEventos.getEventosSupervisorPorAdministrativo(modeloPersonas.getPersonalAdministrativoActual(modeloPersonas.getUsuarioActual()))),
			FilterPanel.stringFilterPanel(columnNames, transformer));
	
		modeloEventos.addListener(table);
		modeloPersonas.addListener(table);
		modelo.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
	
		return table;
	}
	
	private List<EventoSupervisorDTO> filtro (List<EventoSupervisorDTO> lista){
		List<EventoSupervisorDTO> ret=new ArrayList<>();
		
		if(completados) {
			for(EventoSupervisorDTO e:lista) {
				if(e.getEstado().getId().equals(1)) {
					ret.add(e);
				}
			}
		}
		else {
			for(EventoSupervisorDTO e:lista) {
				if(e.getEstado().getId().equals(2)) {
					ret.add(e);
				}
			}
		}
		
		return ret;
	}
	
	private void setSelectedItem(EventoSupervisorDTO evento) 
	{
		selectedItem = evento;
		vista.getBtnMarcarCumplido().setEnabled(selectedItem!=null );

	}
	
	
	private void marcarComoCumplido() 
	{
		for (NotificacionDTO notificacion: modelo.getNotificacionesPorUsuarioActual())
		{
			if ( notificacion.getEventoSupervisor()!=null && notificacion.getEventoSupervisor().equals(selectedItem))
			{
				String[] partes=notificacion.getCodigoTab().split("-");
				notificacion.setCodigoTab(partes[0]+"-1");
				modelo.modificarNotificacion(notificacion);
			}
		}
		EventoSupervisorDTO e=selectedItem;
		e.setEstado(modeloEventos.getEstadosEventos().get(0));
		modeloEventos.modificarEventoSupervisor(e);
	}

}
