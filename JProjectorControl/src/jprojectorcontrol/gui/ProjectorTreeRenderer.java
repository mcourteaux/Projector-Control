/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import jprojectorcontrol.projectors.Projector;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorTreeRenderer extends DefaultTreeCellRenderer
{

    private Icon ICON_POWER_UNKNOWN = UIManager.getIcon("Projector.powerUnknown");
    private Icon ICON_POWER_ON = UIManager.getIcon("Projector.powerOn");
    private Icon ICON_POWER_OFF = UIManager.getIcon("Projector.powerOff");
    private Icon ICON_COOL_DOWN = UIManager.getIcon("Projector.coolDown");

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node.getUserObject() instanceof Projector)
        {
            Projector projector = (Projector) node.getUserObject();
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
        if (node.getUserObject() instanceof ProjectorManager.Folder)
        {
            ProjectorManager.Folder fol = (ProjectorManager.Folder) node.getUserObject();
            setText(fol.getName() + " (" + fol.recursiveProjectorCount() + ")");
        }

        return this;
    }
}
