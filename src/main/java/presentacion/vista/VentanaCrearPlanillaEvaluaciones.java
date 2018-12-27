package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

public class VentanaCrearPlanillaEvaluaciones extends JPanel {
	private static final long serialVersionUID = 1365083541021430674L;

	private JButton btnGuardar;

	private JDateChooser dateFecha;
	private JLabel lblFijarFecha;

	public VentanaCrearPlanillaEvaluaciones() 
	{
		setLayout(new BorderLayout(0, 0));
				
		setPreferredSize(new Dimension(250, 75));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		this.dateFecha=new JDateChooser();
		dateFecha.setBounds(94, 5, 146, 20);
		this.dateFecha.setDate(new java.util.Date());
		panel.setLayout(null);
		
		lblFijarFecha = new JLabel("Fijar Fecha");
		lblFijarFecha.setBounds(10, 5, 74, 20);
		
		panel.add(lblFijarFecha);
		panel.add(dateFecha);
		

		
	
		
				btnGuardar = new JButton("Guardar");
				btnGuardar.setBounds(10, 36, 230, 23);
				panel.add(btnGuardar);
		
	}

	

	

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JDateChooser getDateFecha() {
		return dateFecha;
	}

	public void setDateFecha(JDateChooser dateFecha) {
		this.dateFecha = dateFecha;
	}

}
