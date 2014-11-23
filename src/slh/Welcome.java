package slh;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Welcome extends HttpServlet
{
    private static final long serialVersionUID = -393800861225499483L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // check signout
        if (!req.isRequestedSessionIdValid() || req.getAttribute("signout") != null)
            req.getSession().removeAttribute("userid");
        
        Integer id  = (Integer) req.getSession().getAttribute("userid");
        if (id == null)
            req.setAttribute("test", "need to log in");
        else
            req.setAttribute("test", "LOGGED AS "+ id);
        
        req.getRequestDispatcher("/test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (req.getParameter("clear") != null)
            req.getSession().removeAttribute("userid");
        else
            req.getSession().setAttribute("userid", 100);
        
        doGet(req, resp);
    }
}
