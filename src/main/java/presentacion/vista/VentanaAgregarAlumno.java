package presentacion.vista;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;


import dto.PersonaDTO;
import util.EstilosYColores;
import java.awt.Font;


public class VentanaAgregarAlumno {

	private JDialog frmAgregarAlumno;
	private JTextField textFieldUsuario;
	private JPasswordField IntopasswordField;
	private JPasswordField RepeatpasswordField;
	private JButton btnAgregarAlumno;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JDateChooser dateCreacion;
	private JTextField textFieldTelefono;
	private JTextField textFieldEmail;
	private JTextField txtFieldDNI;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaAgregarAlumno() {
		initialize();
	}
	
	private void initialize() {
		frmAgregarAlumno = new JDialog();
		frmAgregarAlumno.setSize(276, 555);
		frmAgregarAlumno.getContentPane().setLayout(null);
		frmAgregarAlumno.setLocationRelativeTo(null);
		frmAgregarAlumno.setModal(true);
		frmAgregarAlumno.setResizable(false);
		frmAgregarAlumno.setTitle("Agregar Alumno");
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUsuario.setBounds(108, 34, 136, 20);
		frmAgregarAlumno.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		IntopasswordField = new JPasswordField();
		IntopasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		IntopasswordField.setBounds(108, 84, 136, 20);
		frmAgregarAlumno.getContentPane().add(IntopasswordField);
		
		RepeatpasswordField = new JPasswordField();
		RepeatpasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		RepeatpasswordField.setBounds(108, 134, 136, 20);
		frmAgregarAlumno.getContentPane().add(RepeatpasswordField);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario.setBounds(23, 37, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblUsuario);

		JLabel lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(23, 87, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblPassword);
		
		JLabel lblRepeatPass = new JLabel("Repita");
		lblRepeatPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepeatPass.setBounds(23, 128, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblRepeatPass);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setBounds(23, 140, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblContrasea);
		
		btnAgregarAlumno = new JButton("Agregar Alumno");
		btnAgregarAlumno.setBounds(23, 492, 221, 23);
		btnAgregarAlumno.setBackground(style.getBackgroundButtonStandard());
		btnAgregarAlumno.setForeground(style.getForegroundButtonStandard());
		frmAgregarAlumno.getContentPane().add(btnAgregarAlumno);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(108, 184, 136, 20);
		frmAgregarAlumno.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldApellido.setBounds(108, 234, 136, 20);
		frmAgregarAlumno.getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTelefono.setBounds(108, 434, 136, 20);
		frmAgregarAlumno.getContentPane().add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(23, 187, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblNewLabel);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellido.setBounds(23, 240, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblApellido);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefono.setBounds(23, 437, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblTelefono);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setBounds(108, 384, 136, 20);
		frmAgregarAlumno.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(23, 387, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblEmail);
		
		txtFieldDNI = new JTextField();
		txtFieldDNI.setHorizontalAlignment(SwingConstants.CENTER);
		txtFieldDNI.setBounds(108, 284, 136, 20);
		frmAgregarAlumno.getContentPane().add(txtFieldDNI);
		txtFieldDNI.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(23, 287, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblDni);
		
		dateCreacion = new JDateChooser();
		dateCreacion.setBounds(108, 334, 136, 20);
		frmAgregarAlumno.getContentPane().add(dateCreacion);

		JLabel lblFechaIng = new JLabel("Fecha");
		lblFechaIng.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaIng.setBounds(23, 328, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblFechaIng);

		JLabel lblInscripcion = new JLabel("Inscripci\u00F3n");
		lblInscripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInscripcion.setBounds(23, 340, 75, 14);
		frmAgregarAlumno.getContentPane().add(lblInscripcion);

	}
	
	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public JTextField getTextFieldApellido() {
		return textFieldApellido;
	}

	public JTextField getTxtFieldDNI() {
		return txtFieldDNI;
	}

	public JDateChooser getDateCreacion() {
		return dateCreacion;
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

	public JButton getBtnAgregarAlumno() {
		return btnAgregarAlumno;
	}

	public void show() {
		frmAgregarAlumno.setVisible(true);
	}
	
    public void close()
	{
    	frmAgregarAlumno.dispose();
	}

	public void completarDatos(PersonaDTO persona) 
	{
		this.textFieldApellido.setText(persona.getApellido());
		this.textFieldNombre.setText(persona.getNombre());
		this.textFieldTelefono.setText(persona.getTelefono());
		this.textFieldEmail.setText(persona.getEmail());
		
	}
}