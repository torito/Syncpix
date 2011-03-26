package org.torito.social.twitpic.impl;

/**
 * @author torito
 * This class is based on the twitter registration class
 * for the OW2 Chameleon twitter service. 
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * Simple tool generating configuration for the twitter4j implementation
 * of the twitpic service.
 * This tool manage the OAuth.
 * It gives to the user the authorization url and process the PIN code
 * to retrieve the token and tokenSecret.
 * The tool's output is both in the console and files: a metadata file
 * containing the iPOJO instance and a 'cfg' file.
 */
public class Registration {

	/**
	 * Entry Point.
	 * @param args not used.
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		String consumerKey = System.getProperty("consumerKey");
		String consumerSecret = System.getProperty("consumerSecret");
		String apikey = System.getProperty("apikey");
		 if (consumerKey == null && consumerSecret == null && apikey == null) {
			 System.err.println("Using internal consumer data");
			 //Cilia Adapter keys.
			 consumerKey = "Kcn3Uo4GpBYFL8RpOe2g";
 			 consumerSecret = "S3ZbilTct1EB7S47cL6yTTkCgQb9wBB0BRXrSMfr4m4";
 			 //syncpix apikey
 			 apikey = "15feb6b22e6bc58cc7b180b9b55996db";
		 } else {
			 usage();
			 return;
		 } 

		// The factory instance is re-useable and thread safe.
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);

		RequestToken requestToken = twitter.getOAuthRequestToken();
	    AccessToken accessToken = null;
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    while (null == accessToken) {
	      System.out.println("Open the following URL and grant access to your account:");
	      System.out.println(requestToken.getAuthorizationURL());
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
	      }
	    }

	    int id = twitter.verifyCredentials().getId();
	    String token = accessToken.getToken();
	    String tokenSecret = accessToken.getTokenSecret();
	    String screenName = accessToken.getScreenName();

	    System.out.println("id : " + id);
	    System.out.println("token : " + token);
	    System.out.println("token secret : " + tokenSecret);
	    System.out.println("screen name : " + screenName);

	    createXMLInstance(consumerKey, consumerSecret, token, tokenSecret, id, screenName, apikey);
	    createCFGInstance(consumerKey, consumerSecret, token, tokenSecret, id, screenName, apikey);

	    System.exit(0);
	}

	/**
	 * Creates the CFG file to create an iPOJO instance.
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param token the token
	 * @param tokenSecret the token secret
	 * @param id the user id
	 * @param screenName the screen name
	 * @throws FileNotFoundException cannot happen
	 * @throws IOException if the CFG file cannot be created.
	 */
	private static void createCFGInstance(String consumerKey,
			String consumerSecret, String token, String tokenSecret, int id,
			String screenName, String apikey) throws FileNotFoundException, IOException {
		File file = new File("org.torito.social.twitpic-" + screenName + ".cfg");
		file.createNewFile();
		Properties props = new Properties();
		props.put("twitter.consumer-key", consumerKey);
		props.put("twitter.consumer-key-secret", consumerSecret);
		props.put("twitter.token", token);
		props.put("twitter.token-secret", tokenSecret);
		props.put("twitter.userId", "" + id);
		props.put("twitter.screenName", screenName);
		props.put("twitpic.apikey", apikey);
		props.store(new FileOutputStream(file), "Twitter configuration for " + screenName);
		System.out.println(file.getName() + " created");
	}

	/**
	 * Creates the XML file to create an iPOJO instance.
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param token the token
	 * @param tokenSecret the token secret
	 * @param id the user id
	 * @param screenName the screen name
	 * @throws IOException if the metadata file cannot be created.
	 */
	private static void createXMLInstance(String consumerKey,
			String consumerSecret, String token, String tokenSecret, int id,
			String screenName, String apikey) throws IOException {
		File metadata = new File("metadata.xml");
		FileWriter writer = new FileWriter(metadata);
		writer.append("<ipojo>\n");
		writer.append("  <instance component=\"" + "org.torito.social.twitpic" + "\">\n");
		writer.append("    <property name=\"twitter.consumer-key\" value=\"" + consumerKey + "\"/>\n");
		writer.append("    <property name=\"twitter.consumer-key-secret\" value=\"" + consumerSecret + "\"/>\n");
		writer.append("    <property name=\"twitter.token\" value=\"" + token + "\"/>\n");
		writer.append("    <property name=\"twitter.token-secret\" value=\"" + tokenSecret + "\"/>\n");
		writer.append("    <property name=\"twitter.userId\" value=\"" + id + "\"/>\n");
		writer.append("    <property name=\"twitter.screenName\" value=\"" + screenName + "\"/>\n");
		writer.append("    <property name=\"twitpic.apikey\" value=\"" + apikey + "\"/>\n");
		writer.append("  </instance>\n");
		writer.append("</ipojo>\n");
		writer.flush();
		writer.close();
		System.out.println(metadata.getName() + " created");
	}

	/**
	 * Prints the usage.
	 */
	private static void usage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("java -cp twitpic-impl-${VERSION}.jar " +
				"-DconsumerKey=xxx -DconsumerSecret=xxx  -DapiKey=xxx" +
				"org.torito.social.twitpic.impl.Registration");
		System.out.println(buffer);
	}

}
