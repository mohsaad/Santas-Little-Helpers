package slh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        super.doGet(req, resp);
    }
}
