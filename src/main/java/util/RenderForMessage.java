package util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class RenderForMessage extends DefaultTableCellRenderer
{
 
	private int columna ;
	private EstilosYColores style = EstilosYColores.getInstance();

    public RenderForMessage(int Colpatron)
    {
    	this.columna = Colpatron;
    }

	public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column)
   {
      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      if ( table.getValueAt(row,columna).equals("No Visto") )
      {
         this.setBackground(style.getSelectTable());
         this.setForeground(Color.black);
      } 
      else 
      {
    	  this.setBackground(Color.darkGray);
          this.setForeground(Color.white);
      }
      return this;
   }

}