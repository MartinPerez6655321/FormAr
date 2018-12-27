package presentacion.controlador;

import dto.EventoInteresadoDTO;
import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import dto.PersonalAdministrativoDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelVistaAsignarEventos;

public class ControladorPanelAsignadorEventosInteresados {

	private ModeloEventos modelEventos;
	private PanelVistaAsignarEventos vista;
	private EventoInteresadoDTO evento;
	private PersonalAdministrativoDTO selectedItem;

	public ControladorPanelAsignadorEventosInteresados(PanelVistaAsignarEventos vistaAsignador,
			EventoInteresadoDTO selectedItem) {
		modelEventos = ModeloEventos.getInstance();
		this.vista=vistaAsignador;
		vista.setTable(generarTabla());
		this.evento=selectedItem;

		
		vista.getAceptarButton().addActionListener(e -> aceptar());
		this.setSelectedItem(null);
	}

	private void setSelectedItem(PersonalAdministrativoDTO personalAdminisitrativo) 
	{
		selectedItem = personalAdminisitrativo;
		vista.getAceptarButton().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());

	}

	private void aceptar() {
		evento.setAdministrativoAsignado(selectedItem);
		
		modelEventos.modificarEventoInteresado(evento);
		
		
	}

	private TablaGenerica<PersonalAdministrativoDTO> generarTabla() {
		String[] columnNames = new String[] { "Nombre y apellido", "Email","Tel\u00E9fono" };
	
		Transformer<PersonalAdministrativoDTO> transformer = elem -> new String[] { elem.getPersona().getNombre()+" "+elem.getPersona().getApellido() ,
				elem.getPersona().getEmail(),elem.getPersona().getTelefono()};
		
		TablaGenerica<PersonalAdministrativoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> ModeloPersonas.getInstance().getListaPersonalAdministrativo(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modelEventos.addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}

}
