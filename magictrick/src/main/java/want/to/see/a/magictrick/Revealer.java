package want.to.see.a.magictrick;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

// This is purely a TEST class just to see if I could abstract away some logic in creating and
// retrieving references of fragments. So far this is only for Headless Fragments, but I am seeing
// if it is possible to make this work for all Fragments.

// I see that there are 3 types of Fragments.
// Normal fragments that hold views.
// Headless State holding fragments.
// Headless Feature abstraction fragments.

// I will be working on converting this class to work with all the Headless types.
// Currently I only support Headless State fragments

// This converting process will occur outside of this project.
// This is just a proof-of-concept that does seem to work.

// My first goal will be to have this code vetted by the Code Review individuals in StackExchange
// and get feedback from them.
public class Revealer<T extends Secrets> {

	Magician<T> magician;

	private Revealer(Magician<T> magician) { this.magician = magician; }

	public T revealSecret(@NonNull FragmentManager manager) {

		String tag = magician.getSecret().getTag();

		Magician<T> temp = (Magician<T>) manager.findFragmentByTag(tag);

		if (temp == null) {

			magician.hasBeenRevealed();
			manager.beginTransaction()
					.add(magician, magician.getSecret().getTag())
					.commit();

		} else if (temp.isRevealed()) {

//			magician = null;
			return temp.getSecret();

		}

		return magician.getSecret();

	}

	public static class OfMagician<U extends Secrets> {

		Magician<U> magician;

		private OfMagician() {}

		public static <T extends Secrets> OfMagician<T> secrets(Class<T> ignore) {

			return new OfMagician<>();

		}

		public Revealer<U> analyze(Magician<U> magician) {

			this.magician = magician;

			U secret = magician.performTrick();
			this.magician.setSecret(secret);

			return new Revealer<>(this.magician);

		}

	}
}