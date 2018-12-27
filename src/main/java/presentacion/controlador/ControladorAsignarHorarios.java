package presentacion.controlador;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import dto.HorarioDTO;
import presentacion.components.Modal;
import presentacion.components.listagenerica.ListaGenerica;
import presentacion.vista.subcomponentes.BotonHorario;

public class ControladorAsignarHorarios
{
	public static List<HorarioDTO> administrarHorarios(List<HorarioDTO> horarios)
	{
		ListaGenerica<BotonHorario> lista = new ListaGenerica<>(() -> new BotonHorario(ControladorEdicionHorario.crearHorario()));
		
		for(HorarioDTO horario : horarios)
			lista.addElement(new BotonHorario(horario));
		
		lista.setPreferredSize(new Dimension(250, 300));
		Modal.showDialog(lista, "Elegir horarios");
		
		List<HorarioDTO> ret = new LinkedList<>();
		for(BotonHorario btnHorario : lista)
			ret.add(btnHorario.getHorario());
		return ret;
	}
}
