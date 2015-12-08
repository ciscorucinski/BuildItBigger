/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.christopher.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Random;
import java.util.logging.Logger;

import why.so.serious.Joker;

/** An endpoint class we are exposing */
@Api(
  name = "joker",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backends.myapplication.Christopher.example.com",
    ownerName = "backends.myapplication.Christopher.example.com",
    packagePath= ""
  )
)
public class MyEndpoint {

	private static final Logger logger = Logger.getLogger(MyEndpoint.class.getName());

    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {

	    logger.info("Calling getJoke method");
	    MyBean response = new MyBean();
	    Joker joker = new Joker();

	    logger.info("getting joke");
		int jokeCount = joker.getNumberOfJokes();
	    String joke = joker.getJoke(new Random().nextInt(jokeCount));

	    logger.info("got joke and saving it");
	    response.setData(joke);

        return response;

    }

}
