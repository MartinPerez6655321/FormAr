package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dto.HorarioDTO;
import dto.InstructorDTO;
import dto.PersonaDTO;
import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaAgregarInstructor;
import util.Strings;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorAgregarInstructor implements ActionListener
{
	private ModeloPersonas modelUser = ModeloPersonas.getInstance();
	private VentanaAgregarInstructor vista;

	public ControladorAgregarInstructor(VentanaAgregarInstructor vista)
	{
		this.vista = vista;
		this.vista.getBtnAgregarInstructor().addActionListener(this);
		
	}
	
	public void initialize()
	{
		this.vista.show();	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		if(e.getSource()== this.vista.getBtnAgregarInstructor()) 
		{
	
			if (!ValidadorCampos.validarVacio(this.vista.getTextFieldUsuario().getText()) ||
				!ValidadorCampos.validarVacio(Strings.getPasswordString(this.vista.getIntopasswordField())) || 
				!ValidadorCampos.validarVacio(Strings.getPasswordString(this.vista.getRepeatpasswordField())) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldNombre().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldApellido().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldDNI().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldEmail().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldTelefono().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Faltan completar campos al instructor");
				return;
			}
			if(!ValidadorLogico.validarNombreUsuario(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Usuario existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarNombre(this.vista.getTextFieldNombre().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de nombre inv\u00E1lido. Debe ser entre 3 a 16 caracteres tipo letra");
				return;
			}
			if(!ValidadorCampos.validarApellido(this.vista.getTextFieldApellido().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de apellido inv\u00E1lido. Debe ser entre 3 a 16 caracteres tipo letra");
				return;
			}
			if(!ValidadorCampos.validarDNI(this.vista.getTextFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de dni inv\u00E1lido. Deben ser 8 n\u00FAmeros.");
				return;
			}
			if(!ValidadorLogico.dniValido(this.vista.getTextFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Dni existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarUsuario(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de usuario inv\u00E1lido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
				return;
			}

		    String mail = this.vista.getTextFieldEmail().getText().toLowerCase(); 
			if(!ValidadorLogico.emailValido(mail)) 
			{
				JOptionPane.showMessageDialog(null, "Email existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarMail(mail)) 
			{
				JOptionPane.showMessageDialog(null, "Formato de mail inv\u00E1lido. Ej.: ejem.plo@ungs.com");
				return;
			}
			if(!ValidadorLogico.telefonoValido(this.vista.getTextFieldTelefono().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Tel\u00E9fono existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarContraseña(Strings.getPasswordString(this.vista.getIntopasswordField()))) 
			{
				JOptionPane.showMessageDialog(null, "Formato de password inv\u00E1lido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
				return;
			}
			if(!ValidadorCampos.validarTelefono(this.vista.getTextFieldTelefono().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de Tel\u00E9fono inv\u00E1lido. Debe ser alfanum\u00E9rico de 6 a 16");
				return;
			}
			if(!Strings.getPasswordString(this.vista.getIntopasswordField()).equals(Strings.getPasswordString(this.vista.getRepeatpasswordField()))) 
			{
				JOptionPane.showMessageDialog(null, "Passwords insconsistentes");
				return;
			}
			
			UsuarioDTO newUser = new UsuarioDTO ();
			newUser.setEmail(this.vista.getTextFieldUsuario().getText());
			newUser.setPassword(Strings.getPasswordString(this.vista.getIntopasswordField()));
			newUser.setAdministrativo(false);
			newUser.setAdministrador(false);
			newUser.setSupervisor(false);
			newUser.setInstructor(true);
			newUser.setAlumno(false);
			newUser.setDisponibleEnSistema(true);
			
			PersonaDTO persona = new PersonaDTO();
			persona.setNombre(this.vista.getTextFieldNombre().getText());
			persona.setApellido(this.vista.getTextFieldApellido().getText());
			persona.setDni(this.vista.getTextFieldDNI().getText());
			persona.setEmail(mail);
			persona.setTelefono(this.vista.getTextFieldTelefono().getText());
			persona.setDisponibleEnSistema(true);
			
			modelUser.agregarPersona(persona);
			
			InstructorDTO newInstructor = new InstructorDTO();
			newInstructor.setDisponibilidades(new ArrayList<HorarioDTO>());
			newInstructor.setDisponibleEnSistema(true);
			newInstructor.setPersona(persona);
			modelUser.agregarInstructor(newInstructor);
			
			newInstructor.setPersona(persona);
			
			newUser.setPersona(persona);
			
			modelUser.agregarUsuario(newUser);
			
			this.vista.close();
		}
		
	}

}
