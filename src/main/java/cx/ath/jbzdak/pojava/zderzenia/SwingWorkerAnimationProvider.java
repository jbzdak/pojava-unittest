package cx.ath.jbzdak.pojava.zderzenia;

import cx.ath.jbzdak.pojava.zderzenia.gui.AbstractAnimationProvider;
import org.apache.commons.lang3.SerializationUtils;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;

/**
 * Swing worker animation provider.
 */
public class SwingWorkerAnimationProvider extends AbstractAnimationProvider {

    SwingWorker<BallContainer, Void> currentWorker;

    @Override
    public void start() {
        super.start();
        scheduleWorkerIteration();
    }

    void scheduleWorkerIteration(){

        /**
         * Now we create a working copy so calculations are not done on balls that are currently
         * being displayed.
         */
        final BallContainer workingCopy = SerializationUtils.clone(ballContainer);

        /**
         * Swing worker implements following use case: we do some calculations in the backhround thread
         * and then we do something with the resuts (in this case results are not used, as they are
         * stored inside the engine variable.
         *
         * Note that this is a shortcut, and we really should just make copy of balls list and return
         * them explicitly from doInBackground. Only reason it works is that: no other thread (other than SwingWorker)
         * writes to ball list in engine, and proper locking forces memory synchronisation.
         *
         * Basically worker works in this way: first doInBackground is done in background thread, then
         * done method is called in EDT thread. In our case we'll do 10 iterations of engine in background
         * and then display them (from EDT).
         *
         */
        currentWorker = new SwingWorker<BallContainer, Void>() {
            @Override
            protected BallContainer doInBackground() throws Exception {
                /**
                 * Perform calculations in background.
                 */
                for(int ii = 0; ii < 10; ii++){
                    engine.iterate(workingCopy, 0.01);
                    System.out.print("x");
                }
                return workingCopy;
            }

            @Override
            protected void done() {

                try {
                    /**
                     * Pass the calculation results to the ballContainer
                     */
                    ballContainer.setBalls(get().getBalls());
                } catch (InterruptedException e) {
                    return;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }

                /**
                 * Repaint the panel
                 */
                simulationPanel2D.repaint();
                /**
                 * If stop wasn't requested schedule next iteration.
                 */
                if(started){
                    scheduleWorkerIteration();
                }
                System.out.print("y\n");
            }
        };

        /**
         * Start the worker.
         */
        currentWorker.execute();
    }

    @Override
    public void stop() {
        super.stop();
        currentWorker.cancel(true);
    }
}
