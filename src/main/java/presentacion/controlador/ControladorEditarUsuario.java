package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AdministradorDTO;
import dto.AlumnoDTO;
import dto.HorarioDTO;
import dto.InstructorDTO;
import dto.PersonaDTO;
import dto.PersonalAdministrativoDTO;
import dto.SupervisorDTO;
import dto.UsuarioDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaEditarUsuario;
import util.Strings;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorEditarUsuario implements ActionListener, MouseListener
{
	private ModeloEventos modelEventos;
	private ModeloCursos model;
	private ModeloPersonas modeloPersonas;
	private VentanaEditarUsuario vista;
	private UsuarioDTO usuario;
	
	public ControladorEditarUsuario(VentanaEditarUsuario vista, UsuarioDTO user)
	{
		this.model=ModeloCursos.getInstance();
		this.modeloPersonas = ModeloPersonas.getInstance();
		this.modelEventos=ModeloEventos.getInstance();
		this.vista = vista;
		this.usuario = user;
		this.vista.getBtnEditarUsuario().addActionListener(this);
		this.vista.getChckbxAlumno().addMouseListener(this);
		this.vista.getDateFechaIng().setEnabled(false);
		this.vista.getTextFieldUsuario().setText(user.getEmail());
		this.vista.getIntopasswordField().setText(user.getPassword());
		this.vista.getRepeatpasswordField().setText(user.getPassword());
		this.vista.getTextFieldNombre().setText(user.getPersona().getNombre());
		this.vista.getTextFieldApellido().setText(user.getPersona().getApellido());
		this.vista.getTextFieldDNI().setText(user.getPersona().getDni());
		this.vista.getTextFieldEmail().setText(user.getPersona().getEmail());
		this.vista.getTextFieldTelefono().setText(user.getPersona().getTelefono());
		this.vista.getCheckBoxDisponibilidad().setSelected(this.usuario.getDisponibleEnSistema());
		
		this.vista.getChckbxAdministrador().setSelected(this.usuario.getAdministrador());
		this.vista.getChckbxSupervisor().setSelected(this.usuario.getSupervisor());
		this.vista.getChckbxPersonalAdmin().setSelected(this.usuario.getAdministrativo());
		this.vista.getChckbxInstructor().setSelected(this.usuario.getInstructor());
		this.vista.getChckbxAlumno().setSelected(this.usuario.getAlumno());
		
		if(this.usuario.getAlumno())
		{
			this.vista.getDateFechaIng().setEnabled(true);
			this.vista.getDateFechaIng().setDate(this.modeloPersonas.getAlumnoPorPersona(this.usuario.getPersona()).getFechaDeCreacion());
		}
		
		this.vista.getCheckBoxDisponibilidad().setSelected(user.getDisponibleEnSistema());	

		if (usuario.equals(this.modeloPersonas.getUsuarioActual())) 
		{
			this.vista.getCheckBoxDisponibilidad().setEnabled(false);
		}
		
	}
	
	public void initialize()
	{
		this.vista.show();	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()== this.vista.getBtnEditarUsuario()) 
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
			if(!this.usuarioValido(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Usuario existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarUsuario(this.vista.getTextFieldUsuario().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Formato de usuario invalido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
				return;
			}
		    String mail = this.vista.getTextFieldEmail().getText().toLowerCase(); 
			if(!emailValido(mail)) 
			{
				JOptionPane.showMessageDialog(null, "Email existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarContraseña(Strings.getPasswordString(this.vista.getIntopasswordField()))) 
			{
				JOptionPane.showMessageDialog(null, "Formato de password inv\u00E1lido. Debe ser entre 4 y 10 caracteres alfanum\u00E9ricos");
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
				JOptionPane.showMessageDialog(null, "Formato de dni inv\u00E1lido. Deben ser al menos 6 n\u00FAmeros.");
				return;
			}
			if(!this.dniValido(this.vista.getTextFieldDNI().getText())) 
			{
				JOptionPane.showMessageDialog(null, "Dni existente en el sistema");
				return;
			}
			if(!ValidadorCampos.validarMail(mail)) 
			{
				JOptionPane.showMessageDialog(null, "Formato de mail invalido. Ej.: ejem.plo@ungs.com");
				return;
			}
			if(this.vista.getChckbxAlumno().isSelected())
			{
				if(this.vista.getDateFechaIng().getDate()==null) {
					JOptionPane.showMessageDialog(null, "Faltan completar fecha de inscripci\u00F3n del usuario alumno");
					return;					
				}
				if (ValidadorLogico.esProximoAHoy(this.vista.getDateFechaIng().getDate()))
				{
					JOptionPane.showMessageDialog(null,"Fecha inv\u00E1lida. Debe ser anterior o igual a la fecha actual.");
					return;
				}
			}
			if(!telefonoValido(this.vista.getTextFieldTelefono().getText())) 
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
			
			if(!vista.getCheckBoxDisponibilidad().isSelected() && tieneEventosPendientesAsociados(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como administrativo uno o m\u00E1s eventos pendientes asociados y no puede ser eliminado.");
				return;
			}
			
			if(!vista.getCheckBoxDisponibilidad().isSelected() && !tieneCursadasAsociadas(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee una o m\u00E1s cursadas asociadas y no puede ser deshabilitado.");
				return;
			}
			
			if(!vista.getCheckBoxDisponibilidad().isSelected() && this.usuario.getAlumno() && AlumnotieneCursadasAsociadas(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como alumno una o mas cursadas asociadas y no puede ser deshabilitado.");
				return;
			}
			if(!vista.getChckbxInstructor().isSelected() && usuario.getInstructor()  && !tieneCursadasAsociadas(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como instructor una o m\u00E1s cursadas asociadas y no puede ser eliminado.");
				return;
			}
			if(!vista.getChckbxPersonalAdmin().isSelected() && usuario.getAdministrativo()  && tieneEventosPendientesAsociados(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como administrativo uno o m\u00E1s eventos pendientes asociados y no puede ser eliminado.");
				return;
			}
			if(!vista.getChckbxSupervisor().isSelected() && usuario.getSupervisor()  && tieneEventosPendientesAsociados(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como supervisor uno o m\u00E1s eventos pendientes asociados y no puede ser eliminado.");
				return;
			}
			if(!vista.getChckbxAlumno().isSelected() && usuario.getAlumno()  && !tieneInscripcionesAsociadas(this.usuario)) {
				JOptionPane.showMessageDialog(null, "Este usuario posee como alumno uno o m\u00E1s cursadas asociados y no puede ser eliminado.");
				return;
			}

			this.usuario.getPersona().setNombre(this.vista.getTextFieldNombre().getText());
			this.usuario.getPersona().setApellido(this.vista.getTextFieldApellido().getText());
			this.usuario.getPersona().setEmail(mail);
			this.usuario.getPersona().setTelefono(this.vista.getTextFieldTelefono().getText());
			this.usuario.getPersona().setDni(this.vista.getTextFieldDNI().getText());
			this.usuario.setEmail(this.vista.getTextFieldUsuario().getText());
			this.usuario.setPassword(Strings.getPasswordString(this.vista.getIntopasswordField()));
			this.usuario.setDisponibleEnSistema(this.vista.getCheckBoxDisponibilidad().isSelected());
			
			if(this.vista.getChckbxAdministrador().isSelected() && !this.usuario.getAdministrador())
			{
				AdministradorDTO newAdmin = new AdministradorDTO();
				newAdmin.setDisponibleEnSistema(true);
				newAdmin.setPersona(usuario.getPersona());
				usuario.setAdministrador(true);
				this.modeloPersonas.agregarAdministrador(newAdmin);
			}
			if(!this.vista.getChckbxAdministrador().isSelected() && this.usuario.getAdministrador()) 
			{
				AdministradorDTO administrador = modeloPersonas.getAdministrador(usuario);
				this.usuario.setAdministrador(false);
				this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
				this.modeloPersonas.eliminarAdministrador(administrador);
			}
			if(this.vista.getChckbxSupervisor().isSelected() && !this.usuario.getSupervisor())
			{
				SupervisorDTO newSupervisor = new SupervisorDTO();
				newSupervisor.setDisponibleEnSistema(true);
				newSupervisor.setPersona(usuario.getPersona());
				usuario.setSupervisor(true);
				this.modeloPersonas.agregarSupervisor(newSupervisor);
			}
			if(!this.vista.getChckbxSupervisor().isSelected() && this.usuario.getSupervisor()) 
			{
				SupervisorDTO supervisor = modeloPersonas.getSupervisor(usuario);
				this.usuario.setSupervisor(false);
				this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
				this.modeloPersonas.eliminarSupervisor(supervisor);
			}
			if(this.vista.getChckbxPersonalAdmin().isSelected() && !this.usuario.getAdministrativo())
			{
				PersonalAdministrativoDTO newPersonalAdmin = new PersonalAdministrativoDTO();
				newPersonalAdmin.setDisponibleEnSistema(true);
				newPersonalAdmin.setPersona(usuario.getPersona());
				usuario.setAdministrativo(true);
				this.modeloPersonas.agregarPersonalAdministrativo(newPersonalAdmin);
			}
			if(!this.vista.getChckbxPersonalAdmin().isSelected() && this.usuario.getAdministrativo()) 
			{
				PersonalAdministrativoDTO personalAdmin = modeloPersonas.getAdministrativo(usuario);
				this.usuario.setAdministrativo(false);
				this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
				this.modeloPersonas.eliminarPersonalAdministrativo(personalAdmin);
			}
			if(this.vista.getChckbxInstructor().isSelected() && !this.usuario.getInstructor())
			{
				InstructorDTO newInstructor = new InstructorDTO();
				newInstructor.setDisponibleEnSistema(true);
				newInstructor.setDisponibilidades(new ArrayList<HorarioDTO>());
				newInstructor.setPersona(usuario.getPersona());
				usuario.setInstructor(true);
				this.modeloPersonas.agregarInstructor(newInstructor);
			}
			if(!this.vista.getChckbxInstructor().isSelected() && this.usuario.getInstructor()) 
			{
				InstructorDTO inst = modeloPersonas.getInstructor(usuario);
				this.usuario.setInstructor(false);
				this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
				this.modeloPersonas.eliminarInstructor(inst);
			}
			if(this.vista.getChckbxAlumno().isSelected() && !this.usuario.getAlumno())
			{
				AlumnoDTO newAlumno = new AlumnoDTO();
				newAlumno.setDisponibleEnSistema(true);
				newAlumno.setFechaDeCreacion(this.vista.getDateFechaIng().getDate());
				int anio = this.vista.getDateFechaIng().getDate().getYear()+1900;
				newAlumno.setLegajo(this.vista.getTextFieldDNI().getText()+"/"+anio);
				newAlumno.setPersona(usuario.getPersona());
				this.usuario.setAlumno(true);
				this.modeloPersonas.agregarAlumno(newAlumno);
			}
			if(!this.vista.getChckbxAlumno().isSelected() && this.usuario.getAlumno()) 
			{
				AlumnoDTO alumno = modeloPersonas.getAlumno(this.usuario);
				this.usuario.setAlumno(false);
				this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
				this.modeloPersonas.eliminarAlumno(alumno);
			}
			if(this.vista.getChckbxAlumno().isSelected() && this.usuario.getAlumno())
			{
				AlumnoDTO alumno = modeloPersonas.getAlumno(this.usuario);
				alumno.setFechaDeCreacion(this.vista.getDateFechaIng().getDate());
				int anio = this.vista.getDateFechaIng().getDate().getYear()+1900;
				alumno.setLegajo(this.vista.getTextFieldDNI().getText()+"/"+anio);
				this.modeloPersonas.modificarAlumno(alumno);
			}
			
			this.modeloPersonas.modificarPersonaDTO(this.usuario.getPersona());
			this.modeloPersonas.modificarUsuarioDTO(usuario);
			this.vista.close();
			
		}
	
	}
	
	private boolean AlumnotieneCursadasAsociadas(UsuarioDTO usuario2) 
	{
		if (model.getCursadasPorAlumno(modeloPersonas.getAlumnoPorPersona(usuario2.getPersona())).isEmpty())
			return false;
		else
			return true;
	}

	private boolean tieneEventosPendientesAsociados(UsuarioDTO usuario) {

		if (modeloPersonas.getAdministrativo(usuario)!=null)
		{
			if(!(modelEventos.getEventosSupervisorPorAdministrativo(modeloPersonas.getAdministrativo(usuario))).isEmpty()) {
				return true;
			}
			
			if(!(modelEventos.getEventosInteresadosPorAdministrativo(modeloPersonas.getAdministrativo(usuario))).isEmpty()) {
				return true;
			}
			
			if(!(modelEventos.getEventosInasistenciasPorAdministrativo(modeloPersonas.getAdministrativo(usuario))).isEmpty()) {
				return true;
			}
		}
		
		return false;
	}

	private boolean tieneCursadasAsociadas(UsuarioDTO usuario) 
	{	
		return (model.getCursadasPorInstructor(modeloPersonas.getInstructor(usuario)).isEmpty());
	}

	private boolean tieneInscripcionesAsociadas(UsuarioDTO usuario) 
	{	
		return (model.getInscripcionesPorAlumno(modeloPersonas.getAlumno(usuario)).isEmpty());
	}
	
	private boolean usuarioValido(String usuario) 
	{
		List<UsuarioDTO> usuarios = this.modeloPersonas.getUsuarios();
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
		List<PersonaDTO> personas = this.modeloPersonas.getPersonas();
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
		List<PersonaDTO> personas = this.modeloPersonas.getPersonas();
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
		List<PersonaDTO> personas = this.modeloPersonas.getPersonas();
		
		for (PersonaDTO persona: personas)
		{
			if(persona.getDni()!=null)
			{
				if(persona.getDni().equals(this.vista.getTextFieldDNI().getText()) && persona.getId()!=this.usuario.getPersona().getId()) 
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.vista.getChckbxAlumno().isSelected())
		{
//			this.vista.getTextFieldDNI().setEditable(true);
			this.vista.getDateFechaIng().setEnabled(true);
		}
		else
		{
//			this.vista.getTextFieldDNI().setEditable(false);
			this.vista.getDateFechaIng().setEnabled(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
