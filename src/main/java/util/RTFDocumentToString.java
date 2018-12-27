package util;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextPane;
import javax.swing.text.rtf.RTFEditorKit;

public class RTFDocumentToString
{
	private RTFDocumentToString(){}
	
	public static String documentToString(JTextPane txtPane) 
	{
		String ret = "";
	    
	    RTFEditorKit kit = new RTFEditorKit();
	    try (OutputStream os = new StringOutputStream()) 
	    {
			kit.write(os, txtPane.getDocument(), 0, txtPane.getDocument().getLength());
			ret = os.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return ret;
	}
	
	public static void stringToRTFDocument(JTextPane txtPane, String rtfString) 
	{
	    RTFEditorKit kit = new RTFEditorKit();
	    try (InputStream is = new StringInputStream(rtfString)) 
	    {
	    	kit.read(is, txtPane.getDocument(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
