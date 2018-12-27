package presentacion.controlador;

import dto.CursadaDTO;
import modelo.ModeloCursos;
import presentacion.vista.PanelProgramaDeCursada;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ControladorPanelProgramaDeCursada
{
	private static final long MAX_FILE_SIZE = 10485760;
	
	private ModeloCursos model = ModeloCursos.getInstance();
	private PanelProgramaDeCursada vista;
	private CursadaDTO cursada;
	
	public ControladorPanelProgramaDeCursada(PanelProgramaDeCursada vista, CursadaDTO cursada)
	{
		this.vista = vista;
		this.cursada = cursada;
		
		vista.getBtnCargar().addActionListener( e -> cargar() );
		vista.getBtnEliminar().addActionListener( e -> eliminar() );
		
		vista.setCursada(cursada);
	}

	private void cargar()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		if(chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION)
		{
			if(chooser.getSelectedFile().length() < MAX_FILE_SIZE)
			{
				try(FileInputStream fis = new FileInputStream(chooser.getSelectedFile()))
				{
					cursada.setPrograma(Base64.getEncoder().encodeToString(readAllBytes(fis)));
					
					model.modificarCursada(cursada);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(vista, "El archivo no debe superar los 10MB!", "Demasiado grande", JOptionPane.ERROR_MESSAGE);
			}
		}
		vista.setCursada(cursada);
	}
	
	private byte[] readAllBytes(FileInputStream is) throws IOException 
	{
		List<byte[]> list = new LinkedList<>();
		
		int available = is.available();
		int length = 0;
		while(available > 0)
		{
			byte[] retrieved = new byte[available];
			is.read(retrieved);
			list.add(retrieved);
			length = length + available;
			available = is.available();
		}
		
		byte[] ret = new byte[length];
		
		int index = 0;
		for(byte[] retrieved : list)
			for(int retrievedIndex = 0; retrievedIndex < retrieved.length; retrievedIndex++)
			{
				ret[index] = retrieved[retrievedIndex];
				index++;
			}
		
		return ret;
	}

	private void eliminar()
	{
		if(JOptionPane.showConfirmDialog(vista, "Seguro de que desea eliminar el programa de esta cursada?") == JOptionPane.OK_OPTION)
		{
			cursada.setPrograma("");
			model.modificarCursada(cursada);
			vista.setCursada(cursada);
		}
	}
}
