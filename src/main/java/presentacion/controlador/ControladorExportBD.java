package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import presentacion.vista.VentanaExportBD;
import util.ImportExportBD;

public class ControladorExportBD implements ActionListener{

	private VentanaExportBD ventanaExpBD;
	private ImportExportBD importExportBD;
	
	public ControladorExportBD(){

		importExportBD = new ImportExportBD();
		
		ventanaExpBD = new VentanaExportBD();
		this.ventanaExpBD.getBtnExport().addActionListener(this);
		this.ventanaExpBD.getBtnSelectFolder().addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Actions Export
		
		if (e.getSource() == this.ventanaExpBD.getBtnSelectFolder()){
			JFileChooser select = new JFileChooser();
			select.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int seGuarda = select.showSaveDialog(null);

			if(seGuarda == JFileChooser.APPROVE_OPTION){
				String ruta = select.getSelectedFile().getPath();
				this.ventanaExpBD.getTextField().setText(ruta);;
			}
		}
		
		if (e.getSource() == this.ventanaExpBD.getBtnExport()){
			String rutaExport = this.ventanaExpBD.getTextField().getText();
			importExportBD.Exportar(rutaExport,this.ventanaExpBD);
		}
		
	}
		
}
