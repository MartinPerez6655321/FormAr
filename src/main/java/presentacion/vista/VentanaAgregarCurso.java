package presentacion.vista;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import util.EstilosYColores;

public class VentanaAgregarCurso
{
	private JDialog frame;
	private JTextField textFieldPrecio;
	private JTextField textFieldNombre;
	private JButton btnAceptar;
	private JLabel lblHoras;
	private JSpinner spinnerHoras;
	private JLabel lblCodigo;
	private JTextField textFieldCodigo;
	private JTextArea textAreaDescripcion;
	private JLabel lblCaracteresRestantes;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	public VentanaAgregarCurso()
	{
		initialize();
	}
	
	private void initialize()
	{
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Agregar Curso");
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
		
		btnAceptar = new JButton("Agregar");
		btnAceptar.setBounds(30, 347, 322, 23);
		btnAceptar.setBackground(style.getBackgroundButtonStandard());
		btnAceptar.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnAceptar);
		
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

	public JTextField getTextFieldPrecio() {
		return textFieldPrecio;
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setTextFieldPrecio(JTextField textField) {
		this.textFieldPrecio = textField;
	}

	public void setTextFieldNombre(JTextField textField_1) {
		this.textFieldNombre = textField_1;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	  public void close()
	{
	    frame.dispose();
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
