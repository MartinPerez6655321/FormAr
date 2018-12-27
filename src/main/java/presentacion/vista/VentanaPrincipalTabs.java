package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JPanel;

import util.EstilosYColores;

public class VentanaPrincipalTabs
{
	private JFrame frame;
	private JMenuItem btnAdministrador;
	private JMenuItem btnSupervisor;
	private JMenuItem btnPersonalAdministrativo;
	private JMenuItem btnInstructor;

	private JMenu mnBD;
	private JMenuItem btnExportarBD;
	private JMenuItem btnImportarBD;
	
	private JButton btnCerrarSesion;

	private JMenu mnRecado;
	private JMenuItem btnBandejaDeEntrada;
	private JMenuItem btnBandejaDeSalida;

	private Component currentView;
	
	private PanelNotificaciones panelNotificacion;

	private JMenuItem btnAlumno;
	private JMenuItem mntmConfigurarBaseDe;

	private EstilosYColores style = EstilosYColores.getInstance();
	
	public VentanaPrincipalTabs()
	{
		initialize();
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 897, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("FormAR");
		
		try {
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipalTabs.class.getResource("/Imagenes/icon.png")));
		} catch (Exception e) {
			System.err.println("No se pudo cargar el ícono en " + this.getClass().getName());
		}
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnVistas = new JMenu("Vistas");
		menuBar.add(mnVistas);
		
		btnAdministrador = new JMenuItem("Administrador");
		btnSupervisor = new JMenuItem("Supervisor");
		btnPersonalAdministrativo = new JMenuItem("Personal administrativo");
		btnInstructor = new JMenuItem("Instructor");
		btnAlumno=new JMenuItem("Alumno");
		
		mnVistas.add(btnAdministrador);
		mnVistas.add(btnSupervisor);
		mnVistas.add(btnPersonalAdministrativo);
		mnVistas.add(btnInstructor);
		mnVistas.add(btnAlumno);

		mnBD = new JMenu("Import/Export BD");
		menuBar.add(mnBD);
		btnImportarBD = new JMenuItem("Importar");
		btnExportarBD = new JMenuItem("Exportar");
		mnBD.setVisible(false);
		
		mntmConfigurarBaseDe = new JMenuItem("Configurar base de datos");
		mnBD.add(mntmConfigurarBaseDe);
		mnBD.add(btnImportarBD);
		mnBD.add(btnExportarBD);

		mnRecado = new JMenu("Recados");
		menuBar.add(mnRecado);
		btnBandejaDeEntrada = new JMenuItem("Recibidos");
		btnBandejaDeSalida = new JMenuItem("Enviados");
		btnBandejaDeSalida.setPreferredSize(new Dimension(80, 25));
		mnRecado.add(btnBandejaDeEntrada);
		mnRecado.add(btnBandejaDeSalida);
		
		btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.setPreferredSize(new Dimension(135, 25));
		btnCerrarSesion.setBackground(style.getBackgroundButtonStandard());
		btnCerrarSesion.setForeground(style.getForegroundButtonStandard());
		
		JPanel panelOeste = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelOeste.add(btnCerrarSesion);
		menuBar.add(panelOeste);
		
		this.panelNotificacion = new PanelNotificaciones();
		frame.getContentPane().add(panelNotificacion, BorderLayout.WEST);
	}
	
	
	
	public PanelNotificaciones getPanelNotificacion() 
	{
		return panelNotificacion;
	}
	
	



	
	public void setCurrentView(Component view)
	{
		view.setVisible(false);
			if(currentView!=null)
		frame.getContentPane().remove(currentView);
		frame.getContentPane().add(view, BorderLayout.CENTER);
		currentView = view;
		view.setVisible(true);
	}
	
	public void setVisible(boolean visible) 
	{
		frame.setVisible(visible);
	}
	
	public JMenu getMnBD() {
		return mnBD;
	}

	
	public AbstractButton getBtnAdministrador()
	{
		return btnAdministrador;
	}

	public AbstractButton getBtnSupervisor()
	{
		return btnSupervisor;
	}

	public AbstractButton getBtnPersonalAdministrativo()
	{
		return btnPersonalAdministrativo;
	}

	public AbstractButton getBtnInstructor()
	{
		return btnInstructor;
	}

	public JMenuItem getBtnAlumno() {
		return btnAlumno;
	}
	
	public AbstractButton getBtnExportarBD() {
		return btnExportarBD;
	}

	public AbstractButton getBtnImportarBD() {
		return btnImportarBD;
	}
	
	public AbstractButton getBtnConfigurarBD() 
	{
		return mntmConfigurarBaseDe;
	}
	
	public JMenu getMnRecado() {
		return mnRecado;
	}
	
	public AbstractButton getBtnBandejaDeEntrada() {
		return btnBandejaDeEntrada;
	}

	public AbstractButton getBtnBandejaDeSalida() {
		return btnBandejaDeSalida;

	}

	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

    public void close()
	{
    	frame.dispose();
	}

}
