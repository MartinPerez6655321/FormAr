package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.AsistenciaDTO;


import presentacion.components.tabla.TablaGenerica;

public class VentanaTomarAsistencia  extends JPanel
{

/**
	 * 
	 */
	private static final long serialVersionUID = -1348127364995318697L;

	private TablaGenerica<AsistenciaDTO> table;
	
	private JButton btnOk;
	
	
	public VentanaTomarAsistencia()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		this.btnOk=new JButton("Cerrar");
		buttonPane.add(btnOk);
		
		
		
	}


	public TablaGenerica<AsistenciaDTO> getTable() {
		return table;
	}


	public void setTable(TablaGenerica<AsistenciaDTO> table) {
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}


	public JButton getBtnOk() {
		return btnOk;
	}


	public void setBtnOk(JButton btnOk) {
		this.btnOk = btnOk;
	}
	
	
	
	
}
