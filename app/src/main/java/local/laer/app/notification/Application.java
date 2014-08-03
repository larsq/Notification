package local.laer.app.notification;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lars.myapplication.R;

import local.laer.app.notification.support.AbstractTextViewReference;
import local.laer.app.notification.support.NotificationBuilder;
import local.laer.app.notification.support.NotificationManager;

import static java.lang.String.valueOf;
import static local.laer.app.notification.Tags.Event;

/**
 * Created by lars on 2014-06-29.
 */
public class Application extends Activity {
	private Clip clip;


	private NotificationBuilder notificationBuilder() {
		NotificationBuilder builder = new NotificationBuilder();
		builder.setContent(AbstractTextViewReference.byInstance((TextView) findViewById(R.id.content)));
		builder.setTitle(AbstractTextViewReference.byInstance((TextView) findViewById(R.id.content_title)));
		builder.setIcon(R.drawable.ic_notification);
		return builder;
	}

	private Clip clip() {
		Clip c = new Clip(null, 0, notificationBuilder(), new NotificationManager((android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE)));
		return c;
	}

	public void actionUpdate(View view) {
		clip.publish(this);
	}

	public void actionCancel(View view) {
		clip.cancel();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		clip = clip();

		Log.v(valueOf(Event), "application started");

	}


}
