package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.CursadaEmpresaDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaListarCursadaEmpresas extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145700038773251148L;
	
	
	private TablaGenerica<CursadaEmpresaDTO> table;
	private JButton btnGestionarInscripciones;
	private JButton btnCerrar;
	private JButton btnSeleccionar;
	private JButton btnGestionarPagos;
	
	public VentanaListarCursadaEmpresas() 
	{
		setLayout(new BorderLayout(0, 0));
		
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		btnGestionarInscripciones = new JButton("Gestionar inscripciones");
		
		buttonPane.add(btnGestionarInscripciones);
		
		btnCerrar=new JButton("Cerrar");
		buttonPane.add(btnCerrar);
		
		
		btnSeleccionar=new JButton("Seleccionar cursada");
		buttonPane.add(btnSeleccionar);
		
		btnGestionarPagos=new JButton("Gestionar pagos");
		buttonPane.add(btnGestionarPagos);
	}

	public JButton getBtnGestionarPagos() {
		return btnGestionarPagos;
	}

	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public TablaGenerica<CursadaEmpresaDTO> getTable() {
		return table;
	}

	public void setTable(TablaGenerica<CursadaEmpresaDTO> table)
	{
		this.table = table; 
		add(this.table, BorderLayout.CENTER);
	}

	public JButton getBtnGestionarInscripciones() {
		return btnGestionarInscripciones;
	}

	public void setBtnGestionarInscripciones(JButton btnGestionarInscripciones) {
		this.btnGestionarInscripciones = btnGestionarInscripciones;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	
	
	
}
