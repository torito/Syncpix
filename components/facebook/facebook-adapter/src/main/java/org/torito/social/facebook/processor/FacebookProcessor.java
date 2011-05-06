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
package org.torito.social.facebook.processor;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.torito.social.facebook.FacebookException;
import org.torito.social.facebook.FacebookService;


import fr.liglab.adele.cilia.CiliaException;
import fr.liglab.adele.cilia.Data;
import fr.liglab.adele.cilia.framework.ISender;


/**
 * @author torito
 *
 */
public class FacebookProcessor  {


	private String  method;
	
	private String commentLocation;
	
	private FacebookService service;
	
	public Data process(Data arg0) {
		if ("status".equalsIgnoreCase(method)) {
			setStatus(arg0);
		} else if ("upload".equalsIgnoreCase(method)) {
			upload(arg0);
		} else {
			
		}
		Data rdata = new Data(arg0.getContent(), "facebook.info");
		return rdata;
	}

	private void setStatus(Data data){
		try {
			service.setStatus(getStatus(data));
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}

	private void upload(Data data) {
		try {
			service.upload(getFile(data), getCaption(data), null);
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param data
	 * @return
	 */
	private String getCaption(Data data) {
		String caption = null;
		caption = String.valueOf(data.getProperty(commentLocation));
		return caption;
	}

	private File getFile(Data data) {
		File file = null;
		Object objectfile = data.getContent();
		if (objectfile instanceof File) {
			file =  (File)objectfile;
		} else if (objectfile instanceof String) {
			file = new File(String.valueOf(objectfile));
		}
		return file;
	}

	private String getStatus(Data data) {
		String status = null;
		status = String.valueOf(data.getContent());
		return status;
	}
	
	public void setCommentLocation(String location) {
		commentLocation = location;
	}
	
	public void setFacebookMethod(String m) throws CiliaException{
		method = m;
		if (!"status".equalsIgnoreCase(method) && !"upload".equalsIgnoreCase(method)) {
			throw new CiliaException("Implementation only allows \"upload\" and \"status\" methods: " + method);
		}
	}
	
	public void bindService(FacebookService s){
		service = s;
	}

	public void unbindService(FacebookService s){
		service = null;
	}
}
