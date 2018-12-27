package presentacion.vista;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VistaReporteInteraccionesCurso extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGenerarReporte;
	private JComboBox<String> comboBoxCurso;
	
	public VistaReporteInteraccionesCurso() {
		setLayout(null);
		setPreferredSize(new Dimension(512, 118));
		btnGenerarReporte = new JButton("Generar Reporte");
		
		btnGenerarReporte.setBounds(10, 73, 487, 23);
		add(btnGenerarReporte);
		
		JLabel lblCursoElegido = new JLabel("Curso elegido:");
		lblCursoElegido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCursoElegido.setBounds(10, 11, 146, 44);
		add(lblCursoElegido);
		
		comboBoxCurso = new JComboBox<>();
		comboBoxCurso.setBounds(168, 26, 329, 20);
		add(comboBoxCurso);
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public JComboBox<String> getComboBoxCurso() {
		return comboBoxCurso;
	}

	public void setBtnGenerarReporte(JButton btnGenerarReporte) {
		this.btnGenerarReporte = btnGenerarReporte;
	}

	public void setComboBoxCurso(JComboBox<String> comboBoxCurso) {
		this.comboBoxCurso = comboBoxCurso;
	}

}
