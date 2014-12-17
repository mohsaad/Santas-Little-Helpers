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
import slh.services.usermodel.UserModelResponse;
import slh.services.usermodel.UserModelService;
import slh.services.usermodel.UserModelResponse.TraitTreeNode;

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
        UserModelResponse watson = UserModelService.getUserModel("Another one got caught today, it's all over the papers. \"TeenagerArrested in Computer Crime Scandal\", \"Hacker Arrested after Bank Tampering\"... Damn kids. They're all alike. But did you, in your three-piece psychology and 1950's technobrain,ever take a look behind the eyes of the hacker? Did you ever wonder whatmade him tick, what forces shaped him, what may have molded him? I am a hacker, enter my world... Mine is a world that begins with school... I'm smarter than most ofthe other kids, this crap they teach us bores me... Damn underachiever. They're all alike. I'm in junior high or high school. I've listened to teachers explainfor the fifteenth time how to reduce a fraction. I understand it. \"No, Ms.Smith, I didn't show my work. I did it in my head...\" Damn kid. Probably copied it. They're all alike. I made a discovery today. I found a computer. Wait a second, this iscool. It does what I want it to. If it makes a mistake, it's because Iscrewed it up. Not because it doesn't like me... Or feels threatened by me... Or thinks I'm a smart ass... Or doesn't like teaching and shouldn't be here... Damn kid. All he does is play games. They're all alike. And then it happened... a door opened to a world... rushing throughthe phone line like heroin through an addict's veins, an electronic pulse issent out, a refuge from the day-to-day incompetencies is sought... a board isfound. \"This is it... this is where I belong...\" I know everyone here... even if I've never met them, never talked tothem, may never hear from them again... I know you all... Damn kid. Tying up the phone line again. They're all alike... You bet your ass we're all alike... we've been spoon-fed baby food atschool when we hungered for steak... the bits of meat that you did let slipthrough were pre-chewed and tasteless. We've been dominated by sadists, orignored by the apathetic. The few that had something to teach found us will-ing pupils, but those few are like drops of water in the desert. This is our world now... the world of the electron and the switch, thebeauty of the baud. We make use of a service already existing without payingfor what could be dirt-cheap if it wasn't run by profiteering gluttons, andyou call us criminals. We explore... and you call us criminals. We seekafter knowledge... and you call us criminals. We exist without skin color,without nationality, without religious bias... and you call us criminals.You build atomic bombs, you wage wars, you murder, cheat, and lie to usand try to make us believe it's for our own good, yet we're the criminals. Yes, I am a criminal. My crime is that of curiosity. My crime isthat of judging people by what they say and think, not what they look like.My crime is that of outsmarting you, something that you will never forgive mefor. I am a hacker, and this is my manifesto. You may stop this individual,but you can't stop us all... after all, we're all alike.");
        List<String> traits = Lists.newLinkedList();
        if (watson != null && watson.tree != null)
        {
            for (TraitTreeNode child : watson.tree.children)
            {
                if (!child.isNotHeading())
                {
                    for (TraitTreeNode second : child.children)
                    {
                        traits.add(second.name + "  " + (second.percentage * 100) + "%");
                    }
                }
                else
                {
                    traits.add(child.name + "  " + (child.percentage * 100) + "%");
                }

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
