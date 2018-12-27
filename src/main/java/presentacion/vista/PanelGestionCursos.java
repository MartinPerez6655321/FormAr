package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.CursoDTO;
import presentacion.components.tabla.TablaGenerica;

import javax.swing.JButton;

public class PanelGestionCursos extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124769L;
	private TablaGenerica<CursoDTO> table;
	private JButton btnAgregarCursada;
	private JButton btnAgregarCurso ;

	public PanelGestionCursos()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnAgregarCurso = new JButton("Agregar Curso");
		buttonPane.add(btnAgregarCurso);
		
		btnAgregarCursada = new JButton("Agregar Cursada");
		buttonPane.add(btnAgregarCursada);
	}

	public TablaGenerica<CursoDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<CursoDTO> table)
	{
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnAgregarCurso() 
	{
		return btnAgregarCurso;
	}
	
	public JButton getBtnAgregarCursada() 
	{
		return btnAgregarCursada;
	}
	
}

