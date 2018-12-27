package presentacion.vista;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import java.awt.Font;
import javax.swing.JSeparator;
import com.toedter.calendar.JDateChooser;

import util.EstilosYColores;

public class VentanaAgregarUsuario {

	private JDialog frame;
	private JTextField textFieldUsuario;
	private JPasswordField IntopasswordField;
	private JPasswordField RepeatpasswordField;
	private JButton btnAgregarUsuario;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldTelefono;
	private JCheckBox chckbxAdministrador;
	private JCheckBox chckbxSupervisor;
	private JCheckBox chckbxPersonalAdmin;
	private JCheckBox chckbxInstructor;
	private JCheckBox chckbxAlumno;
	private JTextField textFieldEmail;
	private JTextField textFieldDNI;
	private JDateChooser dateFechaIng;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaAgregarUsuario() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setSize(276, 605);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Agregar Usuario");
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUsuario.setBounds(108, 25, 136, 20);
		frame.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		IntopasswordField = new JPasswordField();
		IntopasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		IntopasswordField.setBounds(108, 70, 136, 20);
		frame.getContentPane().add(IntopasswordField);
		
		RepeatpasswordField = new JPasswordField();
		RepeatpasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		RepeatpasswordField.setBounds(108, 115, 136, 20);
		frame.getContentPane().add(RepeatpasswordField);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario.setBounds(23, 28, 75, 14);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Contrase\u00F1a");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(23, 73, 75, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblRepeatPass = new JLabel("Repita");
		lblRepeatPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepeatPass.setBounds(23, 109, 75, 14);
		frame.getContentPane().add(lblRepeatPass);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setBounds(23, 121, 75, 14);
		frame.getContentPane().add(lblContrasea);

		btnAgregarUsuario = new JButton("Agregar Usuario");
		btnAgregarUsuario.setBounds(23, 542, 221, 23);
		btnAgregarUsuario.setBackground(style.getBackgroundButtonStandard());
		btnAgregarUsuario.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnAgregarUsuario);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(108, 160, 136, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldApellido.setBounds(108, 205, 136, 20);
		frame.getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTelefono.setBounds(108, 385, 136, 20);
		frame.getContentPane().add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(23, 163, 75, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellido.setBounds(23, 208, 75, 14);
		frame.getContentPane().add(lblApellido);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefono.setBounds(23, 388, 75, 14);
		frame.getContentPane().add(lblTelefono);
		
		chckbxAdministrador = new JCheckBox("Administrador");
		chckbxAdministrador.setBounds(23, 458, 128, 23);
		frame.getContentPane().add(chckbxAdministrador);
		
		chckbxSupervisor = new JCheckBox("Supervisor");
		chckbxSupervisor.setBounds(147, 458, 97, 23);
		frame.getContentPane().add(chckbxSupervisor);
		
		chckbxPersonalAdmin = new JCheckBox("Personal Admin.");
		chckbxPersonalAdmin.setBounds(23, 484, 128, 23);
		frame.getContentPane().add(chckbxPersonalAdmin);
		
		chckbxInstructor = new JCheckBox("Instructor");
		chckbxInstructor.setBounds(147, 484, 97, 23);
		frame.getContentPane().add(chckbxInstructor);
		
		JLabel lblPermisos = new JLabel("Permisos");
		lblPermisos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPermisos.setBounds(23, 437, 75, 14);
		frame.getContentPane().add(lblPermisos);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(23, 424, 221, 2);
		frame.getContentPane().add(separator);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setBounds(108, 340, 136, 20);
		frame.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(23, 343, 75, 14);
		frame.getContentPane().add(lblEmail);
		
		chckbxAlumno = new JCheckBox("Alumno");
		chckbxAlumno.setBounds(99, 510, 97, 23);
		frame.getContentPane().add(chckbxAlumno);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDNI.setBounds(108, 250, 136, 20);
		frame.getContentPane().add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(23, 253, 75, 14);
		frame.getContentPane().add(lblDni);
		
		dateFechaIng = new JDateChooser();
		dateFechaIng.setBounds(108, 295, 136, 20);
		frame.getContentPane().add(dateFechaIng);

		JLabel lblFechaIng = new JLabel("Fecha");
		lblFechaIng.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaIng.setBounds(23, 289, 75, 14);
		frame.getContentPane().add(lblFechaIng);

		JLabel lblInscripcion = new JLabel("Inscripci\u00F3n");
		lblInscripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInscripcion.setBounds(23, 301, 75, 14);
		frame.getContentPane().add(lblInscripcion);

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

	public JTextField getTextFieldUsuario() {
		return textFieldUsuario;
	}

	public void setTextFieldUsuario(JTextField textFieldUsuario) {
		this.textFieldUsuario = textFieldUsuario;
	}

	public JPasswordField getIntopasswordField() {
		return IntopasswordField;
	}

	public void setIntopasswordField(JPasswordField intopasswordField) {
		IntopasswordField = intopasswordField;
	}

	public JPasswordField getRepeatpasswordField() {
		return RepeatpasswordField;
	}

	public void setRepeatpasswordField(JPasswordField repeatpasswordField) {
		RepeatpasswordField = repeatpasswordField;
	}

	public JCheckBox getChckbxAdministrador() {
		return chckbxAdministrador;
	}

	public JCheckBox getChckbxSupervisor() {
		return chckbxSupervisor;
	}

	public JCheckBox getChckbxPersonalAdmin() {
		return chckbxPersonalAdmin;
	}

	public JCheckBox getChckbxInstructor() {
		return chckbxInstructor;
	}
	
	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}
	
	public JButton getBtnAgregarUsuario() {
		return btnAgregarUsuario;
	}

	public void setBtnAgregarUsuario(JButton btnAgregarUsuario) {
		this.btnAgregarUsuario = btnAgregarUsuario;
	}
		
	public JCheckBox getChckbxAlumno() {
		return chckbxAlumno;
	}

	public JDateChooser getDateFechaIng() {
		return dateFechaIng;
	}

	public JTextField getTextFieldDNI() {
		return textFieldDNI;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}
}
