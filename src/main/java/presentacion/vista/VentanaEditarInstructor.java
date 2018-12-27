package presentacion.vista;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import util.EstilosYColores;

import javax.swing.JDialog;
import java.awt.Font;

public class VentanaEditarInstructor {

	private JDialog frame;
	private JTextField textFieldUsuario;
	private JPasswordField IntopasswordField;
	private JPasswordField RepeatpasswordField;
	private JButton btnEditarInstructor;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldTelefono;
	private JTextField textFieldEmail;
	private JTextField textFieldDNI;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaEditarInstructor() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setSize(276, 504);
		frame.getContentPane().setLayout(null);
		frame.setModal(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Editar Instructor");
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUsuario.setBounds(108, 34, 136, 20);
		frame.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		IntopasswordField = new JPasswordField();
		IntopasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		IntopasswordField.setBounds(108, 84, 136, 20);
		frame.getContentPane().add(IntopasswordField);
		
		RepeatpasswordField = new JPasswordField();
		RepeatpasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		RepeatpasswordField.setBounds(108, 134, 136, 20);
		frame.getContentPane().add(RepeatpasswordField);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario.setBounds(23, 37, 75, 14);
		frame.getContentPane().add(lblUsuario);

		JLabel lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(23, 87, 75, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblRepeatPass = new JLabel("Repita");
		lblRepeatPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepeatPass.setBounds(23, 128, 75, 14);
		frame.getContentPane().add(lblRepeatPass);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setBounds(23, 140, 75, 14);
		frame.getContentPane().add(lblContrasea);
		
		btnEditarInstructor = new JButton("Editar Instructor");
		btnEditarInstructor.setBounds(23, 441, 221, 23);
		btnEditarInstructor.setBackground(style.getBackgroundButtonStandard());
		btnEditarInstructor.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnEditarInstructor);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(108, 184, 136, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldApellido.setBounds(108, 234, 136, 20);
		frame.getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTelefono.setBounds(108, 384, 136, 20);
		frame.getContentPane().add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(23, 187, 75, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellido.setBounds(23, 240, 75, 14);
		frame.getContentPane().add(lblApellido);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(23, 287, 75, 14);
		frame.getContentPane().add(lblDni);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefono.setBounds(23, 387, 75, 14);
		frame.getContentPane().add(lblTelefono);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setBounds(108, 334, 136, 20);
		frame.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(23, 337, 75, 14);
		frame.getContentPane().add(lblEmail);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDNI.setBounds(108, 284, 136, 20);
		frame.getContentPane().add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
	}
	
	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public JTextField getTextFieldApellido() {
		return textFieldApellido;
	}
	
	public JTextField getTextFieldDNI() {
		return textFieldDNI;
	}
	
	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}
	
	public JTextField getTextFieldTelefono() {
		return textFieldTelefono;
	}

	public JTextField getTextFieldUsuario() {
		return textFieldUsuario;
	}

	public JPasswordField getIntopasswordField() {
		return IntopasswordField;
	}

	public JPasswordField getRepeatpasswordField() {
		return RepeatpasswordField;
	}

	public JButton getBtnEditarInstructor() {
		return btnEditarInstructor;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}
}