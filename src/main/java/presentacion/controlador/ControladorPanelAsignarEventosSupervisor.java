package presentacion.controlador;

import dto.EventoSupervisorDTO;
import dto.PersonalAdministrativoDTO;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelVistaAsignarEventos;

public class ControladorPanelAsignarEventosSupervisor {
	
	private PanelVistaAsignarEventos vista;
	private ModeloPersonas modeloPersonas;
	private PersonalAdministrativoDTO selectedItem;
	private EventoSupervisorDTO eventoAsignar;
	private PersonalAdministrativoDTO personalElegido;
	
	public ControladorPanelAsignarEventosSupervisor(PanelVistaAsignarEventos vistaAsignador,EventoSupervisorDTO evento) {

		
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista=vistaAsignador;
		vista.setTable(generarTabla());
		this.eventoAsignar=evento;

		
		vista.getAceptarButton().addActionListener(e -> aceptar());
		this.setSelectedItem(null);
	}
	
	private TablaGenerica<PersonalAdministrativoDTO> generarTabla() {
		String[] columnNames = new String[] { "Nombre y apellido", "Email","Tel\u00E9fono" };
	
		Transformer<PersonalAdministrativoDTO> transformer = elem -> new String[] { elem.getPersona().getNombre()+" "+elem.getPersona().getApellido() ,
				elem.getPersona().getEmail(),elem.getPersona().getTelefono()};
		
		TablaGenerica<PersonalAdministrativoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> modeloPersonas.getListaPersonalAdministrativo(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modeloPersonas.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}
	
	private void setSelectedItem(PersonalAdministrativoDTO personal) 
	{
		selectedItem = personal;
		vista.getAceptarButton().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());

	}
	private void aceptar() 
	{
		
		eventoAsignar.setAdministrativoAsignado(selectedItem);
		this.personalElegido = selectedItem;
		
		
		
	}
	
	public PersonalAdministrativoDTO getPersonalElegido() 
	{
		return this.personalElegido;
	}
	
	
	

}
