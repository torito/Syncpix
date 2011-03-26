package org.torito.social.twitpic;

import java.io.File;


/**
 * @author torito
 *
 */
public interface TwitPicService {

	public MediaInfo uploadMedia(File fileuri);
	
	public MediaInfo uploadMedia(File file, String message);

	/**
	 * This method returns information on a specified image or video.
	 * If the requested media is a video, the API response will contain video information in the 'video' item. 
	 * @param id The id of the image
	 * @return the media information.
	 */
	public MediaInfo showMedia(String id);

	/**
	 * This method returns information on a specified user.
	 * @param id
	 * @return
	 */
	public UserInfo showUser(String id);
	
	/**
	 * This method returns comments for the specified image. 
	 * @param id
	 * @param page
	 * @return
	 */
	public CommentsInfo showComments(String id, int page);
	/**
	 * This method posts a comment for the specified photo.
	 * @param id
	 * @param message
	 * @return
	 */
	public CommentsInfo createComment(String id, String message);
	/**
	 * This method deletes the specified comment.
	 * @param id
	 */
	public void deleteComment(String id);
	
}