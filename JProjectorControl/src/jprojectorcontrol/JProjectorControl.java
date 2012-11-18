/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import jprojectorcontrol.gui.OverviewFrame;
import jprojectorcontrol.gui.ProjectorManager;
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
        Logger logger;
        logger = Logger.getLogger("Network");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));
        logger = Logger.getLogger("UI");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));
        logger = Logger.getLogger("ProjMan");
        logger.setLevel(Level.ALL);
        logger.addAppender(new ConsoleAppender(new PatternLayout()));


        if (testing())
        {
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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

    private static boolean testing() throws Exception
    {
        Utilities.setLaF();
        OverviewFrame of = new OverviewFrame();
        of.setLocationByPlatform(true);
        of.setVisible(true);
        return true;
    }
}
