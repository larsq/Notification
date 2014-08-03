package local.laer.app.notification.support;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.view.View;
import android.widget.RemoteViews;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.robotium.solo.Solo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lars on 2014-06-30.
 */
public class NotificationVerify {
	private final Solo solo;
	private final Notification notification;
	private View view;


	public NotificationVerify(Solo solo, Notification notification) {
		this.solo = solo;
		this.notification = notification;

	}

	public void contentView() {
		RemoteViewsInflater inflater = new RemoteViewsInflater(notification.contentView, solo.getCurrentActivity());
		solo.getCurrentActivity().runOnUiThread(inflater);
		view = inflater.view;
	}

	private static class RemoteViewsInflater implements Runnable, Callable<View> {
		private final RemoteViews remoteViews;
		private final Context context;
		private View view;


		private RemoteViewsInflater(RemoteViews remoteViews, Context context) {
			this.remoteViews = remoteViews;
			this.context = context;
		}


		public void run() {
			view = View.inflate(context, remoteViews.getLayoutId(), null);
			remoteViews.reapply(context, view);
		}

		public View getView() {
			return view;
		}

		@Override
		public View call() throws Exception {
			run();
			return view;
		}
	}

	public void verify(final VerifySoloCallback callback) {
		Preconditions.checkNotNull(callback, "Callback must be set");

		callback.test(solo);
	}


	public static interface VerifySoloCallback {
		public void test(Solo solo);
	}
}
