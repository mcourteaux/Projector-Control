/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors;

import jprojectorcontrol.Status;

/**
 *
 * @author martijncourteaux
 */
public abstract class Command
{
    
    public Status statusCode;
    public boolean hasOutput;
    
    public Command()
    {
    }
    
    public void setStatusCode(Status statusCode)
    {
        this.statusCode = statusCode;
    }

    public Status getStatusCode()
    {
        return statusCode;
    }

    public boolean didSucceed()
    {
        return statusCode == Status.SUCCESS;
    }
    
    public void setHasOutput(boolean fl)
    {
        this.hasOutput = fl;
    }

    public boolean hasOutput()
    {
        return hasOutput;
    }  
}
