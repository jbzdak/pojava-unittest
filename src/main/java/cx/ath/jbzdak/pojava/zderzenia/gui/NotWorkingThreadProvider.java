package cx.ath.jbzdak.pojava.zderzenia.gui;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotWorkingThreadProvider extends AbstractAnimationProvider {

    Thread animationThread;

    Timer timer;

    private boolean isStarted;

    NotWorkingThreadProvider(){
        // We create timer. First argument is timeout (in miliseconds)
        // second is listener to be called after timeout.
        // So we will do iteration and repaint engine ten times a second.
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null){
                    simulationPanel2D.repaint();
                }
            }
        });
    }

    @Override
    public void start() {
        super.start();
        isStarted = true;

        animationThread = new Thread(){
            @Override
            public void run() {
                while (true){
                    if (!isStarted){
                        return;
                    }
                    engine.iterate(ballContainer, 0.01);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        //noop
                    }
                }
            }
        };

        timer.start();
        animationThread.start();
    }

    @Override
    public void stop() {
        super.stop();

        isStarted = false;
        try {
            animationThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        timer.stop();

    }
}
