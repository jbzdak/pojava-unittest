package cx.ath.jbzdak.pojava.zderzenia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {

    private ArrayList<Ball> balls = new ArrayList<Ball>();

    public void addBalls(Ball b){
        balls.add(b);
    }

    public List<Ball> getBalls(){
        return Collections.unmodifiableList(balls);
    }

    public void iterate(double dt){
        for (int ii=0;ii<balls.size(); ii++){
            Ball b1 = balls.get(ii);
            for (int jj=ii+1; jj<balls.size(); jj++){
                Ball b2 = balls.get(jj);
                if (BallUtils.overlaps(b1, b2)){
                   b1.collision(b2);
                }
            }
            b1.iteration(dt);
        }
    }
}
