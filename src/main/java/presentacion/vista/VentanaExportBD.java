package presentacion.vista;

import javax.swing.JTextField;

import util.EstilosYColores;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaExportBD {

	private JFrame frame;
	private JTextField textField;
	private JButton btnSelectFolder;
	private JButton btnExport;
	private EstilosYColores style = EstilosYColores.getInstance();

	/**
	 * Create the application.
	 */
	public VentanaExportBD() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setTitle("Exportar BD");
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
		
		btnSelectFolder = new JButton("Seleccionar destino");
		btnSelectFolder.setBounds(250, 52, 160, 23);
		frame.getContentPane().add(btnSelectFolder);
		
		btnExport = new JButton("Exportar");
		btnExport.setBounds(10, 98, 400, 31);
		btnExport.setBackground(style.getBackgroundButtonStandard());
		btnExport.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnExport);
		
		frame.setVisible(true);
	}
	

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnSelectFolder() {
		return btnSelectFolder;
	}

	public void setBtnSelectFolder(JButton btnSelectFolder) {
		this.btnSelectFolder = btnSelectFolder;
	}

	public JButton getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(JButton btnExport) {
		this.btnExport = btnExport;
	}
	
	public void close() {
		this.frame.dispose();
	}
	
}
