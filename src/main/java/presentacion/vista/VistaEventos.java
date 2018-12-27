package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.components.tabla.TablaGenerica;

public class VistaEventos extends JPanel{
	
private static final long serialVersionUID = 1L;
	
	private TablaGenerica<?> table;

	private JButton btnRegistrarEvento;

	private JButton btnMarcarCumplido;
	
	public VistaEventos()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnRegistrarEvento=new JButton("Registrar evento");
		btnMarcarCumplido=new JButton("Marcar como cumplido");
		
		buttonPane.add(btnRegistrarEvento);
		buttonPane.add(btnMarcarCumplido);
	}

	public TablaGenerica<?> getTable() {
		return table;
	}


	public void setTable(TablaGenerica<?> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnCrearEvento() {
		return btnRegistrarEvento;
	}

	public JButton getBtnMarcarCumplido() {
		return btnMarcarCumplido;
	}

	public void setBtnCrearEvento(JButton btnCrearEvento) {
		this.btnRegistrarEvento = btnCrearEvento;
	}

	public void setBtnMarcarCumplido(JButton btnMarcarCumplido) {
		this.btnMarcarCumplido = btnMarcarCumplido;
	}

}
