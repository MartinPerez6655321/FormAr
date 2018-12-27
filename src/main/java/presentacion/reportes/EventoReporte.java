package presentacion.reportes;

public class EventoReporte {
	
	private String descripcion;
	private String fechaHora;
	private String estado;
	private String interesado;
	
	public EventoReporte(String descripcion, String fechaHora, String estado) {
		this.descripcion = descripcion;
		this.fechaHora = fechaHora;
		this.estado = estado;
	}


	public EventoReporte(String descripcion, String fechaHoraLlamado, String estado, String interesado) {
		this.descripcion = descripcion;
		this.fechaHora = fechaHoraLlamado;
		this.estado = estado;
		this.interesado=interesado;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public String getFechaHora() {
		return fechaHora;
	}


	public String getEstado() {
		return estado;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getInteresado() {
		return interesado;
	}


	public void setInteresado(String interesado) {
		this.interesado = interesado;
	}
	
	

}
