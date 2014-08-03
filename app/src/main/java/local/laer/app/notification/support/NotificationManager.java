package local.laer.app.notification.support;

import android.app.Notification;

/**
 * Facade for NotficationManager to be able to test it
 * Created by lars on 2014-06-30.
 */
public class NotificationManager {
	private final android.app.NotificationManager notificationManager;

	public NotificationManager(android.app.NotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	public void notify(int id, Notification notification) {
		notificationManager.notify(id, notification);
	}

	public void cancel(int id) {
		notificationManager.cancel(id);
	}

	public void cancel(String tag, int id) {
		notificationManager.cancel(tag, id);
	}

	public void notify(String tag, int id, Notification notification) {
		notificationManager.notify(tag, id, notification);
	}

	public void cancelAll() {
		notificationManager.cancelAll();
	}
}
