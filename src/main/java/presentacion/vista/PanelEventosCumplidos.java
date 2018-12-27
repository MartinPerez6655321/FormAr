package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import presentacion.components.tabla.TablaGenerica;

public class PanelEventosCumplidos extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TablaGenerica<?> table;

	public PanelEventosCumplidos()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
	}

	public TablaGenerica<?> getTable() {
		return table;
	}

	

	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	

}
