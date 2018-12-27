package presentacion.vista.subcomponentes;

import javax.swing.JButton;

import dto.AlumnoDTO;

public class BotonAlumno extends JButton
{
	private static final long serialVersionUID = -5282299487713268429L;
	private AlumnoDTO alumno;
	
	public BotonAlumno(AlumnoDTO alumno)
	{
		setAlumno(alumno);
	}
	
	public void setAlumno(AlumnoDTO nuevoAlumno)
	{
		this.alumno = nuevoAlumno;
		setText(alumno.getPersona().getApellido() + ", " + alumno.getPersona().getNombre());		
	}
	
	public AlumnoDTO getAlumno()
	{
		return alumno;
	}
}
