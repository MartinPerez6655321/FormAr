package presentacion.controlador;

import java.util.LinkedList;
import java.util.List;

import dto.SalaDTO;
import modelo.ModeloCursos;
import modelo.ModeloSala;
import presentacion.components.abm.ABMGenerico;
import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.filterimpl.BetweenFilter;
import presentacion.components.tabla.filterimpl.TextFieldFilter;
import presentacion.vista.PanelEditarSala;
import presentacion.vista.PanelGestionUsuarios;
import presentacion.vista.VistaAdministrador;

public class ControladorVistaAdministrador
{
	private PanelGestionUsuarios vistaGestionUsuarios;
	
	public ControladorVistaAdministrador(VistaAdministrador vista)
	{
		vistaGestionUsuarios = new PanelGestionUsuarios();
		new ControladorPanelGestionUsuarios(vistaGestionUsuarios);
		

		List<String> filterNames = new LinkedList<>();
		List<FilterComponent<SalaDTO>> filters = new LinkedList<>();

		filterNames.add("Alias");
		filters.add(new TextFieldFilter<SalaDTO>(elem -> new String[] { elem.getAlias() }));
		
		filterNames.add("C\u00F3digo");
		filters.add(new TextFieldFilter<SalaDTO>(elem -> new String[] { elem.getCodigo() }));
		
		filterNames.add("Capacidad");
		filters.add(new BetweenFilter<SalaDTO>(SalaDTO::getCapacidad));
		
		ABMGenerico<SalaDTO> abmSalas = 
				new ABMGenerico<>(
						new String[] { "Alias", "C\u00F3digo", "Capacidad","Estado sala" }, 
						sala -> new String [] { sala.getAlias(), sala.getCodigo(), String.valueOf(sala.getCapacidad()),getEstado(sala.getDisponibleEnSistema()) },  
						() -> ModeloSala.getInstance().getSalas(), 
						new FilterPanel<>(filterNames, filters), 
						ModeloSala.getInstance(),ModeloCursos.getInstance());
		
		abmSalas.setEditor(new ControladorEditarSala(new PanelEditarSala()));
		
		vista.agregarVista("Gesti\u00F3n Cuenta", vistaGestionUsuarios);
		vista.agregarVista("Gesti\u00F3n Salas", abmSalas);
	}

	private String getEstado(Boolean disponibleEnSistema) 
	{
		if (disponibleEnSistema)
			return "Sala disponible";
		return "Sala no disponible";
	}
}
