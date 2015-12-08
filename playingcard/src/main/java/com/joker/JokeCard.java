package com.joker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

import java.util.Random;

public class JokeCard {

    String joke;
    @DrawableRes int icon;
	int textColor;

    private JokeCard(int icon, int textColor) {

        this.icon = icon;
	    this.textColor = textColor;

    }

	public static JokeCard randomCard() {

		int random = new Random().nextInt(2);

		if (random == 0) return new JokeCard(R.drawable.joker_black, Color.BLACK);
		else return new JokeCard(R.drawable.joker_red, Color.RED);

	}
	public static JokeCard blackCard() {

		return new JokeCard(R.drawable.joker_black, Color.BLACK);

	}
	public static JokeCard redCard() {

		return new JokeCard(R.drawable.joker_red, Color.RED);

	}

	public void setJoke(String joke) { this.joke = joke; }
    String getJoke() { return this.joke; }

    @DrawableRes int getIconResource() { return this.icon; }
    Drawable getIcon(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            return context.getResources().getDrawable(this.icon, context.getTheme());

        } else {

            return context.getResources().getDrawable(this.icon);

        }
    }

	int getTextColor() { return this.textColor; }

}