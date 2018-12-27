package presentacion.controlador;

import dto.InstructorDTO;
import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionInstructor;
import presentacion.vista.VentanaAgregarInstructor;
import presentacion.vista.VentanaEditarInstructor;
import presentacion.vista.VistaConsultarDisponibilidadDeInstructor;

public class ControladorPanelGestionInstructor
{
	private PanelGestionInstructor view;
	private ModeloPersonas modelUser;
	private InstructorDTO selectedItem;
	
	public ControladorPanelGestionInstructor(PanelGestionInstructor panelGestionInstructor)
	{
		modelUser = ModeloPersonas.getInstance();
		this.view = panelGestionInstructor;
		
		setSelectedItem(null);
		
		view.setTable(crearTabla());

		view.getBtnAgregarInstructor().addActionListener( e -> agregarInstructor() );
		
		view.getBtnAsignacionDeSala().addActionListener(
			e -> {
				VistaConsultarDisponibilidadDeInstructor consultaInstructor = new VistaConsultarDisponibilidadDeInstructor(selectedItem);
				Modal.showDialog(consultaInstructor, "Disponibilidad horaria de " + selectedItem.getPersona().getApellido() + ", " + selectedItem.getPersona().getNombre(), consultaInstructor.getBtnOk());
			});
		
		view.getTable().addDoubleClickListener(()->this.editarSelectedUsuario());
	}
	
	private void setSelectedItem(InstructorDTO instructor) 
	{
		selectedItem = instructor;
		
		view.getBtnAsignacionDeSala().setEnabled(selectedItem!=null);
	}

	public TablaGenerica<InstructorDTO> crearTabla()
	{
		Transformer<InstructorDTO> transformer = elem -> new String[] { elem.getPersona().getApellido(), elem.getPersona().getNombre() };
		
		String[] columnNames = new String[] { "Apellido", "Nombre"};
		
		TablaGenerica<InstructorDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> modelUser.getInstructores(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modelUser.addListener(table);

		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}
	
	private void agregarInstructor() 
	{
		ControladorAgregarInstructor controlador = new ControladorAgregarInstructor(new VentanaAgregarInstructor());
		controlador.initialize();
	}
	
	private void editarSelectedUsuario() {
		this.editarInstructor(this.selectedItem);
	}
	
	private void editarInstructor(InstructorDTO instructor) {
		UsuarioDTO usuario = this.modelUser.getUsuarioPersona(instructor.getPersona());
		ControladorEditarInstructor controlador = new ControladorEditarInstructor(new VentanaEditarInstructor(),usuario);
		controlador.initialize();
	}
}
