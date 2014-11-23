package slh.services;

import slh.services.UserModelResponse.TraitTreeNode;
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
    
    public static void main(String [] args)
    {
    	String content = "Call me Ishmael. Some years ago-never mind how long precisely-having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly November in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand of me, that it requires a strong moral principle to prevent me from deliberately stepping into the street, and methodically knocking people's hats off-then, I account it high time to get to sea as soon as I can. This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself upon his sword; I quietly take to the ship. There is nothing surprising in this. If they but knew it, almost all men in their degree, some time or other, cherish very nearly the same feelings towards the ocean with me.";
    	int [] nums = getCategory(getUserModel(content));
    	//if(nums == null)
    	System.out.println(nums[3]);
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
    public static UserModelResponse getUserModel(String content)
    {
        return getUserModel(new UserModelRequest(content));
    }
    
    /**
     * @param request
     * @return returns null if anything goes wrong.
     */
    public static UserModelResponse getUserModel(UserModelRequest request)
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
            {	//System.out.println(profileString);
                return Util.GSON.fromJson(profileString, UserModelResponse.class);
            }
        }
        catch(Throwable t)
        {
        	t.printStackTrace();
            return null;
        }
        
        return null;
    }
    
    public static int [] getCategory(UserModelResponse user)
    {
    	if(user == null)
    		return null;
    	String[] artistCats = {"Artistic interests", "Imagination","Liberalism", "Curiosity", "Self-expression"};
    	double [] artistRanks = {0.5, 0.5, 0.5, 0.5, 0.5};
    	String [] outdoorCats = {"Adventurousness","Activity level","Friendliness", "Excitement", "Challenge"};
    	double [] outdoorRanks = {0.5, 0.5, 0.5, 0.5, 0.5};
    	String[] intellectualCats = {"Intellect", "Orderliness","Self-efficacy", "Morality", "Self-enhancement", };
    	double [] intellectualRanks = {0.5, 0.5, 0.5, 0.5, 0.5};
    	String[] sociableCats = {"Liberalism", "Cheerfulness", "Trust", "Self-consciousness", "Closeness", "Self-expression"};
    	double [] sociableRanks = {0.5, 0.5, 0.5, 0.5, 0.5};
    	
    	
    	int artistScore = 0;
    	int intellectualScore = 0;
    	int sociableScore = 0;
    	int outdoorsScore = 0;
    	int i = 0,j = 0, k = 0, m = 0;
    	//System.out.println(user);
 
    	TraitTreeNode t = user.tree;
    	for(TraitTreeNode child1 : t.children)
        {
    		if(!child1.isHeading())
    		{
    			for(TraitTreeNode child2 : child1.children)
        		{
        			if(!child2.isHeading())
        			{
        				for(TraitTreeNode child3: child2.children)
            			{
            				if(!child3.isHeading())
            				{
            					for(TraitTreeNode child4 : child3.children)
            					{
            						//System.out.println(child4.name);
            						if(i > 4 || j > 4 || k > 4 || m > 4)
            		        			break;
            		        		if(child4.name.toUpperCase().equals(artistCats[i].toUpperCase()))
            		        		{
            		        			if(child4.percentage > artistRanks[i])
            		       					artistScore++;
            		       				
            		        			//System.out.println(i);
            		       				i++;
            		       				
            		        		}
            		        		if(child4.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
            		        		{
            		        			if(child4.percentage > outdoorRanks[j])
            		       					outdoorsScore++;
            		        						
            		      				j++;
            		      				
            		        		}
            		        		if(child4.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
            		        		{
            		        			if(child4.percentage > intellectualRanks[k])
            		        				intellectualScore++;
            		        						
            		        			k++;
            		        			
            		        		}
            		        		if(child4.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
            		        		{
            		       				if(child4.percentage > sociableRanks[m])
            		       					sociableScore++;
            		        						
            		        			m++;
            		        			
            		       			}
            		        		if(i > 4 || j > 4 || k > 4 || m > 4)
            		        			break;
            					}
            				}
            				else
            				{
            					
            					if(i > 4 || j > 4 || k > 4 || m > 4)
        		        			break;
            					if(child3.name.toUpperCase().equals(artistCats[i].toUpperCase()))
        		        		{
        		        			if(child3.percentage > artistRanks[i])
        		       					artistScore++;
        		       						
        		       				i++;
        		        		}
        		        		if(child3.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
        		        		{
        		        			if(child3.percentage > outdoorRanks[j])
        		       					outdoorsScore++;
        		        						
        		      				j++;
        		      				
        		        		}
        		        		if(child3.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
        		        		{
        		        			if(child3.percentage > intellectualRanks[k])
        		        				intellectualScore++;
        		        						
        		        			k++;
        		  
        		        		}
        		        		if(child3.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
        		        		{
        		       				if(child3.percentage > sociableRanks[m])
        		       					sociableScore++;
        		        						
        		        			m++;
        		       			}
        		        		if(i > 4 || j > 4 || k > 4 || m > 4)
        		        			break;
            				}
            			}
            		}
        			else
        			{
        				if(i > 4 || j > 4 || k > 4 || m > 4)
		        			break;
        				if(child2.name.toUpperCase().equals(artistCats[i].toUpperCase()))
    	        		{
    	        			if(child2.percentage > artistRanks[i])
    	       					artistScore++;
    	       						
    	       				i++;
    	        		}
    	        		if(child2.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
    	        		{
    	        			if(child2.percentage > outdoorRanks[j])
    	       					outdoorsScore++;
    	        						
    	      				j++;
    	        		}
    	        		if(child2.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
    	        		{
    	        			if(child2.percentage > intellectualRanks[k])
    	        				intellectualScore++;
    	        						
    	        			k++;
    	        		}
    	        		if(child2.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
    	        		{
    	       				if(child2.percentage > sociableRanks[m])
    	       					sociableScore++;
    	        				
    	        			m++;
    	       			}
    	        		if(i > 4 || j > 4 || k > 4 || m > 4)
		        			break;
        			}	
        		}
    		
    		}
    		else
    		{
    			
    			if(i > 4 || j > 4 || k > 4 || m > 4)
        			break;
    			if(child1.name.toUpperCase().equals(artistCats[i].toUpperCase()))
        		{
        			if(child1.percentage > artistRanks[i])
       					artistScore++;
       						
       				i++;
        		}
        		if(child1.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
        		{
        			if(child1.percentage > outdoorRanks[j])
       					outdoorsScore++;
        						
      				j++;
        		}
        		if(child1.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
        		{
        			if(child1.percentage > intellectualRanks[k])
        				intellectualScore++;
        						
        			k++;
        		}
        		if(child1.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
        		{
       				if(child1.percentage > sociableRanks[m])
       					sociableScore++;
        				
        			m++;
       			}
        		if(i > 4 || j > 4 || k > 4 || m > 4)
        			break;
    		}

    		//System.out.println(i);
    			
    	}
        System.out.println(m);

	    int [] scores = new int[4];
	    scores[0] = artistScore;
	    scores[1] = outdoorsScore;
	    scores[2] = intellectualScore;
	    scores[3] = sociableScore;
	    return scores;
    
    }
}
