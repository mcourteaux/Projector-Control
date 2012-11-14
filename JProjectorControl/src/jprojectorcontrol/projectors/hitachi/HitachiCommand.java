/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors.hitachi;

import jprojectorcontrol.Utilities;
import jprojectorcontrol.projectors.Command;

/**
 *
 * @author martijncourteaux
 */
public class HitachiCommand extends Command
{
    
    private String command;
    private byte[] response;

    public HitachiCommand(String command)
    {
        this.command = command;
    }

    public String getCommand()
    {
        return command;
    }
    
    public byte[] getCommandBytes()
    {
        return Utilities.parseBytes("be:ef:03:06:00:19:d3:02:00:00:60:00:00");
    }

    public void setOutput(byte[] response)
    {
        setHasOutput(true);
        this.response = response;
    }

    public byte[] getResponse()
    {
        return response;
    }
    
    
}
