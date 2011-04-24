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
package org.torito.social.system.tray.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.torito.social.system.tray.SysTrayService;

/**
 * @author torito
 *
 */
public class SysTrayListener implements ActionListener {

	
	private final SysTrayService service;
	
	public SysTrayListener(SysTrayService tservice) {
		service = tservice;
	}

	public void actionPerformed(ActionEvent arg0) {
		service.performAction(arg0.getActionCommand());
	}
	
	

}
