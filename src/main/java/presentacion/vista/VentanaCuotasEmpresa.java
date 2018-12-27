package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.PagoEmpresaDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaCuotasEmpresa extends JPanel
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1774958479975766888L;

	private TablaGenerica<PagoEmpresaDTO> table;
	private JButton btnPagar;
	
	
	public VentanaCuotasEmpresa() 
		{setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnPagar = new JButton("Pagar Cuota");
		
		buttonPane.add(btnPagar);
		
		
		
		
	}


	public TablaGenerica<PagoEmpresaDTO> getTable() {
		return table;
	}


	public void setTable(TablaGenerica<PagoEmpresaDTO> table)
	{
		this.table = table;
		add(table, BorderLayout.CENTER);
	}


	public JButton getBtnPagar() {
		return btnPagar;
	}


	public void setBtnPagar(JButton btnPagar) {
		this.btnPagar = btnPagar;
	}
	
	
}
