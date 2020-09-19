//************************************************************************
// File: RingBuffer.java         Assignment 8
// 
// Author: Ryan Yang           Email: y.ryan.yang@gmail.com
//
// Class: RingBuffer 
//
// Description  :  RingBuffer template
//
//************************************************************************

public class RingBuffer {
    private static boolean DEBUG = false;

    private double[] rb;          // items in the buffer
    private int first=0;            // index for the next dequeue or peek
    private int last=0;             // index for the next enqueue
    private int size=0;             // number of items in the buffer

    // create an empty buffer, with given max capacity
    public RingBuffer(int capacity) {
        rb = new double[capacity];
    }

    // return number of items currently in the buffer
    public int size() {
        return size;
    }

    // is the buffer empty (size equals zero)?
    public boolean isEmpty() {
        return (size()==0);
    }

    // is the buffer full (size equals array capacity)?
    public boolean isFull() {
        return (size() == rb.length);
    }

    // add item x to the end
    public void enqueue(double x) {
        DEBUG("enqueue, Before call isFull");

        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        
        else {
            rb[last]=x;
            last++;
            last = last % rb.length;
            size++;
        }

    }

    private static void DEBUG(String s) {
        if (DEBUG) {
            System.out.println( "DEBUG: " + s);
        }
    }
    // delete and return item from the front
    public double dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        
        else
        {
            int temp=first;//save the initial value
            first++;
            first = first % rb.length;  

            size--;

            return rb[temp];//it's annoying that you can't execute stuff after the return. I mindlessly moved the first++ up without noticing the issues.
        }
    }

    // return (but do not delete) item from the front
    public double peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        else
         return rb[first];

    }

    // a simple test of the constructor and methods in RingBuffer
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(N);

        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }

        double t = buffer.dequeue();
        System.out.println(t);
        buffer.enqueue(t);
        System.out.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }

}
//2,3,4,5,1
//9,6