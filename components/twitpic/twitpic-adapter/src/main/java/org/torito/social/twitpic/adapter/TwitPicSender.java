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
package org.torito.social.twitpic.adapter;

import org.osgi.framework.BundleContext;
import org.torito.social.twitpic.processor.TwitPicProcessor;

import fr.liglab.adele.cilia.Data;
import fr.liglab.adele.cilia.framework.ISender;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project Team</a>
 *
 */
public class TwitPicSender extends TwitPicProcessor implements ISender {

	/**
	 * @param context
	 */
	public TwitPicSender(BundleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fr.liglab.adele.cilia.framework.ISender#send(fr.liglab.adele.cilia.Data)
	 */
	public boolean send(Data data) {
		Data rdata = super.process(data);
		//if it has mediainfo it has been uploaded.
		if (rdata.getName().equalsIgnoreCase("twitpic.mediainfo")) {
			return true;
		}
		//otherwhise there is a problem 
		return false;
	}

}
