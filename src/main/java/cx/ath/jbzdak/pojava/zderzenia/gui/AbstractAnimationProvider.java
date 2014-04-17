package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.BallContainer;
import cx.ath.jbzdak.pojava.zderzenia.Engine;

public  abstract class AbstractAnimationProvider implements AnimationProvider {

    protected SimulationPanel2D simulationPanel2D;

    protected Engine engine;

    protected BallContainer ballContainer;

    protected boolean started = false;

    @Override
    public void setBallContainer(BallContainer ballContainer) {
        this.ballContainer = ballContainer;

    }

    @Override
    public void setSimulationPanel(SimulationPanel2D simPanel) {
        simulationPanel2D = simPanel;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void destroy(){
        if(isStarted()){
            stop();
        }
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;

    }


}
