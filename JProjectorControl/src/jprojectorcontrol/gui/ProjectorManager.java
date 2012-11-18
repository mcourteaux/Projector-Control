/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jprojectorcontrol.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import jprojectorcontrol.ConnectionPoint;
import jprojectorcontrol.projectors.Projector;
import org.apache.log4j.Logger;

/**
 *
 * @author martijncourteaux
 */
public class ProjectorManager
{

    public class Folder
    {

        private String name;
        private Folder parent;
        public List<Folder> subfolders = new ArrayList<Folder>();
        public List<Projector> projectors = new ArrayList<Projector>();

        public Folder(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Folder getParent()
        {
            return parent;
        }

        public void setParent(Folder parent)
        {
            this.parent = parent;
        }

        public int subfolderCount()
        {
            return subfolders.size();
        }

        public int projectorCount()
        {
            return projectors.size();
        }
        
        public int recursiveProjectorCount()
        {
            int count = projectorCount();
            for (Folder f : subfolders)
            {
                count += f.recursiveProjectorCount();
            }
            return count;
        }

        public void addFolder(Folder f)
        {
            if (!subfolders.contains(f))
            {
                subfolders.add(f);
                f.setParent(this);
            }
        }

        public void addProjector(Projector p)
        {
            if (!projectors.contains(p))
            {
                projectors.add(p);
            }
        }

        private void save(PrintStream ps) throws IOException
        {
            ps.print(name);
            ps.println("{");
            for (Folder f : subfolders)
            {
                f.save(ps);
            }
            for (Projector p : projectors)
            {
                ps.println("[");
                ps.println(p.getClass().getName());
                ps.println(p.getConnectionPoint().toString());
                ps.println(p.getLocation());
                ps.println("]");
            }
            ps.println("}");
        }

        protected DefaultMutableTreeNode createTreeNode(boolean includeProjectors)
        {
            DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(this);
            for (Folder f : subfolders)
            {
                dmtn.add(f.createTreeNode(includeProjectors));
            }
            if (includeProjectors)
            {
                for (Projector p : projectors)
                {
                    dmtn.add(new DefaultMutableTreeNode(p));
                }
            }
            return dmtn;
        }

        public boolean removeFolder(Folder fol)
        {
            return subfolders.remove(fol);
        }

        public boolean removeProjector(Projector proj)
        {
            return projectors.remove(proj);
        }

    }
    private Folder root;

    public ProjectorManager()
    {
        root = new Folder("root");
    }

    public Folder getRoot()
    {
        return root;
    }

    public DefaultTreeModel createTreeModel(boolean includeProjectors)
    {
        DefaultTreeModel dtm = new DefaultTreeModel(root.createTreeNode(includeProjectors));
        return dtm;
    }

    public void save(File file) throws IOException
    {
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        root.save(ps);
        ps.flush();
        ps.close();
    }

    public void load(File file) throws IOException
    {
        /*
         * Make sure the root folder is empty again
         */
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        Stack<Folder> folders = new Stack<Folder>();

        while ((line = in.readLine()) != null)
        {
            if (line.endsWith("{"))
            {
                Folder folder = new Folder(line.substring(0, line.length() - 1));
                if (folders.empty())
                {
                    root = folder;
                }
                folders.push(folder);
            } else if (line.equals("["))
            {
                String className = in.readLine();
                String connectPoint = in.readLine();
                String location = in.readLine();

                folders.peek().addProjector(createProjector(className, connectPoint, location));

                // read the ]
                in.readLine();
            } else if (line.equals("}"))
            {
                Folder folder = folders.pop();
                if (!folders.empty())
                {
                    folders.peek().addFolder(folder);
                }
            }
        }
    }

    public Projector createProjector(String className, String connectPoint, String location)
    {
        try
        {
            /*
             * Create the connectPoint
             */
            ConnectionPoint cp = new ConnectionPoint(connectPoint);

            Class<? extends Projector> cl = (Class<? extends Projector>) Class.forName(className);
            Constructor<? extends Projector> constr = cl.getDeclaredConstructor(ConnectionPoint.class);
            Projector proj = constr.newInstance(cp);
            proj.setLocation(location);

            return proj;
        } catch (Exception e)
        {
            Logger.getLogger("ProjMan").error("Cannot create projector", e);
            return null;
        }
    }
}
