package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;

import dto.CursoDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.vista.PanelEliminacionDeshabilitacion;
import presentacion.vista.VentanaEditarCurso;
import presentacion.vista.subcomponentes.DocumentSizeFilter;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorVentanaEditarCurso implements ActionListener, InvalidationListener{
	private ModeloCursos model = ModeloCursos.getInstance();
	private VentanaEditarCurso vista;
	private CursoDTO curso;
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	
	public ControladorVentanaEditarCurso(VentanaEditarCurso ventanaEditarCurso,CursoDTO curso) {
		this.vista=ventanaEditarCurso;
		this.curso=curso;
		
		this.vista.getBtnGuardarCambios().addActionListener(this);
		this.vista.getBtnEliminarCurso().addActionListener(this);
		
		
		
		doc.setDocumentFilter(new DocumentSizeFilter(400));
	    doc.addDocumentListener(new DocumentListener(){
	            @Override
	        public void changedUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void insertUpdate(DocumentEvent e) { updateCount();}
	            @Override
	        public void removeUpdate(DocumentEvent e) { updateCount();}
	        });
	    vista.getTextAreaDescripcion().setDocument(doc);
		
		
		model.addListener(this);
	}
	
	private void updateCount()
    {
        vista.getLblCaracteresRestantes().setText((400 -doc.getLength()) + " caracteres restantes");
    }
	
	public void initialize() {
		this.llenarCampos();
		this.vista.show();
	}
	
	private void llenarCampos() 
	{
		this.vista.getTextFieldNombre().setText(curso.getNombre());
		this.vista.getTextFieldCodigo().setText(curso.getCodigo());
		this.vista.getTextFieldPrecio().setText(curso.getPrecio().toString());
		this.vista.getSpinnerHoras().setValue(curso.getHoras());
		this.vista.getTextAreaDescripcion().setText(curso.getDescripcion());
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()== this.vista.getBtnGuardarCambios()) 
		{
			if (!ValidadorCampos.validarVacio(this.vista.getTextFieldNombre().getText()) || !ValidadorCampos.validarVacio(this.vista.getTextFieldPrecio().getText()) ||
					!ValidadorCampos.validarVacio(this.vista.getTextFieldCodigo().getText()) || !ValidadorCampos.validarVacio(this.vista.getTextAreaDescripcion().getText())) {
					JOptionPane.showMessageDialog(null, "Faltan completar campos al curso");
					return;}
			if(!this.vista.getTextFieldNombre().getText().equals(curso.getNombre())) {
				if(!ValidadorLogico.validarNombreCurso(this.vista.getTextFieldNombre().getText())) {
					JOptionPane.showMessageDialog(null, "Ya existe un curso con ese nombre. Por favor c\u00E1mbielo.");
					return;
				}
			}
			
			
			if (!ValidadorCampos.validarPrecio(this.vista.getTextFieldPrecio().getText()) )
					JOptionPane.showMessageDialog(null, "Ingrese un monto v\u00E1lido");
			
			else if (!ValidadorCampos.validarNombreCursoCursada(this.vista.getTextFieldNombre().getText()))
				JOptionPane.showMessageDialog(null, "Ingresa nombre v\u00E1lido");
			
			else if (ValidadorLogico.validarEditarCurso(this.curso, this.vista.getTextFieldCodigo().getText()))
			{
				editarCurso(this.vista.getTextFieldNombre().getText(), Integer.parseInt(this.vista.getTextFieldPrecio().getText()), (Integer) vista.getSpinnerHoras().getValue(),this.vista.getTextAreaDescripcion().getText());
				model.modificarCurso(curso);
				this.vista.close();
			}
			else
				JOptionPane.showMessageDialog(null, "Ese curso ya existe, verifique el c\u00F3digo del curso");
		}
		else if (e.getSource()==this.vista.getBtnEliminarCurso()) 
		{
			PanelEliminacionDeshabilitacion panelEliDes=new PanelEliminacionDeshabilitacion();
			new ControladorPanelEliminacionDeshabilitacion(panelEliDes,curso);
			Modal.showDialog(panelEliDes, "Elegir tipo", panelEliDes.getBtnDeshabilitar(),panelEliDes.getBtnEliminar());
			this.vista.close();
		}
		
	}
	

	private void editarCurso(String nombre, int precio, int horas,String descripcion) 
	{
		curso.setCodigo(this.vista.getTextFieldCodigo().getText());
		curso.setNombre(nombre);
		curso.setPrecio(precio);
		curso.setHoras(horas);
		curso.setDescripcion(descripcion);
	}
	
	@Override
	public void invalidated(Observable observable)
	{	
		this.llenarCampos();
	}
}
