package cx.ath.jbzdak.pojava.zderzenia;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final List<Ball> balls = new ArrayList<Ball>();
    private final double boxWidth, boxHeight;

    public Engine(double boxWidth, double boxHeight) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
    }

    public void addBalls(Ball b){
        balls.add(b);
    }

    public List<Ball> getBalls(){
        return Collections.unmodifiableList(balls);
    }


    private Ball createRandomBall(double mass, double radius){
        return new Ball(
                RANDOM.nextDouble()*boxWidth,
                RANDOM.nextDouble()*boxHeight,
                RANDOM.nextDouble()*boxWidth*.1,
                RANDOM.nextDouble()*boxHeight*.1,
                mass,
                radius
        );
    }



    public void fillWithBalls(int ballCount, double mass, double radius){
        for(int ii=0; ii < ballCount; ii++){
            addBalls(createRandomBall(mass, radius));
        }
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
