package util;

import java.awt.Color;

public class EstilosYColores {

	private static EstilosYColores instancia;
	
	//Estilos Ventanas
	private Color selectTable;
	private Color backgroundButtonDelete;
	private Color foregroundButtonDelete;
	private Color backgroundButtonCreate;
	private Color foregroundButtonCreate;
	private Color backgroundVentanaStandard;
	private Color backgroundButtonStandard;
	private Color foregroundButtonStandard;
	private Color backgroundSheet;
	private Color backgroundRecado;
	
	public EstilosYColores()
	{
		this.selectTable = new Color (153,204,255);
		this.backgroundButtonDelete = new Color (189,49,49);
		this.foregroundButtonDelete = Color.white;
		this.backgroundButtonCreate = new Color (49,189,63);
		this.foregroundButtonCreate = Color.black;
		this.backgroundVentanaStandard = Color.lightGray;
		this.backgroundButtonStandard = Color.darkGray;
		this.foregroundButtonStandard = Color.white;
		this.backgroundSheet = new Color (255,255,204);
		this.backgroundRecado = Color.orange;
	}
	
	public static EstilosYColores getInstance()   
	{								
		if(instancia == null)
		{
			instancia = new EstilosYColores();
		}
		return instancia;
	}
	
	public Color getSelectTable() {
		return selectTable;
	}
	
	public Color getBackgroundButtonDelete() {
		return backgroundButtonDelete;
	}

	public Color getForegroundButtonDelete() {
		return foregroundButtonDelete;
	}

	public Color getBackgroundButtonCreate() {
		return backgroundButtonCreate;
	}

	public Color getForegroundButtonCreate() {
		return foregroundButtonCreate;
	}

	public Color getBackgroundVentanaStandard() {
		return backgroundVentanaStandard;
	}

	public Color getBackgroundButtonStandard() {
		return backgroundButtonStandard;
	}

	public Color getForegroundButtonStandard() {
		return foregroundButtonStandard;
	}

	public Color getBackgroundSheet() {
		return backgroundSheet;
	}

	public Color getBackgroundRecado() {
		return backgroundRecado;
	}

}
