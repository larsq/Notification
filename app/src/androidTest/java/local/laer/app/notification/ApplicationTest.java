package local.laer.app.notification;

import android.app.Notification;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lars.myapplication.R;
import com.robotium.solo.Solo;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import local.laer.app.notification.support.NotificationManager;
import local.laer.app.notification.support.NotificationVerify;
import local.laer.app.notification.support.Reflection;

import static org.mockito.Mockito.anyString;

/**
 * This class test different aspects for the Application class
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<Application> {
	private NotificationManager notificationManager;
	@Captor
	private ArgumentCaptor<Notification> published;
	@Captor
	private ArgumentCaptor<Integer> cancelledId;
	@Captor
	private ArgumentCaptor<Integer> publishedId;

	private Solo solo;

	public ApplicationTest() {
		super(Application.class);
	}

	@Override
	protected void setUp() throws Exception {
		System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().toString());
		solo = new Solo(getInstrumentation(), getActivity());


		setupMocks();
		injectInClips();
	}

	private void injectInClips() {
		Clip clip = Reflection.get(Clip.class, getActivity());
		Reflection.set(notificationManager, NotificationManager.class, clip);
	}

	private void setupMocks() {
		MockitoAnnotations.initMocks(this);

		notificationManager = Mockito.spy(new NotificationManager((android.app.NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE)));
		Mockito.doCallRealMethod().when(notificationManager).cancel(anyString(), cancelledId.capture());
		Mockito.doCallRealMethod().when(notificationManager).notify(anyString(), publishedId.capture(), published.capture());
	}


	private void enterText(final String title, final String content) {
		final EditText titleText = (EditText) getActivity().findViewById(R.id.content_title);
		final EditText contentText= (EditText) getActivity().findViewById(R.id.content);

		getActivity().runOnUiThread( new Runnable() {
			@Override
			public void run() {
				titleText.setText(title);
				contentText.setText(content);
			}
		});
	}

	private void clickOnView(int id) {
		View v = getActivity().findViewById(id);
		v.callOnClick();
	}

	private NotificationVerify.VerifySoloCallback checkIfTextExists(final String text) {
		return new NotificationVerify.VerifySoloCallback() {
			@Override
			public void test(Solo solo) {
				TextView v = solo.getText(text, true);
				assertNotNull(v);
			}
		};
	}

	@MediumTest
	public void testPublishTitleSet() {
		enterText("title", "content");
		clickOnView(R.id.update_close);

		NotificationVerify verifier = new NotificationVerify(solo, published.getValue());
		verifier.contentView();

		verifier.verify(checkIfTextExists("title"));
	}

	@MediumTest
	public void testPublishContentSet() {
		enterText("title", "content");
		clickOnView(R.id.update_close);

		NotificationVerify verifier = new NotificationVerify(solo, published.getValue());

		verifier.verify(checkIfTextExists("content"));
	}

	@MediumTest
	public void testCancel() {
		enterText("title", "content");
		clickOnView(R.id.update_close);

		Integer expectedId = publishedId.getValue();

		clickOnView(R.id.clear_exit);

		assertNotNull("no id published", expectedId);
		assertEquals("id does not match of published and cancelled", expectedId, cancelledId.getValue());

	}

	@MediumTest
	public void testNoCancel() {
		enterText("title", "content");
		clickOnView(R.id.update_close);

		Integer expectedId = publishedId.getValue();

		assertNotNull("no id published", expectedId);

		List<Integer> cancelled = cancelledId.getAllValues();

		assertTrue(cancelled == null || cancelled.isEmpty());
	}

}