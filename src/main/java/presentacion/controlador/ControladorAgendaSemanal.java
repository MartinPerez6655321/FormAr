package presentacion.controlador;

import java.util.LinkedList;
import java.util.List;

import dto.CursadaDTO;
import presentacion.vista.PanelAgendaSemanal;

public class ControladorAgendaSemanal
{
	private PanelAgendaSemanal view;
	
	private List<CursadaDTO> elems;
	
	public ControladorAgendaSemanal(PanelAgendaSemanal agendaSemanal, List<CursadaDTO> elems)
	{
		view = agendaSemanal;
		
		this.elems = elems;
		view.getDateDesde().addPropertyChangeListener( event -> actualizarFiltros() );
		view.getDateHasta().addPropertyChangeListener( event -> actualizarFiltros() );
		actualizarFiltros();
	}
	
	private void actualizarFiltros() 
	{
		List<CursadaDTO> filtrados = new LinkedList<>();
		
		for(CursadaDTO cursada : elems) 
		{
			if(!(cursada.getFin().before(view.getDateDesde().getDate()) ||
			   cursada.getInicio().after(view.getDateHasta().getDate())))
				filtrados.add(cursada);
		}
		
		view.setCursadas(filtrados);
	}
}
