package presentacion.controlador;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.SalaDTO;
import modelo.ModeloCursos;
import modelo.ModeloSala;
import presentacion.components.Modal;
import presentacion.components.abm.Editor;
import presentacion.vista.PanelEditarSala;
import util.ValidadorLogico;

public class ControladorEditarSala implements Editor<SalaDTO>
{
	
	private ModeloSala modeloSala=ModeloSala.getInstance();
	
	private PanelEditarSala vista;
	
	public ControladorEditarSala(PanelEditarSala vista)
	{
		this.vista = vista;
	}
	
	@Override
	public void crear()
	{
		Modal dialogo = new Modal(vista);
		dialogo.setTitle("Nueva sala");
		dialogo.setResizable(false);
		
		vista.getTxtAlias().setText("");
		vista.getTxtCodigo().setText("");
		vista.getSpinnerCapacidad().setValue(1);
		
		ActionListener actionOk = 
				e -> {
					if(validar())
					{
						SalaDTO sala = new SalaDTO();
						sala.setAlias(vista.getTxtAlias().getText());
						sala.setCodigo(vista.getTxtCodigo().getText());
						sala.setCapacidad((Integer)vista.getSpinnerCapacidad().getValue());
						sala.setDisponibleEnSistema(true);
						modeloSala.agregarSala(sala);
						dialogo.dispose();
					}
				};
		
		vista.getBtnOk().addActionListener(actionOk);
		dialogo.setVisible(true);
		vista.getBtnOk().removeActionListener(actionOk);
	}

	@Override
	public void modificar(SalaDTO elem)
	{
		Modal dialogo = new Modal(vista);
		dialogo.setTitle("Modificando sala: " + elem.getAlias() + "(" + elem.getCodigo() + ")");
		dialogo.setResizable(false);
		
		vista.getTxtAlias().setText(elem.getAlias());
		vista.getTxtCodigo().setText(elem.getCodigo());
		vista.getSpinnerCapacidad().setValue(elem.getCapacidad());
		
		ActionListener actionOk = 
				e -> {
					if(validar(elem))
					{

						
						
						elem.setAlias(vista.getTxtAlias().getText());
						elem.setCodigo(vista.getTxtCodigo().getText());
						elem.setCapacidad((Integer)vista.getSpinnerCapacidad().getValue());
						modeloSala.modificarSala(elem);
						
						dialogo.dispose();
					}
				};
		
		vista.getBtnOk().addActionListener(actionOk);
		dialogo.setVisible(true);
		vista.getBtnOk().removeActionListener(actionOk);
	}
	
	private boolean validar(SalaDTO elem) 
	{
		if(vista.getTxtAlias().getText().trim().isEmpty()) 
		{
			JOptionPane.showMessageDialog(vista, "El Alias está vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if(vista.getTxtCodigo().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(vista, "El Código está vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!elem.getCodigo().equals(vista.getTxtCodigo().getText()) && !ValidadorLogico.validarCodigoSala(vista.getTxtCodigo().getText())) 
		{
			JOptionPane.showMessageDialog(vista, "El Código ya pertenece a otra sala", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	private boolean validar() 
	{
		if(vista.getTxtAlias().getText().trim().isEmpty()) 
		{
			JOptionPane.showMessageDialog(vista, "El Alias est\u00E1 vac\u00EDo", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if(vista.getTxtCodigo().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(vista, "El C\u00F3digo est\u00E1 vac\u00EDo", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!ValidadorLogico.validarCodigoSala(vista.getTxtCodigo().getText())) {
			JOptionPane.showMessageDialog(vista, "El C\u00F3digo ya pertenece a otra sala", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	

	@Override
	public void eliminar(SalaDTO elem)
	{
		for(CursadaDTO cursada: ModeloCursos.getInstance().getCursadas())
		{
			if (cursada.getSala()!=null && cursada.getSala().equals(elem))
			{
				JOptionPane.showMessageDialog(null, "No se puede eliminar sala porque posee una o mas cursadas asociadas",null,JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		int response = JOptionPane.showConfirmDialog(
				null, 
				"Est\u00E1 seguro de que desea eliminar la sala \"" + elem.getAlias() + "\"(" + elem.getCodigo() + ")?", 
				"Confirmar eliminaci\u00F3n de sala", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE);
		
		if(response == JOptionPane.YES_OPTION)
		{
			
			elem.setDisponibleEnSistema(false);
			modeloSala.modificarSala(elem);
		}
	}
}
