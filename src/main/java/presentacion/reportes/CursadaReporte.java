package presentacion.reportes;

public class CursadaReporte {
	
	private String nombre;
	

	private Double promedio;
	private String FechaFinalizacion;
	private String codigo;
	
	public CursadaReporte(String nombre, Double promedio, String fechaFinalizacion,String codigo) {
		
		this.nombre = nombre;
		this.promedio = promedio;
		FechaFinalizacion = fechaFinalizacion;
		this.codigo=codigo;
	}
	

	public String getNombre() {
		return nombre;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public String getFechaFinalizacion() {
		return FechaFinalizacion;
	}

	public void setFechaFinalizacion(String fechaFinalizacion) {
		FechaFinalizacion = fechaFinalizacion;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
