import java.lang.Math;
public class GuitarHero {
    
    static boolean DEBUG = false;
	static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";    


    public static void main(String[] args) {
    

	int    DRAW_SAMPLE_RATE = 20;    // draw at a rate of 20/sec
	int    AUDIO_PER_DRAW   = StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE;

	int    PLAY_TIME        = 10;    // target 60 seconds display window
	int    XWIDTH           = DRAW_SAMPLE_RATE * PLAY_TIME;

	GuitarString[] string = new GuitarString[37];
	for (int i=0; i<37 ;i++ ) {
		double temp = 440*Math.pow(2.0,(i-24.0)/12);
		string[i] = new GuitarString(temp);
	}

        // Set up parameters for visualization
	StdDraw.setCanvasSize(768, 256);
	StdDraw.setPenColor(StdDraw.RED);
	StdDraw.setXscale(0, XWIDTH);
	StdDraw.setYscale(-1, 1);

	// fence post
	double xprev = 0, yprev = 0;

        // the main input loop
        while (true) {

            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {
 
                // the user types this character
                char key = StdDraw.nextKeyTyped();

                if (keyboard.indexOf(key)!= -1) {
                	string[keyboard.indexOf(key)].pluck();
                }
            }

	    // compute the superposition of the samples for duration
	    double sample = 0;
	    for (int i=0;i<37 ;i++ ) {
	    	sample+=string[i].sample();
	    }

	    // send the result to standard audio
	    StdAudio.play(sample);

	   // StdAudio.play(string[24].sample());

	    // advance the simulation of each guitar string by one step
	    for (int i=0; i<37;i++ ) {
	    	string[i].tic();
	    }
	    /*string[0].tic();
	    string[1].tic();
	    string[2].tic();
	    string[3].tic();
	    string[4].tic();
	    string[5].tic();
	    string[6].tic();
	    string[7].tic();
	    string[8].tic();
	    string[9].tic();
	    string[10].tic();	    
	    string[11].tic();
	    string[12].tic();
	    string[13].tic();
	    string[14].tic();
	    string[15].tic();	    
	    string[16].tic();
	    string[17].tic();
	    string[18].tic();
	    string[19].tic();
	    string[20].tic();	    
	    string[21].tic();
	    string[22].tic();
	    string[23].tic();
	    string[24].tic();
	    string[25].tic();	    
	    string[26].tic();
	    string[27].tic();
	    string[28].tic();
	    string[29].tic();
	    string[30].tic();		    
	    string[31].tic();
	    string[32].tic();
	    string[33].tic();
	    string[34].tic();
	    string[35].tic();    
	    string[36].tic();*/

	    // Decide if we need to draw. 
	    //   Audio sample rate is StdAudio.SAMPLE_RATE per second
	    //   Draw sample rate is DRAW_SAMPLE_RATE
	    //   Hence, we draw every StdAudio.SAMPLE_RATE / DRAW_SAMPLE_RATE
	    if (string[0].time() % AUDIO_PER_DRAW == 0) {
		StdDraw.line(xprev, yprev, xprev+1, sample);
		xprev ++;
		yprev = sample;
		
		if (xprev % XWIDTH ==0) {
			StdDraw.clear();
			xprev = xprev%XWIDTH;
		}
	    } // end of if

	} // end of while

    } // end of main

    private static void DEBUG(String s) {
        if (DEBUG) {
            System.out.println( "DEBUG: " + s);
        }
    }

} // end of class