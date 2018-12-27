package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class VistaEventosInteresados extends JPanel {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;

	public VistaEventosInteresados() {
		setLayout(new BorderLayout(0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void agregarVista(String titulo, Component vista) 
	{
		tabbedPane.add(titulo, vista);
	}
}
