package presentacion.controlador;




import javax.swing.JOptionPane;

import dto.EventoInteresadoDTO;
import dto.EventoSupervisorDTO;
import dto.InteresadoDTO;
import dto.PersonaDTO;

import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.components.tabla.filterimpl.TextFieldFilter;
import presentacion.vista.VistaABMInteresado;
import presentacion.vista.VistaNuevoInteresado;

public class ControladorVistaABMInteresado {
	private VistaABMInteresado vista;
	private ModeloPersonas modeloPersonas;
	private PersonaDTO selectedItem;
	private EventoInteresadoDTO evento;
	private EventoSupervisorDTO eventoSupervisor;
	private PersonaDTO interesadoElegido;
	
	
	
	
	public ControladorVistaABMInteresado(VistaABMInteresado vistaInteresado,EventoInteresadoDTO evento) {
		this.vista=vistaInteresado;
		this.evento=evento;
		modeloPersonas = ModeloPersonas.getInstance();
		vista.setTable(this.crearTabla());
		setSelectedItem(null);
		vista.getBtnAceptar().addActionListener(e->aceptar());
		vista.getBtnNuevoInteresado().addActionListener(e->nuevoInteresado());
		vista.getBtnEliminar().addActionListener(e->eliminarInteresado());
		
		vista.show();
	}

	public ControladorVistaABMInteresado(VistaABMInteresado vistaInteresado, EventoSupervisorDTO evento) {
		this.vista=vistaInteresado;
		this.eventoSupervisor=evento;
		modeloPersonas = ModeloPersonas.getInstance();
		vista.setTable(this.crearTabla());
		setSelectedItem(null);
		vista.getBtnAceptar().addActionListener(e->aceptar());
		vista.getBtnNuevoInteresado().addActionListener(e->nuevoInteresado());
		vista.getBtnEliminar().addActionListener(e->eliminarInteresado());
		
		vista.show();
	}

	private void eliminarInteresado() {
		InteresadoDTO e=modeloPersonas.getInteresadoPorPersona(selectedItem);
		
		if(tieneEventosAsociados(e)) {
			JOptionPane.showMessageDialog(null, "<html>No es posible eliminar este interesado porque tiene eventos asociados.</html>");
			return;
		}
		
		modeloPersonas.eliminarInteresado(e);
		
	}

	private boolean tieneEventosAsociados(InteresadoDTO e) {
		return (!ModeloEventos.getInstance().getEventosInteresadoPorInteresado(e).isEmpty());
	}

	private void nuevoInteresado() {
		VistaNuevoInteresado vistaNuevoInteresado=new VistaNuevoInteresado();
		Modal modal=new Modal(vistaNuevoInteresado);
		ControladorRegistrarInteresado controladorVistaNuevoInteresado=new ControladorRegistrarInteresado(vistaNuevoInteresado,modal);
		modal.setVisible(true);
		
		
	}

	private void aceptar() {
		if(evento!=null) {
			evento.setInteresado(this.traerInteresado(selectedItem));
			interesadoElegido=selectedItem;
		}
		
		this.vista.close();
	}

	private InteresadoDTO traerInteresado(PersonaDTO selectedItem2) {
		InteresadoDTO ret=modeloPersonas.getInteresadoPorPersona(selectedItem);
		
		if(ret==null) {
			if(selectedItem2!=null) {
				ret=crearInteresadoAlumno(selectedItem2);
					modeloPersonas.agregarInteresado(ret);	
			}
		}
		
		return ret;
	}

	private InteresadoDTO crearInteresadoAlumno(PersonaDTO selectedItem2) {
		InteresadoDTO e=new InteresadoDTO();
		e.setPersona(selectedItem2);
		e.setDisponibleEnSistema(true);
		e.setId(0);
		
		return e;
	}

	private TablaGenerica<PersonaDTO> crearTabla(){
		Transformer<PersonaDTO> transformer = elem -> new String[] { elem.getNombre()+" "+elem.getApellido() ,
				elem.getTelefono(),elem.getEmail()};
		
		TablaGenerica<PersonaDTO> table = new TablaGenerica<>(
				new String[] { "Nombre y apellido", "Tel\u00E9fono", "Email"}, 
				transformer,  
				() -> 	modeloPersonas.getPersonas(),
				new FilterPanel<>("Filtro: ", new TextFieldFilter<>(transformer)));
		
		modeloPersonas.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}
	
	private void setSelectedItem(PersonaDTO interesado) 
	{
		selectedItem = interesado;
		vista.getBtnAceptar().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());
		vista.getBtnEliminar().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());
	}

	public PersonaDTO getInteresadoElegido() {
		return interesadoElegido;
	}
	
}
