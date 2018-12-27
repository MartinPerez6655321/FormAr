package presentacion.vista;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import dto.CursadaDTO;
import dto.SalaDTO;
import presentacion.components.horarios.GraficoHorarios;
import presentacion.controlador.ControladorAsignacionDeSalasACursada;

public class VistaAsignacionDeSalasACursada extends JPanel
{
	private static final long serialVersionUID = 700700293597197598L;
	private JButton asignarButton;
	private JLabel salaAsignadaLabel;
	private JList<SalaDTO> listSalas;
	private GraficoHorarios graficoHorarios;
	private transient List<SalaDTO> salasOcupadas;
	private JLabel lblAsignacionSala;

	public VistaAsignacionDeSalasACursada(CursadaDTO cursada)
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		
		salaAsignadaLabel = new JLabel();
		salaAsignadaLabel.setBounds(0, 0, 259, 40);
		
		lblAsignacionSala = new JLabel();
		lblAsignacionSala.setBounds(260, 11, 454, 18);

		JPanel panelTitulos = new JPanel();
		panelTitulos.setLayout(null);
		panelTitulos.setPreferredSize(new Dimension((int) getMaximumSize().getWidth(), 40));
		panelTitulos.add(salaAsignadaLabel);
		panelTitulos.add(lblAsignacionSala);
		add(panelTitulos, BorderLayout.NORTH);
		
		JScrollPane scrollListSalas = new JScrollPane();
		
		listSalas = new JList<>();
		
		listSalas.setCellRenderer(new DefaultListCellRenderer()
		{
			private static final long serialVersionUID = 2787706070658834427L;
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus)
			{
				SalaDTO sala = ((SalaDTO)value);
				String salaString = sala.getAlias() + "(" + sala.getCodigo() + ")";
				Component ret = super.getListCellRendererComponent(list, salaString, index, isSelected, cellHasFocus);
				if(salasOcupadas.contains(sala))
				{
					ret.setBackground(Color.DARK_GRAY);
					if(isSelected)
						ret.setForeground(Color.RED);
					else
						ret.setForeground(Color.WHITE);
				}
				return ret;
			}
		});
		listSalas.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollListSalas.setViewportView(listSalas);
		add(scrollListSalas, BorderLayout.WEST);
		
		asignarButton = new JButton("Asignar");
		add(asignarButton, BorderLayout.SOUTH);
		
		graficoHorarios = new GraficoHorarios();
		
		add(graficoHorarios, BorderLayout.CENTER);
		
		new ControladorAsignacionDeSalasACursada(this, cursada);
	}

	public void setCursada(CursadaDTO cursada, List<SalaDTO> salasDisponibles, List<SalaDTO> salasOcupadas)
	{
		salaAsignadaLabel.setText(createCursadaLabelString(cursada));
		lblAsignacionSala.setText("Asignaci\u00F3n de sala para " + cursada.getNombre());
		
		this.salasOcupadas = salasOcupadas;

		DefaultListModel<SalaDTO> model = new DefaultListModel<>();
		for(SalaDTO sala : salasDisponibles)
			model.addElement(sala);
		for(SalaDTO sala : salasOcupadas)
			model.addElement(sala);
		
		listSalas.setModel(model);
	}

	private String createCursadaLabelString(CursadaDTO cursada)
	{
		return "<html>"
				+ (cursada.getSala() == null? "Sin sala asignada." : ("Sala asignada: " + cursada.getSala().getAlias() + "(" + cursada.getSala().getCodigo() + ")")) + 
				"</html>";
	}

	public JButton getAsignarButton()
	{
		return asignarButton;
	}

	public JList<SalaDTO> getSalasList()
	{
		return listSalas;
	}

	public GraficoHorarios getGraficoHorarios()
	{
		return graficoHorarios;
	}
}
