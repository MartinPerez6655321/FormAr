package presentacion.controlador;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.EventoInasistenciaDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VistaEventosInasistenciaCompletados;
import util.Strings;

public class ControladorVistaEventosInasistenciaCompletados {
	

	private VistaEventosInasistenciaCompletados vista;
	
	private ModeloEventos modeloEventos;

	public ControladorVistaEventosInasistenciaCompletados(
			VistaEventosInasistenciaCompletados vistaEventosInasistenciaCompletados) {
		modeloEventos=ModeloEventos.getInstance();
		vista=vistaEventosInasistenciaCompletados;

		vista.setTable(crearTabla());
		
	}

	
	public TablaGenerica<EventoInasistenciaDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Alumno ausente", "Fecha y hora","Cursada", "Responsable"};
		
		Transformer<EventoInasistenciaDTO> transformer = elem -> new String[] { elem.getAlumno().getPersona().getNombre()+" "+elem.getAlumno().getPersona().getApellido(),getFechaHora(elem) ,
				elem.getCursada().getNombre(),elem.getAdministrativoAsignado() == null ? null :elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido()};
		
		TablaGenerica<EventoInasistenciaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarCompletados(modeloEventos.getEventosInasistencia()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modeloEventos.addListener(table);
		ModeloCursos.getInstance().addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		
		return table;
		
	}
	
	private List<EventoInasistenciaDTO> filtrarCompletados(List<EventoInasistenciaDTO> lista){
		
		List<EventoInasistenciaDTO> ret=new ArrayList<EventoInasistenciaDTO>();
		
		for(EventoInasistenciaDTO e:lista) {
			if(e.getEstado().getId().equals(1)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	private String getFechaHora(EventoInasistenciaDTO elem) {
		return this.formatoFecha(elem.getFechaDeInasistencia())+" "+this.formatoHorario(elem.getHoraDeCumplimiento()) ;
	}

	
	private String formatoHorario(Time horaCumplimiento) {
		
		return horaCumplimiento.toString().substring(0, 5);
	}


	private String formatoFecha(Date date) {
		return Strings.formatoFecha(date);
	}
}
