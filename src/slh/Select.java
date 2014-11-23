package slh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dean.jraw.RedditOAuth2Client;
import net.dean.jraw.models.LoggedInAccount;
import slh.services.UserModelResponse;
import slh.services.UserModelService;

public class Select extends HttpServlet
{
    private static final long serialVersionUID = -790341688987007463L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // ALL THE INFO!!!!
        UserModelResponse watson = UserModelService.getUserModel(new TweetGetter().getAllTweets());
        req.setAttribute("traits", watson);
        
        RedditOAuth2Client reddit = (RedditOAuth2Client) req.getSession().getAttribute("reddit_obj");
        
        req.setAttribute("ebay", requestItems());
        
        
        super.doGet(req, resp);
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
            
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null)
            {
                sb.append(line);
            }
            rd.close();
            return sb.toString();
            
            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            return;
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
