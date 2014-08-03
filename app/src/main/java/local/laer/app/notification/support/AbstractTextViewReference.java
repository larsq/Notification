package local.laer.app.notification.support;

import android.content.Context;
import android.widget.TextView;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;


/**
 * Encapsulates a text field.
 * Created by lars on 2014-07-01.
 */
public abstract class AbstractTextViewReference implements Supplier<TextView> {
	static class ByInstance extends AbstractTextViewReference {
		private final TextView textView;

		ByInstance(TextView textView) {
			this.textView = textView;
		}

		@Override
		public TextView get() {
			return textView;
		}
	}

	public static AbstractTextViewReference byInstance(TextView textView) {
		return new ByInstance(textView);
	}

	/**
	 * Returns the text as char sequence.
	 * @return the char sequence or the empty sequence if the field is not set or the text is null
	 */
	public CharSequence getSafeCharSequence() {
		TextView tv = get();

		CharSequence sequence = null;
		if(tv != null) {
			sequence = Optional.fromNullable(tv.getText()).or("");
		}

		return Objects.firstNonNull(sequence, "");
	}


}
