import java.util.*;
import java.io.*;
import java.net.*;
import twitter4j.*;
import twitter4j.conf.*;

public class TweetGetter 
{

	private String userKey;
	private String userSecretKey;
	private ConfigurationBuilder cb;
	
	
	public TweetGetter(String privKey, String privKeySecret)
	{
		userKey = privKey;
		userSecretKey = privKeySecret;
		cb = authorize(userKey, userSecretKey);
	}
	
	
	public static void main(String[] args) 
	{
		TweetGetter tg = new TweetGetter(args[0], args[1]);
		StringBuilder toQuery = tg.getAllTweets();
		System.out.println(toQuery);
	}
	
	private StringBuilder buildQuery(List<Status> statuses)
	{
		StringBuilder totalQ = new StringBuilder();
		for(Status status: statuses)
		{
			totalQ.append(status.getText());
			totalQ.append("\n");
		}
		return totalQ;
	}
	
	private ConfigurationBuilder authorize(String uKey, String uKeySec)
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey("BWynK8v9BAJpuu1DlemrLNXVJ")
			.setOAuthConsumerSecret("M3zLXqZmGknGHjB13hTeMThufbzYH0yX4pAHo40Kly8nmYztLV")
			.setOAuthAccessToken(uKey)
			.setOAuthAccessTokenSecret(uKeySec);
		return cb;
	}
	
	public StringBuilder getAllTweets()
	{
		try
		{
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
		
			List<Status> statuses = twitter.getHomeTimeline();
			return buildQuery(statuses);
		}
		catch (TwitterException te)
		{
			te.printStackTrace();
			System.out.println("Failed");
			System.exit(-1);
			return new StringBuilder();
		}
	}


}