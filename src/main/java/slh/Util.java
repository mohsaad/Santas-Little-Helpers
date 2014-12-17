package slh;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util
{
    public final static Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static Properties properties;
    
    public static final HashMap<Integer, User> userTable = Maps.newHashMap();
    private static int userId = 1;
    
    public static int getNextId()
    {
        int out = userId;
        userId++;
        return out;
    }
    
    public static Properties getProperties()
    {
        if (properties == null)
        {
            properties = new Properties();
            
            URL url = Resources.getResource("credentials.properties");
            
            if (url == null)
                throw new RuntimeException("you forgot to put the credentials.properties in src/main/java");
            
            try (Reader reader = Resources.asCharSource(url, Charsets.UTF_8).openStream();)
            {
                properties.load(reader);
            }
            catch (IOException e)
            {
                // uh... yeah...
                Throwables.propagate(e);
            }
            
        }
        
        return properties;
    }
}
