/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors.hitachi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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
    private int responseLength;
    
    private static Map<String, String> commands = new HashMap<String, String>();
    
    static
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(HitachiCommand.class.getResourceAsStream("/jprojectorcontrol/projectors/hitachi/commands.txt")));
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                String[] splitted = line.split(" ");
                System.out.println(line);
                commands.put(splitted[1], splitted[0]);
            }
            br.close();
            System.out.println("Commands loaded!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

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
        return Utilities.parseBytes(commands.get(command));
    }

    public void setOutput(byte[] response, int responseLength)
    {
        setHasOutput(true);
        this.response = response;
        this.responseLength = responseLength;
    }

    public byte[] getResponse()
    {
        return response;
    }
    
    public int getResponseLength()
    {
        return responseLength;
    }
}
