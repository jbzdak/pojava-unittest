package cx.ath.jbzdak.pojava.zderzenia;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Assert;
import org.junit.Test;

public class BallTests {

    @Test
    public void testBallPosition(){
        Ball ball = new Ball(1, 2, 3, 4, 5, 6);
        Assert.assertEquals(ball.getX(), 1, 0.001);
        Assert.assertEquals(ball.getY(), 2, 0.001);
    }

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

    @Test
    public void testColliosion(){
        Ball b1 = new Ball(0, 0, 0, 1, 1, 1);
        Ball b2 = new Ball(0, 2, 0, -1, 1, 1);
        b1.collision(b2);
        Assert.assertEquals(b1.getVX(), 0, 0.001);
        Assert.assertEquals(b2.getVX(), 0, 0.001);

        Assert.assertEquals(b1.getVY(), -1, 0.001);
        Assert.assertEquals(b2.getVY(), 1, 0.001);

    }

    @Test
    public void testCopyConstructor(){
        Ball b1 = new Ball(1, 2, 3, 4, 5, 6);
        Ball b2 = new Ball(b1);

        Assert.assertArrayEquals(b1.coords, b2.coords, 0.001);
        Assert.assertNotSame(b1.coords, b2.coords);

    }



//    @Test
//    public void testRotationConstructor(){
//        Ball b1 = ballFromPositions(1, 1);
//        Ball b2 = ballFromPositions(1, 2);
//
//        Rotation rot = BallUtils.calculateRotationForCollision(b1, b2);
//
//        Vector3D rb2 = rot.applyTo(b2.getPosition());
//
//        Assert.assertEquals(rb2.getY(), b1.getY(), 0.01);
//
//
//
//    }

    @Test
    public void testCollision1(){
        Ball b1 = new Ball(0, 0, 0, 0, 1, 1);
        Ball b2 = new Ball(2, 0, 2, 0, 1, 1);

        ImmutablePair<Vector3D, Vector3D> result = BallUtils.doCollision(b1, b2);

        Vector3D v1 = result.getLeft();
        Vector3D v2 = result.getRight();

        Assert.assertEquals(v1.getX(), 2.0, 0.001);
        Assert.assertEquals(v2.getX(), 0, 0.001);
    }

    @Test
    public void testCollision2(){
        Ball b1 = new Ball(0, 0, 0, 0, 1, 1);
        Ball b2 = new Ball(0, 2, 0, 2, 1, 1);

        ImmutablePair<Vector3D, Vector3D> result = BallUtils.doCollision(b1, b2);

        Vector3D v1 = result.getLeft();
        Vector3D v2 = result.getRight();

        Assert.assertEquals(v1.getY(), 2.0, 0.001);
        Assert.assertEquals(v2.getY(), 0, 0.001);
    }

    @Test
    public void testCollision3(){
        Ball b1 = new Ball(0, 0, 0, 12, 1, 1);
        Ball b2 = new Ball(2, 0, 2, -9, 1, 1);

        ImmutablePair<Vector3D, Vector3D> result = BallUtils.doCollision(b1, b2);

        Vector3D v1 = result.getLeft();
        Vector3D v2 = result.getRight();

        Assert.assertEquals(v1.getX(), 2.0, 0.001);
        Assert.assertEquals(v2.getX(), 0, 0.001);

        Assert.assertEquals(v1.getY(), 12.0, 0.001);
        Assert.assertEquals(v2.getY(), -9.0, 0.001);
    }

//    @Test
    public void  doCollision(){
        Ball b1 = ballFromPositions(1, 1);
        Ball b2 = ballFromPositions(1, 2);
        BallUtils.doCollision(b1, b2);
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

