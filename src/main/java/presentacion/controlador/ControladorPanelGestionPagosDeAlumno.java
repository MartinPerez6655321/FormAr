package presentacion.controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import dto.AlumnoDTO;
import dto.CuotaDTO;
import dto.CursadaDTO;
import dto.PagoDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.reportes.ComprobanteDePago;
import presentacion.vista.PanelGestionPagosDeAlumno;
import util.Strings;

public class ControladorPanelGestionPagosDeAlumno
{
	private PanelGestionPagosDeAlumno vista;
	private ModeloCursos model;
	private AlumnoDTO alumno;
	private CuotaPago cuotaSeleccionada;
	
	public ControladorPanelGestionPagosDeAlumno(PanelGestionPagosDeAlumno vista, AlumnoDTO alumno)
	{
		this.vista = vista;
		this.alumno = alumno;
		this.model = ModeloCursos.getInstance();
		
		vista.getBtnRegistrarPago().addActionListener( e -> registrarPago() );
		vista.getBtnComprobantePago().addActionListener(e-> verComprobante());
		
		
		setSelectedItem(null);
		vista.setTable(crearTabla());
	}

	private void verComprobante() {
		
		ComprobanteDePago comprobantePago = new ComprobanteDePago(alumno,model.getPagoPorCuota(cuotaSeleccionada.getCuota()));
		comprobantePago.mostrar();
		
	}

	private TablaGenerica<CuotaPago> crearTabla() {
		Transformer<CuotaPago> transformer = elem -> new String[] 
				{ 
					elem.getCursada().getNombre()+" ("+elem.getCursada().getCurso().getNombre()+")",Integer.toString(elem.getCuota().getMonto()),
					Strings.formatoFecha(elem.getCuota().getFechaLimite()),
					getEstadoPago(elem)
					
				};
		

		String[] columnNames = new String[] { "Cursada (curso)","Monto","Fecha L\u00EDmite", "Estado"};

		
		TablaGenerica<CuotaPago> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> obtenerCuotasDeAlumno(alumno),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}
	
	private void setSelectedItem(CuotaPago cuota)
	{
		this.cuotaSeleccionada = cuota;
		vista.getBtnRegistrarPago().setEnabled(cuota!=null && !cuota.getPago());
		vista.getBtnComprobantePago().setEnabled(cuota!=null && cuota.getPago());
	}

	private void registrarPago()
	{
		PagoDTO pago = new PagoDTO();
		
		pago.setAlumno(alumno);
		pago.setCuota(cuotaSeleccionada.getCuota());
		pago.setDisponibleEnSistema(true);
		pago.setFecha(Date.valueOf(LocalDate.now()));
		model.agregarPago(pago);
		
		ComprobanteDePago comprobantePago = new ComprobanteDePago(alumno,pago);
		comprobantePago.mostrar();
	}
	

	
	private List<CuotaPago> obtenerCuotasDeAlumno(AlumnoDTO alumno)
	{
		List<CuotaPago> cuotas = new LinkedList<>();
		List<CuotaDTO> cuotasPagas = model.cuotasPagasPorAlumno(alumno);
		
		for(CursadaDTO cursada:model.getCursadasPorAlumno(alumno)) {
			for(CuotaDTO cuota : cursada.getCuotas()) {
				if(cuotasPagas.contains(cuota)) {
					CuotaPago aux=new CuotaPago(cuota);
					aux.setPago(true);
					
					aux.setCursada(cursada);
					cuotas.add(aux);
				}
				else {
					CuotaPago aux=new CuotaPago(cuota);
					aux.setCursada(cursada);
					cuotas.add(aux);
				}
			}
		}
		return cuotas;
	}
	
	private String getEstadoPago(CuotaPago elem) {
		String ret;
		
		if(!elem.getPago()) {
			ret="Impaga";
		}
		else {
			ret="Pagado";
		}
		
		return ret;
	}


	private class CuotaPago {
		
		CuotaDTO cuota;
		Boolean pago;
		CursadaDTO cursada;
		
		public CuotaPago(CuotaDTO cuota2) {
			this.cuota=cuota2;
			pago=false;
			
		}
		public CuotaDTO getCuota() {
			return cuota;
		}
		public Boolean getPago() {
			return pago;
		}
		
		public void setPago(Boolean pago) {
			this.pago = pago;
		}
		public CursadaDTO getCursada() {
			return cursada;
		}
		public void setCursada(CursadaDTO cursada) {
			this.cursada = cursada;
		}
		
	}
}
