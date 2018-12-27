package presentacion.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelHideAndShow extends JPanel
{
	private static final long serialVersionUID = 7247941372861747331L;
	private JButton hideAndShowButton;
	private JPanel contentPane;
	
	public PanelHideAndShow(Component scrollPaneFilter, String constraints)
	{
		setLayout(new BorderLayout());
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		hideAndShowButton = new JButton("...");
		hideAndShowButton.setMargin(new Insets(-10, -10, -10, -10));
		hideAndShowButton.setPreferredSize(new Dimension(25, (int) getPreferredSize().getHeight()));
		
		add(hideAndShowButton, constraints);
		add(contentPane, BorderLayout.CENTER);
		contentPane.add(scrollPaneFilter, BorderLayout.CENTER);
		
		hideAndShowButton.addActionListener( e -> contentPane.setVisible(!contentPane.isVisible()));
	}
	
	public void setButtonText(String text)
	{
		hideAndShowButton.setText(text);
	}

	public void setHidden(boolean b)
	{
		contentPane.setVisible(!b);
	}
}