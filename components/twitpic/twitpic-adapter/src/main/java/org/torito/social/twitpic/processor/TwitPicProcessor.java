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
package org.torito.social.twitpic.processor;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.torito.social.twitpic.MediaInfo;
import org.torito.social.twitpic.TwitPicService;

import fr.liglab.adele.cilia.Data;
import fr.liglab.adele.cilia.framework.ISender;


/**
 * @author torito
 *
 */
public class TwitPicProcessor  {


	private BundleContext bcontext;

	private String twitteruser;

	private String fileLocation;

	private String commentLocation;

	public TwitPicProcessor (BundleContext context){
		bcontext = context;
	}
	/* (non-Javadoc)
	 * @see fr.liglab.adele.cilia.framework.ISender#send(fr.liglab.adele.cilia.Data)
	 */
	public Data process(Data arg0) {
		TwitPicService service;
		MediaInfo minfo = null;
		ServiceReference refs[] = null;
		if (twitteruser == null || twitteruser.isEmpty()) {
			//TODO message, unable to send
			System.err.println("Unable to send tweet, twitter user is invalid: " + twitteruser);
			return arg0;
		}
		try {
			refs = bcontext.getAllServiceReferences(TwitPicService.class.getName(), "(twitter.screenName=" + twitteruser +")");
		} catch (InvalidSyntaxException e1) {
			e1.printStackTrace();
		}
		if (refs == null  || refs.length<1) {
			System.err.println("Unable to send tweet, twitter service is not available: " + twitteruser);
			return arg0;			
		}
		service = (TwitPicService)bcontext.getService(refs[0]);
		try {
			minfo = service.uploadMedia(getFile(arg0), getComment(arg0));
		} catch (Exception e) {
			e.printStackTrace();
			return arg0;
		} finally {
			bcontext.ungetService(refs[0]);
		}
		Data rdata = new Data(minfo, "twitpic.mediainfo");
		return rdata;
	}

	private File getFile(Data data) {
		File file = null;
		if(fileLocation != null && !fileLocation.isEmpty() && data.getProperty(fileLocation) != null ) {
			Object objectfile = data.getProperty(fileLocation);
			if (objectfile instanceof File) {
				file =  (File)objectfile;
			} else if (objectfile instanceof String) {
				file = new File(String.valueOf(objectfile));
			}
		}
		return file;
	}

	private String getComment(Data data) {
		String ntweet = null;
		if(commentLocation != null && !commentLocation.isEmpty() && data.getProperty(commentLocation) != null ) {
			ntweet = String.valueOf(data.getProperty(commentLocation));
		}
		return ntweet;
	}

	public void setTwitteruser(String twitteruser) {
		this.twitteruser = twitteruser;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public void setCommentLocation(String commentLocation) {
		this.commentLocation = commentLocation;
	}
}
