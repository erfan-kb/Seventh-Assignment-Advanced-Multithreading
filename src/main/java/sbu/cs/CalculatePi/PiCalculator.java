package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */
    private static final int THREAD_COUNT = 4;

    private static final int SCALE = 10000;

    public String calculate(int floatingPoint)
    {
        BigDecimal pi = BigDecimal.ZERO;

        BigDecimal term;

        BigDecimal one = new BigDecimal(1);

        boolean sign = true;



        for (int i = 0; i < THREAD_COUNT; i++) {

            int start = i * SCALE / THREAD_COUNT;

            int end = (i + 1) * SCALE / THREAD_COUNT;

            PiThread thread = new PiThread(start, end, sign);

            thread.start();

            sign = !sign;

            try {

                thread.join();

                pi = pi.add(thread.getSum());

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }
        return pi.setScale(floatingPoint, RoundingMode.HALF_UP).toString();
    }
    private class PiThread extends Thread {

        private final int start;

        private final int end;

        private final boolean sign;

        private BigDecimal sum;



        public PiThread(int start, int end, boolean sign) {

            this.start = start;

            this.end = end;

            this.sign = sign;

            this.sum = BigDecimal.ZERO;

        }



        public void run() {

            for (int i = start; i < end; i++) {

                BigDecimal numerator =

                        new BigDecimal(sign ? 1 : -1)

                                .multiply(new BigDecimal(4 * i + 1))

                                .multiply(new BigDecimal(4 * i + 3))

                                .multiply(new BigDecimal(4 * i + 5));

                BigDecimal denominator =

                        new BigDecimal(2)

                                .multiply(new BigDecimal(4 * i + 2))

                                .multiply(new BigDecimal(4 * i + 4))

                                .multiply(new BigDecimal(4 * i + 6));

                sum = sum.add(numerator.divide(denominator, SCALE, RoundingMode.HALF_UP));

            }

        }



        public BigDecimal getSum() {

            return sum;

        }

    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
    }
}