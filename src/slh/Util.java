package slh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util
{
    public final static Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
}
