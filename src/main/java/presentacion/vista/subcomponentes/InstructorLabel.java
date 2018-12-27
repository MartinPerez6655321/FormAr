package presentacion.vista.subcomponentes;

import javax.swing.JLabel;

import dto.InstructorDTO;

public class InstructorLabel extends JLabel
{
	private static final long serialVersionUID = -5044327529519330810L;
	private transient InstructorDTO instructor;
	
	public InstructorLabel(InstructorDTO instructor)
	{
		super();
		setInstructor(instructor);
	}
	
	private void setInstructor(InstructorDTO instructor)
	{
		this.instructor = instructor;
		setText(instructor.getPersona().getApellido() + ", " + instructor.getPersona().getNombre());
	}
	
	public InstructorDTO getInstructor() 
	{
		return instructor;
	}
}