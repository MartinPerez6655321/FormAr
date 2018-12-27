package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.UsuarioDTO;
import presentacion.components.tabla.TablaGenerica;
import util.EstilosYColores;

import javax.swing.JButton;

public class PanelGestionUsuarios extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124769L;
	TablaGenerica<UsuarioDTO> table;
	private JButton btnAgregarUsuario;
	private JButton btnEliminarUsuario;
	private EstilosYColores style = EstilosYColores.getInstance();

	public PanelGestionUsuarios()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnAgregarUsuario = new JButton("Agregar Usuario");
		btnAgregarUsuario.setBackground(style.getBackgroundButtonCreate());
		btnAgregarUsuario.setForeground(style.getForegroundButtonCreate());
		buttonPane.add(btnAgregarUsuario);

		btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.setBackground(style.getBackgroundButtonDelete());
		btnEliminarUsuario.setForeground(style.getForegroundButtonDelete());
		buttonPane.add(btnEliminarUsuario);
		
		
		

	}

	public TablaGenerica<UsuarioDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<UsuarioDTO> table)
	{
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnAgregarUsuario() {
		return btnAgregarUsuario;
	}

	public JButton getBtnEliminarUsuario() {
		return btnEliminarUsuario;
	}

}

