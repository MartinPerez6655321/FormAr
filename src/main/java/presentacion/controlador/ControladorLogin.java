package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JOptionPane;


import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaLogin;

public class ControladorLogin implements ActionListener {
	private VentanaLogin ventanaLogin;
	private ModeloPersonas model;

	public ControladorLogin(VentanaLogin ventana) {
		this.ventanaLogin = ventana;
		this.ventanaLogin.getBtnAcceso().addActionListener(this);

		this.model = ModeloPersonas.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventanaLogin.getBtnAcceso()) {

			if (this.ventanaLogin.getTextFieldUsuario().getText().equals("")
					&& this.ventanaLogin.getTextFieldContraseña().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "<html>El sistema no admite campos vac\u00EDos</html>");
				return;
			} else if (this.ventanaLogin.getTextFieldUsuario().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "<html>El campo usuario esta vac\u00EDo</html>");
				return;
			} else if (this.ventanaLogin.getTextFieldContraseña().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "<html>El campo contrase\u00F1a est\u00E1 vac\u00EDo</html>");
				return;
			}

		}

		List<UsuarioDTO> usuariosEnSistema = model.getUsuarios();
		Boolean usuarioExiste = false;
		UsuarioDTO usuario=null;
		for (UsuarioDTO usuarioDTO : usuariosEnSistema) {

			if (this.ventanaLogin.getTextFieldContraseña().getText().equals(usuarioDTO.getPassword())
					&& this.ventanaLogin.getTextFieldUsuario().getText().equals(usuarioDTO.getEmail())
					&& usuarioDTO.getDisponibleEnSistema()==true)
				{
				usuarioExiste = true;
				usuario=usuarioDTO;
				}

		}

		if (!usuarioExiste) 
		{
			JOptionPane.showMessageDialog(null,
					"<html>El nombre de usuario y/o la<br>contrase\u00F1a ingresados no son correctos.</html>");
			return;
		} 
		else 
		{
			model.setUsuarioActual(usuario);
			
			new ControladorVentanaPrincipalTabs(usuario);

			this.ventanaLogin.close();
			usuarioExiste = true;

		}

	}



	public void inicializar() {
		this.ventanaLogin.show();
	}


	
	
}
