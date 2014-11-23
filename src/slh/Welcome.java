package slh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import slh.persist.User;
import slh.persist.UserManager;

public class Welcome extends HttpServlet
{
    private static final long serialVersionUID = -393800861225499483L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // check signout
        if (!req.isRequestedSessionIdValid() || req.getParameter("signout") != null)
        {
           req.getSession().removeAttribute("userid");
           resp.sendRedirect("/app/welcome");
           return;
        }
        
        req.setAttribute("userid", req.getSession().getAttribute("userid"));
        req.setAttribute("signout", req.getParameter("signout"));
        
        if (req.getSession().getAttribute("userid") == null)
        {
            req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
        }
        else
        {
            UserManager manager = new UserManager();
            User user = manager.get((long) req.getSession().getAttribute("userid"));
            req.setAttribute("user", user.getName());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
