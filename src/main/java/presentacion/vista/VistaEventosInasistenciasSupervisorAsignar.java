package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.components.tabla.TablaGenerica;

public class VistaEventosInasistenciasSupervisorAsignar extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAsignarEvento;
	private TablaGenerica<?> table;
	
	public VistaEventosInasistenciasSupervisorAsignar()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		
		btnAsignarEvento = new JButton("Asignar Evento");
		
		buttonPane.add(btnAsignarEvento);
	}

	public TablaGenerica<?> getTable() {
		return table;
	}


	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnAsignarEvento() {
		return btnAsignarEvento;
	}

	public void setBtnAsignarEvento(JButton btnAsignarEvento) {
		this.btnAsignarEvento = btnAsignarEvento;
	}


}
