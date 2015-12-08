package why.so.serious;

import java.util.Arrays;
import java.util.List;

public class Joker {

    private static String[] jokes = new String[] {

            "A day without sunshine is like, night.",
            "On the other hand, you have different fingers.",
            "I just got lost in thought. It was unfamiliar territory.",
            "42.7% of all statistics are made up on the spot.",
            "99 percent of lawyers give the rest a bad name.",
            "I feel like I'm diagonally parked in a parallel universe.",
            "Love may be blind, but marriage is a real eye-opener.",
            "You have the right to remain silent. Anything you say will be misquoted, then used against you.",
            "I wonder how much deeper the ocean would be without sponges.",
            "Honk if you love peace and quiet.",
            "Remember: half the people you know are below average.",
            "Despite the cost of living, have you noticed how popular it remains?",
            "Nothing is foolproof to a talented fool.",
            "He who laughs last thinks slowest.",
            "Depression is merely anger without enthusiasm.",
            "Eagles may soar, but dogs don't get sucked into jet engines.",
            "The early bird may get the worm, but the second mouse gets the cheese.",
            "I intend to live forever -- so far so good.",
            "Borrow money from a pessimist -- they don't expect it back.",
            "If Barbie is so popular, why do you have to buy all her friends?",
            "The only substitute for good manners is fast reflexes.",
            "Support bacteria -- it's the only culture some people have.",
            "When everything's coming your way, you're in the wrong lane and going the wrong way.",
            "If at first you don't succeed, destroy all evidence that you tried.",
            "A conclusion is the place where you got tired of thinking.",
            "Experience is something you don't get until after you need it.",
            "For every action there is an equal and opposite criticism.",
            "Bills travel though the mail at twice the speed of checks.",
            "Never do card tricks for the group you play poker with.",
            "Realize that no matter what you do, the grocery store check-out line you're in will always take the longest.",
            "No one is listening until you make a mistake.",
            "Anything you buy will go on sale the next day.",
            "Success always occurs in private and failure in full view.",
            "The colder the x-ray table the more of your body is required on it.",
            "The hardness of butter is directly proportional to the softness of the bread.",
            "The severity of an itch is inversely proportional to your ability to reach it.",
            "To steal ideas from one person is plagiarism; to steal from many is research.",
            "Monday is an awful way to spend 1/7 of your life.",
            "You never really learn to swear until you learn to drive.",
            "Two wrongs are only the beginning.",
            "The problem with the gene pool is that there is no lifeguard.",
            "The sooner you fall behind the more time you'll have to catch up.",
            "A clear conscience is usually the sign of a bad memory.",
            "Change is inevitable, except from vending machines.",
            "Get a new car for your spouse -- it'll be a great trade!",
            "Plan to be spontaneous -- tomorrow.",
            "Always try to be modest, and be proud of it!",
            "If you think nobody cares, try missing a couple of payments.",
            "If at first you don't succeed, then skydiving isn't for you."


    };

    private static final String jokeProvider = "funny.com";
    private static final String jokeProviderURL = "http://www.funny.com/cgi-bin/WebObjects/Funny.woa/wa/funny?fn=C3DAR";


    public List<String> getJokes() { return Arrays.asList(jokes); }
    public String getJoke(int i) throws ArrayIndexOutOfBoundsException { return jokes[i]; }
    public int getNumberOfJokes() { return jokes.length; }

    public String getJokeProvider() { return jokeProvider; }
    public String getJokeProviderURL() { return jokeProviderURL; }

}