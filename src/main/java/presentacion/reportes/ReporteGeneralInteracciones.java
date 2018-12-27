package presentacion.reportes;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.EventoInteresadoDTO;
import modelo.ModeloEventos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Strings;

public class ReporteGeneralInteracciones {

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ReporteGeneralInteracciones.class);
	private String reportSource = System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ReporteGeneralInteracciones.jrxml";
	
	
    public ReporteGeneralInteracciones(String cursoDeInteres)
    {
    	
		Map<String, Object> parametersMap = new HashMap<>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		
		parametersMap.put("CursoInteres", cursoDeInteres);
		
		org.apache.log4j.BasicConfigurator.configure();
		
    	try		{
    	
    		JasperCompileManager.compileReportToFile(reportSource,System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ReporteGeneralInteracciones.jasper");
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile(System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ReporteGeneralInteracciones.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(generarListaEventosReporte(cursoDeInteres)));
			
			
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteAlumno.Jasper", ex);
			
		}
    }       
    
	
	
	
	private List<EventoReporte> generarListaEventosReporte(String cursoDeInteres) {
		List<EventoReporte> ret=new ArrayList<>();
		
		for(EventoInteresadoDTO e:ModeloEventos.getInstance().getEventosInteresados()) {
			if(e.getCurso()!=null && e.getCurso().getNombre().equals(cursoDeInteres)) {
				EventoReporte eventoNuevo=new EventoReporte(e.getDescripcion(),fechaHoraLlamado(e.getFechaDeLlamado(),e.getHoraDeLlamado()),e.getEstado().getNombre(),e.getInteresado().getPersona().getNombre()+" "+e.getInteresado().getPersona().getApellido());
				ret.add(eventoNuevo);
				}
		}
		
		
		return ret;
	}


	private String fechaHoraLlamado(Date fechaDeLlamado, Time horaDeLlamado) {
		return Strings.formatoFecha(fechaDeLlamado)+" "+formatoHorario(horaDeLlamado);
	}

	private String formatoHorario(Time hora) {
		
		return hora.toString().substring(0, 5);
	}



	public void mostrar()
	{
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}
   
	public void imprimir() {
	
		try {
			JasperPrintManager.printReport(reporteLleno,true);
		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}
}
