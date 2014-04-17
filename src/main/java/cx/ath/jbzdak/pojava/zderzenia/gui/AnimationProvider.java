package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.BallContainer;
import cx.ath.jbzdak.pojava.zderzenia.Engine;

/**
 * Interface of class that provides us with animation capabilities.
 */
public interface AnimationProvider {

    /**
     * Engine to do calculations.
     */
    void setEngine(Engine engine);

    void setBallContainer(BallContainer ballContainer);

    /**
     * Panel to display calculations upon.
     */
    void setSimulationPanel(SimulationPanel2D simPanel);

    /**
     * Methos to start simulation (and animation)
     */
    void start();

    /**
     * Methos to stop simulation (and animation)
     */
    void stop();

    /**
     *
     * @return true if it was started (and not stopped).
     */
    boolean isStarted();

    /**
     * Destroy this instance
     */
    void destroy();
}
