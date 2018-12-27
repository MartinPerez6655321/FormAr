package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import modelo.ModeloCursos;

import modelo.ModeloEventos;
import modelo.ModeloPersonas;

import presentacion.components.Modal;

import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelVistaAsignarEventos;
import presentacion.vista.VistaEventosInteresadosPendientesCompletados;
import util.Strings;

public class ControladorVistaEventosInteresadosPendientes {

	

	private VistaEventosInteresadosPendientesCompletados vista;
	private ModeloEventos modeloEventos;
	private ModeloCursos modelo;
	private EventoInteresadoDTO selectedItem;

	public ControladorVistaEventosInteresadosPendientes(
			VistaEventosInteresadosPendientesCompletados vistaEventosInteresadosPendientes) {
		vista=vistaEventosInteresadosPendientes;
		modeloEventos=ModeloEventos.getInstance();
		modelo=ModeloCursos.getInstance();
		vista.setTable(crearTabla());
		vista.getBtnAsignarEvento().addActionListener(e->asignarEvento());
		this.setSelectedItem(null);
		
	}

	
	
	private void asignarEvento() 
	{
		
		if(selectedItem.getAdministrativoAsignado()!=null) {
			JOptionPane.showMessageDialog(null, "Este evento ya posee un responsable asignado.");
		}
		else {
			
			PanelVistaAsignarEventos vistaAsignador=new PanelVistaAsignarEventos();
			new ControladorPanelAsignadorEventosInteresados(vistaAsignador,selectedItem);
			Modal.showDialog(vistaAsignador, "Asignar Personal Administrativo a evento", vistaAsignador.getAceptarButton());
			
		}
	}



	public TablaGenerica<EventoInteresadoDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Descripci\u00F3n", "Interesado","Curso","Fecha y hora de llamado","Fecha y hora de cumplimiento", "Responsable"};
		
		Transformer<EventoInteresadoDTO> transformer = elem -> new String[] {elem.getDescripcion(),elem.getInteresado().getPersona().getNombre()+" "+elem.getInteresado().getPersona().getApellido(),getCurso(elem.getCurso()),getFechaHoraLlamado(elem),getFechaHoraCumplimiento(elem),elem.getAdministrativoAsignado() == null ? null :elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido() };
		
		TablaGenerica<EventoInteresadoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarPendientes(modeloEventos.getEventosInteresados()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modelo.addListener(table);

		modeloEventos.addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		


		
		table.addSelectionListener(this::setSelectedItem);

		return table;
		
	}
	private void setSelectedItem(EventoInteresadoDTO evento) 
	{
		selectedItem = evento;
		vista.getBtnAsignarEvento().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());

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



	private List<EventoInteresadoDTO> filtrarPendientes(List<EventoInteresadoDTO> lista){
		
		List<EventoInteresadoDTO> ret=new ArrayList<>();
		
		for(EventoInteresadoDTO e:lista) {
			if(e.getEstado().getId().equals(2)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	
}
