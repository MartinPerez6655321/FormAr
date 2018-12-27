package presentacion.vista;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultStyledDocument;

import util.EstilosYColores;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import java.awt.Font;

public class VentanaVerRecado {

	private JDialog dialogVerRecado;
	private JTextField txtEmisor;
	private JTextField txtAsunto;
	private JTextPane textAreaMensaje;
	private JTextField txtFecha;
	private JTextField txtHora;
	private JLabel lblEmisorReceptor;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaVerRecado() {
		initialize();
	}
	
	private void initialize() {
		dialogVerRecado =  new JDialog();
		dialogVerRecado.setModal(true);
		lblEmisorReceptor = new JLabel();
		lblEmisorReceptor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmisorReceptor.setBounds(24, 30, 59, 14);
		
		txtEmisor = new JTextField();
		txtEmisor.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmisor.setBounds(93, 27, 129, 20);
		txtEmisor.setColumns(10);
		
		JLabel lblAsunto = new JLabel("Asunto");
		lblAsunto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAsunto.setBounds(24, 68, 59, 14);

		txtAsunto = new JTextField();
		txtAsunto.setHorizontalAlignment(SwingConstants.CENTER);
		txtAsunto.setBounds(93, 65, 129, 20);
		txtAsunto.setColumns(20);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFecha.setBounds(232, 30, 46, 14);
		
		txtFecha = new JTextField();
		txtFecha.setHorizontalAlignment(SwingConstants.CENTER);
		txtFecha.setBounds(288, 27, 86, 20);
		txtFecha.setColumns(10);
		
		JLabel lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHora.setBounds(232, 68, 46, 14);
		
		txtHora = new JTextField();
		txtHora.setHorizontalAlignment(SwingConstants.CENTER);
		txtHora.setBounds(288, 65, 86, 20);
		txtHora.setColumns(10);

		textAreaMensaje = new JTextPane();
		textAreaMensaje.setBackground(style.getBackgroundSheet());
		textAreaMensaje.setContentType( "text/richtext" );
		textAreaMensaje.setDocument(new DefaultStyledDocument());
		
		JScrollPane scrollPaneMensaje = new JScrollPane(textAreaMensaje);
		scrollPaneMensaje.setBounds(10, 132, 383, 222);
	
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelNorte.add(new JSeparator(SwingConstants.VERTICAL));
		panelNorte.add(lblEmisorReceptor);
		panelNorte.add(txtEmisor);
		panelNorte.add(new JSeparator(SwingConstants.VERTICAL));
		panelNorte.add(lblAsunto);
		panelNorte.add(txtAsunto);
		panelNorte.add(new JSeparator(SwingConstants.VERTICAL));
		panelNorte.add(lblFecha);
		panelNorte.add(txtFecha);
		panelNorte.add(new JSeparator(SwingConstants.VERTICAL));
		panelNorte.add(lblHora);
		panelNorte.add(txtHora);
		panelNorte.setBackground(style.getBackgroundRecado());
		
		dialogVerRecado.add(panelNorte , BorderLayout.NORTH);
		dialogVerRecado.add(scrollPaneMensaje, BorderLayout.CENTER);
		
		dialogVerRecado.setSize(800, 500);
		dialogVerRecado.setLocationRelativeTo(null);
		dialogVerRecado.setResizable(false);
		dialogVerRecado.setModal(true);
		dialogVerRecado.setTitle("Mensaje");

	}
	
	public JTextField getTxtEmisor() {
		return txtEmisor;
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	public JTextField getTxtHora() {
		return txtHora;
	}
	
	public JTextField getTxtAsunto() {
		return txtAsunto;
	}
	
	public JTextPane getTextAreaMensaje() {
		return textAreaMensaje;
	}

	public JLabel getLblEmisorReceptor() {
		return lblEmisorReceptor;
	}

	public void show() {
		dialogVerRecado.setVisible(true);
	}
	
    public void close()
	{
    	dialogVerRecado.dispose();
	}
}