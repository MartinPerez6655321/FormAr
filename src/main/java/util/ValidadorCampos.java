package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.HorarioDTO;

public class ValidadorCampos
{
//	private static final String pattDni = "^[1-9]{1}[0-9]{6,9}$";
	private static final String pattNombreCursoCursada = "^^[a-zA-Z0-9Ò—·ÈÌÛ˙¡…Õ”⁄ ]{0,43}$";
	private static final String pattNombreApellido = "^[a-zA-ZÒ—·ÈÌÛ˙¡…Õ”⁄ ]{3,16}$";
	private static final String pattTelefono = "^[0-9-+]{6,16}$";
	private static final String pattMail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String pattUserContraseÒa = "[a-zA-Z0-9]{4,10}$";	
	private static final String pattDni = "^[1-9]{1}[0-9]{5,10}$";
	
	private static final String pattPrecio = "[0-9]*";

	private static final String pattDireccion = "^[a-zA-Z]{1}([a-zA-Z0-9\\sÒ—]{0,78})$";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static boolean validarContraseÒa(String contraseÒa)
	{
		return contraseÒa.matches(pattUserContraseÒa);
	}

	public static boolean validarUsuario(String usuario)
	{
		return usuario.matches(pattUserContraseÒa);
	}
	
	public static boolean validarDNI(String dni)
	{
		return dni.matches(pattDni);
	}

	public static boolean validarNombreCursoCursada(String nombre)
	{
		return nombre.matches(pattNombreCursoCursada);
	}

	public static boolean validarNombre(String nombre)
	{
		return nombre.matches(pattNombreApellido);
	}
	
	public static boolean validarApellido(String apellido)
	{
		return apellido.matches(pattNombreApellido);
	}

	public static boolean validarTelefono(String telefono)
	{
		return telefono.matches(pattTelefono);
	}

	public static boolean validarMail(String mail)
	{
		return mail.matches(pattMail);
	}

	public static boolean validarDireccion(String direccion)
	{
		return direccion.matches(pattDireccion);
	}

	public static boolean validarVacio(String campo)
	{
		return !campo.trim().isEmpty();
	}

	public static boolean validarPrecio(String precio)
	{
		return precio.matches(pattPrecio);
	}

	public static String convertirFecha(Date fecha) 
	{
		return dateFormat.format(fecha);	
	}

	@SuppressWarnings("deprecation")
	public static String getHorarios(List<HorarioDTO> horarios) 
	{
		String horariosString="";
		for (HorarioDTO horarioDTO : horarios) 
		{
			horariosString=horariosString+horarioDTO.getDiaDeLaSemana().getNombre()+" "+getDosDigitos(horarioDTO.getHoraInicio().getHours())+":"
					+getDosDigitos(horarioDTO.getHoraInicio().getMinutes())+" a "
					+getDosDigitos(horarioDTO.getHoraFin().getHours())+":"
					+ getDosDigitos(horarioDTO.getHoraFin().getMinutes())+" ";
					
		}
		return horariosString;
	}

	public static String getDosDigitos(int hours) 
	{
		
		if (hours<10)
			return "0"+hours;
		else
			return ""+hours;
			
	}

	public static String getHorarios(HorarioDTO horario) 
	{
		List<HorarioDTO> list=new ArrayList<HorarioDTO>();
		list.add(horario);
		
		return getHorarios(list);
	}

}
