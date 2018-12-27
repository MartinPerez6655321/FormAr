package presentacion.vista;


import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class VentanaEditarCurso {
	private JDialog frame;
	private JTextField textFieldPrecio;
	private JTextField textFieldNombre;
	private JButton btnGuardarCambios;
	private JLabel lblNombre ;
	private JLabel lblPrecio;
	private JButton btnEliminarCurso;
	private JLabel lblHoras;
	private JSpinner spinnerHoras;
	private JLabel lblCodigo;
	private JTextField textFieldCodigo;
	private JTextArea textAreaDescripcion;

	private JLabel lblCaracteresRestantes;

	
	public VentanaEditarCurso() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Editar Curso");
		frame.setBounds(100, 100, 400, 420);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n");
		lblDescripcin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescripcin.setBounds(30, 89, 76, 30);
		frame.getContentPane().add(lblDescripcin);
	
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 49, 54, 17);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(132, 47, 220, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(30, 245, 42, 17);
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblPrecio);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPrecio.setBounds(132, 243, 220, 20);
		frame.getContentPane().add(textFieldPrecio);
		textFieldPrecio.setColumns(10);
		
		
		
		lblHoras = new JLabel("Horas");
		lblHoras.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoras.setBounds(30, 301, 76, 14);
		frame.getContentPane().add(lblHoras);
		
		spinnerHoras = new JSpinner();
		spinnerHoras.setModel(new SpinnerNumberModel(25, 1, null, 1));
		spinnerHoras.setBounds(132, 298, 220, 20);
		frame.getContentPane().add(spinnerHoras);
		
		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigo.setBounds(30, 189, 54, 30);
		frame.getContentPane().add(lblCodigo);
		
		textFieldCodigo = new JTextField();
		textFieldCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCodigo.setBounds(132, 194, 220, 20);
		frame.getContentPane().add(textFieldCodigo);
		textFieldCodigo.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(132, 96, 220, 68);
		frame.getContentPane().add(scrollPane);
		
		textAreaDescripcion = new JTextArea();
		scrollPane.setViewportView(textAreaDescripcion);
		textAreaDescripcion.setLineWrap(true);
		
		btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setBounds(30, 348, 150, 23);
		frame.getContentPane().add(btnGuardarCambios);
		
		btnEliminarCurso = new JButton("Eliminar Curso");
		btnEliminarCurso.setBounds(202, 348, 150, 23);
		frame.getContentPane().add(btnEliminarCurso);
		lblCaracteresRestantes = new JLabel("");
		lblCaracteresRestantes.setBounds(197, 169, 155, 14);
		frame.getContentPane().add(lblCaracteresRestantes);
		
	
	}

	public JTextField getTextFieldCodigo() {
		return textFieldCodigo;
	}

	public void setTextFieldCodigo(JTextField textFieldCodigo) {
		this.textFieldCodigo = textFieldCodigo;
	}

	public JSpinner getSpinnerHoras() {
		return spinnerHoras;
	}

	public void show() 
	{
		frame.setVisible(true);
	}

	public void close() 
	{
		frame.dispose();
	}

	public JTextField getTextFieldPrecio() {
		return textFieldPrecio;
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public JLabel getLblNombre() {
		return lblNombre;
	}

	public JLabel getLblPrecio() {
		return lblPrecio;
	}

	public void setTextFieldPrecio(JTextField textFieldPrecio) {
		this.textFieldPrecio = textFieldPrecio;
	}

	public void setTextFieldNombre(JTextField textFieldNombre) {
		this.textFieldNombre = textFieldNombre;
	}

	public void setBtnGuardarCambios(JButton btnGuardarCambios) {
		this.btnGuardarCambios = btnGuardarCambios;
	}

	public void setLblNombre(JLabel lblNombre) {
		this.lblNombre = lblNombre;
	}

	public void setLblPrecio(JLabel lblPrecio) {
		this.lblPrecio = lblPrecio;
	}

	public JButton getBtnEliminarCurso() {
		return btnEliminarCurso;
	}

	public void setBtnEliminarCurso(JButton btnEliminarCurso) {
		this.btnEliminarCurso = btnEliminarCurso;
	}

	public JTextArea getTextAreaDescripcion() {
		return textAreaDescripcion;
	}

	public void setTextAreaDescripcion(JTextArea textAreaDescripcion) {
		this.textAreaDescripcion = textAreaDescripcion;
	}

	public JLabel getLblCaracteresRestantes() {
		return lblCaracteresRestantes;
	}

	public void setLblCaracteresRestantes(JLabel lblCaracteresRestantes) {
		this.lblCaracteresRestantes = lblCaracteresRestantes;
	}
}
