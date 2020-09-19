
import java.lang.Math;
public class GuitarHeroShepardTone {
    
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

	int[] tones = new int[3];
	for (int i=0;i<3 ;i++ ) {
		tones[i]=12*i;
	}
	int counter=0;
        // the main input loop
        while (true) {
        	counter++;
        	if (counter % (10*AUDIO_PER_DRAW )== 0) 
        	{
				for (int i=0;i<3 ;i++ ) 
				{
					string[tones[i]].pluck();   
					tones[i]++;	
					tones[i] = tones[i] % 36;	
				}     		
        	}       	

	    // compute the superposition of the samples for duration
	    double sample = 0;
	    for (int i=0;i<37 ;i++ ) {
	    	double coefficient = Math.sin(i*Math.PI/36.0);
	    	sample+= coefficient*string[i].sample();
	    }

	    // send the result to standard audio
	    StdAudio.play(sample);

	   // StdAudio.play(string[24].sample());

	    // advance the simulation of each guitar string by one step
	    for (int i=0; i<37;i++ ) {
	    	string[i].tic();
	    }

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


} // end of class