package presentacion.vista;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import presentacion.components.tabla.TablaGenerica;

public class PanelEventosSupervisorCompletados extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TablaGenerica<?> table;
	
	
	public PanelEventosSupervisorCompletados()
	{
		setLayout(new BorderLayout(0, 0));
		
		
		
	}

	public TablaGenerica<?> getTable() {
		return table;
	}

	

	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

}
