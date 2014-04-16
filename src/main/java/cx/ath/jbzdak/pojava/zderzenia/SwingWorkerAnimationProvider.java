package cx.ath.jbzdak.pojava.zderzenia;

import cx.ath.jbzdak.pojava.zderzenia.gui.AbstractAnimationProvider;

import javax.swing.SwingWorker;

/**
 * Swing worker animation provider.
 */
public class SwingWorkerAnimationProvider extends AbstractAnimationProvider {


    boolean started;

    SwingWorker<Void, Void> currentWorker;

    @Override
    public void start() {
        started=true;
        scheduleWorkerIteration();
    }

    void scheduleWorkerIteration(){
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
        currentWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                /**
                 * Perform calculations in background.
                 */
                for(int ii = 0; ii < 10; ii++){
                    engine.iterate(0.01);
                    System.out.print("x");
                }
                return null;
            }

            @Override
            protected void done() {
                /**
                 * Repain the panel
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


        currentWorker.execute();
    }

    @Override
    public void stop() {
        started=false;
        currentWorker.cancel(true);
    }
}
