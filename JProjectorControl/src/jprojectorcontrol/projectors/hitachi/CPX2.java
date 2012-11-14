/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors.hitachi;

import java.net.Socket;
import jprojectorcontrol.ConnectionPoint;
import jprojectorcontrol.projectors.Command;
import jprojectorcontrol.projectors.Projector;

/**
 *
 * @author martijncourteaux
 */
public class CPX2 extends Projector
{

    public static final int PROTOCOL_23 = 0;
    public static final int PROTOCOL_9715 = 1;
    private int protocol;

    public CPX2(ConnectionPoint cp)
    {
        this(cp, cp.getPort() == 23 ? PROTOCOL_23 : PROTOCOL_9715);
    }

    public CPX2(ConnectionPoint cp, int protocol)
    {
        super(cp);
        this.protocol = protocol;
        if (protocol < PROTOCOL_23 || protocol > PROTOCOL_9715)
        {
            throw new IllegalArgumentException("Protocol doesn't exist");
        }
    }

    @Override
    public void executeCommand(Command command) throws Exception
    {
        ConnectionPoint cp = getConnectionPoint();
        Socket socket = cp.createSocket();
        socket.close();
        switch (protocol)
        {
            case PROTOCOL_23:
            {
                execute23(socket, command);
                return;
            }
            case PROTOCOL_9715:
            {
                execute9715(socket, command);
                return;
            }
        }
    }

    private void execute23(Socket s, Command command) throws Exception
    {
        
    }
    
    private void execute9715(Socket s, Command command) throws Exception
    {
        
    }
}
