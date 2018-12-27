package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


import presentacion.components.tabla.TablaGenerica;

public class PanelGestionCuotasAlumno extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TablaGenerica<?> table;
	private JButton btnVerComprobante;

	public PanelGestionCuotasAlumno()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnVerComprobante=new JButton("Ver comprobante de pago");
		buttonPane.add(btnVerComprobante);
		
	}

	public TablaGenerica<?> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<?> table)
	{
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnVerComprobante() {
		return btnVerComprobante;
	}

	public void setBtnVerComprobante(JButton btnVerComprobante) {
		this.btnVerComprobante = btnVerComprobante;
	}

}
