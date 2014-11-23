package slh.persist;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

public class UserManager
{

    private UserTransaction utx;
    private EntityManager   em;

    public UserManager()
    {
        utx = getUserTransaction();
        em = getEm();
    }

    public User create(String username, String redditKey)
    {
        User user = new User();
        user.setName(username);
        user.setRedditKey(redditKey);

        // write
        try
        {
            utx.begin();
            em.persist(user);
            utx.commit();
            return user;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE)
                {
                    utx.rollback();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    public void delete(long id)
    {
        try
        {
            utx.begin();
            User user = em.find(User.class, id);
            if (user != null)
            {
                em.remove(user);
                utx.commit();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE)
                {
                    utx.rollback();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void update(User user)
    {
        if (user == null)
            return;
        
        try
        {
            utx.begin();
            em.merge(user);
            utx.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE)
                {
                    utx.rollback();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public User get(long id)
    {
        try
        {
            utx.begin();
            User user = em.find(User.class, id);
            if (user != null)
            {
                return user;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (utx.getStatus() == javax.transaction.Status.STATUS_ACTIVE)
                {
                    utx.rollback();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    private UserTransaction getUserTransaction()
    {
        InitialContext ic;
        try
        {
            ic = new InitialContext();
            return (UserTransaction) ic.lookup("java:comp/UserTransaction");
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // There are two ways of obtaining the connection information for some services in Java 

    // Method 1: Auto-configuration and JNDI
    // The Liberty buildpack automatically generates server.xml configuration 
    // stanzas for the SQL Database service which contain the credentials needed to 
    // connect to the service. The buildpack generates a JNDI name following  
    // the convention of "jdbc/<service_name>" where the <service_name> is the 
    // name of the bound service. 
    // Below we'll do a JNDI lookup for the EntityManager whose persistence 
    // context is defined in web.xml. It references a persistence unit defined 
    // in persistence.xml. In these XML files you'll see the "jdbc/<service name>"
    // JNDI name used.

    private EntityManager getEm()
    {
        InitialContext ic;
        try
        {
            ic = new InitialContext();
            return (EntityManager) ic.lookup("java:comp/env/slh-user/entitymanager");
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // Method 2: Parsing VCAP_SERVICES environment variable
    // The VCAP_SERVICES environment variable contains all the credentials of 
    // services bound to this application. You can parse it to obtain the information 
    // needed to connect to the SQL Database service. SQL Database is a service
    // that the Liberty buildpack auto-configures as described above, so parsing
    // VCAP_SERVICES is not a best practice.

    // see HelloResource.getInformation() for an example

}
