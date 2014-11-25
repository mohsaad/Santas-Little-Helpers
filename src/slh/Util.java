package slh;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util
{
    public final static Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    public static final HashMap<Integer, User> userTable = Maps.newHashMap();
    private static int userId = 1;
    
    public static int getNextId()
    {
        int out = userId;
        userId++;
        return out;
    }
}
