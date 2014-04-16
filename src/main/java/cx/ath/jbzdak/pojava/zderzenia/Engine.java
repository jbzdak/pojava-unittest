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

    void setBalls(List<Ball> balls){
        this.balls.clear();
        this.balls.addAll(balls);
    }

    public double getBoxWidth() {
        return boxWidth;
    }

    public double getBoxHeight() {
        return boxHeight;
    }

    private Ball createRandomBall(double mass, double radius){
        return new Ball(
                RANDOM.nextDouble()*(boxWidth-2*radius)+radius,
                RANDOM.nextDouble()*(boxHeight-2*radius)+radius,
                RANDOM.nextDouble()*boxWidth*.1,
                RANDOM.nextDouble()*boxHeight*.1,
                mass,
                radius
        );
    }

    private boolean overlapsWithOtherBalls(Ball b){

        for (Ball b2: balls){
            if (BallUtils.overlaps(b, b2)) return true;
        }

        return false;
    }

    public void fillWithBalls(int ballCount, double mass, double radius){
        for(int ii=0; ii < ballCount; ii++){
            Ball b=null;
            /**
             * Adds new ball so it doesn't overlap with aleady existing ones.
             */
            for (int jj=0; jj<100;jj++){
                Ball candidate = createRandomBall(mass, radius);
                if(!overlapsWithOtherBalls(candidate)){
                    b = candidate;
                    break;
                }
            }
            if (b==null){
                throw new IllegalStateException("Couldn't add ball");
            }
            addBalls(b);
        }
    }

    /**
     * We force single collision per iteration (not so good idea).
     */
    private void checkCollisions(Ball b1, int ii){
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

    public void iterate(double dt){

        for (int ii=0;ii<balls.size(); ii++){
            Ball b1 = balls.get(ii);
            checkCollisions(b1, ii);
            b1.iteration(dt);
        }
    }
}
