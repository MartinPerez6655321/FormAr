package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;



import presentacion.components.tabla.TablaGenerica;

public class PanelEventosSupervisorAsignar extends JPanel
{
	private static final long serialVersionUID = -4889484838976801544L;
	
	private TablaGenerica<?> table;
	private JButton btnAsignarEvento;
	private JButton btnCrearEvento;

	public PanelEventosSupervisorAsignar()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnCrearEvento = new JButton("Crear Evento");
		buttonPane.add(btnCrearEvento);
		
		btnAsignarEvento = new JButton("Asignar Evento");
		
		buttonPane.add(btnAsignarEvento);
	}

	public TablaGenerica<?> getTable() {
		return table;
	}

	public JButton getBtnAsignarEvento() {
		return btnAsignarEvento;
	}

	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnCrearEvento() {
		return btnCrearEvento;
	}

	public void setBtnCrearEvento(JButton btnCrearEvento) {
		this.btnCrearEvento = btnCrearEvento;
	}

	public void setBtnAsignarEvento(JButton btnAsignarEvento) {
		this.btnAsignarEvento = btnAsignarEvento;
	}
}
