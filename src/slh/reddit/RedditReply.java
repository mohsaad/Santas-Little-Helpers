package slh.reddit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import slh.OAuthReplyServlet;

public class RedditReply extends OAuthReplyServlet
{
    private static final long serialVersionUID = 1L;
    protected static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
    protected static final String OAUTH_AUTH_URL = "https://ssl.reddit.com/api/v1/authorize";
    protected static final String OAUTH_TOKEN_URL = "https://ssl.reddit.com/api/v1/access_token";
    protected static final String clientid = "yUkDIAYOnlYlNg";
    protected static final String secret = "2tRSAPMd4VELiY1YAR-W8JLJbbo";
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
    
    
}
