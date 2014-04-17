package cx.ath.jbzdak.pojava.zderzenia;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallContainer implements Serializable {

    protected final List<Ball> balls = new ArrayList<Ball>();
    protected final double boxWidth;
    protected final double boxHeight;

    public BallContainer(double boxWidth, double boxHeight) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
    }

    public void addBalls(Ball b){
        balls.add(b);
    }

    public List<Ball> getBalls(){
        return Collections.unmodifiableList(balls);
    }

    public void setBalls(List<Ball> balls){
        this.balls.clear();
        this.balls.addAll(balls);
    }

    public double getBoxWidth() {
        return boxWidth;
    }

    public double getBoxHeight() {
        return boxHeight;
    }

    private Ball createRandomBall(SecureRandom random, double mass, double radius){
        return new Ball(
                random.nextDouble()*(boxWidth-2*radius)+radius,
                random.nextDouble()*(boxHeight-2*radius)+radius,
                random.nextDouble()*boxWidth*.1,
                random.nextDouble()*boxHeight*.1,
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

        final SecureRandom random = new SecureRandom();

        for(int ii=0; ii < ballCount; ii++){
            Ball b=null;
            /**
             * Adds new ball so it doesn't overlap with aleady existing ones.
             */
            for (int jj=0; jj<100;jj++){
                Ball candidate = createRandomBall(random, mass, radius);
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
}
