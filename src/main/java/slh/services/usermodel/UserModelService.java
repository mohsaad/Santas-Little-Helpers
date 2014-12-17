package slh.services.usermodel;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import slh.Util;
import slh.services.usermodel.UserModelResponse.TraitTreeNode;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserModelService
{
    // in case its local
    private static final String serviceName = "user_modeling";
    private static String       baseURL, username, password;

    // init
    static
    {
        //get proeprty auth data
        Properties props = Util.getProperties();
        baseURL = props.getProperty(serviceName + ".url");
        username = props.getProperty(serviceName + ".username");
        password = props.getProperty(serviceName + ".password");

        // THEN get the VCAP data...
        getCloudAuthData();
    }

    /**
     * Get authorization from the cloud!
     */
    private static void getCloudAuthData()
    {
        JsonObject sysEnv = null;
        String envServices = System.getenv("VCAP_SERVICES");
        if (envServices == null)
            return;
        sysEnv = (new JsonParser()).parse(envServices).getAsJsonObject();

        if (sysEnv.has(serviceName))
        {
            JsonObject creds = sysEnv.get(serviceName).getAsJsonArray().get(0).getAsJsonObject().get("credentials").getAsJsonObject();
            baseURL = creds.get("url").getAsString();
            username = creds.get("username").getAsString();
            password = creds.get("password").getAsString();
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
        catch (Throwable t)
        {
            t.printStackTrace();
            return null;
        }

        return null;
    }

    public static int[] getCategory(UserModelResponse user)
    {
        if (user == null)
            return null;
        String[] artistCats = { "Artistic interests", "Imagination", "Liberalism", "Curiosity", "Self-expression" };
        double[] artistRanks = { 0.5, 0.5, 0.5, 0.5, 0.5 };
        String[] outdoorCats = { "Adventurousness", "Activity level", "Friendliness", "Excitement", "Challenge" };
        double[] outdoorRanks = { 0.5, 0.5, 0.5, 0.5, 0.5 };
        String[] intellectualCats = { "Intellect", "Orderliness", "Self-efficacy", "Morality", "Self-enhancement", };
        double[] intellectualRanks = { 0.5, 0.5, 0.5, 0.5, 0.5 };
        String[] sociableCats = { "Liberalism", "Cheerfulness", "Trust", "Self-consciousness", "Closeness", "Self-expression" };
        double[] sociableRanks = { 0.5, 0.5, 0.5, 0.5, 0.5 };

        int artistScore = 0;
        int intellectualScore = 0;
        int sociableScore = 0;
        int outdoorsScore = 0;
        int i = 0, j = 0, k = 0, m = 0;
        //System.out.println(user);

        TraitTreeNode t = user.tree;
        for (TraitTreeNode child1 : t.children)
        {
            if (!child1.isNotHeading())
            {
                for (TraitTreeNode child2 : child1.children)
                {
                    if (!child2.isNotHeading())
                    {
                        for (TraitTreeNode child3 : child2.children)
                        {
                            if (!child3.isNotHeading())
                            {
                                for (TraitTreeNode child4 : child3.children)
                                {
                                    //System.out.println(child4.name);
                                    if (i > 4 || j > 4 || k > 4 || m > 4)
                                        break;
                                    if (child4.name.toUpperCase().equals(artistCats[i].toUpperCase()))
                                    {
                                        if (child4.percentage > artistRanks[i])
                                            artistScore++;

                                        //System.out.println(i);
                                        i++;

                                    }
                                    if (child4.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
                                    {
                                        if (child4.percentage > outdoorRanks[j])
                                            outdoorsScore++;

                                        j++;

                                    }
                                    if (child4.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
                                    {
                                        if (child4.percentage > intellectualRanks[k])
                                            intellectualScore++;

                                        k++;

                                    }
                                    if (child4.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
                                    {
                                        if (child4.percentage > sociableRanks[m])
                                            sociableScore++;

                                        m++;

                                    }
                                    if (i > 4 || j > 4 || k > 4 || m > 4)
                                        break;
                                }
                            }
                            else
                            {

                                if (i > 4 || j > 4 || k > 4 || m > 4)
                                    break;
                                if (child3.name.toUpperCase().equals(artistCats[i].toUpperCase()))
                                {
                                    if (child3.percentage > artistRanks[i])
                                        artistScore++;

                                    i++;
                                }
                                if (child3.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
                                {
                                    if (child3.percentage > outdoorRanks[j])
                                        outdoorsScore++;

                                    j++;

                                }
                                if (child3.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
                                {
                                    if (child3.percentage > intellectualRanks[k])
                                        intellectualScore++;

                                    k++;

                                }
                                if (child3.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
                                {
                                    if (child3.percentage > sociableRanks[m])
                                        sociableScore++;

                                    m++;
                                }
                                if (i > 4 || j > 4 || k > 4 || m > 4)
                                    break;
                            }
                        }
                    }
                    else
                    {
                        if (i > 4 || j > 4 || k > 4 || m > 4)
                            break;
                        if (child2.name.toUpperCase().equals(artistCats[i].toUpperCase()))
                        {
                            if (child2.percentage > artistRanks[i])
                                artistScore++;

                            i++;
                        }
                        if (child2.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
                        {
                            if (child2.percentage > outdoorRanks[j])
                                outdoorsScore++;

                            j++;
                        }
                        if (child2.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
                        {
                            if (child2.percentage > intellectualRanks[k])
                                intellectualScore++;

                            k++;
                        }
                        if (child2.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
                        {
                            if (child2.percentage > sociableRanks[m])
                                sociableScore++;

                            m++;
                        }
                        if (i > 4 || j > 4 || k > 4 || m > 4)
                            break;
                    }
                }

            }
            else
            {

                if (i > 4 || j > 4 || k > 4 || m > 4)
                    break;
                if (child1.name.toUpperCase().equals(artistCats[i].toUpperCase()))
                {
                    if (child1.percentage > artistRanks[i])
                        artistScore++;

                    i++;
                }
                if (child1.name.toUpperCase().equals(outdoorCats[j].toUpperCase()))
                {
                    if (child1.percentage > outdoorRanks[j])
                        outdoorsScore++;

                    j++;
                }
                if (child1.name.toUpperCase().equals(intellectualCats[k].toUpperCase()))
                {
                    if (child1.percentage > intellectualRanks[k])
                        intellectualScore++;

                    k++;
                }
                if (child1.name.toUpperCase().equals(sociableCats[m].toUpperCase()))
                {
                    if (child1.percentage > sociableRanks[m])
                        sociableScore++;

                    m++;
                }
                if (i > 4 || j > 4 || k > 4 || m > 4)
                    break;
            }

            //System.out.println(i);

        }
        System.out.println(m);

        int[] scores = new int[4];
        scores[0] = artistScore;
        scores[1] = outdoorsScore;
        scores[2] = intellectualScore;
        scores[3] = sociableScore;
        return scores;

    }
}
