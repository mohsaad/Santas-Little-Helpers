package slh;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetGetter 
{

	private String userKey = "312215961-VEtIIx88ubWxIif7449KbG3Pp9SbKLpawy4J1rI5";
	private String userSecretKey = "312215961-VEtIIx88ubWxIif7449KbG3Pp9SbKLpawy4J1rI5";
	private ConfigurationBuilder cb;
	
	public TweetGetter()
	{
		cb = authorize(userKey, userSecretKey);
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
	
	public String getAllTweets()
	{
		try
		{
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
		
			List<Status> statuses = twitter.getHomeTimeline();
			return buildQuery(statuses).toString();
		}
		catch (TwitterException te)
		{
			te.printStackTrace();
			return null;
		}
	}
}