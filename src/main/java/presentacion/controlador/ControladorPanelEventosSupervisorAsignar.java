package presentacion.controlador;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EventoSupervisorDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelEventosSupervisorAsignar;
import presentacion.vista.PanelVistaAsignarEventos;
import presentacion.vista.VistaRegistrarEventoSupervisor;
import util.Strings;

public class ControladorPanelEventosSupervisorAsignar {
	private PanelEventosSupervisorAsignar panel;
	private ModeloEventos model;
	private EventoSupervisorDTO selectedItem;

	
	public ControladorPanelEventosSupervisorAsignar(PanelEventosSupervisorAsignar panel) {
		this.panel = panel;
		model = ModeloEventos.getInstance();
		setSelectedItem(null);
		panel.setTable(crearTabla());
		panel.getBtnAsignarEvento().addActionListener(e->asignarEvento());
		panel.getBtnCrearEvento().addActionListener(e->crearEvento());
		panel.getTable().addDoubleClickListener(()->editar());
		
	}
	
	private void editar() {
		VistaRegistrarEventoSupervisor vistaRegistrarEventoSupervisor = new VistaRegistrarEventoSupervisor();
		Modal modal=new Modal(vistaRegistrarEventoSupervisor);
		new ControladorEditarEventoSupervisor(vistaRegistrarEventoSupervisor,selectedItem,modal);
		modal.setResizable(false);
		modal.setVisible(true);
		
	}

	private void crearEvento() {
		
		VistaRegistrarEventoSupervisor vistaRegistrarEventoSupervisor = new VistaRegistrarEventoSupervisor();
		Modal modal=new Modal(vistaRegistrarEventoSupervisor);
		new ControladorRegistrarEventoSupervisor(vistaRegistrarEventoSupervisor,1,modal);
		modal.setResizable(false);
		modal.setVisible(true);
		
	}

	private void asignarEvento() 
	{
		
		if(selectedItem.getAdministrativoAsignado()!=null) {
			JOptionPane.showMessageDialog(null, "Este evento ya posee un responsable asignado.");
		}
		else {
			
			PanelVistaAsignarEventos vistaAsignador=new PanelVistaAsignarEventos();
			new ControladorPanelAsignadorEventosSupervisor(vistaAsignador,selectedItem);
			Modal.showDialog(vistaAsignador, "Asignar Personal Administrativo a evento", vistaAsignador.getAceptarButton());
			
		}
	}

	public TablaGenerica<EventoSupervisorDTO> crearTabla()
	{ 	
		String[] columnNames = new String[] { "Descripci\u00F3n", "Fecha Cumplimiento","Hora de cumplimiento", "Responsable"};
		
		Transformer<EventoSupervisorDTO> transformer = elem -> new String[] { elem.getDescripcion() ,
				elem.getFechaDeCumplimiento()==null ? null:formatoFecha(elem.getFechaDeCumplimiento()),elem.getHoraDeCumplimiento()==null ? null: formatoHorario(elem.getHoraDeCumplimiento()),elem.getAdministrativoAsignado() == null ? null :elem.getAdministrativoAsignado().getPersona().getNombre()+" "+elem.getAdministrativoAsignado().getPersona().getApellido()};
		
		TablaGenerica<EventoSupervisorDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				()->filtrarPendientes(model.getEventosSupervisor()),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		ModeloCursos.getInstance().addListener(table);
		ModeloPersonas.getInstance().addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
		
	}
	
	private List<EventoSupervisorDTO> filtrarPendientes(List<EventoSupervisorDTO> lista){
		
		List<EventoSupervisorDTO> ret=new ArrayList<EventoSupervisorDTO>();
		
		for(EventoSupervisorDTO e:lista) {
			if(e.getEstado().getId().equals(2)) {
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	private String formatoHorario(Time horaCumplimiento) {
		
		return horaCumplimiento.toString().substring(0, 5);
	}


	private String formatoFecha(Date date) {
		return Strings.formatoFecha(date);
	}
	private void setSelectedItem(EventoSupervisorDTO evento) 
	{
		selectedItem = evento;
		
		panel.getBtnAsignarEvento().setEnabled(selectedItem!=null && selectedItem.getEstado().getId().equals(model.getEstadosEventos().get(1).getId()));
		
	}
	

	
	
	
	
	
}
