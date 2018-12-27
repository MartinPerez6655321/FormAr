package presentacion.vista.subcomponentes;

import javax.swing.JButton;

import dto.CursadaDTO;

public class botonCursada extends JButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6186435037844392814L;
	private CursadaDTO cursada;
	
	public botonCursada(CursadaDTO cursada) 
	{
		setCursada(cursada);
	}

	public CursadaDTO getCursada() 
	{
		return cursada;
	}

	public void setCursada(CursadaDTO cursada) 
	{
		this.cursada = cursada;
		
		setText(cursada.getNombre());
	}
}
