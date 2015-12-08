package want.to.see.a.magictrick;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class Magician<T extends Secrets> extends Fragment {

	private boolean isRevealed;
	private T secret;
	private Trick<T> trick;

	public Magician() {}

	public static <T extends Secrets> Magician<T> performance(Trick<T> trick) {

		Magician<T> magician = new Magician<>();
		magician.trick = trick;
		magician.isRevealed = false;

		return magician;
	}

	protected T performTrick() {

		return trick.onCreateSecret();

	}

	protected void hasBeenRevealed() { this.isRevealed = true; }
	protected boolean isRevealed() { return this.isRevealed; }

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	public void setSecret(T secret) { this.secret = secret; }
	public T getSecret() { return this.secret; }

}