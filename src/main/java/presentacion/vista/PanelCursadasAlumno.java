package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.CursadaDTO;
import presentacion.components.tabla.TablaGenerica;

public class PanelCursadasAlumno extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnPrograma;
	private JButton btnDescargarPrograma;
	private JButton btnTramites;
	private TablaGenerica<CursadaDTO> table;
	private JButton btnCuotas;

	public PanelCursadasAlumno()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		
		
		btnPrograma = new JButton("Ver programa");
		buttonPane.add(btnPrograma);
		btnDescargarPrograma=new JButton("Descargar programa");
		buttonPane.add(btnDescargarPrograma);
		
		btnCuotas=new JButton("Ver cuotas");
		buttonPane.add(btnCuotas);
		
		btnTramites=new JButton("Tr\u00E1mites");
		buttonPane.add(btnTramites);
		
	}

	public JButton getBtnPrograma() {
		return btnPrograma;
	}

	public JButton getBtnDescargarPrograma() {
		return btnDescargarPrograma;
	}

	public TablaGenerica<CursadaDTO> getTable() {
		return table;
	}

	public void setBtnPrograma(JButton btnPrograma) {
		this.btnPrograma = btnPrograma;
	}

	public void setBtnDescargarPrograma(JButton btnDescargarPrograma) {
		this.btnDescargarPrograma = btnDescargarPrograma;
	}

	public void setTable(TablaGenerica<CursadaDTO> table) {
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}

	public JButton getBtnTramites() {
		return btnTramites;
	}

	public void setBtnTramites(JButton btnTramites) {
		this.btnTramites = btnTramites;
	}

	public JButton getBtnCuotas() {
		return btnCuotas;
	}

	public void setBtnCuotas(JButton btnCuotas) {
		this.btnCuotas = btnCuotas;
	}

}
