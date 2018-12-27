package presentacion.vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dto.AlumnoDTO;
import util.EstilosYColores;

public class VentanaInscripcionAlumno {

	private JDialog frame;
	private JTextField textFieldFiltro;
	private JButton btnConcretarInscripcion;
	private JTable tableAlumnos;
	private DefaultTableModel modelAlumnos;
	private String[] nombreColumnasAlumnos = { "Legajo","Nombre","Apellido" };
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaInscripcionAlumno() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Inscripci\u00F3n Alumno");
		frame.setBounds(100, 100, 342, 313);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 316, 199);
		frame.getContentPane().add(scrollPane);
		
		modelAlumnos = new DefaultTableModel(null, nombreColumnasAlumnos) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableAlumnos = new JTable(modelAlumnos);

		tableAlumnos.setBackground(Color.darkGray);
		tableAlumnos.setForeground(Color.WHITE);
		
		tableAlumnos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableAlumnos.getColumnModel().getColumn(0).setResizable(false);
		tableAlumnos.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableAlumnos.getColumnModel().getColumn(1).setResizable(false);
		tableAlumnos.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableAlumnos.getColumnModel().getColumn(2).setResizable(false);
		
		tableAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAlumnos.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(tableAlumnos);
		
		textFieldFiltro = new JTextField();
		textFieldFiltro.setBounds(74, 6, 86, 20);
		frame.getContentPane().add(textFieldFiltro);
		textFieldFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFiltro.setBounds(10, 9, 54, 14);
		frame.getContentPane().add(lblFiltro);
		
		btnConcretarInscripcion = new JButton("Concretar Inscripci\u00F3n");
		btnConcretarInscripcion.setBounds(10, 247, 316, 26);
		btnConcretarInscripcion.setBackground(style.getBackgroundButtonStandard());
		btnConcretarInscripcion.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnConcretarInscripcion);
		frame.setLocationRelativeTo(null);
	
	}

	public JButton getBtnConcretarInscripcion() {
		return btnConcretarInscripcion;
	}

	public JTextField getTextFieldFiltro() {
		return textFieldFiltro;
	}

	public JTable getTablaAlumnos() {
		return tableAlumnos;
	}

	public DefaultTableModel getModelAlumnos() {
		return modelAlumnos;
	}

	public String[] getNombreColumnasAlumnos() {
		return nombreColumnasAlumnos;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}

	public void llenarTabla(ArrayList<AlumnoDTO> alumnos)
	{
		// TODO Auto-generated method stub
		
	}
}
