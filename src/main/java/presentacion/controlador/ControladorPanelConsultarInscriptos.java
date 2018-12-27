package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.InscripcionAlumnoDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.components.tabla.filterimpl.TextFieldFilter;
import presentacion.vista.PanelConsultarInscriptos;

public class ControladorPanelConsultarInscriptos {
	private PanelConsultarInscriptos panel;
	private CursadaDTO cursadaConsultada;
	private ModeloCursos model;
	
	public ControladorPanelConsultarInscriptos(PanelConsultarInscriptos panelConsultarInscriptos,
			CursadaDTO selectedItem) {
		model=ModeloCursos.getInstance();
		this.panel=panelConsultarInscriptos;
		this.cursadaConsultada=selectedItem;
		panel.setTable(crearTabla());
		
	}

	private TablaGenerica<AlumnoDTO> crearTabla() {
		Transformer<AlumnoDTO> transformer = elem -> new String[] {elem.getPersona().getNombre()+" "+elem.getPersona().getApellido(),elem.getLegajo()};
		
		TablaGenerica<AlumnoDTO> table = new TablaGenerica<>(
				new String[] { "Nombre y apellido", "Legajo" }, 
				transformer,  
				() -> obtenerAlumnosInscriptos(cursadaConsultada.getInscripciones()),
				new FilterPanel<>("Filtro: ", new TextFieldFilter<>(transformer)));
		
		model.addListener(table);
		return table;
	}
	


	private List<AlumnoDTO> obtenerAlumnosInscriptos(List<InscripcionAlumnoDTO> inscripciones) {
		 List<AlumnoDTO> ret=new ArrayList<>();
		 for(InscripcionAlumnoDTO e:cursadaConsultada.getInscripciones()) {
			 if(!ret.contains(e.getAlumno())) {
				 ret.add(e.getAlumno());}
		 }
		 return ret;
	}

}
