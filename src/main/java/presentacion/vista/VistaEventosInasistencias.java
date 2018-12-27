package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class VistaEventosInasistencias extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;

	public VistaEventosInasistencias() {
		setLayout(new BorderLayout(0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void agregarVista(String titulo, Component vista) 
	{
		tabbedPane.add(titulo, vista);
	}
}
