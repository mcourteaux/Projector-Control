/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.projectors;

import jprojectorcontrol.ConnectionPoint;

/**
 *
 * @author martijncourteaux
 */
public abstract class Projector
{
    
    private ConnectionPoint connectionPoint;
    protected int powerStateCache;
    private String location;
    
    public Projector(ConnectionPoint host)
    {
        this.connectionPoint = host;
        this.powerStateCache = -1;
    }
    
    public abstract void executeCommand(Command command) throws Exception;
    
    public abstract int getPower(boolean forceUpdate) throws Exception;
    public abstract void setPower(boolean power) throws Exception;
    
    public String getLocation()
    {
        return location;
    }
    
    public String getBrand()
    {
        String str = getClass().getPackage().getName().substring(Projector.class.getPackage().getName().length() + 1);
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }
    
    public String getModelName()
    {
        return getClass().getSimpleName();
    }

    public ConnectionPoint getConnectionPoint()
    {
        return connectionPoint;
    }
    
    @Override
    public String toString()
    {
        return getBrand() + " " + getModelName();
    }
}
