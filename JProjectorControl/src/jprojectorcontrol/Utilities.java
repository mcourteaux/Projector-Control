/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class Utilities
{

    /**
     * Converts a byte array to a string like: <code>192.168.1.10</code>
     * @param address
     * @return 
     */
    public static String hostAddressToString(byte[] address)
    {
        if (address.length != 4)
        {
            throw new IllegalArgumentException("Byte array should be 4 bytes long.");
        }
        return String.format("%d.%d.%d.%d", 0xFF & address[0], 0xFF & address[1], 0xFF & address[2], 0xFF & address[3]);
    }
    
    public static byte[] parseBytes(String bytesStr)
    {
        String[] bStr = bytesStr.split(":");
        byte[] buffer = new byte[bStr.length];
        for (int i = 0; i < buffer.length; ++i)
        {
            buffer[i] = (byte) Integer.parseInt(bStr[i], 16);
        }
        return buffer;
    }
    
    public static String bytesToString(byte[] buffer)
    {
        String str = "";
        for (int i = 0; i < buffer.length; ++i)
        {
            str += ":" + String.format("%02x", 0xFF & buffer[i]);
        }
        return str.substring(1);
    }
    
    public static void setLaF()
    {
        try
        {
            if (System.getProperty("mrj.version") != null)
            {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "JProjectorControl");
            }
        } catch (Exception e)
        {
        }
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("Tree.leafIcon", UIManager.get("Tree.closedIcon"));
            
            UIManager.put("Projector.powerOn", new ImageIcon(Utilities.class.getResource("gui/img/Button-Blank-Green-Icon.png")));
            UIManager.put("Projector.powerOff", new ImageIcon(Utilities.class.getResource("gui/img/Button-Blank-Red-Icon.png")));
            UIManager.put("Projector.powerUnknown", new ImageIcon(Utilities.class.getResource("gui/img/Button-Blank-Gray-Icon.png")));
            UIManager.put("Projector.coolDown", new ImageIcon(Utilities.class.getResource("gui/img/Button-Blank-Blue-Icon.png")));
        } catch (Exception e)
        {
            Logger.getLogger("UI").warn("Cannot set LaF", e);
        }
        
        
    }
}
