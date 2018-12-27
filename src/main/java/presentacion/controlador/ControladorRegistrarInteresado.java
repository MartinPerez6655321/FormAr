package presentacion.controlador;


import javax.swing.JOptionPane;

import dto.PersonaDTO;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.VistaNuevoInteresado;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorRegistrarInteresado {


	private VistaNuevoInteresado vista ;
	private PersonaDTO interesadoElegido;
	private Modal modal;
	
	public ControladorRegistrarInteresado(VistaNuevoInteresado vistaNuevoInteresado,Modal modal) 
	{
		this.vista=vistaNuevoInteresado;
		this.modal=modal;
		
		this.vista.getBtnGuardarYAsignar().addActionListener(e->asignarGuardar());
	}


	private void asignarGuardar() 
	{
		if(verificarCampos()) 
		{	
				guardarDatos();
				modal.dispose();
		}
	}


	private boolean verificarCampos() 
	{
		if(vista.getTextFieldNombre().getText().isEmpty() || vista.getTextFieldApellido().getText().isEmpty() || vista.getTextFieldEmail().getText().isEmpty()||vista.getTextFieldTelefono().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Hay campos vacios.");
			return false;
		}
		if(!ValidadorCampos.validarMail(this.vista.getTextFieldEmail().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Formato de mail invalido. Ej.: ejem.plo@ungs.com");
			return false;
		}
		if(!ValidadorCampos.validarTelefono(this.vista.getTextFieldTelefono().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Formato de Teléfono inválido. Recuerde que sólo puede contener números o los signos - o + , y ser de 6 a 16 carácteres.");
			return false;
		}
		if (!ValidadorLogico.validarMailPersona(this.vista.getTextFieldEmail().getText()))
		{
			JOptionPane.showMessageDialog(null, "Ya hay una persona en el sistema con ese mail");
			return false;
		}
		if (!ValidadorLogico.validarTelefonoPersona(this.vista.getTextFieldTelefono().getText()))
		{
			JOptionPane.showMessageDialog(null, "Ya hay una persona en el sistema con ese numero de telefono");
			return false;
		}
		
		
		return true;
	}


	private void guardarDatos() {
		interesadoElegido=new PersonaDTO();
		interesadoElegido.setNombre(vista.getTextFieldNombre().getText());
		interesadoElegido.setApellido(vista.getTextFieldApellido().getText());
		interesadoElegido.setTelefono(vista.getTextFieldTelefono().getText());
		interesadoElegido.setEmail(vista.getTextFieldEmail().getText());
		interesadoElegido.setId(0);
		interesadoElegido.setDisponibleEnSistema(true);
		
		ModeloPersonas.getInstance().agregarPersona(interesadoElegido);
	}


	public PersonaDTO getInteresadoElegido() {
		return interesadoElegido;
	}


	public void setInteresadoElegido(PersonaDTO interesadoElegido) {
		this.interesadoElegido = interesadoElegido;
	}

}
