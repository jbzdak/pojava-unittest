package cx.ath.jbzdak.pojava.zderzenia;

import org.junit.Assert;
import org.junit.Test;

public class BallTests {

    @Test
    public void testMove(){
        Ball ball = new Ball(0, 0, 1, 2, 5, 1);
        ball.iteration(0.1);
        Assert.assertEquals(ball.getX(), 0.1, 0.001);
        Assert.assertEquals(ball.getY(), 0.2, 0.001);
    }

    @Test
    public void testDistance1(){
        double distance = BallUtils.distance(
                ballFromPositions(0, 0),
                ballFromPositions(0, 1)
        );
        Assert.assertEquals(1.0, distance, 0.0001);
    }

    @Test
    public void testDistance2(){
        double distance = BallUtils.distance(
                ballFromPositions(1, 0),
                ballFromPositions(0, 0)
        );
        Assert.assertEquals(1.0, distance, 0.0001);
    }

    @Test
    public void testDistance3(){
        double distance = BallUtils.distance(
                ballFromPositions(0, 1),
                ballFromPositions(0, 0)
        );
        Assert.assertEquals(1.0, distance, 0.0001);
    }

    @Test
    public void testOverlaps(){
        Assert.assertTrue(BallUtils.overlaps(
                ballFromPositions(0, 0, 1),
                ballFromPositions(0, 0.5, 1)
        ));
    }

    @Test
    public void testOverlaps1(){
        Assert.assertTrue(BallUtils.overlaps(
                ballFromPositions(0, 0, 1),
                ballFromPositions(0, 2, 1)
        ));
    }

    Ball ballFromPositions(double x, double y){
        return new Ball(
                x, y, 0, 0, 0, 0
        );
    }

    Ball ballFromPositions(double x, double y, double radius){
        return new Ball(
                x, y, 0, 0, 0, radius
        );
    }
}

