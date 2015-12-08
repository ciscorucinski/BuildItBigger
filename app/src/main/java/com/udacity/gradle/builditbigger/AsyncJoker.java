package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.christopher.myapplication.backends.joker.Joker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

class AsyncJoker extends AsyncTask<AsyncHandler, Void, String> {

	private static Joker myApiService = null;
	private AsyncHandler handler;

	public static final String JOKE_TEXT = "com.udacity.gradle.builditbigger.AsyncJoker.joker_text";

	@Override
	protected String doInBackground(AsyncHandler... params) {

		if (myApiService == null) {  // Only do this once

			Joker.Builder builder = new Joker.Builder(AndroidHttp.newCompatibleTransport(),
			                                          new AndroidJsonFactory(), null)
					// - 10.0.2.2 is localhost's IP address in Android emulator
					// - turn off compression when running against local devappserver
					.setRootUrl("http://10.0.2.2:8080/_ah/api/")
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {

						@Override
						public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
								throws IOException {

							abstractGoogleClientRequest.setDisableGZipContent(true);

						}
					});

			myApiService = builder.build();

		}

		// Retrieve the class from the param. It will be used to send data via callback
		handler = params[0];

		String data = "";

		try { data = myApiService.getJoke().execute().getData(); }
		catch (IOException ignore) { }

		return data;

	}

	@Override
	protected void onPostExecute(String result) {

		Intent intent = new Intent();
		intent.putExtra(JOKE_TEXT, result);

		handler.OnReceiveJoke(intent);

	}

}