package presentacion.controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dto.CursoDTO;
import dto.EmpresaDTO;

import modelo.ModeloCursos;

import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VentanaAgregarCursada;
import presentacion.vista.VentanaAgregarCurso;
import presentacion.vista.VentanaListarCursadaEmpresas;
import presentacion.vista.VentanaListarCursos;


public class ControladoListarCursos implements ActionListener 
{
	private VentanaListarCursos view;
	private ModeloCursos model;
	private CursoDTO selectedItem;
	private EmpresaDTO empresaRecibida;
	
	
	
	public ControladoListarCursos(VentanaListarCursos ventanaListarCursos, EmpresaDTO empresaRecibida) 
	{
		this.empresaRecibida=empresaRecibida;
		this.model=ModeloCursos.getInstance();
		this.view=ventanaListarCursos;
		setSelectedItem(null);

		view.setTable(crearTabla());
		view.getBtnAgregarCurso().addActionListener(this);
		view.getBtnAgregarCursada().addActionListener(this);
		view.getBtnSeleccionarCursadExistente().addActionListener(this);
	}




	private TablaGenerica<CursoDTO> crearTabla() 
	{

		Transformer<CursoDTO> transformer = elem -> new String[]
				{
				elem.getNombre(),
				elem.getCodigo(),
				elem.getDescripcion(),
				getDisponible(elem.getDisponibleEnSistema())};

		Obtainer<CursoDTO> obtainer = new Obtainer<CursoDTO>() {

			@Override
			public List<CursoDTO> obtain() 
			{
				return model.getCursos();
			}

		};

		String[] columnNames = new String[] { "Nombre", "C\u00F3digo","Descripci\u00F3n","Curso Activo" };

		TablaGenerica<CursoDTO> table = new TablaGenerica<>(columnNames, transformer, obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);

		table.addSelectionListener(this::setSelectedItem);

		return table;
	}




	private String getDisponible(Boolean disponibleEnSistema) 
	{
		if (disponibleEnSistema)
			return "Si";
		return "No";
	}




	private void setSelectedItem(CursoDTO curso) 
	{
		this.selectedItem=curso;	
		this.view.getBtnAgregarCursada().setEnabled(curso!=null && curso.getDisponibleEnSistema());
		
	}




	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==this.view.getBtnAgregarCurso())
		{
			VentanaAgregarCurso ventana=new VentanaAgregarCurso();
			ControladorAgregarCurso controlador=new ControladorAgregarCurso(ventana);
			controlador.initialize();
		}
		if (e.getSource()==this.view.getBtnAgregarCursada())
		{
			new ControladorAgregarCursada(new VentanaAgregarCursada(), this.selectedItem,true,empresaRecibida).initialize();
		}
		
		if (e.getSource()==this.view.getBtnSeleccionarCursadExistente()) 
		{
			VentanaListarCursadaEmpresas ventana=new VentanaListarCursadaEmpresas();
			new ControladorListarCursadaEmpresas(ventana, empresaRecibida,true);
			Modal.showDialog(ventana, "Agregar cursada a la empresa", ventana.getBtnSeleccionar());
			
			
		}
		
	}

}
