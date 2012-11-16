/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import javax.swing.JOptionPane;
import jprojectorcontrol.projectors.hitachi.CPX2;
import jprojectorcontrol.projectors.hitachi.HitachiCommand;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author martijncourteaux
 */
public class JProjectorControl
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Logger logger = Logger.getLogger("Network");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));
        
        
        CPX2 proj = new CPX2(new ConnectionPoint("10.2.2.134:23"));
        System.out.println(proj);
        
        HitachiCommand hcAsk = new HitachiCommand("POWER-GET");
        proj.executeCommand(hcAsk);
        System.out.println("Power status: " + Utilities.bytesToString(hcAsk.getResponse()));
        
        HitachiCommand hc = new HitachiCommand(JOptionPane.showInputDialog("Give next command"));
        proj.executeCommand(hc);
        System.out.println("Projector answered: " + Utilities.bytesToString(hc.getResponse()));
        
    }
}
