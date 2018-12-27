package presentacion.controlador;

import java.util.Calendar;

import dto.InstructorDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import modelo.ModeloCursos;
import presentacion.vista.VistaConsultarDisponibilidadDeInstructor;

public class ControladorVistaConsultarDisponibilidadInstructor implements InvalidationListener
{
	private InstructorDTO instructor;
	private VistaConsultarDisponibilidadDeInstructor vista;
	private ModeloCursos model;
	
	public ControladorVistaConsultarDisponibilidadInstructor(VistaConsultarDisponibilidadDeInstructor vista, InstructorDTO instructor)
	{
		this.instructor = instructor;
		this.vista = vista;
		this.model = ModeloCursos.getInstance();
		model.addListener(this);

		this.vista.getCalendarA().addPropertyChangeListener("calendar", e -> actualizar());
		this.vista.getCalendarB().addPropertyChangeListener("calendar", e -> actualizar());
		
		actualizar();
	}
	
	@Override
	public void invalidated(Observable observable)
	{
		actualizar();
	}

	private void actualizar()
	{
		Calendar fechaFiltroA = vista.getCalendarA().getCalendar();
		Calendar fechaFiltroB = vista.getCalendarB().getCalendar();
		
		vista.setInstructor(instructor);
		
		if(fechaFiltroA == null || fechaFiltroB == null)
			vista.setCursadasDelInstructor(ModeloCursos.getInstance().getCursadasPorInstructor(instructor));
		else
			if(fechaFiltroA.after(fechaFiltroB))
				vista.setCursadasDelInstructor(ModeloCursos.getInstance().getCursadasPorInstructorYFechas(instructor, fechaFiltroB, fechaFiltroA));
			else
				vista.setCursadasDelInstructor(ModeloCursos.getInstance().getCursadasPorInstructorYFechas(instructor, fechaFiltroA, fechaFiltroB));
	}
}
