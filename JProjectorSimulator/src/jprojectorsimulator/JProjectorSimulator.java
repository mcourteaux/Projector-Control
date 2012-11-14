/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorsimulator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author martijncourteaux
 */
public class JProjectorSimulator
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(9715);
        
        while (true)
        {
            try
            {
                 Socket s = ss.accept();
                 System.out.println("Connected");
                 
                 s.getInputStream().read(new byte[17]);
                 s.getOutputStream().write(new byte[]{0x1D, 0x01, 0x00});
                 s.getOutputStream().flush();
                 s.close();
            } catch (Exception e)
            {
            }
        }
    }
}
