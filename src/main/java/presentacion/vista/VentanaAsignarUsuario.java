package presentacion.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import util.EstilosYColores;

public class VentanaAsignarUsuario {

	private JDialog frame;
	private JTextField textFieldFiltro;
	private JButton btnSelecionarUsuario;
	private JTable tableUsuario;
	private DefaultTableModel modelUsuarios;
	private String[] nombreColumnasUsuarios = { "Usuario","Nombre","Apellido" };
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaAsignarUsuario() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Selecci\u00F3n de Usuario");
		frame.setBounds(100, 100, 342, 313);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 316, 199);
		frame.getContentPane().add(scrollPane);
		
		modelUsuarios = new DefaultTableModel(null, nombreColumnasUsuarios) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableUsuario = new JTable(modelUsuarios);

		tableUsuario.setBackground(Color.darkGray);
		tableUsuario.setForeground(Color.WHITE);
		
		tableUsuario.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableUsuario.getColumnModel().getColumn(0).setResizable(false);
		tableUsuario.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableUsuario.getColumnModel().getColumn(1).setResizable(false);
		tableUsuario.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableUsuario.getColumnModel().getColumn(2).setResizable(false);
		
		tableUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableUsuario.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(tableUsuario);
		
		textFieldFiltro = new JTextField();
		textFieldFiltro.setBounds(74, 6, 86, 20);
		frame.getContentPane().add(textFieldFiltro);
		textFieldFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFiltro.setBounds(10, 9, 54, 14);
		frame.getContentPane().add(lblFiltro);
		
		btnSelecionarUsuario = new JButton("Seleccionar Usuario");
		btnSelecionarUsuario.setBounds(10, 247, 316, 26);
		btnSelecionarUsuario.setBackground(style.getBackgroundButtonStandard());
		btnSelecionarUsuario.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnSelecionarUsuario);
		frame.setLocationRelativeTo(null);
	
	}

	public JTextField getTextFieldFiltro() {
		return textFieldFiltro;
	}

	public JTable getTablaUsuarios() {
		return tableUsuario;
	}

	public DefaultTableModel getModelUsuarios() {
		return modelUsuarios;
	}

	public String[] getNombreColumnasUsuarios() {
		return nombreColumnasUsuarios;
	}
	
	public JButton getBtnSelecionarUsuario() {
		return btnSelecionarUsuario;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}
}
