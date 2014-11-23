package slh;

import java.io.IOException;

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
            req.setAttribute("user", "AbrarSyed");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (req.getParameter("email") != null)
            req.getSession().setAttribute("userid", 100);
        
        doGet(req, resp);
    }
}
