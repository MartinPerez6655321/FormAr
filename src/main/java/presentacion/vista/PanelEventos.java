package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


import presentacion.components.tabla.TablaGenerica;


public class PanelEventos extends JPanel
{
	private static final long serialVersionUID = 1464592073634728143L;
	
	private TablaGenerica<?> table;
	private JButton btnRegistrarEvento;
	private JButton btnMarcarComoCumplido;

	public PanelEventos()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnRegistrarEvento = new JButton("Registrar Evento");
		
		buttonPane.add(btnRegistrarEvento);
		
		btnMarcarComoCumplido = new JButton("Marcar como cumplido");
		buttonPane.add(btnMarcarComoCumplido);
		
	}
	public PanelEventos getPanel()
	{
		return this;
	}
	
	
	public TablaGenerica<?> getTable() {
		return table;
	}

	public JButton getBtnRegistrarEvento() {
		return btnRegistrarEvento;
	}

	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public void setBtnRegistrarEvento(JButton btnRegistrarEvento) {
		this.btnRegistrarEvento = btnRegistrarEvento;
	}
	
	public JButton getBtnMarcarComoCumplido() {
		return btnMarcarComoCumplido;
	}

	public void setBtnMarcarComoCumplido(JButton btnMarcarComoCumplido) {
		this.btnMarcarComoCumplido = btnMarcarComoCumplido;
	}
	
}
