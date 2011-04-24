package org.torito.social.twitpic.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.torito.social.twitpic.CommentsInfo;
import org.torito.social.twitpic.MediaInfo;
import org.torito.social.twitpic.TwitPicService;
import org.torito.social.twitpic.UserInfo;

import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.conf.PropertyConfiguration;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;
import twitter4j.util.ImageUpload;

/**
 * @author torito
 *
 */
public class TwitPicServiceImpl implements TwitPicService{

	private String apikey;
	private String screenName;
	private String token;
	private String tokenSecret;
	private String consumerKey;
	private String consumerKeySecret;
	private String userId;
	private ImageUpload upload;


	protected void start() {
		System.out.println("[Twitpic] - Authenticating " + screenName);
		Properties props = new Properties();
		props.setProperty(PropertyConfiguration.OAUTH_CONSUMER_KEY, consumerKey);
		props.setProperty(PropertyConfiguration.OAUTH_CONSUMER_SECRET, consumerKeySecret);

		props.setProperty(PropertyConfiguration.OAUTH_ACCESS_TOKEN, token);
		props.setProperty(PropertyConfiguration.OAUTH_ACCESS_TOKEN_SECRET, tokenSecret);
		props.setProperty(PropertyConfiguration.USER, screenName);
		AccessToken authToken = new AccessToken(token, tokenSecret);
		PropertyConfiguration conf = new PropertyConfiguration(props);
		// Enable the OAuth authentification.
		OAuthAuthorization authorization = new OAuthAuthorization(conf, consumerKey, consumerKeySecret, authToken);
		upload = ImageUpload.getTwitpicUploader(apikey, authorization);
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#uploadMedia(java.io.File, java.lang.String)
	 */

	private void stop (){}
	
	public MediaInfo uploadMedia(File file) {
		return uploadMedia(file, null);
	}

	public MediaInfo uploadMedia(File file, String message) {
		String imageurl = null;
		try {
			if (message != null) {
				imageurl = upload.upload(file, message);
			} else {
				imageurl = upload.upload(file);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		MediaInfo mi = new MediaInfo();
		mi.setMessage(message);
		mi.setLocation(imageurl);
		return mi;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#showMedia(java.lang.String)
	 */
	public MediaInfo showMedia(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#showUser(java.lang.String)
	 */
	public UserInfo showUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#showComments(java.lang.String, int)
	 */
	public CommentsInfo showComments(String id, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#createComment(java.lang.String, java.lang.String)
	 */
	public CommentsInfo createComment(String id, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#deleteComment(java.lang.String)
	 */
	public void deleteComment(String id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#uploadMedia(java.io.InputStream)
	 */
	public MediaInfo uploadMedia(InputStream is) {
		return uploadMedia(is, null);
	}

	/* (non-Javadoc)
	 * @see org.torito.social.twitpic.TwitPicService#uploadMedia(java.io.InputStream, java.lang.String)
	 */
	public MediaInfo uploadMedia(InputStream file, String message) {
		String imageurl = null;
		try {
			if (message != null) {
				imageurl = upload.upload("syncpix.jpg", file, message);
			} else {
				imageurl = upload.upload(null,file);
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		MediaInfo mi = new MediaInfo();
		mi.setMessage(message);
		mi.setLocation(imageurl);
		return mi;
	}


}