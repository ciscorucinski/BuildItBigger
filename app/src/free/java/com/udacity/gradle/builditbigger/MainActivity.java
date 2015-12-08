package com.udacity.gradle.builditbigger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joker.JokeCard;
import com.joker.JokerCardFragment;

import java.util.EnumSet;
import java.util.Stack;

import want.to.see.a.magictrick.GestureInformation;
import want.to.see.a.magictrick.Gestures;
import want.to.see.a.magictrick.Magician;
import want.to.see.a.magictrick.Revealer;
import want.to.see.a.magictrick.Secrets;
import want.to.see.a.magictrick.Trick;
import why.so.serious.Joker;


public class MainActivity extends AppCompatActivity
		implements MainActivityFragment.onFragmentInteraction,
		           Gestures.Filterable<JokerCardFragment>,
		           Gestures.OnGestureListener<JokerCardFragment> {

	private Cards cards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// This is a proof-of-concept that I can abstract the creating and holding of Fragment
		// instances. Check the Revealer class to see more information
		cards = Revealer.OfMagician.secrets(Cards.class)
				.analyze(Magician.performance(new StateTrick()))
				.revealSecret(getSupportFragmentManager());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.joke_source) {

			final Joker jokes = new Joker();
			final Context context = getBaseContext();

			String htmlEncodedMessage = String.format                                     (
					getString(R.string.dialog_text_template),
					getString(R.string.dialog_text_domain), jokes.getJokeProvider(),
					getString(R.string.dialog_text_url), jokes.getJokeProviderURL(),
					getString(R.string.dialog_text_notes), getString(R.string.dialog_note));

			new AlertDialog.Builder(this)
					.setTitle(getString(R.string.dialog_title))
					.setMessage(Html.fromHtml(htmlEncodedMessage))
					.setPositiveButton(getString(R.string.dialog_button_ok), null)
					.setNeutralButton(getString(R.string.dialog_button_copy_url),
					                  new ClipboardListener(context, jokes.getJokeProviderURL()))
					.show();

			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onNewJoke(JokerCardFragment card, JokeCard jokeCard) {

		card.setVisible();
		this.cards.list.push(jokeCard);

	}

	@Override
	public void onFragmentCreated(JokerCardFragment card) {

		displayNewCardOn(card);

	}

	//	@Override
//	public View.OnTouchListener getGestureListener(JokerCardFragment fragment) {
//
//		Gestures<JokerCardFragment> gestures = new Gestures<>(this);
//
//		gestures.listenOn(fragment);
//		gestures.setOnFingerGestureListener(gestures);
//		gestures.filterGestures(
//				EnumSet.of(GestureInformation.LEFT, GestureInformation.RIGHT));
//		gestures.setConsumeTouchEvents(true);
//
//		return gestures;
//
//	}

	@Override
	public Gestures<JokerCardFragment> modifyGestureListener(
			@NonNull Gestures<JokerCardFragment> gestures) {

		gestures.filterGestures(EnumSet.of(GestureInformation.LEFT, GestureInformation.RIGHT));
		gestures.addGestureListener(this);

		return gestures;
	}

	@Override
	public void onFilteredGesture(JokerCardFragment fragment, GestureInformation swipe, int fingers) {

		// Handle what happens to the list based on the gesture
		if (!cards.list.isEmpty()) {

			if (fingers == 1) { cards.list.pop(); }
			else if (fingers >= 2) { cards.list.clear(); }

		}

		// Handle what happens to the list after handling the gesture
		displayNewCardOn(fragment);

	}

	private void displayNewCardOn(JokerCardFragment fragment) {

		if (this.cards.list.isEmpty()) { fragment.setInvisible(); }
		else { fragment.setJoke(this.cards.list.peek()); }

	}

	public static class ClipboardListener implements DialogInterface.OnClickListener {

		Context context;
		String text;

		public ClipboardListener(Context context, String copyText) {

			this.context = context;
			this.text = copyText;

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {

			ClipboardManager clipboard =
					(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText                                       (
					context.getString(R.string.dialog_clipboard_copied_title), this.text);

			clipboard.setPrimaryClip(clip);

			Toast.makeText(context, R.string.dialog_clipboard_copied_message, Toast.LENGTH_SHORT)
					.show();

		}

	}

	private static class Cards extends Secrets {
		public Stack<JokeCard> list;
	}

	public static class StateTrick extends Trick<Cards> {

		@Override
		public Cards onCreateSecret() {

			Cards cards = new Cards();
			cards.list = new Stack<>();

			return cards;

		}
	}

}
