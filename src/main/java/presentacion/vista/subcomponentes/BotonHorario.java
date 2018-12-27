package presentacion.vista.subcomponentes;

import javax.swing.JButton;

import dto.HorarioDTO;
import presentacion.controlador.ControladorEdicionHorario;

public class BotonHorario extends JButton
{
	private static final long serialVersionUID = -5282299487713268429L;
	private HorarioDTO horario;
	
	public BotonHorario(HorarioDTO horario)
	{
		setHorario(horario);
		addActionListener(e -> modificar());
	}
	
	private void modificar()
	{
		ControladorEdicionHorario.modificarHorario(horario);
		setHorario(horario);
	}
	
	public void setHorario(HorarioDTO nuevoHorario)
	{
		this.horario = nuevoHorario;
		setText(horario.getDiaDeLaSemana().getNombre() + ": " + horario.getHoraInicio() + "-" + horario.getHoraFin());
	}
	
	public HorarioDTO getHorario()
	{
		return horario;
	}
}
