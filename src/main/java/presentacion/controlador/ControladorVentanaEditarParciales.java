package presentacion.controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dto.ParcialDTO;
import modelo.ModeloCursos;

import presentacion.vista.VentanaEditarParcial;

public class ControladorVentanaEditarParciales implements ActionListener 
{
	private VentanaEditarParcial view;
	
	private ParcialDTO parcialRecibido;
	
	private ModeloCursos model=ModeloCursos.getInstance();
	
	
	public ControladorVentanaEditarParciales(VentanaEditarParcial ventana, ParcialDTO parcial)
	{
		this.view=ventana;
		this.parcialRecibido=parcial;
		
		this.view.llenarCombo();
		
		this.view.getBtnGuardar().addActionListener(this);
		
		this.view.llenarDatos(parcial);
		this.view.getComboPresencia().addActionListener(this);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==this.view.getBtnGuardar())
		{
			if (this.view.getComboPresencia().getSelectedItem().equals("Presente"))
			{
				this.parcialRecibido.setNota(stringAInteger((String)this.view.getComboNotas().getSelectedItem()));
				if (stringAInteger((String)this.view.getComboNotas().getSelectedItem())>35)
					this.parcialRecibido.setEstado(model.getEstadoEvaluacion().get(1));
				else
					this.parcialRecibido.setEstado(model.getEstadoEvaluacion().get(2));
				
				
				this.parcialRecibido.setObservaciones(this.view.getTextFieldDescripcion().getText());
				model.modificarParcial(parcialRecibido);
			}
			else
			{
				this.parcialRecibido.setNota(10);
				this.parcialRecibido.setEstado(model.getEstadoEvaluacion().get(3));
				this.parcialRecibido.setObservaciones("El alumno se ausento");
				model.modificarParcial(parcialRecibido);
				
				
			}
			
			
		}
		
		if(e.getSource()==this.view.getComboPresencia())
		{
			if (this.view.getComboPresencia().getSelectedItem().equals("Ausente"))
			{
				this.view.getTextFieldDescripcion().setEnabled(false);
				this.view.getComboNotas().setEnabled(false);
			}
			else
			{
				this.view.getTextFieldDescripcion().setEnabled(true);
				this.view.getComboNotas().setEnabled(true);
			}
		}
		
	}


	private Integer stringAInteger(String selectedItem) 
	{
		if (selectedItem.equals("10"))
			return 100;
		String numero="";
		for (int i = 0; i < selectedItem.length(); i++) 
		{
			
			if (selectedItem.charAt(i)!='.')
				numero=numero+String.valueOf(selectedItem.charAt(i));
			
			
		}
		return Integer.parseInt(numero);
	}
}
