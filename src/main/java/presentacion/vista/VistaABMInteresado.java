package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dto.PersonaDTO;
import presentacion.components.tabla.TablaGenerica;

public class VistaABMInteresado {
	private JDialog frame;
	private TablaGenerica<PersonaDTO> table;
	private DefaultTableModel model;
	private String[] nombreColumnas = { "Nombre y Apellido","Tel\u00E9fono","Email"};
	private JButton btnAceptar;
	private JButton btnNuevoInteresado;
	private JButton btnEliminar;
	
	public VistaABMInteresado() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Gesti\u00F3n Interesados");
		frame.setBounds(100, 100, 810, 646);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel botonera = new JPanel();
		frame.getContentPane().add(botonera, BorderLayout.SOUTH);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonera.add(btnAceptar);
		
		btnNuevoInteresado = new JButton("Nuevo");
		btnNuevoInteresado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonera.add(btnNuevoInteresado);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		
		model = new DefaultTableModel(null, nombreColumnas) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		
		
	}
	public void show() 
	{
		frame.setVisible(true);
	}

	public void close() 
	{
		frame.dispose();
	}

	public JDialog getFrame() {
		return frame;
	}

	public TablaGenerica<PersonaDTO> getTable() {
		return table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}



	public void setFrame(JDialog frame) {
		this.frame = frame;
	}

	public void setTable(TablaGenerica<PersonaDTO> table) {
		this.table = table;

		frame.getContentPane().add(table, BorderLayout.CENTER);
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JButton getBtnNuevoInteresado() {
		return btnNuevoInteresado;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public void setBtnNuevoInteresado(JButton btnNuevoInteresado) {
		this.btnNuevoInteresado = btnNuevoInteresado;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
}
