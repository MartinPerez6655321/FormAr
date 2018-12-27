package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VistaEventosInteresadosPendientesCompletados;
import util.Strings;

public class ControladorVistaEventosInteresadosCompletados {
	private VistaEventosInteresadosPendientesCompletados vista;
	
	private ModeloEventos modeloEventos;

	public ControladorVistaEventosInteresadosCompletados(
			VistaEventosInteresadosPendientesCompletados vistaEventosInteresadosPendientes) {
		vista=vistaEventosInteresadosPendientes;
		modeloEventos=ModeloEventos.getInstance();
		vista.setTable(crearTabla());
		
	}

	
	
	public TablaGenerica<EventoInteresadoDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Descripci\u00F3n", "Interesado","Curso","Fecha y hora de llamado","Fecha y hora de cumplimiento", "Responsable"};
		
		Transformer<EventoInteresadoDTO> transformer = elem -> new String[] {elem.getDescripcion(),elem.getInteresado().getPersona().getNombre()+" "+elem.getInteresado().getPersona().getApellido(),getCurso(elem.getCurso()),getFechaHoraLlamado(elem),getFechaHoraCumplimiento(elem),elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido() };
		
		TablaGenerica<EventoInteresadoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarCompletados(modeloEventos.getEventosInteresados()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		ModeloCursos.getInstance().addListener(table);
		modeloEventos.addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		
		return table;
		
	}
	
	private String getCurso(CursoDTO curso) {
		String ret;
		
		if(curso==null) {
			ret="N/A";
		}
		else {
			ret=curso.getNombre();
		}
		
		
		return ret;
	}

	
	
	private String getFechaHoraLlamado(EventoInteresadoDTO elem) {
		return Strings.formatoFecha(elem.getFechaDeLlamado())+" "+Strings.formatoHorario(elem.getHoraDeLlamado());
	}



	private String getFechaHoraCumplimiento(EventoInteresadoDTO elem) {
		return Strings.formatoFecha(elem.getFechaDeCumplimiento())+" "+Strings.formatoHorario(elem.getHoraDeCumplimiento());
	}



	private List<EventoInteresadoDTO> filtrarCompletados(List<EventoInteresadoDTO> lista){
		
		List<EventoInteresadoDTO> ret=new ArrayList<EventoInteresadoDTO>();
		
		for(EventoInteresadoDTO e:lista) {
			if(e.getEstado().getId().equals(1)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}

}
