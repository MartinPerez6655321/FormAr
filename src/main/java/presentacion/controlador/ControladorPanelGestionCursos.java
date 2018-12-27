package presentacion.controlador;

import javax.swing.JOptionPane;

import dto.CursoDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionCursos;
import presentacion.vista.VentanaAgregarCursada;
import presentacion.vista.VentanaAgregarCurso;
import presentacion.vista.VentanaEditarCurso;

public class ControladorPanelGestionCursos{
	
	private PanelGestionCursos panel;
	private ModeloCursos model;
	private CursoDTO selectedItem;

	public ControladorPanelGestionCursos(PanelGestionCursos panel) {

		this.panel = panel;
		model = ModeloCursos.getInstance();
		setSelectedItem(null);
		panel.setTable(crearTabla());
		panel.getBtnAgregarCurso().addActionListener
		(
				e -> { agregarCursoNuevo();}
				);
		panel.getBtnAgregarCursada().addActionListener(e -> agregarCursadaNueva());
		panel.getTable().addDoubleClickListener(()->this.editarSelectedCurso());
		
	}


	private void agregarCursadaNueva() {
		if (this.panel.getTable().getSelectedElem() == null) {
			JOptionPane.showMessageDialog(null, "Selecciona un curso");
		} else {
			this.selectedItem = this.panel.getTable().getSelectedElem();
			new ControladorAgregarCursada(new VentanaAgregarCursada(), this.selectedItem).initialize();
		}
		
	}


	private void agregarCursoNuevo() 
	{
		new ControladorAgregarCurso(new VentanaAgregarCurso()).initialize();
	}


	private void setSelectedItem(CursoDTO curso) 
	{
		selectedItem = curso;
		panel.getBtnAgregarCursada().setEnabled(selectedItem!=null && selectedItem.getDisponibleEnSistema());

		panel.getBtnAgregarCurso().setEnabled(true);

	}
	
	public TablaGenerica<CursoDTO> crearTabla()
	{
		Transformer<CursoDTO> transformer = elem -> new String[] 
				{ 
					elem.getNombre(),
					elem.getDescripcion(),
					elem.getCodigo(),
					Integer.toString(elem.getPrecio()), 
					Integer.toString(elem.getHoras()),
					getEstado(elem.getDisponibleEnSistema())
					
				};
		

		String[] columnNames = new String[] { "Nombre","Descripci\u00F3n","C\u00F3digo", "Precio", "Duraci\u00F3n (en horas)","Estado" };

		
		TablaGenerica<CursoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> model.getCursos(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}
	
	
	private String getEstado(Boolean disponibleEnSistema) 
	{
		if (disponibleEnSistema)
			return "Curso activo";
		else
			return "Curso inactivo";
	}


	private void editarSelectedCurso() {
		if (!this.panel.getTable().getSelectedElem().getDisponibleEnSistema())
		{
			panel.getBtnAgregarCursada().setEnabled(false);
			
		}
		else {
			this.editarCurso(this.selectedItem);
		}
		
	}
	
	
	private void editarCurso(CursoDTO curso) {
		new ControladorVentanaEditarCurso(new VentanaEditarCurso(), curso).initialize();
	}
}