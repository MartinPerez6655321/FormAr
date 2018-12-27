package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.ParcialDTO;

import javax.swing.JLabel;

public class VentanaEditarParcial extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6170654164354629599L;
	
	private JButton btnGuardar;
	
	private JComboBox<String> comboNotas;
	private JTextField textFieldDescripcion;
	private JComboBox<String> comboPresencia;
	
	public VentanaEditarParcial()
	{
		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(400, 200));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		panel.setLayout(null);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(82, 166, 230, 23);
		panel.add(btnGuardar);

		comboNotas = new JComboBox<String>();
		comboNotas.setBounds(237, 32, 138, 20);
		panel.add(comboNotas);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setBounds(21, 85, 356, 70);
		panel.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		
		JLabel lblSeleccionarNota = new JLabel("Seleccionar Nota:");
		lblSeleccionarNota.setBounds(21, 35, 118, 14);
		panel.add(lblSeleccionarNota);
		
		JLabel lblAnotaciones = new JLabel("Anotaciones:");
		lblAnotaciones.setBounds(21, 60, 99, 14);
		panel.add(lblAnotaciones);
		
		comboPresencia = new JComboBox<String>();
		comboPresencia.setBounds(21, 11, 99, 20);
		panel.add(comboPresencia);
	}

	public JComboBox<String> getComboPresencia() {
		return comboPresencia;
	}

	public void setComboPresencia(JComboBox<String> comboPresencia) {
		this.comboPresencia = comboPresencia;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JComboBox<String> getComboNotas() {
		return comboNotas;
	}

	public void setComboNotas(JComboBox<String> comboNotas) {
		this.comboNotas = comboNotas;
	}
	
	public void llenarCombo()
	{
		String[] arreglo= {"1.0","1.5","2.0","2.5","3.0","3.5","4.0","4.5","5.0","5.5","6.0","6.5","7.0","7.5","8.0","8.5","9.0","9.5","10"		
		};
		for (int i = 0; i < arreglo.length; i++) 
		{
			this.comboNotas.addItem(arreglo[i]);
		}
		this.comboPresencia.addItem("Presente");
		this.comboPresencia.addItem("Ausente");
		
		
		
	}

	public JTextField getTextFieldDescripcion() {
		return textFieldDescripcion;
	}

	public void setTextFieldDescripcion(JTextField textFieldDescripcion) {
		this.textFieldDescripcion = textFieldDescripcion;
	}

	public void llenarDatos(ParcialDTO parcial) 
	{
		this.textFieldDescripcion.setText(parcial.getObservaciones());
		
		switch (parcial.getNota())
		{
		case 10:
			this.comboNotas.setSelectedIndex(0);
			break;
		case 15:
			this.comboNotas.setSelectedIndex(1);break;
		case 20:
			this.comboNotas.setSelectedIndex(2);break;
		case 25:
			this.comboNotas.setSelectedIndex(3);break;
		case 30:
			this.comboNotas.setSelectedIndex(4);break;
		case 35:
			this.comboNotas.setSelectedIndex(5);break;
		case 40:
			this.comboNotas.setSelectedIndex(6);break;
		case 45:
			this.comboNotas.setSelectedIndex(7);break;
		case 50:
			this.comboNotas.setSelectedIndex(8);break;
		case 55:
			this.comboNotas.setSelectedIndex(9);break;
		case 60:
			this.comboNotas.setSelectedIndex(10);break;
		case 65:
			this.comboNotas.setSelectedIndex(11);break;
		case 70:
			this.comboNotas.setSelectedIndex(12);break;
		case 75:
			this.comboNotas.setSelectedIndex(13);break;
		case 80:
			this.comboNotas.setSelectedIndex(14);break;
		case 85:
			this.comboNotas.setSelectedIndex(15);break;
		case 90:
			this.comboNotas.setSelectedIndex(16);break;
		case 95:
			this.comboNotas.setSelectedIndex(17);break;
		case 100:
			this.comboNotas.setSelectedIndex(18);break;
		
		}
		
	}
	
	
	
}
