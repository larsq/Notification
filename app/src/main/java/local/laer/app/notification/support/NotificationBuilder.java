package local.laer.app.notification.support;

import android.app.Notification;
import android.content.Context;

/**
 * Created by lars on 2014-07-01.
 */
public class NotificationBuilder {
	private AbstractTextViewReference title;
	private AbstractTextViewReference content;
	private Integer icon;

	public AbstractTextViewReference getTitle() {
		return title;
	}

	public void setTitle(AbstractTextViewReference title) {
		this.title = title;
	}

	public AbstractTextViewReference getContent() {
		return content;
	}

	public void setContent(AbstractTextViewReference content) {
		this.content = content;
	}

	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public Notification notification(Context context) {
		Notification.Builder builder = new Notification.Builder(context);

		if(title !=null) {
			builder.setContentTitle(title.getSafeCharSequence());
		}

		if(content != null) {
			builder.setContentText(content.getSafeCharSequence());
		}

		if(icon != null) {
			builder.setSmallIcon(icon.intValue());
		}

		return builder.build();
	}
}
