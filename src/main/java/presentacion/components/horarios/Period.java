package presentacion.components.horarios;

import java.awt.Color;
import java.sql.Time;

import dto.DiaDeLaSemanaDTO;

public class Period
{
	private DiaDeLaSemanaDTO day; 
	private Time start;
	private Time end;
	private String string;
	private Color color;
	
	public Period(DiaDeLaSemanaDTO day, Time start, Time end, String string, Color color)
	{
		this.setDay(day);
		this.setStart(start);
		this.setEnd(end);
		this.setString(string);
		this.setColor(color);
	}

	public DiaDeLaSemanaDTO getDay()
	{
		return day;
	}

	public void setDay(DiaDeLaSemanaDTO day)
	{
		this.day = day;
	}

	public Time getStart()
	{
		return start;
	}

	public void setStart(Time start)
	{
		this.start = start;
	}

	public Time getEnd()
	{
		return end;
	}

	public void setEnd(Time end)
	{
		this.end = end;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
