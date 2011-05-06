/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.torito.social.facebook.impl;

import java.io.File;

import org.json.JSONObject;
import org.torito.social.facebook.FacebookException;
import org.torito.social.facebook.FacebookService;

import com.google.code.facebookapi.FacebookJsonRestClient;

/**
 * @author torito
 *
 */
public class FacebookServiceImpl  implements FacebookService{
    private  String apiKey ;
    private  String secret ;
    private  String sessionId ;
    /**
     * 
     */
    private FacebookJsonRestClient client;
    /**
     * 
     * @param ak
     * @param as
     * @param sid
     * @param tk
     */
	public FacebookServiceImpl(String ak, String as, String sid, String tk){
		 client = new FacebookJsonRestClient(ak, as, sid);
	}
	
	private void start(){
		 client = new FacebookJsonRestClient(apiKey,secret,  sessionId);
	}
	/* (non-Javadoc)
	 * @see org.torito.social.facebook.FacebookMedia#createAlbum(java.lang.String, java.lang.String, java.lang.String)
	 */
	public long createAlbum(String name, String description, String location)
			throws FacebookException {
		long aid = 0;
		try {
			JSONObject nalbum = client.photos_createAlbum(name, description, location);
			String sid = String.valueOf(nalbum.get("aid"));
			aid = Long.parseLong(( sid.split("_")[1]));
		} catch (Exception e) {
			e.printStackTrace();
			throw new FacebookException(e.getMessage());
		}
		return aid;
	}
	/**
	 * 
	 * @param photo
	 * @param caption
	 * @param albumId
	 */
	public void upload(String photo, String caption, String albumId){
		try {
			upload(new File(photo), caption, albumId);
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param photo
	 * @param caption
	 * @param albumId
	 */
	public void upload(File photo, String caption, String albumId)
			throws FacebookException {
		try {
			client.photos_upload(photo, caption, albumId);
		} catch (com.google.code.facebookapi.FacebookException e) {
			e.printStackTrace();
			throw new FacebookException(e.getMessage());
		}
		
	}

	/* (non-Javadoc)
	 * @see org.torito.social.facebook.FacebookService#setStatus(java.lang.String)
	 */
	public void setStatus(String status)
			throws FacebookException {
		try {
			client.users_setStatus(status);
		} catch (com.google.code.facebookapi.FacebookException e) {
			e.printStackTrace();
			throw new FacebookException(e.getMessage());
		}
	}
}
