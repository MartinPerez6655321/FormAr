package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;

import com.github.lgooddatepicker.timepicker.TimePicker;

import dto.DiaDeLaSemanaDTO;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;

public class PanelEdicionHorario extends JPanel
{
	private static final long serialVersionUID = 2778216686083917187L;
	private TimePicker pickerInicio;
	private TimePicker pickerFin;
	private JComboBox<DiaDeLaSemanaDTO> cmbDia;
	private JButton btnOk;
	
	public PanelEdicionHorario()
	{
		setLayout(null);
		
		JLabel lblInicio = new JLabel("Inicio");
		lblInicio.setBounds(10, 11, 95, 14);
		add(lblInicio);
		
		JLabel lblFin = new JLabel("Fin");
		lblFin.setBounds(10, 36, 95, 14);
		add(lblFin);
		
		JLabel lblDa = new JLabel("D\u00EDa");
		lblDa.setBounds(10, 61, 95, 14);
		add(lblDa);
		
		cmbDia = new JComboBox<>();
		cmbDia.setBounds(115, 57, 122, 22);
		cmbDia.setRenderer(new DefaultListCellRenderer()
		{
			private static final long serialVersionUID = 3450645754798867460L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus)
			{
				return super.getListCellRendererComponent(list, ((DiaDeLaSemanaDTO)value).getNombre(), index, isSelected, cellHasFocus);
			}
		});
		add(cmbDia);

		pickerInicio = new TimePicker();
		pickerInicio.setBounds(115, 8, 122, 20);
		add(pickerInicio);

		pickerFin = new TimePicker();
		pickerFin.setBounds(115, 33, 122, 20);
		add(pickerFin);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 94, 229, 33);
		add(panel);
		
		btnOk = new JButton("Ok");
		panel.add(btnOk);
		
		setPreferredSize(new Dimension(250, 140));
	}
	
	public TimePicker getPickerInicio()
	{
		return pickerInicio;
	}

	public TimePicker getPickerFin()
	{
		return pickerFin;
	}

	public JComboBox<DiaDeLaSemanaDTO> getCmbDia()
	{
		return cmbDia;
	}

	public JButton getBtnOk(){
		return btnOk;
	}
}
