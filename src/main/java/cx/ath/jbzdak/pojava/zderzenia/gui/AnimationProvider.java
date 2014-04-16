package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Engine;

/**
 * Interface of class that provides us with animation capabilities.
 */
public interface AnimationProvider {

    /**
     * Engine to do calculations.
     */
    void setEngine(Engine engine);

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
     * Destroy this instance
     */
    void destroy();
}
