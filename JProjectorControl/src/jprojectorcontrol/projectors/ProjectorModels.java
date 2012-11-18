/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import jprojectorcontrol.projectors.hitachi.CPX2;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorModels
{

    private final static List<Class<? extends Projector>> models = new ArrayList<Class<? extends Projector>>();

    static
    {
        models.add(CPX2.class);
    }

    public static DefaultListModel createListModel()
    {
        DefaultListModel dlm = new DefaultListModel();
        dlm.ensureCapacity(models.size());
        for (Class cl : models)
        {
            dlm.addElement(cl);
        }
        return dlm;
    }

    public static DefaultComboBoxModel createComboBoxModel()
    {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (Class cl : models)
        {
            m.addElement(cl);
        }
        return m;
    }
}
