package presentacion.controlador;


import javax.swing.JOptionPane;

import dto.PersonaDTO;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.vista.VistaNuevoInteresado;
import util.ValidadorCampos;

public class ControladorVistaNuevoInteresado {


	private VistaNuevoInteresado vista ;
	private ModeloPersonas modeloPersonas = ModeloPersonas.getInstance();
	private PersonaDTO interesadoElegido;
	private Modal modal;
	
	public ControladorVistaNuevoInteresado(VistaNuevoInteresado vistaNuevoInteresado,Modal modal) 
	{
		this.vista=vistaNuevoInteresado;
		this.modal=modal;
		
		this.vista.getBtnGuardarYAsignar().addActionListener(e->asignarGuardar());
	}


	private void asignarGuardar() 
	{
		if(verificarCampos()) {
				guardarDatos();
				modal.dispose();
		}
	}


	private boolean verificarCampos() 
	{
		if(vista.getTextFieldNombre().getText().isEmpty() || vista.getTextFieldApellido().getText().isEmpty() || vista.getTextFieldEmail().getText().isEmpty()||vista.getTextFieldTelefono().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Hay campos vac\u00EDos.");
			return false;
		}
		if(!ValidadorCampos.validarMail(this.vista.getTextFieldEmail().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Formato de mail inv\u00E1lido. Ej.: ejem.plo@ungs.com");
			return false;
		}
		if(!ValidadorCampos.validarTelefono(this.vista.getTextFieldTelefono().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Formato de Tel\u00E9fono inv\u00E1lido. Debe ser alfanum\u00E9rico de 6 a 16");
			return false; 
		}
		return true;
	}


	private void guardarDatos() {
		interesadoElegido.setDni("");
		interesadoElegido = new PersonaDTO();
		interesadoElegido.setNombre(vista.getTextFieldNombre().getText());
		interesadoElegido.setApellido(vista.getTextFieldApellido().getText());
		interesadoElegido.setTelefono(vista.getTextFieldTelefono().getText());
		interesadoElegido.setEmail(vista.getTextFieldEmail().getText());
		interesadoElegido.setId(0);
		interesadoElegido.setDisponibleEnSistema(true);
		
		modeloPersonas.agregarPersona(interesadoElegido);
	}


	public PersonaDTO getInteresadoElegido() {
		return interesadoElegido;
	}


	public void setInteresadoElegido(PersonaDTO interesadoElegido) {
		this.interesadoElegido = interesadoElegido;
	}

}
