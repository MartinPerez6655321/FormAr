package presentacion.vista;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.CursadaDTO;
import dto.HorarioDTO;
import dto.InstructorDTO;
import dto.PersonaDTO;
import presentacion.components.PanelHideAndShow;
import presentacion.components.horarios.GraficoHorarios;
import presentacion.components.horarios.Period;
import presentacion.controlador.ControladorVistaConsultarDisponibilidadInstructor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JCalendar;

import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;

public class VistaConsultarDisponibilidadDeInstructor extends JPanel
{
	private static final long serialVersionUID = -4920262302418087257L;
	private transient InstructorDTO instructor;
	private transient List<CursadaDTO> cursadas;
	
	private JLabel lblInstructor;
	private JButton btnOk;
	private GraficoHorarios graficoHorarios;
	private JCalendar calendarA;
	private JCalendar calendarB;

	public VistaConsultarDisponibilidadDeInstructor(InstructorDTO instructor)
	{
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(1000, 600));
		cursadas = new LinkedList<>();
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		lblInstructor = new JLabel();
		contentPane.add(lblInstructor, BorderLayout.NORTH);
		
		graficoHorarios = new GraficoHorarios();
		contentPane.add(graficoHorarios, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel filterPane = new JPanel();
		filterPane.setPreferredSize(new Dimension(240, (int)getMaximumSize().getHeight()));
		
		filterPane.add(new JLabel("Entre: "));
		calendarA = new JCalendar();
		filterPane.add(calendarA);
		
		filterPane.add(new JLabel("y: "));
		calendarB = new JCalendar();
		filterPane.add(calendarB);
		
		PanelHideAndShow hide = new PanelHideAndShow(filterPane, BorderLayout.WEST);

		add(contentPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
		
		btnOk = new JButton("Ok");
		buttonPane.add(btnOk);
		add(hide, BorderLayout.EAST);
		
		new ControladorVistaConsultarDisponibilidadInstructor(this, instructor);
	}
	
	public JCalendar getCalendarA()
	{
		return calendarA;
	}

	public JCalendar getCalendarB()
	{
		return calendarB;
	}

	public void setInstructor(InstructorDTO instructor)
	{
		this.instructor = instructor;
		lblInstructor.setText(createInstructorLabelString(instructor.getPersona()));
		actualizarGraficoHorarios();
	}
	
	public void setCursadasDelInstructor(List<CursadaDTO> cursadas) 
	{
		this.cursadas = cursadas;
		actualizarGraficoHorarios();
	}
	
	private void actualizarGraficoHorarios() 
	{
		List<Period> periods = new LinkedList<>();
		
		for(HorarioDTO disponible : instructor.getDisponibilidades())
		{
			periods.add(new Period(disponible.getDiaDeLaSemana(), disponible.getHoraInicio(), disponible.getHoraFin(), "", Color.GREEN));
		}
		
		for(CursadaDTO cursada : cursadas)
		{
			for(HorarioDTO horario : cursada.getHorarios())
			{
				periods.add(new Period(horario.getDiaDeLaSemana(), horario.getHoraInicio(), horario.getHoraFin(), cursada.getNombre(), Color.ORANGE));
			}
		}
		
		graficoHorarios.setPeriods(periods);
	}
	
	public JButton getBtnOk()
	{
		return btnOk;
	}

	private static String createInstructorLabelString(PersonaDTO instructor)
	{
		return "<html>Disponibilidad horaria y cursadas asignadas de: " + 
				instructor.getApellido() + ", " + instructor.getNombre() + "</html>";
	}
}
