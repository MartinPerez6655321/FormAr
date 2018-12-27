package main;

import javax.swing.UIManager;

import presentacion.controlador.ControladorLogin;
import presentacion.vista.VentanaLogin;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		VentanaLogin ventanaLogin=new VentanaLogin();
		ControladorLogin controladoLogin=new ControladorLogin(ventanaLogin);
		controladoLogin.inicializar();
	}
}
