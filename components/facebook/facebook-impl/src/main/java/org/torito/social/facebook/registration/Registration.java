/*
 * Copyright Adele Team LIG
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

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;

import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.Permission;



/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project Team</a>
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

	private final static String FB_URL = "http://api.new.facebook.com/login.php?api_key=";
	/**
	 * Facebook service factory.
	 */
	Factory fbFactory;

	ComponentInstance facebook = null;
	/**
	 * Create a facebook session and register the application.
	 */
	public void register() {
		String sessionID = null;
		String token = null;
		if (facebook != null) {
			facebook.dispose();
			facebook = null;
		}
		FacebookJsonRestClient client = new FacebookJsonRestClient(API_KEY,API_SECRET );
		try{
			token = client.auth_createToken();
			String url =  FB_URL + API_KEY + "&auth_token=" + token + "&v=1.0";
			//open the browser to grant permission.
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.err.println("Press enter when adding permissions");
			br.readLine();
			sessionID = client.auth_getSession( token );
			//if user has no permission to publish streams (status, photos, etc), ask for it in a browser.
			if (!client.users_hasAppPermission(Permission.PUBLISH_STREAM)) {
				url = "http://www.facebook.com/authorize.php?api_key=" + API_KEY + "&v=1.0&ext_perm=publish_stream";
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				System.err.println("Press enter to continue");
				br.readLine();
			}

		} catch (Exception e){
			e.printStackTrace();
			System.err.println("Unable to create Session");
			return;
		}
		try {
			createInstannce(API_KEY, client.getSecret(), client.getCacheSessionKey() );
		}catch(Exception ex){
			System.err.println("Unable to create Facebook Service" );
			ex.printStackTrace();
		}
	}


	private void createInstannce(String apikey, String secret, String sessionID) throws UnacceptableConfiguration, MissingHandlerException, ConfigurationException {
		Properties props = new Properties();
		props.put("facebook.apiKey", apikey);
		props.put("facebook.secret", secret);
		props.put("facebook.sessionId", sessionID);
		facebook = fbFactory.createComponentInstance(props);
	}
	/**
	 * 
	 * @param apikey
	 * @param apisecret
	 * @param token
	 * @param sessionID
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void createCFGInstance(String apikey,
			String apisecret, String token, String sessionID) throws FileNotFoundException, IOException {
		File file = new File("org.torito.social.facebook-" + 0 + ".cfg");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		Properties props = new Properties();
		props.put("facebook.apikey", apikey);
		props.put("facebook.apisecret", apisecret);
		props.put("facebook.sessionId", sessionID);
		props.put("facebook.token", token);
		props.store(new FileOutputStream(file), "Facebook configuration for " );
		System.out.println(file.getName() + " created");
	}
}