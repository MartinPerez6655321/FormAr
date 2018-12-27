package util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JPasswordField;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.HorarioDTO;

public class Strings
{
	private Strings(){}
	
	public static String getPasswordString(JPasswordField field)
	{
		char[] password = field.getPassword();
		StringBuilder ret = new StringBuilder(password.length);
		for(int i = 0; i < field.getPassword().length; i++)
		{
			ret.append(password[i]);
			password[i] = 0;
		}
		return ret.toString();
	}
	
	public static String horariosListString(List<HorarioDTO> horarios) 
	{
		if(horarios.isEmpty())
			return "Sin horarios asignados";
		
		StringBuilder ret = new StringBuilder();
		
		ret.append(horarioString(horarios.get(0)));
		for(int i = 1; i < horarios.size() - 1; i++)
			ret.append(", " + horarioString(horarios.get(i)));
		if(horarios.size()>1)
			ret.append(" y " + horarioString(horarios.get(horarios.size()-1)));
		
		return ret.toString();
	}
	
	private static String horarioString(HorarioDTO horario) 
	{
		return horario.getDiaDeLaSemana().getNombre() + ": " + horario.getHoraInicio() + "-" + horario.getHoraFin();
	}
	
	public static String formatoHorario(Time horaCumplimiento) {
		
		return horaCumplimiento.toString().substring(0, 5);
	}

	public static String formatoFecha(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}
	
	public static String alumnosListString(List<AlumnoDTO> alumnos) 
	{
		if(alumnos.isEmpty())
			return "Sin alumnos asignados";
		
		StringBuilder ret = new StringBuilder();
		
		ret.append(alumnoString(alumnos.get(0)));
		for(int i = 1; i < alumnos.size() - 1; i++)
			ret.append(", " + alumnoString(alumnos.get(i)));
		if(alumnos.size()>1)
			ret.append(" y " + alumnoString(alumnos.get(alumnos.size()-1)));
		
		return ret.toString();
	}

	private static String alumnoString(AlumnoDTO alumno) 
	{
		return alumno.getPersona().getApellido() + " " + alumno.getPersona().getNombre();
	}

	
	
	public static String cursadasListString(List<CursadaDTO> cursadas) 
	{
		if(cursadas.isEmpty())
			return "Sin alumnos asignados";
		
		StringBuilder ret = new StringBuilder();
		
		ret.append(cursadaString(cursadas.get(0)));
		for(int i = 1; i < cursadas.size() - 1; i++)
			ret.append(", " + cursadaString(cursadas.get(i)));
		if(cursadas.size()>1)
			ret.append(" y " + cursadaString(cursadas.get(cursadas.size()-1)));
		
		return ret.toString();
	}

	private static String cursadaString(CursadaDTO cursadaDTO)
	{
		return cursadaDTO.getNombre();
	}
	
}
