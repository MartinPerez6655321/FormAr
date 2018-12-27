package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.PersonaDTO;
import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaEditarAlumno;
import util.Strings;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorEditarAlumno implements ActionListener
{

	private ModeloPersonas model = ModeloPersonas.getInstance();
	private VentanaEditarAlumno vista;
	private UsuarioDTO usuario;
	private AlumnoDTO alumnoModificado;

	public ControladorEditarAlumno(VentanaEditarAlumno vista, UsuarioDTO user)
	{
		this.vista = vista;
		this.usuario = user;
		this.vista.getBtnEditarAlumno().addActionListener(this);
		this.vista.getTextFieldNombre().setText(this.usuario.getPersona().getNombre());
		this.vista.getTextFieldApellido().setText(this.usuario.getPersona().getApellido());
		alumnoModificado = this.model.getAlumnoPorPersona(this.usuario.getPersona());
		this.vista.getDateCreacion().setDate(alumnoModificado.getFechaDeCreacion());
		this.vista.getTxtFieldDNI().setText(this.usuario.getPersona().getDni());
		this.vista.getTextFieldEmail().setText(this.usuario.getPersona().getEmail());
		this.vista.getTextFieldTelefono().setText(this.usuario.getPersona().getTelefono());
		this.vista.getTextFieldUsuario().setText(this.usuario.getEmail());
		this.vista.getIntopasswordField().setText(this.usuario.getPassword());
		this.vista.getRepeatpasswordField().setText(this.usuario.getPassword());
	}
	
	public void initialize()
	{
		this.vista.show();	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (!ValidadorCampos.validarVacio(this.vista.getTextFieldUsuario().getText()) ||
				!ValidadorCampos.validarVacio(Strings.getPasswordString(this.vista.getIntopasswordField())) || 
				!ValidadorCampos.validarVacio(Strings.getPasswordString(this.vista.getRepeatpasswordField())) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldNombre().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldApellido().getText()) ||
				!ValidadorCampos.validarVacio(this.vista.getTxtFieldDNI().getText()) ||
				!ValidadorCampos.validarVacio(this.vista.getDateCreacion().getDateFormatString()) ||
				!ValidadorCampos.validarVacio(this.vista.getTextFieldEmail().getText()) || 
				!ValidadorCampos.validarVacio(this.vista.getTextFieldTelefono().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Faltan completar campos al alumno");
				return;
			}
		if(!this.usuarioValido(this.vista.getTextFieldUsuario().getText())) 
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
		if(!ValidadorCampos.validarDNI(this.vista.getTxtFieldDNI().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Formato de dni inv\u00E1lido. Deben ser 8 numeros.");
			return;
		}
		if(!this.dniValido(this.vista.getTxtFieldDNI().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Dni existente en el sistema");
			return;
		}
		if (ValidadorLogico.esProximoAHoy(this.vista.getDateCreacion().getDate()))
		{
			JOptionPane.showMessageDialog(null,"Fecha inv\u00E1lida. Debe ser anterior o igual a la fecha actual.");
			return;
		}
	    String mail = this.vista.getTextFieldEmail().getText().toLowerCase(); 
		if(!emailValido(mail)) 
		{
			JOptionPane.showMessageDialog(null, "Email existente en el sistema");
			return;
		}
		if(!ValidadorCampos.validarMail(mail)) 
		{
			JOptionPane.showMessageDialog(null, "Formato de mail inv\u00E1lido. Ej.: ejem.plo@ungs.com");
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
		if(!telefonoValido(this.vista.getTextFieldTelefono().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Tel\u00E9fono existente en el sistema");
			return;
		}
		if(!Strings.getPasswordString(this.vista.getIntopasswordField()).equals(Strings.getPasswordString(this.vista.getRepeatpasswordField()))) 
		{
			JOptionPane.showMessageDialog(null, "Passwords insconsistentes");
			return;
		}
		
		alumnoModificado.setFechaDeCreacion(this.vista.getDateCreacion().getDate());
		int anio = this.vista.getDateCreacion().getDate().getYear()+1900;
		alumnoModificado.setLegajo(this.vista.getTxtFieldDNI().getText()+"/"+anio);
		
		PersonaDTO personaModificada = usuario.getPersona();
		personaModificada.setDni(vista.getTxtFieldDNI().getText());
		personaModificada.setNombre(this.vista.getTextFieldNombre().getText());
		personaModificada.setApellido(this.vista.getTextFieldApellido().getText());
		personaModificada.setEmail(mail);
		personaModificada.setTelefono(this.vista.getTextFieldTelefono().getText());
		usuario.setEmail(this.vista.getTextFieldUsuario().getText());
		usuario.setPassword(Strings.getPasswordString(this.vista.getIntopasswordField()));

		this.model.modificarPersonaDTO(personaModificada);

		alumnoModificado.setPersona(personaModificada);
		usuario.setPersona(personaModificada);
		
		this.model.modificarAlumno(alumnoModificado);

		this.model.modificarUsuarioDTO(usuario);
		
		this.vista.close();
		
	}
	
	private boolean usuarioValido(String usuario) 
	{
		List<UsuarioDTO> usuarios = this.model.getUsuarios();
		UsuarioDTO usuarioEdit =this.usuario;
		
		for (UsuarioDTO user: usuarios)
		{
			if(user.getEmail().equals(usuario)&&user.getId()!=usuarioEdit.getId()) 
			{
				return false;
			}
		}
		return true;
	}

	private boolean emailValido(String email) 
	{
		List<PersonaDTO> personas = this.model.getPersonas();
		UsuarioDTO usuarioEdit =this.usuario;
		
		for (PersonaDTO persona: personas)
		{
			if(persona.getEmail()!=null)
			{
				if(persona.getEmail().equals(email)&&persona.getId()!=usuarioEdit.getPersona().getId())
				{
					return false;
				}				
			}
		}
		return true;
	}
	
	private boolean telefonoValido(String telefono) 
	{
		List<PersonaDTO> personas = this.model.getPersonas();
		UsuarioDTO usuarioEdit =this.usuario;
		
		for (PersonaDTO persona: personas)
		{
			if(persona.getTelefono().equals(telefono)&&persona.getId()!=usuarioEdit.getPersona().getId()) 
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean dniValido(String dni) 
	{
		for (PersonaDTO persona : model.getPersonas())
		{
			if(persona.getDni()!=null)
			{
				if(persona.getDni().equals(this.vista.getTxtFieldDNI().getText()) && persona.getId()!=this.usuario.getPersona().getId()) 
				{
					return false;
				}
			}
		}
		return true;
	}
	
}


