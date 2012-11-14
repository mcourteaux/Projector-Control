/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

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
        return String.format("%d.%d.%d.%d", address[0], address[1], address[2], address[3]);
    }
}
