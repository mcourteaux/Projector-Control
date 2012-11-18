/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author martijncourteaux
 */
public class ComboBoxTreeRenderer extends DefaultListCellRenderer
{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof DefaultMutableTreeNode)
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            ProjectorManager.Folder folder = (ProjectorManager.Folder) node.getUserObject();
            int depth = node.getLevel();
            String spacer = createDepthSpacer(depth);
            setText(spacer + folder.getName() + " (" + folder.projectorCount() + ")");
        }
        
        return this;
    }

    private String createDepthSpacer(int depth)
    {
        if (depth <= 0) return "";
        return String.format("%" + (depth * 4) + "s", "");
    }
    
    
    
}
