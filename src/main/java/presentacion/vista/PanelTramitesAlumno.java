package presentacion.vista;




import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class PanelTramitesAlumno extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGenerarReporteAcadmico;
	private JButton btnSolicitarReporte;
	
	public PanelTramitesAlumno()
	{
		setLayout(null);
		setPreferredSize(new Dimension(416, 159));
		JLabel lblTramites = new JLabel("Tr\u00E1mites");
		lblTramites.setFont(new Font("Tahoma", Font.ITALIC, 24));
		lblTramites.setBounds(10, 28, 276, 38);
		add(lblTramites);
		
		btnGenerarReporteAcadmico = new JButton("Generar reporte acad\u00E9mico");
		btnGenerarReporteAcadmico.setBounds(10, 84, 192, 51);
		add(btnGenerarReporteAcadmico);
		
		btnSolicitarReporte = new JButton("Solicitar reporte al instituto");
		btnSolicitarReporte.setBounds(212, 84, 192, 51);
		add(btnSolicitarReporte);

		
		
	}

	public JButton getBtnGenerarReporteAcadmico() {
		return btnGenerarReporteAcadmico;
	}

	public JButton getBtnSolicitarReporte() {
		return btnSolicitarReporte;
	}

	public void setBtnGenerarReporteAcadmico(JButton btnGenerarReporteAcadmico) {
		this.btnGenerarReporteAcadmico = btnGenerarReporteAcadmico;
	}

	public void setBtnSolicitarReporte(JButton btnSolicitarReporte) {
		this.btnSolicitarReporte = btnSolicitarReporte;
	}
}
