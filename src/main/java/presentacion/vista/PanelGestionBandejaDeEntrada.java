package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.RecadoDTO;
import presentacion.components.tabla.TablaGenerica;
import presentacion.controlador.ControladorPanelGestionBandejaDeEntrada;
import util.EstilosYColores;

import javax.swing.JButton;

public class PanelGestionBandejaDeEntrada extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124769L;
	TablaGenerica<RecadoDTO> table;
	private JButton btnCrearRecado;
	private JButton btnEliminarRecado;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	public PanelGestionBandejaDeEntrada()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnCrearRecado = new JButton("Crear Recado");
		btnCrearRecado.setBackground(style.getBackgroundButtonCreate());
		btnCrearRecado.setForeground(style.getForegroundButtonCreate());
		buttonPane.add(btnCrearRecado);
		
		btnEliminarRecado = new JButton("Eliminar Recado");
		btnEliminarRecado.setBackground(style.getBackgroundButtonDelete());
		btnEliminarRecado.setForeground(style.getForegroundButtonDelete());
		buttonPane.add(btnEliminarRecado);
		
		//necesito crearlo antes de agregar la tabla... error de diseño?
		new ControladorPanelGestionBandejaDeEntrada(this);
		add(table, BorderLayout.CENTER);
	}

	public TablaGenerica<RecadoDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<RecadoDTO> table)
	{
		this.table = table;
	}

	public JButton getBtnCrearRecado() {
		return btnCrearRecado;
	}

	public JButton getBtnEliminarRecado() {
		return btnEliminarRecado;
	}

}

