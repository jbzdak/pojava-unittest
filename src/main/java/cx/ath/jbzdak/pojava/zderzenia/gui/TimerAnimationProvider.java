package cx.ath.jbzdak.pojava.zderzenia.gui;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Easiest implementation of animation. Does all the calculations in Event Thread (so will only work for not
 * intensive applications).
 *
 *
 */
public class TimerAnimationProvider extends AbstractAnimationProvider {

    private final Timer timer;

    TimerAnimationProvider(){
        // We create timer. First argument is timeout (in miliseconds)
        // second is listener to be called after timeout.
        // So we will do iteration and repaint engine ten times a second.
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine != null){
                    engine.iterate(0.05);
                    simulationPanel2D.repaint();
                }
            }
        });
    }

    @Override
    public void start() {
        // Just start the timer
        timer.start();
    }

    @Override
    public void stop() {
        //Stop the timer
        timer.stop();
    }
}
