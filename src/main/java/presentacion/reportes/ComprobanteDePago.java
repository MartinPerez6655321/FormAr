package presentacion.reportes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.AlumnoDTO;

import dto.PagoDTO;
import modelo.ModeloCursos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ComprobanteDePago {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ComprobanteDePago.class);
	private String reportSource =System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+ "reportes" + File.separator + "ComprobanteDePago.jrxml";
	private ModeloCursos modelo;
	
    public ComprobanteDePago(AlumnoDTO alumno,PagoDTO pago)
    {
    	modelo=ModeloCursos.getInstance();
		Map<String, Object> parametersMap = new HashMap<>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		parametersMap.put("Curso", modelo.getCursadaPorCuota(pago.getCuota()).getCurso().getNombre());
		parametersMap.put("DNI", alumno.getPersona().getDni());
		parametersMap.put("NombreApellido", alumno.getPersona().getNombre()+" "+alumno.getPersona().getApellido());
		
		parametersMap.put("Monto", "$ "+pago.getCuota().getMonto());
		parametersMap.put("FechaPago",new SimpleDateFormat("dd/MM/yyyy").format(pago.getFecha()));
		parametersMap.put("Vencimiento", new SimpleDateFormat("dd/MM/yyyy").format(pago.getCuota().getFechaLimite()));
		
		List<PagoDTO> pagos=new ArrayList<>();
		pagos.add(pago);
		
		org.apache.log4j.BasicConfigurator.configure();
		
    	try		{
    	
    		JasperCompileManager.compileReportToFile(reportSource,System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ComprobanteDePago.jasper");
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile(System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ComprobanteDePago.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(pagos));
			
			
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ComprobanteDePago.Jasper", ex);
			
		}
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
