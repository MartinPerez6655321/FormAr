//package presentacion.vista;
//
//import javax.swing.JPanel;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.JLabel;
//
//import java.awt.Font;
//
//import javax.swing.JSeparator;
//
//import javax.swing.border.LineBorder;
//import javax.swing.table.DefaultTableModel;
//
//import java.awt.Color;
//import java.awt.GridLayout;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.ListSelectionModel;
//import javax.swing.SwingConstants;
//import javax.swing.JButton;
//
//public class PanelGestionInscripcionAlumno extends JPanel {
//	
//	private static final long serialVersionUID = 1L;
//	private JTable tablaCursadas;
//	private DefaultTableModel modelCursadas;
//	private JTextField textFieldFiltro;
//	private JButton btnInscribirAlumno;
//
//	private String[] nombreColumnasCursadas = { "Nombre","Vacantes","Precio","Comienza","Horario"};
//	
//	public PanelGestionInscripcionAlumno() {
//		setBorder(new LineBorder(new Color(0, 0, 0)));
//		
//		JLabel lblFiltro = new JLabel("Filtro");
//		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 13));
//		
//		textFieldFiltro = new JTextField();
//		textFieldFiltro.setHorizontalAlignment(SwingConstants.CENTER);
//		textFieldFiltro.setColumns(10);
//		
//		JSeparator separator = new JSeparator();
//				
//		JPanel panel = new JPanel();
//		
//		JScrollPane scrollPane = new JScrollPane();
//		
//		
//		GroupLayout groupLayout = new GroupLayout(this);
//		groupLayout.setHorizontalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addContainerGap()
//					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
//						.addGroup(groupLayout.createSequentialGroup()
//							.addComponent(lblFiltro)
//							.addGap(10)
//							.addComponent(textFieldFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
//						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
//					.addContainerGap())
//		);
//		groupLayout.setVerticalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addGap(6)
//					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(textFieldFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//						.addComponent(lblFiltro, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
//					.addGap(12)
//					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//					.addGap(21)
//					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
//					.addGap(15)
//					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
//					.addContainerGap())
//		);
//		panel.setLayout(new GridLayout(1, 0, 0, 0));
//		
//		
//		modelCursadas = new DefaultTableModel(null, nombreColumnasCursadas) {
//			private static final long serialVersionUID = 1L;
//
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//		
//		tablaCursadas = new JTable(modelCursadas);
//
//		tablaCursadas.setBackground(Color.darkGray);
//		tablaCursadas.setForeground(Color.WHITE);
//		
//		tablaCursadas.getColumnModel().getColumn(0).setPreferredWidth(50);
//		tablaCursadas.getColumnModel().getColumn(0).setResizable(false);
//		tablaCursadas.getColumnModel().getColumn(1).setPreferredWidth(50);
//		tablaCursadas.getColumnModel().getColumn(1).setResizable(false);
//		tablaCursadas.getColumnModel().getColumn(2).setPreferredWidth(50);
//		tablaCursadas.getColumnModel().getColumn(2).setResizable(false);
//		tablaCursadas.getColumnModel().getColumn(3).setPreferredWidth(50);
//		tablaCursadas.getColumnModel().getColumn(3).setResizable(false);
//		
//		tablaCursadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		tablaCursadas.getTableHeader().setReorderingAllowed(false);
//		
//		scrollPane.setViewportView(tablaCursadas);
//		panel.setLayout(new GridLayout(1, 0, 0, 0));
//		
//		btnInscribirAlumno = new JButton("Inscribir Alumno");
//		panel.add(btnInscribirAlumno);
//	
//		setLayout(groupLayout);
//	}
//
//	public JTextField getTextFieldFiltro() {
//		return textFieldFiltro;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//	
//	public JTable getTablaCursadas() {
//		return tablaCursadas;
//	}
//
//	public DefaultTableModel getModelCursadas() {
//		return modelCursadas;
//	}
//
//	public String[] getNombreColumnasCursadas() {
//		return nombreColumnasCursadas;
//	}
//	
//	public JButton getBtnInscribirAlumno() {
//		return btnInscribirAlumno;
//	}
//	
//}

package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.CursadaDTO;
import presentacion.components.tabla.TablaGenerica;
import javax.swing.JButton;

public class PanelGestionInscripcionAlumno extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124769L;
	TablaGenerica<CursadaDTO> table;
	private JButton btnInscribirAlumno;

	public PanelGestionInscripcionAlumno()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnInscribirAlumno = new JButton("Inscribir Alumno");
		buttonPane.add(btnInscribirAlumno);
	}

	public TablaGenerica<CursadaDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<CursadaDTO> table)
	{
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnInscribirAlumno() 
	{
		return btnInscribirAlumno;
	}
	
	
}


