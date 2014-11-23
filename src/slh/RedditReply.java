package slh;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dean.jraw.RedditOAuth2Client;
import net.dean.jraw.http.Credentials;
import net.dean.jraw.http.oauth.AuthData;
import net.dean.jraw.http.oauth.OAuthHelper;
import net.dean.jraw.models.LoggedInAccount;

public class RedditReply extends HttpServlet
{
    private static final long serialVersionUID = 1150046621799636149L;
    private static final String redirect = "http://slh.mybluemix.net/redditauthreply";
    private static final Credentials credentials = Credentials.webapp(null, null, "yUkDIAYOnlYlNg", "2tRSAPMd4VELiY1YAR-W8JLJbbo");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        RedditOAuth2Client reddit = new RedditOAuth2Client("slh/1.0");
        OAuthHelper authHelper = reddit.getOAuthHelper();
        
        if (req.getParameter("loginReddit") != null)
        {
            try
            {
                String promptUrl = authHelper.getAuthorizationUrl(credentials.getClientId(), redirect, true, "history", "mysubreddits", "identity", "read");
                
                req.getSession().setAttribute("reddit_obj", reddit);
                req.getSession().setAttribute("reddit_auth_helper", authHelper);
                
                resp.sendRedirect(promptUrl);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return;
        }

        // Retrieve the access token
        try
        {
            // get it again
            reddit = (RedditOAuth2Client) req.getSession().getAttribute("reddit_obj");
            authHelper = (OAuthHelper) req.getSession().getAttribute("reddit_auth_helper");
            AuthData data = authHelper.onUserChallenge(req.getRequestURL().toString() + "?" + req.getQueryString(), redirect, credentials);
            LoggedInAccount me = reddit.onAuthorized(data, credentials);
            
            User user = new User();
            user.id = Util.getNextId();
            user.redditKey = data.getAccessToken();
            user.name = me.data("name");
            Util.userTable.put((int) user.id, user);
            
            req.getSession().setAttribute("userid", user.id);
            req.getSession().setAttribute("reddit_auth_data", data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        resp.sendRedirect("welcome");
    }
}
