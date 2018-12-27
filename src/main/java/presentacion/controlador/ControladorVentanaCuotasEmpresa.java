package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import dto.PagoEmpresaDTO;
import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.reportes.ComprobanteDePagoEmpresa;
import presentacion.vista.VentanaCuotasEmpresa;
import util.Strings;

public class ControladorVentanaCuotasEmpresa implements ActionListener 
{

	private ModeloCursos model;
	private VentanaCuotasEmpresa vista;
	private PagoEmpresaDTO selectedItem;
	private CursadaEmpresaDTO cursadaRecibida;
	private EmpresaDTO empresaRecibida;
	public ControladorVentanaCuotasEmpresa(VentanaCuotasEmpresa vistaCuotasEmpresa, CursadaEmpresaDTO selectedItem, EmpresaDTO empresaRecibida) 
	{
		this.empresaRecibida=empresaRecibida;
		this.model=ModeloCursos.getInstance();
		this.vista=vistaCuotasEmpresa;
		this.cursadaRecibida=selectedItem;
		
		setSelectedItem(null);
		vista.setTable(crearTabla());
		
		this.vista.getBtnPagar().addActionListener(this);
		
	}

	private TablaGenerica<PagoEmpresaDTO> crearTabla() 
	{
		Transformer<PagoEmpresaDTO> transformer = elem -> new String[] 
				{ 
					getEmpresa(elem.getEmpresa()),
					"$"+elem.getMonto().toString(),
					Strings.formatoFecha(elem.getFechaLimite()),
					verificarFecha(elem.getFechaLimite(),elem.getRealizado()),
					
					};
	
		String[] columnNames = new String[] 
				{ 
					"Empresa","Monto", "Fecha l\u00EDmite pago","Pago realizado"
				};
	
		TablaGenerica<PagoEmpresaDTO> table = new TablaGenerica<>(
			columnNames, 
			transformer,
			() -> model.getPagosCursada(cursadaRecibida,empresaRecibida),
			FilterPanel.stringFilterPanel(columnNames, transformer));
	
		model.addListener(table);
	
		table.addSelectionListener(this::setSelectedItem);
	
		return table;
	}

	private String getEmpresa(Integer idEmpresa) 
	{
		for (EmpresaDTO empresa:ModeloCursos.getInstance().getEmpresas())
		{
			if (empresa.getId().equals(idEmpresa))
				return empresa.getNombre();
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	private String verificarFecha(Date limite, Date realizado) 
	{
		
		if (limite.getYear()-5>= realizado.getYear())
			return "No pagada";
		return "Pagada";
	}


	private void setSelectedItem(PagoEmpresaDTO pago) 
	{
		this.selectedItem=pago;
		this.vista.getBtnPagar().setEnabled(pago!=null && pago.getFechaLimite().getYear()-5>= pago.getRealizado().getYear() );
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==this.vista.getBtnPagar())
		{
			selectedItem.setRealizado(new java.util.Date());
			model.modificarPagoEmpresa(selectedItem);
			
			ComprobanteDePagoEmpresa comprobantePago = new ComprobanteDePagoEmpresa(empresaRecibida,selectedItem);
			comprobantePago.mostrar();
			
		}
		
	}

}
