package slh.reddit;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dean.jraw.RedditOAuth2Client;
import net.dean.jraw.http.Credentials;
import net.dean.jraw.http.oauth.AuthData;
import net.dean.jraw.http.oauth.OAuthHelper;
import net.dean.jraw.models.LoggedInAccount;
import slh.persist.User;
import slh.persist.UserManager;

public class RedditReply extends HttpServlet
{
    private static final long serialVersionUID = 1150046621799636149L;
    private static final String redirect = "http://slh.mybluemix.net/app/redditauthreply";
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
            AuthData data = authHelper.onUserChallenge("http://slh.mybluemix.net/app/login", redirect, credentials);
            LoggedInAccount me = reddit.onAuthorized(data, credentials);
            
            String username = me.data("name");
            String redditKey = data.getAccessToken();
            UserManager manager = new UserManager();
            User user = manager.create(username, redditKey);
            req.getSession().setAttribute("userid", user.getId());
            req.getSession().setAttribute("reddit", reddit);
            resp.sendRedirect("/welcome");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.sendRedirect("/welcome");
        }
    }
}
