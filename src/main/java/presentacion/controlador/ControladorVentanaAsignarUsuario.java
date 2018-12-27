package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import dto.UsuarioDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaAsignarUsuario;
import presentacion.vista.VentanaCrearRecado;

public class ControladorVentanaAsignarUsuario implements ActionListener, KeyListener
{
	private ModeloPersonas modeloPersonas = ModeloPersonas.getInstance();
	private VentanaAsignarUsuario vista;
	private VentanaCrearRecado vistaCrearRecado;
	private UsuarioDTO usuarioSeleccionado;
	private List<UsuarioDTO> usuarios_en_tabla;
	private List<UsuarioDTO> usuarios_filtrados;

	public ControladorVentanaAsignarUsuario(VentanaCrearRecado view)
	{
		this.vistaCrearRecado = view;
		this.vista = new VentanaAsignarUsuario();
		this.usuarioSeleccionado = null;
		this.vista.getBtnSelecionarUsuario().addActionListener(this);
		this.vista.getTextFieldFiltro().addKeyListener(this);
		this.usuarios_filtrados = new ArrayList<UsuarioDTO>();
	}
	
	public void initialize()
	{
		this.reiniciarTabla();
		this.llenarTabla();
		this.vista.show();
	}
	
	public UsuarioDTO getUsuarioSeleccionado()
	{
		return this.usuarioSeleccionado;
	}
	
	public void selectUsuario(UsuarioDTO user)
	{
		this.usuarioSeleccionado = user;
	}
	
	private void reiniciarTabla() 
	{
		this.vista.getModelUsuarios().setRowCount(0); //Para vaciar la tabla
		this.vista.getModelUsuarios().setColumnCount(0);
		this.vista.getModelUsuarios().setColumnIdentifiers(this.vista.getNombreColumnasUsuarios());	
	}
	
	private void llenarTabla()
	{	
		this.usuarios_en_tabla = modeloPersonas.getUsuarios();
		for (int i = 0; i < this.usuarios_en_tabla.size(); i ++)
		{
			if(usuarios_en_tabla.get(i).getDisponibleEnSistema() && !usuarios_en_tabla.get(i).equals(this.modeloPersonas.getUsuarioActual()))
			{
				usuarios_filtrados.add(usuarios_en_tabla.get(i));
					Object[] fila = {usuarios_en_tabla.get(i).getEmail(),
								     usuarios_en_tabla.get(i).getPersona().getNombre(),
								     usuarios_en_tabla.get(i).getPersona().getApellido()
								    };
				this.vista.getModelUsuarios().addRow(fila);
			}
		}			
	}
	
	public void filtrarTablaTipo(String tipo)
	{	
		reiniciarTabla();
		this.usuarios_filtrados.clear();
		
		for(int i=0;i<usuarios_en_tabla.size();i++)
		{
			if(usuarios_en_tabla.get(i).getDisponibleEnSistema() && !usuarios_en_tabla.get(i).equals(this.modeloPersonas.getUsuarioActual())) 
			{
				String getUsuario = usuarios_en_tabla.get(i).getEmail().toUpperCase();
				String getApellido = usuarios_en_tabla.get(i).getPersona().getApellido().toUpperCase();
				String getNombre = usuarios_en_tabla.get(i).getPersona().getNombre().toUpperCase();
				if(getUsuario.indexOf(tipo.toUpperCase()) != -1 || getNombre.indexOf(tipo.toUpperCase()) != -1 || getApellido.indexOf(tipo.toUpperCase()) != -1 )
				{
					usuarios_filtrados.add(usuarios_en_tabla.get(i));
					Object[] fila ={usuarios_en_tabla.get(i).getEmail(),
									usuarios_en_tabla.get(i).getPersona().getNombre(),
									usuarios_en_tabla.get(i).getPersona().getApellido()
								   };
					this.vista.getModelUsuarios().addRow(fila);
				}					
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.vista.getBtnSelecionarUsuario()) 
		{
			UsuarioDTO user = this.usuarios_filtrados.get(this.vista.getTablaUsuarios().getSelectedRow());
			if(user!=null) 
			{
				this.selectUsuario(user);
				vistaCrearRecado.getTxtReceptor().setText(user.getPersona().getApellido()+" "+user.getPersona().getNombre());	
				this.vista.close();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String tipo = this.vista.getTextFieldFiltro().getText();
		this.filtrarTablaTipo(tipo);
	}

}
