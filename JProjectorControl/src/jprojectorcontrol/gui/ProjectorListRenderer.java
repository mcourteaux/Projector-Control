/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.UIManager;
import jprojectorcontrol.projectors.Projector;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorListRenderer extends DefaultListCellRenderer
{
    
    private Icon ICON_POWER_UNKNOWN = UIManager.getIcon("Projector.powerUnknown");
    private Icon ICON_POWER_ON = UIManager.getIcon("Projector.powerOn");
    private Icon ICON_POWER_OFF = UIManager.getIcon("Projector.powerOff");
    private Icon ICON_COOL_DOWN = UIManager.getIcon("Projector.coolDown");


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof Projector)
        {
            Projector projector = (Projector) value;
            try
            {
                switch (projector.getPower(false))
                {
                    case -1:
                        setIcon(ICON_POWER_UNKNOWN);
                        break;
                    case 0:
                        setIcon(ICON_POWER_OFF);
                        break;
                    case 1:
                        setIcon(ICON_POWER_ON);
                        break;
                    case 2:
                        setIcon(ICON_COOL_DOWN);
                        break;
                }
            } catch (Exception ex)
            {
                Logger.getLogger("UI").error(null, ex);
            }
            setText("[" + projector.toString() + "] " + projector.getLocation() + " (" + projector.getConnectionPoint().toString() + ")");
        }
        
        return this;
    }
    
}
