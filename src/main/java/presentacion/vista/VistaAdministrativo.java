package presentacion.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTabbedPane;

public class VistaAdministrativo extends JPanel
{
	private static final long serialVersionUID = -297319751293633073L;
	private JTabbedPane tabbedPane;
	
	public VistaAdministrativo()
	{
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
	}
	
	public void agregarVista(String titulo, Component vista) 
	{
		tabbedPane.add(titulo, vista);
	}
	
	public JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}
}
