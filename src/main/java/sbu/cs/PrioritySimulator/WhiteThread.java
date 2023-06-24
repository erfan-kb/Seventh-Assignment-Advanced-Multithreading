package sbu.cs.PrioritySimulator;

import java.util.concurrent.CountDownLatch;

public class WhiteThread extends ColorThread {
    private int delay;
    private CountDownLatch CDLatch;

    public WhiteThread(int delay,CountDownLatch CDLatch ){
        this.CDLatch = CDLatch;
        this.delay = delay;
    }
    private static final String MESSAGE = "hi finished blacks, hi finished blues!";

    void printMessage() {
        super.printMessage(new Message(this.getClass().getName(), getMessage()));
    }

    @Override
    String getMessage() {
        return MESSAGE;
    }

    @Override
    public void run() {
        try
        {
            Thread.currentThread().setPriority(MIN_PRIORITY);
            Thread.sleep(delay);
            CDLatch.countDown();    // use countDown() method of the CountDownLatch
            printMessage();
        }

        // use catch block to handle InterruptedException
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}