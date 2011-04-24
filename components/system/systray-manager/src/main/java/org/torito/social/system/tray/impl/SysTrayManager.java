package org.torito.social.system.tray.impl;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.torito.social.system.tray.SysTrayService;


/**
 * @author torito
 *This manager with track SysTrayService in order to register tray icons.
 *The SysTrayService ServiceReference must provide the required information such as
 *an array of strings (systray.items) containing the menu items labels.
 *the systray name (systray.name)
 *the icon file (systray.icon)
 */
public class SysTrayManager implements ServiceTrackerCustomizer{

	/**
	 * OSGi bundle context.
	 */
	private final BundleContext bcontext;
	/**
	 * The OSGi service tracker.
	 */
	private ServiceTracker tracker;
	/**
	 * The services to track.
	 */
	private static final String FILTER = SysTrayService.class.getName();
	/**
	 * The property contained in the ServiceReference of the SysTrayService
	 */
	private static final String ITEMS = "systray.items";

	private static final String NAME = "systray.name";
	
	private static final String ICON = "systray.icon";

	public SysTrayManager(BundleContext context) {
		bcontext = context;
	}
	/**
	 * On validate.
	 */
	protected void start() {
		tracker = new ServiceTracker(bcontext,  FILTER, this);
		tracker.open();
	}

	/**
	 * On un validate.
	 */
	protected void stop (){
		tracker.close();
		tracker = null;
	}
	/**
	 * 
	 * @param service
	 * @param reference
	 * @return
	 * @throws Exception
	 */
	private TrayIcon registerTray(SysTrayService service, ServiceReference reference) throws Exception {
		String name = (String )reference.getProperty(NAME);
		String icon = (String )reference.getProperty(ICON);
		if (name == null) {
			name = "default";
		}
		if (icon == null){
			icon = "tray.gif";
		}
		PopupMenu popup = getMenu(service, reference);
		TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(icon), name, popup);
		SystemTray.getSystemTray().add(trayIcon);
		return trayIcon;
	}

	private TrayIcon registerTray(TrayIcon tray, SysTrayService service, ServiceReference reference) throws Exception {
		PopupMenu popup = getMenu(service, reference);
		tray.setPopupMenu(popup);
		return tray;
	}
	
	private PopupMenu getMenu(SysTrayService service, ServiceReference reference) throws Exception {
		String [] items = (String [])reference.getProperty(ITEMS);

		if (!SystemTray.isSupported()) {
			throw new UnsupportedOperationException("SysTray is Unsupported ");
		}
		if (items == null) {
			throw new NullPointerException(ITEMS + "not found in service : " + reference.getProperty("service.id"));
		}


		PopupMenu popup = new PopupMenu();
		SysTrayListener listener = new SysTrayListener(service);
		for (String it : items) {
			if ("|".equalsIgnoreCase(it)){
				popup.addSeparator();
			} else {
				MenuItem defaultItem = new MenuItem(it);
				defaultItem.addActionListener(listener);
				popup.add(defaultItem);
			}
		}
		return popup;
	}
	
	public Object addingService(ServiceReference reference) {
		SysTrayService service = (SysTrayService)bcontext.getService(reference);
		try {
			return registerTray(service, reference);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Method called by the ServiceTracker when service properties are modified.
	 * This method will modify the menu when service configuration is modified.
	 * TODO: check other properties, such as image and name.
	 */
	public void modifiedService(ServiceReference reference, Object service) {	
		SysTrayService tservice = (SysTrayService)bcontext.getService(reference);
		try {
			registerTray((TrayIcon)service, tservice, reference);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method called by the ServiceTracker when service will be removed.
	 * This method will remove the trayicon.
	 */
	public void removedService(ServiceReference reference, Object service) {
		TrayIcon trayicon = (TrayIcon) service;
		SystemTray.getSystemTray().remove(trayicon);
		bcontext.ungetService(reference);
	}
}