package presentacion.controlador;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;

import dto.PeriodoInscripcionDTO;

import dto.NotificacionDTO;

import modelo.ModeloCursos;
import modelo.ModeloNotificaciones;
import modelo.ModeloSala;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelConsultarInscriptos;
import presentacion.vista.PanelGestionCursada;
import presentacion.vista.VistaAgregarHorario;
import presentacion.vista.VistaAsignacionDeInstructoresACursada;
import presentacion.vista.VistaAsignacionDeSalasACursada;
import util.ValidadorCampos;

public class ControladorPanelGestionCursada
{
	private PanelGestionCursada view;
	private ModeloCursos modelCursos;
	private ModeloNotificaciones modelNotificaciones;
	private CursadaDTO selectedItem;
	
	public ControladorPanelGestionCursada(PanelGestionCursada panelGestionCursada)
	{
		modelCursos = ModeloCursos.getInstance();
		this.view = panelGestionCursada;
		modelNotificaciones=ModeloNotificaciones.getInstance();
		setSelectedItem(null);

		view.getBtnAsignacionDeSala().addActionListener(e -> mostrarAsignarSala());
		view.getbtnDesasignarSala().addActionListener(e -> desasignarSala());
		view.getBtnAsignacionDeInstructores().addActionListener(e -> mostrarAsignarInstructores());
		view.getBtnCerrarCursada().addActionListener(e -> cerrarCursada());
		view.getBtnAgregarHorario().addActionListener(e -> agregarHorario());
		view.getBtnEliminar().addActionListener(e->eliminarCursada());

	
		view.setTable(crearTabla());
		view.getTable().addDoubleClickListener(()->this.verInscriptos());
	}
	
	private void eliminarCursada() {
		
		if(this.selectedItem.getInscripciones().isEmpty()) {
			if(this.selectedItem.getSala()==null) {
				if(this.selectedItem.getInstructores()==null || this.selectedItem.getInstructores().isEmpty()) {
					if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere eliminar esta cursada?</html>", "Eliminar Cursada",JOptionPane.YES_NO_OPTION)==0) {
					
						eliminarNotificacionCursada(selectedItem);
						modelCursos.eliminarCursada(selectedItem);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No es posible eliminar esta cursada porque tiene uno o m\u00E1s instructores asociados.");
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "No es posible eliminar esta cursada porque tiene una sala asociada.");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No es posible eliminar esta cursada porque tiene alumnos inscriptos.");
		}
		
	}

	private void eliminarNotificacionCursada(CursadaDTO selectedItem) {
		
		for(NotificacionDTO e:modelNotificaciones.getNotificaciones()) {
			if(e.getCursada()!=null && e.getCursada().getId()==selectedItem.getId()) {
				modelNotificaciones.eliminarNotificacion(e);
			}
		}
		
	}
	
	private void verInscriptos() {
		PanelConsultarInscriptos panelConsultarInscriptos=new PanelConsultarInscriptos();
		new ControladorPanelConsultarInscriptos(panelConsultarInscriptos,selectedItem);
		Modal.showDialog(panelConsultarInscriptos, "Inscriptos actuales", panelConsultarInscriptos.getBtnAceptar());
	}

	private void agregarHorario() {
		VistaAgregarHorario asignacionHorario = new VistaAgregarHorario(selectedItem);
		Modal.showDialog(asignacionHorario, "Asignacion de horario a cursada", asignacionHorario.getBtnAsignarHorario());
	}

	private void mostrarAsignarSala() 
	{
		VistaAsignacionDeSalasACursada asignacionSalaACursada = new VistaAsignacionDeSalasACursada(selectedItem);
		
		Modal.showDialog(asignacionSalaACursada, "Asignacion de salas a cursada", asignacionSalaACursada.getAsignarButton());
	}
	
	private void desasignarSala() 
	{
		if((!selectedItem.getEstado().getId().equals(1) || 
			JOptionPane.showConfirmDialog(null, "Estï¿½ seguro de que desea desasignar la sala de este curso?", 
					"Desasignar sala de cursada en curso", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION))
			desasignarSala(selectedItem);
	}
	
	private void desasignarSala(CursadaDTO cursada) 
	{
		cursada.setSala(null);
		modelCursos.modificarCursada(cursada);
	}
	
	private void mostrarAsignarInstructores() 
	{
		VistaAsignacionDeInstructoresACursada asignacionInstructoresACursada = new VistaAsignacionDeInstructoresACursada();
		new ControladorAsignacionDeInstructoresACursada(asignacionInstructoresACursada, selectedItem);
		Modal.showDialog(asignacionInstructoresACursada, "Asignar instructores a cursada", asignacionInstructoresACursada.getAceptarButton());
	}
	
	private void cerrarCursada()
	{
		if((!selectedItem.getEstado().getId().equals(1) ||  JOptionPane.showConfirmDialog(null, "Estï¿½ seguro de que desea cancelar esa cursada?", "Cerrar cursada en curso", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) &&
			this.cerrarCursada(selectedItem))
		{
			JOptionPane.showMessageDialog(null, "La cursada fue cancelada exitosamente.");
			
		}
		this.selectedItem.setInscripciones(new ArrayList<InscripcionAlumnoDTO>());
		modelCursos.modificarCursada(selectedItem);
	}
	
	public TablaGenerica<CursadaDTO> crearTabla()
	{
		Transformer<CursadaDTO> transformer = elem -> new String[] 
					{ 
						elem.getNombre(), 
						elem.getSala() == null? "Sin sala asignada" : elem.getSala().getAlias() + "(" + elem.getSala().getCodigo() + ")", 
						elem.getEstado().getNombre(), 
						ValidadorCampos.getHorarios(elem.getHorarios()), 
						instructoresListString(elem.getInstructores()),
						elem.getCurso().getCodigo()+":"+elem.getCurso().getNombre(),
						esCursadaEmpresa(elem)
					};
		
		String[] columnNames = new String[] 
					{ 
						"Nombre", 
						"Sala", 
						"Estado", 
						"Horarios", 
						"Instructores",
						"Codigo y Nombre curso",
						"Es empresa"
					};
		
		TablaGenerica<CursadaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				() -> modelCursos.getCursadas(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modelCursos.addListener(table);
		ModeloSala.getInstance().addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}

	private String esCursadaEmpresa(CursadaDTO elem) 
	{
		for(CursadaEmpresaDTO cursada: modelCursos.getCursadasEmpresas())
		{
			if (cursada.getCursada().equals(elem))
				return "Si";
		}
		return "No";
	}

	private String instructoresListString(List<InstructorDTO> instructores) 
	{
		if(instructores.isEmpty())
			return "Sin instructores asignados";
		
		StringBuilder ret = new StringBuilder();
		
		ret.append(instructorString(instructores.get(0)));
		for(int i = 1; i < instructores.size() - 1; i++)
			ret.append(", " + instructorString(instructores.get(i)));
		if(instructores.size()>1)
			ret.append(" y " + instructorString(instructores.get(instructores.size()-1)));
		
		return ret.toString();
	}
	
	private String instructorString(InstructorDTO instructor) 
	{
		return instructor.getPersona().getApellido() + " " + instructor.getPersona().getNombre();
	}
	
	private boolean cerrarCursada(CursadaDTO selectedItem)
	{
		
		selectedItem.setEstado(modelCursos.getEstadosCursada().get(2));
		this.liberarRecursos(selectedItem);
		
		PeriodoInscripcionDTO periodo=selectedItem.getPeriodosDeInscripcion().get(0);
		periodo.setEstado(modelCursos.getEstadosDePeriodoDeInscripcion().get(2));
		modelCursos.modificarPeriodoDeInscripcion(periodo);
		
		return modelCursos.modificarCursada(selectedItem);
		
	}
	
	private void liberarRecursos(CursadaDTO selectedItem) {
		if(selectedItem.getSala()!=null) {
			selectedItem.setSala(null);
		}
		if(selectedItem.getInstructores()!=null) {
			selectedItem.getInstructores().clear();
		}
		
	}

	private void setSelectedItem(CursadaDTO cursada) 
	{
		selectedItem = cursada;
		
		view.getBtnAsignacionDeSala().setEnabled(selectedItem!=null
				&& !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4)
				&& selectedItem.getDisponibleEnSistema()
				);
		
		view.getbtnDesasignarSala().setEnabled(selectedItem!=null
				&& selectedItem.getSala() != null
				&& !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4));
		
		view.getBtnAsignacionDeInstructores().setEnabled(selectedItem!=null 
				&& !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4)
				&& selectedItem.getDisponibleEnSistema());
		
		view.getBtnCerrarCursada().setEnabled(selectedItem!=null 
				&& !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4)
				&& !selectedItem.getEstado().getId().equals(1)
				&& selectedItem.getDisponibleEnSistema());
		view.getBtnAgregarHorario().setEnabled(selectedItem!=null 
				&& !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4)
				&& selectedItem.getDisponibleEnSistema());
		
		view.getBtnEliminar().setEnabled(selectedItem!=null && !selectedItem.getEstado().getId().equals(3)
				&& !selectedItem.getEstado().getId().equals(4)
				&& selectedItem.getDisponibleEnSistema());
		
	}

	public PanelGestionCursada getView() {
		return view;
	}

	public void setView(PanelGestionCursada view) {
		this.view = view;
	}
	


}
