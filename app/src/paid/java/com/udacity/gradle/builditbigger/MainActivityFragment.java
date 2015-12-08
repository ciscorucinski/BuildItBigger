package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.joker.JokeCard;
import com.joker.JokerCardFragment;

import want.to.see.a.magictrick.Gestures;


public class MainActivityFragment extends Fragment implements View.OnClickListener, AsyncHandler {

//	private Joker joker;
	private JokerCardFragment card;
	private Gestures.Filterable<JokerCardFragment> fragmentFilterInteraction;
	private onFragmentInteraction fragmentListener;

	public MainActivityFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_main, container, false);
//		AdView mAdView = (AdView) root.findViewById(R.id.adView);
		Button btnTellJoke = (Button) root.findViewById(R.id.buttonJoke);

//		joker = new Joker();

		// Note: I am looking to see if I can abstract this logic away into the Revealer class.
		// It will NOT occur in this project, but in my own individual project later on.
		card = (JokerCardFragment) getFragmentManager().findFragmentById(R.id.card_container);

		if (card == null) {

			card = new JokerCardFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.card_container, card)
					.commit();

		}

		Gestures<JokerCardFragment> gestureListener = initializeGestureHandler();
		gestureListener = fragmentFilterInteraction.modifyGestureListener(gestureListener);

		card.setOnTouchListener(gestureListener);

		btnTellJoke.setOnClickListener(this);

//		initializeAds(mAdView);

		return root;

	}

//	private void initializeAds(AdView mAdView) {
//
//		// Create an ad request. Check logcat output for the hashed device ID to
//		// get test ads on a physical device. e.g.
//		// "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
//		// Use AdRequest.Builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) to get ads on emulator
//		AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//
//		mAdView.loadAd(adRequest);
//
//	}

	private Gestures<JokerCardFragment> initializeGestureHandler() {

		Gestures<JokerCardFragment> gestureListener = new Gestures<>();

		gestureListener.listenOn(card);
		gestureListener.setOnFingerGestureListener(gestureListener);
		gestureListener.setConsumeTouchEvents(true);

		return gestureListener;

	}

	@Override
	public void onClick(View v) { tellJoke(); }

	private void tellJoke()                                                                                                    {

		try {

			new AsyncJoker().execute(this);

		} catch (ArrayIndexOutOfBoundsException e) {

			Toast.makeText(getActivity(), "404 - Joke Not Found!", Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	public void onStart() {

		super.onStart();
		fragmentListener.onFragmentCreated(card);

	}

	@Override
	public void onAttach(Context context) {

		super.onAttach(context);

		if (context instanceof onFragmentInteraction) { fragmentListener = (onFragmentInteraction) context; }
		else { throw new RuntimeException(context.toString() + " must implement onFragmentInteraction"); }

		if (context instanceof Gestures.Filterable) {
			fragmentFilterInteraction = (Gestures.Filterable) context;
		}
		else { throw new RuntimeException(context.toString() + " must implement Filterable"); }

	}

	@Override
	public void onDetach() {

		super.onDetach();
		fragmentListener = null;
		fragmentFilterInteraction = null;

	}

	@Override
	public void OnReceiveJoke(Intent jokeIntent) {

		JokeCard cardJokeCard = JokeCard.randomCard();
		String joke = jokeIntent.getStringExtra(AsyncJoker.JOKE_TEXT);

		cardJokeCard.setJoke(joke);

		// Update display
		card.setJoke(cardJokeCard);
		fragmentListener.onNewJoke(card, cardJokeCard);

	}

	public interface onFragmentInteraction {

		void onNewJoke(JokerCardFragment card, JokeCard jokeCard);
		void onFragmentCreated(JokerCardFragment card);

	}


}
