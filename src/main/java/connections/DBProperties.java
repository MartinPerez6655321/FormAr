package connections;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class DBProperties
{
	private Properties dbProperties;
	private static DBProperties instance;
	private static final String FILE_PATH = System.getenv("APPDATA") + "/FormAR/config/dataBase.properties";
	
	public static DBProperties getInstance()
	{
		if(instance==null)
			instance = new DBProperties();
		return instance;
	}
	
	private DBProperties()
	{
		dbProperties = readProperties();
	}
	
	public String get(String key)
	{
		return dbProperties.getProperty(key);
	}
	
	public void put(String key, String value)
	{
		dbProperties.put(key, value);
	}
	private static Properties readProperties()
	{
		Properties prop = new Properties();
		
		try {
		    prop.load(new FileInputStream(FILE_PATH));
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			ConfiguradorBD bd = new ConfiguradorBD();
			bd.setVisible(true);
			if(bd.pressedOk() && crearArchivo(bd.getProperties()))
				prop = readProperties();
			else {
				bd.dispose();
			}
		}
		return prop;
	}
	
	private static boolean crearArchivo(Properties p)
	{
		File folder = new File("config");
		if(!folder.exists())
			folder.mkdir();
		try {
			File f = new File(FILE_PATH); 
			if(!f.exists())
				f.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			p.store(fos, null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void intentarDeNuevo()
	{
		ConfiguradorBD bd = new ConfiguradorBD();
		bd.setCurrentProps(dbProperties);
		bd.setVisible(true);
		if(bd.pressedOk() && crearArchivo(bd.getProperties()))
			dbProperties = readProperties();
		else{
			bd.dispose();
		}
	}
}
