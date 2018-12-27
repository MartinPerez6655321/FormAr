package presentacion.controlador;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EventoSupervisorDTO;
import dto.PersonalAdministrativoDTO;
import dto.RecadoDTO;
import dto.SupervisorDTO;
import dto.UsuarioDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import modelo.ModeloRecados;
import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.components.tabla.filterimpl.TextFieldFilter;
import presentacion.vista.PanelGestionUsuarios;
import presentacion.vista.VentanaAgregarUsuario;
import presentacion.vista.VentanaEditarUsuario;

public class ControladorPanelGestionUsuarios
{
	private ModeloEventos modelEventos;
	private ModeloRecados modelRecados;
	private ModeloPersonas modeloPersonas;
	private ModeloCursos model;
	
	private UsuarioDTO selectedItem;
	private PanelGestionUsuarios panel;
	
	public ControladorPanelGestionUsuarios(PanelGestionUsuarios vista)
	{
		model=ModeloCursos.getInstance();
		modelEventos = ModeloEventos.getInstance();
		modelRecados=ModeloRecados.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.panel = vista;
		setSelectedItem(null);
		panel.setTable(crearTabla());
		panel.getBtnAgregarUsuario().addActionListener ( e -> agregarUsuario() );
		panel.getBtnEliminarUsuario().addActionListener ( e -> eliminarUsuario() );
		
		panel.getTable().addDoubleClickListener(() -> this.editarSelectedUsuario());
		
	}

	private void setSelectedItem(UsuarioDTO usuario) 
	{
		selectedItem = usuario;
		this.panel.getBtnEliminarUsuario().setEnabled(selectedItem!=null);
	}
	
	public TablaGenerica<UsuarioDTO> crearTabla()
	{
		Transformer<UsuarioDTO> transformer = elem -> new String[] { elem.getEmail() ,
					getPermisos(elem),
					elem.getDisponibleEnSistema() == true ? "[Disponible]":"[No Disponible]"
		};
		
		List<String> filterNames = new LinkedList<>();
		List<FilterComponent<UsuarioDTO>> filters = new LinkedList<>();
		
		filterNames.add("Usuario");
		filters.add(new TextFieldFilter<UsuarioDTO>(elem -> new String[] { elem.getEmail() }));
		
		filterNames.add("Tipo de usuario");
		filters.add(new TextFieldFilter<UsuarioDTO>(elem -> new String[] { this.getPermisos(elem) }));
		
		FilterPanel<UsuarioDTO> filterPanel = new FilterPanel<>(filterNames, filters);
		
		TablaGenerica<UsuarioDTO> table = new TablaGenerica<>(
				new String[] { "Usuario", "Tipo de usuario", "Disponibilidad"}, 
				transformer,  
				() -> modeloPersonas.getUsuarios(),
				filterPanel);
		
		modeloPersonas.addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}

	private void agregarUsuario() {
		ControladorAgregarUsuario controlador = new ControladorAgregarUsuario(new VentanaAgregarUsuario());
		controlador.initialize();	
	}
	
	private void eliminarUsuario() {

		if(selectedItem.getInstructor() && tieneCursadasAsociadas(selectedItem)) {
			JOptionPane.showMessageDialog(null, "Este usuario posee como instructor una o m\u00E1s cursadas asociadas y no puede ser eliminado.");
			return;
		}
		if(selectedItem.getAdministrativo() && tieneEventosPendientesAsociados(selectedItem)) {
			JOptionPane.showMessageDialog(null, "Este usuario posee como administrativo uno o m\u00E1s eventos pendientes asociados y no puede ser eliminado.");
			return;
		}
		if(selectedItem.getSupervisor() && tieneEventosAsociadosComoSupervisor(modeloPersonas.getSupervisor(selectedItem))) {
			JOptionPane.showMessageDialog(null, "Este usuario posee como supervisor uno o m\u00E1s eventos asociados y no puede ser eliminado.");
			return;
		}
		if(tieneRecadosAsociados(selectedItem)) {
			JOptionPane.showMessageDialog(null, "Este usuario posee recados asociados y no puede ser eliminado.");
			return;
		}
				
		if (selectedItem.getAlumno() && !model.getCursadasPorAlumno(modeloPersonas.getAlumno(selectedItem)).isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Este alumno posee cursadas activas");
			return;			
		}
		if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere eliminar usuario?</html>", "Eliminar Usuario",JOptionPane.YES_NO_OPTION)==0) 
		{
			this.modeloPersonas.eliminarUsuario(selectedItem);
		}
		
	}
	
	private void editarSelectedUsuario() {
		this.editarUsuario(this.selectedItem);
	}
	
	private void editarUsuario(UsuarioDTO usuario) {
		ControladorEditarUsuario controlador = new ControladorEditarUsuario(new VentanaEditarUsuario(),usuario);
		controlador.initialize();
	}

	public String getPermisos(UsuarioDTO usuario)
	{
		String permiso="";
		if(usuario.getAdministrador()) {permiso=permiso+"[Administrador] ";}
		if(usuario.getSupervisor()) {permiso=permiso+"[Supervisor] ";}
		if(usuario.getAdministrativo()) {permiso=permiso+"[Personal Admin.] ";}
		if(usuario.getInstructor()) {permiso=permiso+"[Instructor] ";}
		if(usuario.getAlumno()) {permiso=permiso+"[Alumno] ";}
		return permiso;
	}
	
	private boolean tieneEventosPendientesAsociados(UsuarioDTO usuario) {
		PersonalAdministrativoDTO administrativo = modeloPersonas.getAdministrativo(usuario);
		
		if(!(modelEventos.getEventosSupervisorPorAdministrativo(administrativo)).isEmpty()) {
			return true;
		}
		
		if(!(modelEventos.getEventosInteresadosPorAdministrativo(administrativo)).isEmpty()) {
			return true;
		}
	
		if(!(modelEventos.getEventosInasistenciasPorAdministrativo(administrativo)).isEmpty()) {
			return true;
		}
	
		return false;
	}

	private boolean tieneCursadasAsociadas(UsuarioDTO usuario) {
		
		return (!model.getCursadasPorInstructor(modeloPersonas.getInstructor(usuario)).isEmpty());
	}
	

	private boolean tieneEventosAsociadosComoSupervisor(SupervisorDTO supervisor) {
		for(EventoSupervisorDTO e:modelEventos.getEventosSupervisor()) {
			if(e.getSupervisor().getId()==(supervisor.getId())) {
				return true;
			}
		}
		return false;
		
	}

	private boolean tieneRecadosAsociados(UsuarioDTO user) {
		for (RecadoDTO recado: modelRecados.getRecados()) 
		{
			if(recado.getEmisor().equals(user)||recado.getReceptor().equals(user))
				return true;
		}
		return false;
	}
	
}
