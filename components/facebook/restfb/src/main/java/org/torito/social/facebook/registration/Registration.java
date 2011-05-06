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
package org.torito.social.facebook.registration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.torito.social.facebook.FacebookException;
import org.torito.social.facebook.FacebookService;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;




/**
 * @author torito
 *
 */

public class Registration {
	/**
	 * SyncPix api key
	 */
	private final static String API_KEY = "dd8dcf6a439076c09536aef8a53fa2ea";
	/**
	 * SyncPix api secret key
	 * WARNING!!! this secret api must be obtained using a service Web.
	 */
	private final static String API_SECRET = "dfa1bcb6b48a56263434404aed54ac50";
	/**
	 * Facebook service factory.
	 */
	Factory fbFactory;
	
	FacebookService facebook;
	/**
	 * Create a facebook session and register the application.
	 */
	public void register() {
		String sessionID = null;
		String token = null;
		String secret = null;
		//embed a browser to obtain the token ¬¬
		FacebookClient client = new DefaultFacebookClient(token);
	}
	/**
	 * 
	 * @param message
	 */
	public void status(String message){
		if (facebook == null) {
			System.err.println("There is any Facebook service available");
		} else {
			try {
				facebook.setStatus(message);
			} catch (FacebookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createInstannce(String apikey, String secret, String sessionID) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
		Properties props = new Properties();
		props.put("facebook.token", apikey);
		fbFactory.createComponentInstance(props);
	}
}
