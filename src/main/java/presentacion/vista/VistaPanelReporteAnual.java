package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;

public class VistaPanelReporteAnual extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGenerarReporte;
	private JComboBox<String> comboBox;
	
	public VistaPanelReporteAnual() {
		setLayout(null);
		setPreferredSize(new Dimension(493, 118));
		btnGenerarReporte = new JButton("Generar Reporte");
		
		btnGenerarReporte.setBounds(10, 73, 454, 23);
		add(btnGenerarReporte);
		
		JLabel lblAoElegido = new JLabel("A\u00F1o elegido:");
		lblAoElegido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAoElegido.setBounds(10, 11, 124, 44);
		add(lblAoElegido);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(133, 26, 331, 20);
		add(comboBox);
		
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setBtnGenerarReporte(JButton btnGenerarReporte) {
		this.btnGenerarReporte = btnGenerarReporte;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}
}
