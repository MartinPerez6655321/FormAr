package presentacion.controlador;

import presentacion.vista.PanelGestionMisCursadas;
import presentacion.vista.VistaInstructor;

public class ControladorVistaInstructor 
{

	private PanelGestionMisCursadas panelGestionAsitencia;
	
	
	
	public ControladorVistaInstructor(VistaInstructor vista)
	{
		this.panelGestionAsitencia=new PanelGestionMisCursadas();
		new ControladorPanelGestionMisCursadas(this.panelGestionAsitencia);
		
		vista.agregarVista("Gestionar mis cursos", this.panelGestionAsitencia);
		
		
	}
	
	
}
