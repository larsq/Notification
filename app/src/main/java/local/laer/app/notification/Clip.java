package local.laer.app.notification;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.lars.myapplication.R;

import local.laer.app.notification.support.NotificationBuilder;
import local.laer.app.notification.support.NotificationManager;
import local.laer.app.notification.support.AbstractTextViewReference;

/**
 * This class handles the current clip
 * Created by lars on 2014-06-29.
 */
public class Clip {
	private final String tag;
	private final int id;
	private final NotificationBuilder notificationBuilder;
	private final NotificationManager notificationManager;

	public Clip(String tag, int id, NotificationBuilder notificationBuilder, NotificationManager notificationManager) {
		this.tag = tag;
		this.id = id;
		this.notificationBuilder = notificationBuilder;
		this.notificationManager = notificationManager;
	}

	public void publish(Context context) {
		Notification notification = notificationBuilder.notification(context);
		notificationManager.notify(tag, id, notification);
	}

	public void cancel() {
		notificationManager.cancel(tag, id);
	}
}
