package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.components.tabla.filterimpl.TextFieldFilter;
import presentacion.vista.PanelEstadoCursada;
import util.Strings;

public class ControladorPanelEstadoCursada {

	private PanelEstadoCursada ventana;
	private ModeloCursos model;
	private CursadaDTO selectedItem;

	
	public ControladorPanelEstadoCursada(PanelEstadoCursada ventanaEstadoCursada) {
		this.ventana = ventanaEstadoCursada;
		model = ModeloCursos.getInstance();
		setSelectedItem(null);
		ventana.setTable(crearTabla());
		ventana.getBtnExtenderPeriodoInscripcion().addActionListener(e -> { iniciarCursada();});
		ventana.getBtnCancelarCursada().addActionListener(e -> {cancelarCursada(selectedItem);});
		
		
	}
	
		private boolean cancelarCursada(CursadaDTO selectedItem) {
		selectedItem.setEstado(model.getEstadosCursada().get(2));
		this.liberarRecursos(selectedItem);
		return model.modificarCursada(selectedItem);
		
	}
	
	private void liberarRecursos(CursadaDTO selectedItem) 
	{
		for (EmpresaDTO empresa: model.getEmpresas())
		{
			for(CursadaEmpresaDTO cursadaEmpresa: empresa.getCursadas())
			{
				if (cursadaEmpresa.getCursada().equals(selectedItem))
				{
					cursadaEmpresa.getCursada().setInscripciones(new ArrayList<>());
					model.modificarCursadaEmpresa(cursadaEmpresa);
				}
			}
		}
		
		if(selectedItem.getSala()!=null) {
			selectedItem.setSala(null);
		}
		if(selectedItem.getInstructores()!=null) {
			selectedItem.getInstructores().clear();
		}
		
	}

	private void setSelectedItem(CursadaDTO curso) 
	{
		selectedItem = curso;
		ventana.getBtnExtenderPeriodoInscripcion().setEnabled(selectedItem!=null);
		ventana.getBtnCancelarCursada().setEnabled(selectedItem!=null);
	}
	
	private boolean iniciarCursada() {
		if(selectedItem.getInscripciones().size()<selectedItem.getVacantesMinimas()) {
			if(JOptionPane.showConfirmDialog(null, "¿Desea iniciar esta cursada aunque la cantidad de inscriptos ("+selectedItem.getInscripciones().size()+ ") sea menor a las m\u00EDnimas vacantes ("+ selectedItem.getVacantesMinimas() +") ?", "Confirmar inicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==0) {
				selectedItem.setEstado(model.getEstadosCursada().get(0));
				return model.modificarCursada(selectedItem);
			}
			else {
				return false;
			}
		}
		else {
			selectedItem.setEstado(model.getEstadosCursada().get(0));
			return model.modificarCursada(selectedItem);
		}
		
	}
	
	public TablaGenerica<CursadaDTO> crearTabla()
	{
		
		
		Transformer<CursadaDTO> transformer = elem -> new String[] { elem.getNombre() ,
				elem.getEstado().getNombre(),elem.getInscripciones().size()+"",elem.getVacantesMinimas()+"",Strings.formatoFecha(elem.getInicio())};
		
		TablaGenerica<CursadaDTO> table = new TablaGenerica<>(
				new String[] { "Nombre","Estado","Cantidad de inscriptos","Cantidad de inscriptos m\u00EDnimos","Fecha de inicio" }, 
				transformer,  
				() -> filtrar(model.getCursadas()),
				new FilterPanel<>("Filtro: ", new TextFieldFilter<>(transformer)));
		
		model.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}

	private List<CursadaDTO> filtrar(List<CursadaDTO> cursadas) {
		
		List<CursadaDTO> aux=new ArrayList<>();
		for (CursadaDTO cursada:cursadas) 
		{
			if(cursada.getEstado().getId()==model.getEstadosCursada().get(4).getId()) 
			{	
				aux.add(cursada);
			}
		}
		
		
		
		return aux;
	}

	public PanelEstadoCursada getVentana() {
		return ventana;
	}

}
