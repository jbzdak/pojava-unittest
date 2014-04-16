package cx.ath.jbzdak.pojava.zderzenia.gui;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Now we'll do the animation the hardest way. Using raw threads.
 *
 *
 */
public class ThreadAnimationProvider extends AbstractAnimationProvider {


    /**
     * Lock --- lock guarantees that only one thread may acquire it at
     * any given moment. So either calculationThread does the calculations
     * or repaintTimer (EDT thread) paints them.
     *
     * 1. repaint timer won't see intermediate results (it is not very important)
     * 2. EDT will see changes made by calculationThread. Without lock JVM
     * could store all changes to the engine inside local cache of calculationThread,
     * not synchronized with main memory.
     *
     */
    private final Lock threadLock = new ReentrantLock();

    /**
     * Thread for prforming the calculations.
     */
    private Thread calculationThread;

    /**
     * Timer that paints calculation results.
     */
    private Timer repaintTimer;
    {
        repaintTimer = new Timer(20, null);
        repaintTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                threadLock.lock(); // Note lock acquisition
                System.out.print("y\n");
                try{
                    simulationPanel2D.repaint();
                }finally {
                    threadLock.unlock();
                }
            }
        });
    }

    @Override
    public void start() {

        /**
         * Basically you shouldn't kill a thread forcibly, you should only ask "dear thread please die".
         * In this example I do it using interrupts. So if thread reclieves interrupt it'll die.
         */
        Runnable threadRunnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(Thread.interrupted()){
                        return; //If thread was interrupted we finish it.
                    }
                    System.out.print("x");
                    threadLock.lock();
                    try{
                        engine.iterate(0.01);
                        Thread.sleep(1); //If thread was interrupted we finish it.
                    } catch (InterruptedException e) {
                        return;
                    } finally {
                        threadLock.unlock();
                    }
                }
            }
        };

        calculationThread = new Thread(threadRunnable);
        calculationThread.start();
        repaintTimer.start();
    }

    @Override
    public void stop() {
        repaintTimer.stop();
        calculationThread.interrupt(); //Ask theread to die
        try {
            calculationThread.join(); //Wait for it to die.
        } catch (InterruptedException e) {
            return;
        }
    }
}
