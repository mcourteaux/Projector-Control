/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import jprojectorcontrol.projectors.Projector;

/**
 *
 * @author martijncourteaux
 */
public class ModelListRenderer extends DefaultListCellRenderer
{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Class)
        {
            setText(Projector.getBrandAndModelName((Class) value));
        }
        
        return this;
    }
    
}
