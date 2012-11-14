/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import jprojectorcontrol.projectors.hitachi.CPX2;
import jprojectorcontrol.projectors.hitachi.HitachiCommand;

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
        CPX2 proj = new CPX2(new ConnectionPoint("localhost:9715"));
        System.out.println(proj);
        proj.executeCommand(new HitachiCommand("POWER-ON"));
    }
}
