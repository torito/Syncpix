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
package org.torito.social.facebook.impl;

import java.io.File;

import org.torito.social.facebook.FacebookException;
import org.torito.social.facebook.FacebookService;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;


/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project Team</a>
 *
 */
public class FacebookServiceImpl  implements FacebookService{

	private  String token ;
	/**
	 * 
	 */
	private FacebookClient client;
	/**
	 * 
	 * @param ak
	 * @param as
	 * @param sid
	 * @param tk
	 */
	public FacebookServiceImpl(String token){
		client = new DefaultFacebookClient(token);
		this.token = token;
	}

	private void start(){
		client = new DefaultFacebookClient(token);
		DefaultWebRequestor con = new DefaultWebRequestor();
		
	}

	public long createAlbum(String arg0, String arg1, String arg2)
	throws FacebookException {

		return 0L;
	}

	
	public void upload(File arg0, String arg1, String arg2)
	throws FacebookException {

	}

	public void setStatus(String arg0) throws FacebookException {

	}
}
