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
    
    public Command()
    {
    }
    
    protected void setStatusCode(Status statusCode)
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
    
    
}
