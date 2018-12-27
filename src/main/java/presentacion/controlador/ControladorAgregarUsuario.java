package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dto.AdministradorDTO;
import dto.AlumnoDTO;
import dto.HorarioDTO;
import dto.InstructorDTO;
import dto.PersonaDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaAgregarUsuario;
import util.Strings;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorAgregarUsuario implements ActionListener, MouseListener
{
	private ModeloPersonas model = ModeloPersonas.getInstance();
	private VentanaAgregarUsuario vista;

	public ControladorAgregarUsuario(VentanaAgregarUsuario vista)
	{
		this.vista = vista;
		this.vista.getBtnAgregarUsuario().addActionListener(this);
		this.vista.getChckbxAlumno().addMouseListener(this);
		this.vista.getDateFechaIng().setEnabled(false);
	}
	
	public void initialize()
	{
		this.vista.show();	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()== this.vista.getBtnAgregarUsuario()) 
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
				JOptionPane.showMessageDialog(null, "Faltan completar campos al usuario");
				return;
			}
			if(!ValidadorLogico.validarNombreUsuario(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Usuario existente en el sistema");
				return;
			}			
			if(!ValidadorCampos.validarUsuario(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de usuario inv\u00E1lido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
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
				JOptionPane.showMessageDialog(null, "Formato de dni inv\u00E1lido. Deben ser al menos 7 numeros.");
				return;
			}
			if(!ValidadorLogico.dniValido(this.vista.getTextFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Dni existente en el sistema");
				return;
			}
		    String mail = this.vista.getTextFieldEmail().getText().toLowerCase(); 
			if(!ValidadorLogico.emailValido(mail)) 
			{
				JOptionPane.showMessageDialog(null, "Email existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarContraseña(Strings.getPasswordString(this.vista.getIntopasswordField()))) 
			{
				JOptionPane.showMessageDialog(null, "Formato de password inv\u00E1lido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
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
			if(!ValidadorCampos.validarTelefono(this.vista.getTextFieldTelefono().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de Tel\u00E9fono inv\u00E1lido. Recuerde que s\u00F3lo puede contener n\u00FAmeros o los signos - o + , y ser de 6 a 16 caracteres.");
				return;
			}
			if(!Strings.getPasswordString(this.vista.getIntopasswordField()).equals(Strings.getPasswordString(this.vista.getRepeatpasswordField()))) 
			{
				JOptionPane.showMessageDialog(null, "Passwords insconsistentes");
				return;
			}
			if(this.vista.getChckbxAlumno().isSelected())
			{
				if(this.vista.getDateFechaIng().getDate()==null) {
					JOptionPane.showMessageDialog(null, "Faltan completar fecha inscripci\u00F3n del usuario alumno");
					return;
				}
				if (ValidadorLogico.esProximoAHoy(this.vista.getDateFechaIng().getDate()))
				{
					JOptionPane.showMessageDialog(null,"Fecha inv\u00E1lida. Debe ser anterior o igual a la fecha actual.");
					return;
				}
			}
			
			UsuarioDTO newUser = new UsuarioDTO ();
			newUser.setId(0);
			newUser.setEmail(this.vista.getTextFieldUsuario().getText());
			newUser.setPassword(Strings.getPasswordString(this.vista.getIntopasswordField()));
			newUser.setDisponibleEnSistema(true);
			
			newUser.setAdministrador(this.vista.getChckbxAdministrador().isSelected());
			newUser.setSupervisor(this.vista.getChckbxSupervisor().isSelected());
			newUser.setAdministrativo(this.vista.getChckbxPersonalAdmin().isSelected());
			newUser.setInstructor(this.vista.getChckbxInstructor().isSelected());
			newUser.setAlumno(this.vista.getChckbxAlumno().isSelected());
			
			PersonaDTO persona = new PersonaDTO();
			persona.setNombre(this.vista.getTextFieldNombre().getText());
			persona.setApellido(this.vista.getTextFieldApellido().getText());
			persona.setEmail(mail);
			persona.setTelefono(this.vista.getTextFieldTelefono().getText());
			persona.setDisponibleEnSistema(true);
			persona.setDni(this.vista.getTextFieldDNI().getText());
			this.model.agregarPersona(persona);
			if(this.vista.getChckbxAdministrador().isSelected())
			{
				AdministradorDTO newAdmin = new AdministradorDTO();
				newAdmin.setDisponibleEnSistema(true);
				newAdmin.setPersona(persona);
				this.model.agregarAdministrador(newAdmin);
			}
			if(this.vista.getChckbxSupervisor().isSelected())
			{
				SupervisorDTO newSupervisor = new SupervisorDTO();
				newSupervisor.setDisponibleEnSistema(true);
				newSupervisor.setPersona(persona);
				this.model.agregarSupervisor(newSupervisor);
			}
			if(this.vista.getChckbxPersonalAdmin().isSelected())
			{
				PersonalAdministrativoDTO newPersonalAdmin = new PersonalAdministrativoDTO();
				newPersonalAdmin.setDisponibleEnSistema(true);
				newPersonalAdmin.setPersona(persona);
				this.model.agregarPersonalAdministrativo(newPersonalAdmin);
			}
			if(this.vista.getChckbxInstructor().isSelected())
			{
				InstructorDTO newInstructor = new InstructorDTO();
				newInstructor.setDisponibleEnSistema(true);
				newInstructor.setDisponibilidades(new ArrayList<HorarioDTO>());
				newInstructor.setPersona(persona);
				this.model.agregarInstructor(newInstructor);
			}
			if(this.vista.getChckbxAlumno().isSelected())
			{
				AlumnoDTO newAlumno = new AlumnoDTO();
				newAlumno.setFechaDeCreacion(this.vista.getDateFechaIng().getDate());
				int anio = this.vista.getDateFechaIng().getDate().getYear()+1900;
				newAlumno.setLegajo(this.vista.getTextFieldDNI().getText()+"/"+anio);
				newAlumno.setDisponibleEnSistema(true);
				newAlumno.setPersona(persona);
				this.model.agregarAlumno(newAlumno);
			}

			newUser.setPersona(persona);
			
			this.model.agregarUsuario(newUser);
			
			this.vista.close();
		}
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.vista.getChckbxAlumno().isSelected())
		{
			this.vista.getDateFechaIng().setEnabled(true);
		}
		else
		{
			this.vista.getDateFechaIng().setEnabled(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}


}
