package slh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dean.jraw.RedditOAuth2Client;
import slh.services.UserModelResponse;
import slh.services.UserModelResponse.TraitTreeNode;
import slh.services.UserModelService;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

public class Select extends HttpServlet
{
    private static final long serialVersionUID = -790341688987007463L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // ALL THE INFO!!!!
        UserModelResponse watson = UserModelService.getUserModel(new TweetGetter().getAllTweets());
        List<String> traits = Lists.newLinkedList();
        for (TraitTreeNode child : watson.tree.children)
        {
            if (child.isHeading())
            {
                for (TraitTreeNode second : child.children)
                {
                    traits.add(second.name + "  " + (second.percentage*100)+ "%");
                }
            }
            else
            {
                traits.add(child.name + "  " + (child.percentage*100)+ "%");
            }
                
        }
        req.setAttribute("traits", traits);
        
        RedditOAuth2Client reddit = (RedditOAuth2Client) req.getSession().getAttribute("reddit_obj");
        List<String> topics = reddit.getTrendingSubreddits();
        
        req.setAttribute("topics", topics);
        req.setAttribute("ebay", requestItems(Joiner.on(',').join(topics)));
        
        req.getRequestDispatcher("/select.jsp").forward(req, resp);
    }
    
    public String requestItems(String query)
    {
        query  = turnIntoUrl(query);
        
        StringBuilder totalQ = new StringBuilder();
        totalQ.append("http://svcs.ebay.com/services/search/FindingService/v1");;
        totalQ.append("?OPERATION-NAME=findItemsByKeywords");
        totalQ.append("&SECURITY-APPNAME=Mohammad-2aac-45bb-bd22-49867c29d6c6");
        totalQ.append("&RESPONSE-DATA-FORMAT=JSON");
        totalQ.append("&callback=cb_findItemsByKeywords");
        totalQ.append("&REST-PAYLOAD");
        totalQ.append("&keywords=");
        totalQ.append(query);
        totalQ.append("&paginationInput.entriesPerPage=3");
        
        //apply filters
        
        try
        {
            URLConnection connection = new URL(totalQ.toString()).openConnection();
            //connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(response));
            String out = CharStreams.toString(rd);
            rd.close();
            return out;
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    private String turnIntoUrl(String query)
    {
        try{
            return URLEncoder.encode(query.toString(), "UTF-8");
        }
        catch(UnsupportedEncodingException e){
            return "";
        }
    }
}
