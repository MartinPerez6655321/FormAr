package presentacion.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class Modal extends JDialog
{
	private static final long serialVersionUID = -1709445648573901914L;
	private JButton[] buttons;
	private ActionListener disposer;

	public Modal(Component content, int x, int y, int width, int height, JButton... buttons)
	{
		setBounds(x, y, width, height);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(content, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		
		initializeButtons(buttons);
	}

	private void initializeButtons(JButton... buttons)
	{
		this.buttons = buttons;
		disposer = e -> dispose();
		
		if(buttons.length > 0)
			getRootPane().setDefaultButton(buttons[0]);
		for(JButton button : buttons)
			button.addActionListener(disposer);
	}
	
	@Override
	public void dispose()
	{
		for(JButton button : buttons)
			button.removeActionListener(disposer);
		super.dispose();
	}
	
	public Modal(Component content, JButton... buttons)
	{
		Dimension preferredSize = content.getPreferredSize();
		int width = preferredSize.width + 10;
		int height= preferredSize.height + 20;
		
		int x;
		int y;
		try
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			x = (screenSize.width - preferredSize.width)/2;
			y = (screenSize.height - preferredSize.height)/2;
		} catch (HeadlessException e) {
			e.printStackTrace();
			x = 100;
			y = 100;
		}
		
		setBounds(x, y, width, height);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(content, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		
		initializeButtons(buttons);
	}
	
	public static Modal showDialog(JPanel content, int x, int y, int width, int height, JButton... buttons)
	{
		return showDialog(content, x, y, width, height, null, buttons);
	}
	
	public static Modal showDialog(JPanel content, String titulo, JButton... buttons)
	{
		Dimension preferredSize = content.getPreferredSize();
		int width = preferredSize.width + 10;
		int height= preferredSize.height + 20;
		
		int x;
		int y;
		try
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			x = (screenSize.width - preferredSize.width)/2;
			y = (screenSize.height - preferredSize.height)/2;
		} catch (HeadlessException e) {
			e.printStackTrace();
			x = 100;
			y = 100;
		}
		
		return showDialog(content, x, y, width, height, titulo, buttons);
	}
	
	public static Modal showDialog(JPanel content, int x, int y, String titulo, JButton... buttons)
	{
		Dimension preferredSize = content.getPreferredSize();
		int width = preferredSize.width + 10;
		int height= preferredSize.height + 20;
		
		return showDialog(content, x, y, width, height, titulo, buttons);
	}
	
	public static Modal showDialog(JPanel content, int x, int y, int width, int height, String titulo, JButton... buttons)
	{
		Modal ret = new Modal(content, x, y, width, height, buttons);
		ret.setTitle(titulo);
		ret.setResizable(false);
		ret.setVisible(true);
		return ret;
	}
}
