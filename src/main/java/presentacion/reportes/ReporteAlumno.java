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
import dto.CursadaDTO;
import dto.ParcialDTO;
import dto.PlanillaDeParcialesDTO;
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
import util.Strings;

public class ReporteAlumno {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ReporteAlumno.class);
	private String reportSource =System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+ "reportes" + File.separator + "ReporteAlumno.jrxml";
	
	
    public ReporteAlumno(AlumnoDTO alumno)
    {
    	
		Map<String, Object> parametersMap = new HashMap<>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		parametersMap.put("NombreApellido", alumno.getPersona().getNombre()+" "+alumno.getPersona().getApellido());
		parametersMap.put("Legajo", alumno.getLegajo());
		
		org.apache.log4j.BasicConfigurator.configure();
		
    	try		{
    	
    		JasperCompileManager.compileReportToFile(reportSource,System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ReporteAlumno.jasper");
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile(System.getenv("APPDATA")+File.separator+ "FormAR"+File.separator+"reportes" + File.separator + "ReporteAlumno.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(generarListaAlumnoCursadas(alumno)));
			
			
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteAlumno.Jasper", ex);
			
		}
    }       
    
    

	private List<CursadaReporte> generarListaAlumnoCursadas(AlumnoDTO alumno) {
    	List<CursadaReporte> ret=new ArrayList<>();
    	for(CursadaDTO e:ModeloCursos.getInstance().getCursadasPorAlumno(alumno)) {
    		if(e.getEstado().getNombre().equals("Finalizado")) {
        		int promedioCursoActual = 0;
        		String fin="";
        		if(e.getParciales()!=null) {
        			promedioCursoActual=calcularPromedio(e.getParciales(),alumno);
        		}
        		if(e.getFin()!=null) {
        			
        			fin=Strings.formatoFecha(e.getFin());
        		}
        		if(promedioCursoActual>=0 && !fin.equals("")) {
        			CursadaReporte cursadaReporteNueva = new CursadaReporte(e.getCurso().getNombre(),this.integerToDecimal(promedioCursoActual),fin, e.getCurso().getCodigo());
        			ret.add(cursadaReporteNueva);
        		}
    		}
    		
    	}
    	
		return ret;
	}

	private int calcularPromedio(List<PlanillaDeParcialesDTO> parciales,AlumnoDTO alumno) {
		int cantidad=0;
		int notaTotal=0;
		for(PlanillaDeParcialesDTO e:parciales) {
			cantidad++;
			for (ParcialDTO e2:e.getParciales()) {
				if(e2.getAlumno().getId()==alumno.getId()) {
					notaTotal=notaTotal+e2.getNota();
				}
			}
		}
		
		if(cantidad!=0) {
			return notaTotal/cantidad;}
		return 0;
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
	private double integerToDecimal(int i) 
	{
		return i/10.0;
	}
}
