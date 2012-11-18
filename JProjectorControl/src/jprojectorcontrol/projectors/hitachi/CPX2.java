/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors.hitachi;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import jprojectorcontrol.ConnectionPoint;
import jprojectorcontrol.ProjectorException;
import jprojectorcontrol.Status;
import jprojectorcontrol.Utilities;
import jprojectorcontrol.projectors.Command;
import jprojectorcontrol.projectors.Projector;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class CPX2 extends Projector
{

    public static final int PROTOCOL_23 = 0;
    public static final int PROTOCOL_9715 = 1;
    private static final int MAXIMUM_RESPONSE_LENGTH = 3;
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
    public int getPower(boolean forceUpdate) throws Exception
    {
        if (forceUpdate)
        {
            HitachiCommand hc = new HitachiCommand("POWER-GET");
            executeCommand(hc);
            if (hc.getResponseLength() == 3)
            {
                byte[] response = hc.getResponse();
                powerStateCache = response[0];
                return response[0];
            }

            throw new ProjectorException("Projector didn't respond as expected.");
        } else
        {
            return powerStateCache;
        }
    }

    @Override
    public void setPower(boolean power) throws Exception
    {
        String cmd = power ? "POWER-ON" : "POWER-OFF";
        HitachiCommand hc = new HitachiCommand(cmd);
        executeCommand(hc);
        if (hc.getResponse()[0] != 0x06)
        {
            throw new ProjectorException("Projector didn't respond as expected.");
        }
    }

    @Override
    public void executeCommand(Command command) throws Exception
    {
        ConnectionPoint cp = getConnectionPoint();
        Socket socket = cp.createSocket();

        Logger.getLogger("Network").info("Execute command");

        HitachiCommand hc = (HitachiCommand) command;
        switch (protocol)
        {
            case PROTOCOL_23:
            {
                execute23(socket, hc);
                return;
            }
            case PROTOCOL_9715:
            {
                execute9715(socket, hc);
                return;
            }
        }
    }

    private void execute23(Socket s, HitachiCommand command) throws Exception
    {
        OutputStream dos = s.getOutputStream();
        InputStream dis = s.getInputStream();

        dos.write(command.getCommandBytes());
        dos.flush();

        byte[] response = new byte[MAXIMUM_RESPONSE_LENGTH];
        int length = dis.read(response);
        command.setOutput(response, length);

        if (response[0] == 0x06)
        {
            Logger.getLogger("Network").info("Projector did send success code.");
            command.setStatusCode(Status.SUCCESS);
        } else if (response[0] == 0x1D)
        {
            Logger.getLogger("Network").info("Projector sent an anwser.");
            command.setStatusCode(Status.SUCCESS);
        } else if (response[0] == 0x15)
        {
            Logger.getLogger("Network").error("Projector doesn't understand");
            command.setStatusCode(Status.ERROR);
        } else
        {
            Logger.getLogger("Network").error("Unexcepted answer");
            command.setStatusCode(Status.ERROR);
        }

        dos.close();
        dis.close();
        s.close();
    }

    private void execute9715(Socket s, HitachiCommand command) throws Exception
    {
        Logger.getLogger("Network").info("Execute 9715");

        OutputStream dos = s.getOutputStream();
        InputStream dis = s.getInputStream();

        Logger.getLogger("Network").info("Streams opened");

        byte[] buffer = new byte[17];
        byte[] rawCommand = command.getCommandBytes();
        buffer[0] = 0x02;
        buffer[1] = 0x0D;
        int checksum = 0x02 + 0x0D;
        for (int i = 0; i < 13; ++i)
        {
            buffer[2 + i] = rawCommand[i];
            checksum += rawCommand[i];
        }
        buffer[15] = (byte) (0xFF & (~checksum + 1));
        buffer[16] = 0x01;

        Logger.getLogger("Network").info("Byte buffer: " + Utilities.bytesToString(buffer));

        dos.write(buffer);
        dos.flush();

        byte[] response = new byte[MAXIMUM_RESPONSE_LENGTH];
        int length = dis.read(response);
        command.setOutput(response, length);

        if (response[0] == 0x06)
        {
            Logger.getLogger("Network").info("Projector did send success code.");
            command.setStatusCode(Status.SUCCESS);
        } else if (response[0] == 0x1D)
        {
            Logger.getLogger("Network").info("Projector sent an anwser.");
            command.setStatusCode(Status.SUCCESS);
        } else if (response[0] == 0x15)
        {
            Logger.getLogger("Network").error("Projector doesn't understand");
            command.setStatusCode(Status.ERROR);
        } else
        {
            Logger.getLogger("Network").error("Unexcepted answer");
            command.setStatusCode(Status.ERROR);
        }

        dos.close();
        dis.close();
        s.close();
    }
}
