package presentacion.vista;

import java.awt.BorderLayout;


import javax.swing.JButton;

import javax.swing.JPanel;

import dto.ParcialDTO;

import presentacion.components.tabla.TablaGenerica;

public class VentanaSubirNotasAlumnos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857932386812828961L;

	private JButton btnAceptar;
	private JPanel panelComboBox;
	private TablaGenerica<ParcialDTO> table;

	public VentanaSubirNotasAlumnos() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		
		
		btnAceptar = new JButton("Aceptar");
		buttonPane.add(btnAceptar);

	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JPanel getPanelComboBox() {
		return panelComboBox;
	}

	public TablaGenerica<ParcialDTO> getTable() {
		return table;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public void setPanelComboBox(JPanel panelComboBox) {
		this.panelComboBox = panelComboBox;
	}

	public void setTable(TablaGenerica<ParcialDTO> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	
}
