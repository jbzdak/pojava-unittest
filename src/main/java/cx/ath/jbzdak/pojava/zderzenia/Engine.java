package cx.ath.jbzdak.pojava.zderzenia;

import java.util.List;

public class Engine {


    /**
     * We force single collision per iteration (not so good idea).
     */
    private void checkCollisions(BallContainer ballContainer, Ball b1, int ii){

        List<Ball> balls = ballContainer.getBalls();

        double boxWidth = ballContainer.getBoxWidth();

        double boxHeight = ballContainer.getBoxHeight();

        if(b1.getX()-b1.getRadius()<0 || b1.getX()+b1.getRadius()>boxWidth){
            b1.setVX(-b1.getVX());
            return;
        }
        if(b1.getY()-b1.getRadius()<0 || b1.getY()+b1.getRadius()>boxHeight){
            b1.setVY(-b1.getVY());
            return;
        }
        for (int jj=ii+1; jj<balls.size(); jj++){
            Ball b2 = balls.get(jj);
            if (BallUtils.overlaps(b1, b2)){
                b1.collision(b2);
                return;
            }
        }
    }

    public void iterate(BallContainer ballContainer, double dt){

        List<Ball> balls = ballContainer.getBalls();

        for (int ii=0;ii<balls.size(); ii++){
            Ball b1 = balls.get(ii);
            checkCollisions(ballContainer, b1, ii);
            b1.iteration(dt);
        }
    }
}
