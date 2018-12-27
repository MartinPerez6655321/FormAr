package presentacion.controlador;

import java.awt.event.ActionEvent;

import Temporizador.Temporizador;
import connections.ConnectionManager;
import dto.UsuarioDTO;
import presentacion.vista.PanelGestionBandejaDeEntrada;
import presentacion.vista.PanelGestionBandejaDeSalida;

import presentacion.vista.VentanaLogin;

import presentacion.vista.VentanaPrincipalTabs;
import presentacion.vista.VistaAdministrador;
import presentacion.vista.VistaAdministrativo;
import presentacion.vista.VistaInstructor;
import presentacion.vista.VistaPresentacion;
import presentacion.vista.VistaAlumno;


import presentacion.vista.VistaSupervisor;

public class ControladorVentanaPrincipalTabs {
	private VentanaPrincipalTabs vista;

	private VistaAdministrativo vistaAdministrativo;
	private ControladorVistaAdministrativo controladorAdministrativo;

	private VistaAdministrador vistaAdministrador;

	private VistaInstructor vistaInstructor;
	private VistaSupervisor vistaSupervisor;


	private Temporizador temporizador;


	private VistaAlumno vistaAlumno;
	
	
	
	public ControladorVentanaPrincipalTabs(UsuarioDTO usuario)
	{

		vista = new VentanaPrincipalTabs();

		vista.getBtnInstructor().setVisible(usuario.getInstructor());
		vista.getBtnAdministrador().setVisible(usuario.getAdministrador());
		vista.getBtnPersonalAdministrativo().setVisible(usuario.getAdministrativo());
		vista.getBtnSupervisor().setVisible(usuario.getSupervisor());

		vista.getBtnAlumno().setVisible(usuario.getAlumno());

		vista.getBtnAdministrador().addActionListener(this::mostrarVistaAdministrador);
		vista.getBtnSupervisor().addActionListener(this::mostrarVistaSupervisor);
		vista.getBtnPersonalAdministrativo().addActionListener(this::mostrarVistaPersonalAdministrativo);
		vista.getBtnInstructor().addActionListener(this::mostrarVistaInstructor);
		vista.getBtnAlumno().addActionListener(this::mostrarVistaAlumno);

		vista.getBtnImportarBD().addActionListener(this::mostrarVistaImportar);
		vista.getBtnExportarBD().addActionListener(this::mostrarVistaExportar);
		vista.getBtnConfigurarBD().addActionListener(e -> configurarBD());

		vista.getBtnBandejaDeEntrada().addActionListener(this::mostrarVistaBandejaDeEntrada);
		vista.getBtnBandejaDeSalida().addActionListener(this::mostrarVistaBandejaDeSalida);

		vista.getBtnCerrarSesion().addActionListener(e -> this.cerrarSesion());
		
		if (usuario.getAdministrador()) {
			vista.getMnBD().setVisible(true);
		}

		if (usuario.getAdministrativo()) {
			vistaAdministrativo = new VistaAdministrativo();
			this.controladorAdministrativo = new ControladorVistaAdministrativo(vistaAdministrativo);
		}

		if (usuario.getAdministrador()) {
			vistaAdministrador = new VistaAdministrador();
			new ControladorVistaAdministrador(vistaAdministrador);
		}

		if (usuario.getInstructor()) {
			vistaInstructor = new VistaInstructor();
			new ControladorVistaInstructor(vistaInstructor);
		}

		if (usuario.getSupervisor()) {
			vistaSupervisor = new VistaSupervisor();
			new ControladorVistaSupervisor(vistaSupervisor);
		}

		this.temporizador=new Temporizador(new ControladorPanelNotificacion(vista.getPanelNotificacion(), vista, this.controladorAdministrativo,
				this.vistaAdministrativo), this.vista);

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(usuario.getAlumno())
		{
			vistaAlumno=new VistaAlumno();
			new ControladorVistaAlumno(vistaAlumno);
		}

		vista.setCurrentView(new VistaPresentacion());
		
		vista.setVisible(true);
	}

	private void configurarBD()
	{
		ConnectionManager.getConnectionManager().manageConnectionParameters();
	}

	private void mostrarVistaAdministrador(ActionEvent e) {
		vista.setCurrentView(vistaAdministrador);
	}

	
	private void mostrarVistaAlumno(ActionEvent e)
	{
		vista.setCurrentView(vistaAlumno);
	}
	
	private void mostrarVistaSupervisor(ActionEvent e)
	{

		vista.setCurrentView(vistaSupervisor);
	}

	private void mostrarVistaPersonalAdministrativo(ActionEvent e) {
		vista.setCurrentView(vistaAdministrativo);
	}

	private void mostrarVistaInstructor(ActionEvent e) {
		vista.setCurrentView(vistaInstructor);
	}

	private void mostrarVistaImportar(ActionEvent e) {
		new ControladorImportBD(this.vista);
	}

	private void mostrarVistaExportar(ActionEvent e) {
		new ControladorExportBD();
	}

	private void mostrarVistaBandejaDeEntrada(ActionEvent e) {
		PanelGestionBandejaDeEntrada vistaBandejaEntrada = new PanelGestionBandejaDeEntrada();
		vista.setCurrentView(vistaBandejaEntrada);
	}

	private void mostrarVistaBandejaDeSalida(ActionEvent e) {
		PanelGestionBandejaDeSalida vistaBandejaSalida = new PanelGestionBandejaDeSalida();
		vista.setCurrentView(vistaBandejaSalida);
	}

	private void cerrarSesion() {
		this.vista.close();
		VentanaLogin ventanaLogin=new VentanaLogin();
		ControladorLogin controladoLogin=new ControladorLogin(ventanaLogin);
		controladoLogin.inicializar();
	}
	
}
