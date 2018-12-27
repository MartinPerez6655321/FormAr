package presentacion.controlador;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EventoInasistenciaDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelEditarEventoInasistencia;
import presentacion.vista.PanelVistaAsignarEventos;
import presentacion.vista.VistaEventosInasistenciasSupervisorAsignar;
import util.Strings;

public class ControladorVistaEventosInasistenciasSupervisorAsignar {
	private VistaEventosInasistenciasSupervisorAsignar vista;
	private EventoInasistenciaDTO selectedItem;

	private ModeloEventos modeloEventos;

	private PanelEditarEventoInasistencia panelEditar;

	private ControladorPanelEditarEventoInasistencia controladorPanelEditar;

	public ControladorVistaEventosInasistenciasSupervisorAsignar(
			VistaEventosInasistenciasSupervisorAsignar vistaEventosInasistenciasSupervisorAsignar) {
		vista=vistaEventosInasistenciasSupervisorAsignar;
	
		modeloEventos=ModeloEventos.getInstance();
		vista.setTable(crearTabla());
		setSelectedItem(null);
		vista.getBtnAsignarEvento().addActionListener(e->asignarEvento());
		vista.getTable().addDoubleClickListener(()->editar());
		
	}
	
	private void editar() {
		panelEditar=new PanelEditarEventoInasistencia();
		Modal modal=new Modal(panelEditar);
		new ControladorPanelEditarEventoInasistencia(panelEditar,selectedItem,modal);
		modal.setResizable(false);
		modal.setVisible(true);
	}

	private void asignarEvento() {
		if(selectedItem.getAdministrativoAsignado()!=null) {
			JOptionPane.showMessageDialog(null, "Este evento ya posee un responsable asignado.");
		}
		else {
			
			PanelVistaAsignarEventos vistaAsignador=new PanelVistaAsignarEventos();
			new ControladorPanelAsignadorEventosSupervisor(vistaAsignador,selectedItem);
			Modal.showDialog(vistaAsignador, "Asignar Personal Administrativo a evento", vistaAsignador.getAceptarButton());
			
		}
		
	}

	public TablaGenerica<EventoInasistenciaDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Alumno ausente", "Fecha y hora","Cursada", "Responsable"};
		
		Transformer<EventoInasistenciaDTO> transformer = elem -> new String[] { elem.getAlumno().getPersona().getNombre()+" "+elem.getAlumno().getPersona().getApellido(),getFechaHora(elem) ,
				elem.getCursada().getNombre(),elem.getAdministrativoAsignado() == null ? null :elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido()};
		
		TablaGenerica<EventoInasistenciaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarPendientes(modeloEventos.getEventosInasistencia()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modeloEventos.addListener(table);
		ModeloCursos.getInstance().addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
		
	}
	
	private String getFechaHora(EventoInasistenciaDTO elem) {
		return this.formatoFecha(elem.getFechaDeInasistencia())+" "+this.formatoHorario(elem.getHoraDeCumplimiento()) ;
	}

	private List<EventoInasistenciaDTO> filtrarPendientes(List<EventoInasistenciaDTO> lista){
		
		List<EventoInasistenciaDTO> ret=new ArrayList<>();
		
		for(EventoInasistenciaDTO e:lista) {
			if(e.getEstado().getId().equals(2)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	private void setSelectedItem(EventoInasistenciaDTO evento) 
	{
		selectedItem = evento;
		
		vista.getBtnAsignarEvento().setEnabled(selectedItem!=null && selectedItem.getEstado().getId().equals(modeloEventos.getEstadosEventos().get(1).getId()));
		
	}

	
	private String formatoHorario(Time horaCumplimiento) {
		
		return horaCumplimiento.toString().substring(0, 5);
	}


	private String formatoFecha(Date date) {
		return Strings.formatoFecha(date);
	}
}
