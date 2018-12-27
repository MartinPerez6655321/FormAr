package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.InteresadoDTO;
import dto.PersonaDTO;
import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaAgregarAlumno;
import util.Strings;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorAgregarAlumno implements ActionListener
{
	private ModeloPersonas model = ModeloPersonas.getInstance();
	private VentanaAgregarAlumno vista;
	private InteresadoDTO interesadoRecibido;

	public ControladorAgregarAlumno(VentanaAgregarAlumno vista)
	{
		this.vista = vista;
		this.vista.getBtnAgregarAlumno().addActionListener(this);
		this.vista.getDateCreacion().setDate(new Date());
	}
	
	public ControladorAgregarAlumno(VentanaAgregarAlumno ventanaAgregarAlumno, InteresadoDTO selectedItem)
	{
		this.vista = ventanaAgregarAlumno;
		
		this.vista.getDateCreacion().setDate(new Date());
		this.interesadoRecibido=selectedItem;
		this.vista.completarDatos(interesadoRecibido.getPersona());
		this.vista.getBtnAgregarAlumno().addActionListener(this);
	}

	public void initialize()
	{
		this.vista.show();	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()== this.vista.getBtnAgregarAlumno()) 
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
			if(!ValidadorCampos.validarDNI(this.vista.getTxtFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de dni inv\u00E1lido. Deben ser 8 n\u00FAmeros.");
				return;
			}
			if(!ValidadorLogico.dniValido(this.vista.getTxtFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Dni existente en el sistema");
				return;
			}
			Date fechaActual = new Date();
			Date fechaLeida = this.vista.getDateCreacion().getDate();
			String fechaActualString = new SimpleDateFormat("dd-MM-yyyy").format(fechaActual);
			String fechaLeidaString = new SimpleDateFormat("dd-MM-yyyy").format(fechaLeida);
			if(fechaActualString.compareTo(fechaLeidaString)<0) 
			{
				JOptionPane.showMessageDialog(null, "Fecha inv\u00E1lida. Debe ser anterior o igual a la fecha actual.");
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
			newUser.setInstructor(false);
			newUser.setAlumno(true);
			newUser.setDisponibleEnSistema(true);
			
			PersonaDTO persona = new PersonaDTO();
			if (interesadoRecibido!=null)
			{
				persona = interesadoRecibido.getPersona();
				persona.setNombre(this.vista.getTextFieldNombre().getText());
				persona.setApellido(this.vista.getTextFieldApellido().getText());
				persona.setEmail(mail);
				persona.setTelefono(this.vista.getTextFieldTelefono().getText());
				persona.setDisponibleEnSistema(true);
				model.modificarPersonaDTO(persona);
			}
			else
			{
				persona = new PersonaDTO();
				persona.setNombre(this.vista.getTextFieldNombre().getText());
				persona.setApellido(this.vista.getTextFieldApellido().getText());
				persona.setEmail(mail);
				persona.setTelefono(this.vista.getTextFieldTelefono().getText());
				persona.setDisponibleEnSistema(true);
				model.agregarPersona(persona);
			}
			
		
			
			AlumnoDTO newAlumno = new AlumnoDTO();
			newAlumno.setDisponibleEnSistema(true);
			persona.setDni(this.vista.getTxtFieldDNI().getText());
			newAlumno.setFechaDeCreacion(this.vista.getDateCreacion().getDate());
			int anio = this.vista.getDateCreacion().getDate().getYear()+1900;
			newAlumno.setLegajo(this.vista.getTxtFieldDNI().getText()+"/"+anio);
			newAlumno.setPersona(persona);
			model.agregarAlumno(newAlumno);
			
			newAlumno.setPersona(persona);
			
			newUser.setPersona(persona);

			this.model.agregarUsuario(newUser);
			
			this.vista.close();
		}
		
	}

}
