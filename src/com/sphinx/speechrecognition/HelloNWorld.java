package com.sphinx.speechrecognition;

import java.io.IOException;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class HelloNWorld {

    public static void main(String[] args) throws IOException {
        ConfigurationManager cm;

        if (args.length > 0) {
            cm = new ConfigurationManager(args[0]);
        } else {
            cm = new ConfigurationManager(HelloNWorld.class.getResource("hellongram.config.xml"));
        }

        // allocate the recognizer
        System.out.println("Loading...");
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        printInstructions();

        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestResultNoFiller();
                System.out.println("You said: " + resultText + '\n');
                String out = ("Say " + "\"" + resultText + "\"");
				Process p = Runtime.getRuntime().exec(out);	
            } else {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }


    /** Prints out what to say for this demo. */
    private static void printInstructions() {
        System.out.println("Sample sentences:\n" +
                "the green one right in the middle\n" +
                "the purple one on the lower right side\n" +
                "the closest purple one on the far left side\n" +
                "the only one left on the left\n\n" +
                "Refer to the file hellongram.test for a complete list.\n");
    }
	
}
