/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorException extends Exception
{

    public ProjectorException(Throwable cause)
    {
        super(cause);
    }

    public ProjectorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProjectorException(String message)
    {
        super(message);
    }

    public ProjectorException()
    {
    }
    
}
