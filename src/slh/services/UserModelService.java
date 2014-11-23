package slh.services;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import slh.Util;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class UserModelService
{
    // in case its local
    private static final String serviceName = "user_modeling";
    private static String       baseURL     = "https://gateway.watsonplatform.net/systemu/service/";
    private static String       username    = "345d932d-8950-4ead-a69f-0cf8e2e2532a";
    private static String       password    = "9lJcUZd7fsk1";

    // init
    static
    {
        getCloudAuthData();
    }

    /**
     * Get authorization from the cloud!
     */
    private static void getCloudAuthData()
    {
        JSONObject sysEnv = null;
        try
        {
            String envServices = System.getenv("VCAP_SERVICES");
            if (envServices == null)
                return;
            sysEnv = JSONObject.parse(envServices);
        }
        catch (IOException e)
        {
            return;
        }

        for (Object key : sysEnv.keySet())
        {
            String keyString = (String) key;
            if (keyString.startsWith(serviceName))
            {
                JSONArray services = (JSONArray) sysEnv.get(key);
                JSONObject service = (JSONObject) services.get(0);
                JSONObject credentials = (JSONObject) service.get("credentials");
                baseURL = (String) credentials.get("url");
                username = (String) credentials.get("username");
                password = (String) credentials.get("password");
            }
        }
    }
    
    /**
     * @param content
     * @return returns null if anything goes wrong.
     */
    public static UserModelResponse[] getUserModel(String content)
    {
        return getUserModel(new UserModelRequest(content));
    }
    
    /**
     * @param request
     * @return returns null if anything goes wrong.
     */
    public static UserModelResponse[] getUserModel(UserModelRequest request)
    {
        try
        {
            Executor executor = Executor.newInstance().auth(username, password);
            URI profileURI = new URI(baseURL + "api/v2/profile").normalize();

            Request profileRequest = Request.Post(profileURI)
                    .addHeader("Accept", "application/json")
                    .bodyString(Util.GSON.toJson(request), ContentType.APPLICATION_JSON);
            
            String profileString = executor.execute(profileRequest).handleResponse(new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse r) throws ClientProtocolException, IOException
                {
                    if (r.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                        return EntityUtils.toString(r.getEntity());
                    else
                        return null;
                }

            });

            if (profileString != null)
            {
                return Util.GSON.fromJson(profileString, UserModelResponse[].class);
            }
        }
        catch(Throwable t)
        {
            return null;
        }
        
        return null;
    }
}
