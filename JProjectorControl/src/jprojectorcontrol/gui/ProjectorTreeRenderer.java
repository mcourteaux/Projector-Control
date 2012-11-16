/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import jprojectorcontrol.projectors.Projector;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorTreeRenderer extends DefaultTreeCellRenderer
{
    
    private ImageIcon ICON_POWER_ON = null;
    private ImageIcon ICON_POWER_OFF = null;
    private ImageIcon ICON_COOL_DOWN = null;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        Projector projector = (Projector) value;
        if (projector != null)
        {
            try
            {
                switch (projector.getPower(false))
                {
                    case 0: setIcon(ICON_POWER_OFF); break;
                    case 1: setIcon(ICON_POWER_ON); break;
                    case 2: setIcon(ICON_COOL_DOWN); break;
                }
            } catch (Exception ex)
            {
                Logger.getLogger(ProjectorTreeRenderer.class.getName()).error(null, ex);
            }
            
            setText(projector.getLocation() + " (" + projector.getConnectionPoint().toString() + ")");
        }
        
        return this;
    }
    
}
