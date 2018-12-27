package presentacion.controlador;

import java.util.LinkedList;
import java.util.List;

import dto.AlumnoDTO;
import dto.CuotaDTO;
import dto.CursadaDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.reportes.ComprobanteDePago;
import presentacion.vista.PanelGestionCuotasAlumno;
import util.Strings;

public class ControladorPanelGestionCuotasAlumno {
	
	private PanelGestionCuotasAlumno vista;
	private ModeloCursos modelo;
	private AlumnoDTO alumnoActual;
	private CursadaDTO cursadaElegida;
	private CuotaPago cuotaSeleccionada;
	
	
	public ControladorPanelGestionCuotasAlumno(PanelGestionCuotasAlumno gestionarPagos,AlumnoDTO alumno,CursadaDTO cursada) {
		this.vista=gestionarPagos;
		this.modelo=ModeloCursos.getInstance();
		this.alumnoActual=alumno;
		this.cursadaElegida=cursada;
		
		this.vista.setTable(crearTabla());
		this.vista.getBtnVerComprobante().addActionListener(e->verComprobante());
		this.setSelectedItem(null);
	}


	private void verComprobante() {
		ComprobanteDePago comprobantePago = new ComprobanteDePago(alumnoActual,modelo.getPagoPorCuota(cuotaSeleccionada.getCuota()));
		comprobantePago.mostrar();
		
	}


	private TablaGenerica<CuotaPago> crearTabla() {
		Transformer<CuotaPago> transformer = elem -> new String[] 
				{ 
					Integer.toString(elem.getCuota().getMonto()),
					Strings.formatoFecha(elem.getCuota().getFechaLimite()),
					getEstadoPago(elem)
					
				};
		

		String[] columnNames = new String[] { "Monto","Fecha L\u00EDmite", "Estado"};

		
		TablaGenerica<CuotaPago> table = new TablaGenerica<>(
				columnNames, 
				transformer,  
				() -> obtenerCuotasDeAlumno(alumnoActual,cursadaElegida),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modelo.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
	}
	
	
	private void setSelectedItem(CuotaPago cuota)
	{
		this.cuotaSeleccionada = cuota;
		vista.getBtnVerComprobante().setEnabled(cuota!=null && cuota.getPago());
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


	private List<CuotaPago> obtenerCuotasDeAlumno(AlumnoDTO alumno,CursadaDTO cursada)
	{
		List<CuotaPago> cuotas = new LinkedList<>();
		List<CuotaDTO> cuotasPagas = modelo.cuotasPagasPorAlumno(alumno);
		
		
		for(CuotaDTO cuota : cursada.getCuotas()) {
			if(cuotasPagas.contains(cuota)) {
				CuotaPago aux=new CuotaPago(cuota);
				aux.setPago(true);
				cuotas.add(aux);
			}
			else {
				CuotaPago aux=new CuotaPago(cuota);
				cuotas.add(aux);
			}
		}
		return cuotas;
	}
	
	private class CuotaPago {
		
		CuotaDTO cuota;
		Boolean pago;
		
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
		
		
	}

}
