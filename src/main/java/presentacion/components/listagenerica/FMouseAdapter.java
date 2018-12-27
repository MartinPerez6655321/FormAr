package presentacion.components.listagenerica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class FMouseAdapter implements MouseMotionListener, MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseDragged(MouseEvent arg0){}
	@Override
	public void mouseMoved(MouseEvent arg0){}
}
