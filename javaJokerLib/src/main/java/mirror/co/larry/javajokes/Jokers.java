package mirror.co.larry.javajokes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Jokers {

    private ArrayList<String> jokes = new ArrayList<String>(Arrays.asList(
            "\nCan a kangaroo jump higher than a house?\n" +
                    "-\n" +
                    "Of course, a house doesn't jump at all.",
            "\nA man asks a farmer near a field, \"Sorry sir, would you mind if I crossed your field instead of going around it? You see, I have to catch the 4:23 train. \" \n" +
                    "\n" +
                    "The farmer says, \"Sure, go right ahead. And if my bull sees you, you'll even catch the 4:11 one.\"",
            "\nWhat is the difference between a snowman and a snowwoman?\n" +
                    "-\n" +
                    "Snowballs.",
            "\nWhat's the difference between BOOM! Aaaaargh! And Aaaaaargh! BOOM!?\n" +
                    "-\n" +
                    "The difference is whether you're falling from the 1st or the 10th floor. ",
            "\nWomen call me ugly occasionally. But that's only until they hear how much money I make.\n" +
                    "-\n" +
                    "Then they say I'm poor and ugly.",
            "\nDarling, do you think I've gotten too fat?\n" +
                    "-\n" +
                    "Worry not my dear. Every good steak has some proper lard."

    ));
    // reference
    // https://short-funny.com/new-jokes.php

    public String getJoke(){
        Random random = new Random();
        return jokes.get(random.nextInt(jokes.size()));
    }

    public ArrayList<String> jokeList(){
        return jokes;
    }

}
