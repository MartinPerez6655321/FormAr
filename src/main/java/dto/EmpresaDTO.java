package dto;

import java.util.List;

import dto.AlumnoDTO;
import dto.CursadaEmpresaDTO;

public class EmpresaDTO
{

	private Integer id;
	private String nombre;
	private PersonaDTO contacto;
	private List<AlumnoDTO> alumnos;
	private List<CursadaEmpresaDTO> cursadas;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public PersonaDTO getContacto()
	{
		return contacto;
	}

	public void setContacto(PersonaDTO contacto)
	{
		this.contacto = contacto;
	}

	public List<AlumnoDTO> getAlumnos()
	{
		return alumnos;
	}

	public void setAlumnos(List<AlumnoDTO> alumnos)
	{
		this.alumnos = alumnos;
	}

	public List<CursadaEmpresaDTO> getCursadas()
	{
		return cursadas;
	}

	public void setCursadas(List<CursadaEmpresaDTO> cursadas)
	{
		this.cursadas = cursadas;
	}

	public Boolean getDisponibleEnSistema()
	{
		return disponibleEnSistema;
	}

	public void setDisponibleEnSistema(Boolean disponibleEnSistema)
	{
		this.disponibleEnSistema = disponibleEnSistema;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof EmpresaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EmpresaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getNombre();
		ret = ret + ", " + getContacto();
		ret = ret + ", " + getAlumnos();
		ret = ret + ", " + getCursadas();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
