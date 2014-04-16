package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Engine;

public  abstract class AbstractAnimationProvider implements AnimationProvider {

    protected SimulationPanel2D simulationPanel2D;

    protected Engine engine;

    @Override
    public void setSimulationPanel(SimulationPanel2D simPanel) {
        simulationPanel2D = simPanel;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void destroy(){

    }
}
