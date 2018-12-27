package presentacion.controlador;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.EventoSupervisorDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelEventosSupervisorCompletados;
import util.Strings;

public class ControladorPanelEventosSupervisorCompletados {

	private PanelEventosSupervisorCompletados panel;
	private ModeloEventos model;

	public ControladorPanelEventosSupervisorCompletados(PanelEventosSupervisorCompletados vistaEventosCompletadosSupervisor) {
		
		panel=vistaEventosCompletadosSupervisor;
		model = ModeloEventos.getInstance();
		panel.setTable(crearTabla());
	}

	public TablaGenerica<EventoSupervisorDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Descripci\u00F3n", "Fecha Cumplimiento","Hora de cumplimiento", "Responsable"};
		
		Transformer<EventoSupervisorDTO> transformer = elem -> new String[] { elem.getDescripcion() ,
				elem.getFechaDeCumplimiento()==null ? null:formatoFecha(elem.getFechaDeCumplimiento()),elem.getHoraDeCumplimiento()==null ? null: formatoHorario(elem.getHoraDeCumplimiento()),elem.getAdministrativoAsignado() == null ? null :elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido()};
		
		TablaGenerica<EventoSupervisorDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarCompletados(model.getEventosSupervisor()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		ModeloCursos.getInstance().addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		return table;
		
	}
	
	private List<EventoSupervisorDTO> filtrarCompletados(List<EventoSupervisorDTO> lista){
		
		List<EventoSupervisorDTO> ret=new ArrayList<EventoSupervisorDTO>();
		
		for(EventoSupervisorDTO e:lista) {
			if(e.getEstado().getId().equals(1)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	private String formatoHorario(Time horaCumplimiento) {
		
		return horaCumplimiento.toString().substring(0, 5);
	}


	private String formatoFecha(Date date) {
		return Strings.formatoFecha(date);
	}

}
