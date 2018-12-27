package presentacion.vista;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class VistaPresentacion extends JPanel
{
	private static final long serialVersionUID = -297319751293633073L;
    private Image image = new ImageIcon(getClass().getResource("/Imagenes/Presentacion.jpg")).getImage();

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(),
                        this);
        setOpaque(false);
        super.paint(g);
    }
    
}
