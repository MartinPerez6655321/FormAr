package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;

import dto.CursoDTO;
import modelo.ModeloCursos;
import presentacion.vista.VentanaAgregarCurso;
import presentacion.vista.subcomponentes.DocumentSizeFilter;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorAgregarCurso implements ActionListener
{
	private ModeloCursos model = ModeloCursos.getInstance();
	private VentanaAgregarCurso vista;
	private CursoDTO curso;
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	
	public ControladorAgregarCurso(VentanaAgregarCurso vista)
	{
		this.vista = vista;
		this.vista.getBtnAceptar().addActionListener(this);
		curso=new CursoDTO();
		
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

        updateCount();
		
	}
	
	public void initialize()
	{
		this.vista.show();
		
	}

	private void updateCount()
    {
        vista.getLblCaracteresRestantes().setText((400 -doc.getLength()) + " caracteres restantes");
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()== this.vista.getBtnAceptar()) 
		{
			if (!ValidadorCampos.validarVacio(this.vista.getTextFieldNombre().getText()) || !ValidadorCampos.validarVacio(this.vista.getTextFieldPrecio().getText() ) || 
					!ValidadorCampos.validarVacio(this.vista.getTextFieldCodigo().getText())  || !ValidadorCampos.validarVacio(this.vista.getTextAreaDescripcion().getText())){
					JOptionPane.showMessageDialog(null, "Faltan completar campos al curso");
					return;}
			if (!ValidadorCampos.validarPrecio(this.vista.getTextFieldPrecio().getText()) )
					JOptionPane.showMessageDialog(null, "Ingrese un monto v\u00E1lido");
			
			else if (!ValidadorCampos.validarNombreCursoCursada(this.vista.getTextFieldNombre().getText()))
				JOptionPane.showMessageDialog(null, "Ingrese nombre v\u00E1lido");
			else if(!ValidadorLogico.validarNombreCurso(this.vista.getTextFieldNombre().getText())) {
				JOptionPane.showMessageDialog(null, "Ya existe un curso con ese nombre. Por favor c\u00E1mbielo.");
			}
			
			
			else if (ValidadorLogico.validarCrearCurso(this.vista.getTextFieldCodigo().getText()))
			{
				this.crearCurso(this.vista.getTextFieldNombre().getText(), Integer.parseInt(this.vista.getTextFieldPrecio().getText()), (Integer)this.vista.getSpinnerHoras().getValue(),this.vista.getTextAreaDescripcion().getText());
				model.agregarCurso(curso);
				this.vista.close();
			}
			else
				JOptionPane.showMessageDialog(null, "Ese curso ya existe, verifique el c\u00F3digo del curso");
		}
	}
	
	private void crearCurso(String nombre, int precio, int horas,String descripcion) 
	{
		curso.setNombre(nombre);
		curso.setCodigo(this.vista.getTextFieldCodigo().getText());
		curso.setPrecio(precio);
		curso.setHoras(horas);
		curso.setDisponibleEnSistema(true);
		curso.setDescripcion(descripcion);
	}
}
