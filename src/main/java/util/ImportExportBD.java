package util;

import javax.swing.JOptionPane;

import connections.ConnectionManager;
import presentacion.controlador.ControladorLogin;
import presentacion.vista.VentanaExportBD;
import presentacion.vista.VentanaImportBD;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaPrincipalTabs;

public class ImportExportBD {

	ConnectionManager Connection = ConnectionManager.getConnectionManager();
	
	public void Exportar(String rutaExport, VentanaExportBD vista) {
		
		String ruta = rutaExport;
		String nombre = "\\"+Connection.getBD()+".sql";
		String comando = "";
		if(ruta.trim().length()!=0) {
			try {
//				comando = "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump --opt -u"+Connection.getUsuario()+" -p"+Connection.getPassword()+" -B "+Connection.getBD()+" -r "+ruta+nombre;
				comando = "C:\\ProgramData\\FormAr\\bin\\mysqldump --opt -u"+Connection.getUsuario()+" -p"+Connection.getPassword()+" -B "+Connection.getBD()+" -r "+ruta+nombre;
				Runtime rt = Runtime.getRuntime();
				rt.exec(comando);
				vista.close();
				JOptionPane.showMessageDialog(null, "Exportacion Exitosa!!");
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "No se realizo la Exportacion");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Introduzca el destino de la BD");
		}
	}
	
	public void Importar(String rutaImport, VentanaImportBD vista, VentanaPrincipalTabs view) {
	
		String ruta = rutaImport;
		String comando = "";
		if(ruta.trim().length()!=0) {
			try {
//				comando = "cmd /c \"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe\""+" -u"+Connection.getUsuario()+" -p"+Connection.getPassword()+" "+Connection.getBD()+" < "+ruta;
				comando = "cmd /c \"C:\\ProgramData\\FormAr\\bin\\mysql.exe\""+" -u"+Connection.getUsuario()+" -p"+Connection.getPassword()+" "+Connection.getBD()+" < "+ruta;
				Runtime rt = Runtime.getRuntime();
				rt.exec(comando);
				vista.close();
				view.close();
				JOptionPane.showMessageDialog(null, "Importacion Exitosa!!");
				VentanaLogin ventanaLogin=new VentanaLogin();
				ControladorLogin controladoLogin=new ControladorLogin(ventanaLogin);
				controladoLogin.inicializar();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Falta seleccionar el archivo.sql");
		}
	}
	
}
