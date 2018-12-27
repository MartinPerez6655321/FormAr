package util;

import java.awt.Color;

import modelo.ModeloRecados;
import presentacion.vista.VentanaPrincipalTabs;

public class TemporizadorNotificacionRecado extends Thread {

	boolean cronometroActivo;
	private VentanaPrincipalTabs vista;
	private ModeloRecados model = ModeloRecados.getInstance();

	public TemporizadorNotificacionRecado(VentanaPrincipalTabs vista) {
		super();
		this.vista = vista;


		cronometroActivo = true;
		start();
	}

	public void run() {
		try {
			while (cronometroActivo) {
				Thread.sleep(5000);
				
				actualizarVistaPrincipalTabs();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}



	public void actualizarVistaPrincipalTabs()
	{
		int cantEntradas = this.model.cantidadDeRecadosNoVistos();
		this.vista.getMnRecado().setText("Recados ("+cantEntradas+")");
		this.vista.getBtnBandejaDeEntrada().setText("Recibidos ("+cantEntradas+")");
		if(cantEntradas!=0)
		{
			this.vista.getMnRecado().setForeground(Color.RED);
			this.vista.getBtnBandejaDeEntrada().setBackground(Color.orange);
		}
		else {
			this.vista.getMnRecado().setForeground(null);
			this.vista.getBtnBandejaDeEntrada().setBackground(null);
		}
		this.model.actualizarRecados();
	}
	
}