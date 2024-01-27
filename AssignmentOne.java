// Nicolas Rodriguez
// UCF Spring 2024 - COP 4520 Assignment One


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// class for handling threads
class PrimesThread extends Thread {
    // ints to track range
    private int lo;
    private int hi;

    // array to track primes
    private ArrayList<Integer> primes;

    // overloaded constructor for setting range and recieving primes array
    public PrimesThread(int lo, int hi, ArrayList<Integer> primes) {
        this.lo = lo;
        this.hi = hi;
        this.primes = primes;
    }

    // here we need to override the run method from threads
    @Override
    public void run() {
        // loop through range and find primes
        for (int i = lo; i < hi; i++) {
            if (isPrime(i)) {
                // implement safety since all threads will be modifying this
                synchronized (primes) {
                    primes.add(i);
                }
            }
        }
    }

    // class method for checking primes
    private boolean isPrime(int n) {
        // quick check for 1 or less than 1
        if (n <= 1) return false;

        // optimal prime checker, return false remainder is 0
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n%i == 0) return false;
        }

        // end of loop, so true
        return true;
    }
}

// main class
public class AssignmentOne {
    public static void main(String[] args) throws InterruptedException, IOException {
        int lastNum = 100000000;
        int nThreads = 8;

        // array to track primes we have foubd
        ArrayList<Integer> primes = new ArrayList<>();
        
        // array to instantiate threads
        ArrayList<PrimesThread> threads = new ArrayList<>();

        // track how long it takes
        long startTime = System.currentTimeMillis();

        // loop to instantiate threads and give range to each
        // inspiration taken from canvas slides
        int range = lastNum / nThreads;
        for (int i = 0; i < nThreads; i++) {
            // lo end of range 
            int lo = i * range + 1;
            // hi end of range, if last thread make hi 10^8
            int hi = (i == nThreads-1) ? lastNum : (i+1) * range;
            
            // make thread, give range and primes array to track
            PrimesThread thread = new PrimesThread(lo, hi, primes);

            // add thread to array and start
            threads.add(thread);
            thread.start();
        }

        // ensure all threads finish
        for (PrimesThread thread : threads) {
            thread.join();
        }

        // stop tracking time and calucate
        long stopTime = System.currentTimeMillis();
        long executionTime = stopTime - startTime;

        // sum all primes
        long sum = 0;
        for (long prime : primes) 
            sum += prime;

        // sort the primes and get last 10
        Collections.sort(primes);
        List<Integer> lastTenPrimes = primes.subList(primes.size()-10, primes.size());

        // write results to primes.txt
        try (
        FileWriter writer = new FileWriter("primes.txt")) {
            writer.write("Execution Time: " + executionTime + "ms    Primes Found: " + primes.size() + "    Sum: " + sum + "\n");
            writer.write("Top Ten Primes: " + lastTenPrimes);
        }
    }
}