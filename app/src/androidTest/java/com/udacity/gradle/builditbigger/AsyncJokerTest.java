package com.udacity.gradle.builditbigger;

import android.content.Intent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;

public class AsyncJokerTest {

	private class MockJokeReceiver implements AsyncHandler {

		@Override public void OnReceiveJoke(Intent joke) {

			latch.countDown();

		}

	}
	final CountDownLatch latch = new CountDownLatch(1);

	@Test(timeout = 5000)
	public void JokerAsyncGivesNonEmptyStringReturnValue() {

		try {

			AsyncJoker joker = new AsyncJoker();
			joker.execute(new MockJokeReceiver());
			latch.await();

			String nonEmptyString = joker.get();

			assertFalse(nonEmptyString.isEmpty());
			assertFalse(nonEmptyString.equals("failed to connect to /10.0.2.2 (port 8080) after 20000ms"));

		} catch (Exception ignore) { }

	}
}