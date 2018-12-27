package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.ParcialDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaTomarParcial extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8906947314761364174L;

	private TablaGenerica<ParcialDTO> table;
	
	private JButton btnOk;
	
	
	
	public VentanaTomarParcial()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		this.btnOk=new JButton("Cerrar");
		buttonPane.add(btnOk);
		
	}



	public TablaGenerica<ParcialDTO> geTable() {
		return table;
	}



	public void setTable(TablaGenerica<ParcialDTO> table) {
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
