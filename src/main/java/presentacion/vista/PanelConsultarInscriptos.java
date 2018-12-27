package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.AlumnoDTO;

import presentacion.components.tabla.TablaGenerica;

public class PanelConsultarInscriptos extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private TablaGenerica<AlumnoDTO> table;
	private JButton btnAceptar;
	
	public PanelConsultarInscriptos() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new BorderLayout(0, 0));
		
		btnAceptar = new JButton("Aceptar");
		buttonPane.add(btnAceptar);
		
		
	}

	public TablaGenerica<AlumnoDTO> getTable() {
		return table;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setTable(TablaGenerica<AlumnoDTO> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
}
