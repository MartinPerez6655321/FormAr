package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;


import presentacion.components.tabla.TablaGenerica;

public class PanelGestionPagosDeAlumno extends JPanel
{
	private static final long serialVersionUID = -3601748654798415255L;
	
	private JButton btnRegistrarPago;
	private TablaGenerica<?>  table;

	private JButton btnComprobantePago;
	
	public PanelGestionPagosDeAlumno()
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 700));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnRegistrarPago = new JButton("Registrar pago");
		buttonPane.add(btnRegistrarPago);
		
		btnComprobantePago = new JButton("Ver comprobante pago");
		buttonPane.add(btnComprobantePago);
		
		
	}

	public JButton getBtnRegistrarPago()
	{
		return btnRegistrarPago;
	}

	public TablaGenerica<?> getTable() {
		return table;
		
	}

	public void setBtnRegistrarPago(JButton btnRegistrarPago) {
		this.btnRegistrarPago = btnRegistrarPago;
	}

	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnComprobantePago() {
		return btnComprobantePago;
	}

	public void setBtnComprobantePago(JButton btnComprobantePago) {
		this.btnComprobantePago = btnComprobantePago;
	}
}
