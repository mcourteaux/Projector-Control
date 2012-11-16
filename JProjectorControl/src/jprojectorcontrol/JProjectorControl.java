/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Logger logger = Logger.getLogger("Network");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));


        CPX2 proj = new CPX2(new ConnectionPoint("10.2.2.134:23"));
        System.out.println(proj);

        HitachiCommand hcAsk = new HitachiCommand("POWER-GET");
        proj.executeCommand(hcAsk);
        System.out.println("Power status: " + Utilities.bytesToString(hcAsk.getResponse()));

        while (true)
        {
            System.out.println("Give next command");
            HitachiCommand hc = new HitachiCommand(in.readLine());
            if (hc.getCommandBytes() == null)
            {
                return;
            }
            proj.executeCommand(hc);
            System.out.println("Projector answered: " + Utilities.bytesToString(hc.getResponse()));
        }
    }
}
