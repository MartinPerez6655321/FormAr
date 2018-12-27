package presentacion.vista;

import javax.swing.JPanel;


import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import util.EstilosYColores;

public class VistaNuevoInteresado extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldTelefono;
	private JTextField textFieldEmail;
	private JButton btnGuardar;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VistaNuevoInteresado() {
		setLayout(null);
		setPreferredSize(new Dimension(248, 330));
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(20, 29, 65, 25);
		add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellido.setBounds(20, 86, 65, 32);
		add(lblApellido);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelfono.setBounds(20, 151, 65, 25);
		add(lblTelfono);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(20, 211, 65, 25);
		add(lblEmail);
		
		btnGuardar = new JButton("Guardar interesado");
		btnGuardar.setBounds(20, 277, 206, 23);
		btnGuardar.setBackground(style.getBackgroundButtonStandard());
		btnGuardar.setForeground(style.getForegroundButtonStandard());
		add(btnGuardar);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(95, 31, 131, 20);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldApellido.setBounds(95, 92, 131, 20);
		add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTelefono.setBounds(95, 153, 131, 20);
		add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setBounds(95, 213, 131, 20);
		add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public JTextField getTextFieldApellido() {
		return textFieldApellido;
	}

	public JTextField getTextFieldTelefono() {
		return textFieldTelefono;
	}

	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}

	public JButton getBtnGuardarYAsignar() {
		return btnGuardar;
	}

	public void setTextFieldNombre(JTextField textFieldNombre) {
		this.textFieldNombre = textFieldNombre;
	}

	public void setTextFieldApellido(JTextField textFieldApellido) {
		this.textFieldApellido = textFieldApellido;
	}

	public void setTextFieldTelefono(JTextField textFieldTelefono) {
		this.textFieldTelefono = textFieldTelefono;
	}

	public void setTextFieldEmail(JTextField textFieldEmail) {
		this.textFieldEmail = textFieldEmail;
	}

	public void setBtnGuardarYAsignar(JButton btnGuardarYAsignar) {
		this.btnGuardar = btnGuardarYAsignar;
	}
}
