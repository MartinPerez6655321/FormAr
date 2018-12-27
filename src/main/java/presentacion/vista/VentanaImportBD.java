package presentacion.vista;

import javax.swing.JTextField;

import util.EstilosYColores;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaImportBD {

	private JFrame frame;
	private JTextField textField;
	private JButton btnSelectFile;
	private JButton btnImport;
	private EstilosYColores style = EstilosYColores.getInstance();

	/**
	 * Create the application.
	 */
	public VentanaImportBD() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setTitle("Importar BD");
		frame.setResizable(false);
		frame.setSize(425, 186);
		frame.getContentPane().setBackground(style.getBackgroundVentanaStandard());
	    frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 52, 230, 22);
		textField.setEditable(false);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnSelectFile = new JButton("Seleccionar archivo");
		btnSelectFile.setBounds(250, 52, 160, 23);
		frame.getContentPane().add(btnSelectFile);
		
		btnImport = new JButton("Importar");
		btnImport.setBounds(10, 98, 400, 31);
		btnImport.setBackground(style.getBackgroundButtonStandard());
		btnImport.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnImport);
		
		frame.setVisible(true);
	}
	

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnSelectFile() {
		return btnSelectFile;
	}

	public void setBtnSelectFile(JButton btnSelectFile) {
		this.btnSelectFile = btnSelectFile;
	}

	public JButton getBtnImport() {
		return btnImport;
	}

	public void setBtnImport(JButton btnImport) {
		this.btnImport = btnImport;
	}

	public void close() {
		this.frame.dispose();
	}

	
}
