package com.joker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class JokerCardFragment extends Fragment {

    private View card;

    private TextView txtJoke;
    private ImageView imgCardTop, imgCardBottom;
	private View.OnTouchListener touchListener;

	public JokerCardFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		card = inflater.inflate(R.layout.fragment_joker_card, container, false);

		txtJoke = (TextView) card.findViewById(R.id.joke_text);
		imgCardTop = (ImageView) card.findViewById(R.id.card_icon_top);
		imgCardBottom = (ImageView) card.findViewById(R.id.card_icon_bottom);

		if (card != null) card.setOnTouchListener(touchListener);

		return card;

	}

    public void setInvisible() { this.card.setVisibility(View.INVISIBLE); }
    public void setVisible() { this.card.setVisibility(View.VISIBLE); }

	public void setOnTouchListener(View.OnTouchListener listener) {

		this.touchListener = listener;
		if (card != null) card.setOnTouchListener(listener);

	}

	@Override
	public void onStop() {

		super.onStop();
		this.card = null;
		txtJoke = null;
		imgCardBottom = null;
		imgCardTop= null;
		touchListener = null;
	}

	public void setJoke(JokeCard jokeCard) {

        if (jokeCard == null) return;
        if (txtJoke == null) return;

        txtJoke.setText(jokeCard.getJoke());
	    txtJoke.setTextColor(jokeCard.getTextColor());
        imgCardTop.setImageResource(jokeCard.getIconResource());
        imgCardBottom.setImageResource(jokeCard.getIconResource());

    }

}
