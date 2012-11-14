/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class ConnectionPoint
{
    private InetAddress host;
    private int port;

    public ConnectionPoint(InetAddress host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public ConnectionPoint(String host, int port) throws UnknownHostException
    {
        this.host = InetAddress.getByName(host);
        this.port = port;
    }

    /**
     * 
     * @param hostAndPort host and port seperated by a colon. Example: google.be:2020
     */
    public ConnectionPoint(String hostAndPort) throws UnknownHostException
    {
        int colonIndex = hostAndPort.indexOf(':');
        this.host = InetAddress.getByName(hostAndPort.substring(0, colonIndex));
        this.port = Integer.parseInt(hostAndPort.substring(colonIndex + 1));
    }

    public String getHostIP()
    {
        return Utilities.hostAddressToString(this.host.getAddress());
    }

    public int getPort()
    {
        return port;
    }

    @Override
    public String toString()
    {
        return getHostIP() + ":" + port;
    }
    
    public Socket createSocket() throws IOException
    {
        Logger.getLogger("Network").info("Connecting to: " + toString());
        return new Socket(host, port);
    }
    
    
    
    
}
