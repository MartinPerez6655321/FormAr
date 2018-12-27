package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.CursoDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaListarCursos extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3281022417614507228L;

	private TablaGenerica<CursoDTO> table;

	private JButton btnAgregarCurso;
	private JButton btnAgregarCursada;
	private JButton BtnSeleccionarCursadExistente;
	
	public VentanaListarCursos() 
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		this.btnAgregarCurso=new JButton("Agregar Curso");
		buttonPane.add(this.btnAgregarCurso);
		
		

		
		this.btnAgregarCursada=new JButton("Agregar Cursada a la empresa");
		buttonPane.add(this.btnAgregarCursada);
		
		this.BtnSeleccionarCursadExistente=new JButton("Agregar cursada existente");
		buttonPane.add(this.BtnSeleccionarCursadExistente);
	}



	public JButton getBtnSeleccionarCursadExistente() {
		return BtnSeleccionarCursadExistente;
	}





	public JButton getBtnAgregarCursada() {
		return btnAgregarCursada;
	}



	public void setBtnAgregarCursada(JButton btnAgregarCursada) {
		this.btnAgregarCursada = btnAgregarCursada;
	}



	public JButton getBtnAgregarCurso() {
		return btnAgregarCurso;
	}



	public void setBtnAgregarCurso(JButton btnAgregarCurso) {
		this.btnAgregarCurso = btnAgregarCurso;
	}



	public TablaGenerica<CursoDTO> getTable() {
		return table;
	}



	public void setTable(TablaGenerica<CursoDTO> table) {
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}



	
	
	

}
