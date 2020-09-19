import java.util.Random;

public class GuitarString {

    private RingBuffer          buffer; // ring buffer
    private static final double DECAY = 0.996;
    private static final int RATE = 44100;
    private int N;
    private static int time = 0;

    static boolean DEBUG = true;
    // create a guitar string of the given frequency
    public GuitarString(double frequency) 
    {
        this.N = (int) Math.round(RATE/frequency);
        buffer = new RingBuffer(N);
        for (int i=0;i<N ;i++ ) {
           buffer.enqueue(0); 
        }     
    }

    // create a guitar string with size & initial values given by the array
    public GuitarString(double[] init) 
    {
        N = init.length;

        buffer = new RingBuffer(N);
        for (int i=0;i<N ;i++ ) 
        {
            buffer.enqueue(init[i]);    
        }

    }

    // pluck the guitar string by replacing the buffer with white noise
    public void pluck() {
        for (int i=0; i<N ;i++ ) {
            //DEBUG(" before pos" + i);
            double t = Math.random()-0.5;
           // DEBUG("random is " +t);
            buffer.dequeue();
            buffer.enqueue(t);
            //DEBUG(" after pos" + i);
        }

    }

    // advance the simulation one time step
    public void tic() {
        double t = buffer.dequeue();
        buffer.enqueue(DECAY*0.5*( t+buffer.peek() ) );

        time++;

    }

    // return the current sample
    public double sample() {
        return buffer.peek();

    }

    // return number of times tic was called
    public int time() {
        return this.time;

    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        GuitarString testString = new GuitarString(samples);
        for (int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }

    private static void DEBUG(String s)
    {
        if (DEBUG) {
            System.out.println("DEBUG"+s);
        }
    }

}
