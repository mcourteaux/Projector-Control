/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors.hitachi;

import jprojectorcontrol.projectors.Command;

/**
 *
 * @author martijncourteaux
 */
public class HitachiCommand extends Command
{
    
    private String command;

    public HitachiCommand(String command)
    {
        this.command = command;
    }

    public String getCommand()
    {
        return command;
    }
    
}
