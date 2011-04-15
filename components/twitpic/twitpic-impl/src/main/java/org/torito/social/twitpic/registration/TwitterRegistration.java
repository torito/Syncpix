
package org.torito.social.twitpic.registration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.Factory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * @author torito
 *
 */
public class TwitterRegistration {
	
	/**
	 * The Twitter Factory
	 * filter = (factory.name=org.ow2.chameleon.twitter.twitter4j)
	 */
	Factory ifactory;
	/**
	 * The twitter service component instance.
	 */
	ComponentInstance ti = null;
	
	public void register() throws Exception{
		register(null, null);
	}
	
	public void register(String consumer, String key) throws Exception{
		if (consumer == null && key == null ) {
			//Cilia Adapter keys.
			consumer = "Kcn3Uo4GpBYFL8RpOe2g";
			key = "S3ZbilTct1EB7S47cL6yTTkCgQb9wBB0BRXrSMfr4m4";
		}

		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(consumer, key);

		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(requestToken.getAuthorizationURL()));
			System.out.print("Enter the PIN(if available) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try{
				if(pin.length() > 0){
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				}else{
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if(401 == te.getStatusCode()){
					System.out.println("Unable to get the access token.");
				}else{
					te.printStackTrace();
				}
				return;
			}
		}

		int id = twitter.verifyCredentials().getId();
		String token = accessToken.getToken();
		String tokenSecret = accessToken.getTokenSecret();
		String screenName = accessToken.getScreenName();

		Properties props = new Properties();
		props.put("twitter.consumer-key", consumer);
		props.put("twitter.consumer-key-secret", key);
		props.put("twitter.token", token);
		props.put("twitter.token-secret", tokenSecret);
		props.put("twitter.userId", "" + id);
		props.put("twitter.screenName", screenName);
		System.out.println(props);
		ifactory.createComponentInstance(props);
	}
}
