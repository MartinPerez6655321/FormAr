package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.RecadoDTO;
import presentacion.components.tabla.TablaGenerica;
import presentacion.controlador.ControladorPanelGestionBandejaDeSalida;
import util.EstilosYColores;

import javax.swing.JButton;

public class PanelGestionBandejaDeSalida extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124769L;
	TablaGenerica<RecadoDTO> table;
	private JButton btnEliminar;
	private EstilosYColores style = EstilosYColores.getInstance();

	public PanelGestionBandejaDeSalida()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnEliminar = new JButton("Eliminar Recado");
		btnEliminar.setBackground(style.getBackgroundButtonDelete());
		btnEliminar.setForeground(style.getForegroundButtonDelete());
		buttonPane.add(btnEliminar);
		
		//necesito crearlo antes de agregar la tabla... error de diseño?
		new ControladorPanelGestionBandejaDeSalida(this);
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

	public JButton getBtnEliminar() {
		return btnEliminar;
	}


}

