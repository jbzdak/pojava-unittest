package cx.ath.jbzdak.pojava.zderzenia;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class BallUtils{

    public static double distance(Ball b1, Ball b2){
        return Math.sqrt(
            Math.pow(
               b1.getX()-b2.getX(), 2
            ) +
            Math.pow(
                b1.getY()-b2.getY(), 2
            )
        );
    }

    public static boolean overlaps(Ball b1, Ball b2){
        return distance(b1, b2) <= (b1.getRadius() + b2.getRadius());
    }

//    static Rotation calculateRotationForCollision(Ball balla, Ball ballb){
//
//    }

    /**
     * This performs a collison of two balls.
     *
     * Algorithm is as follows: First we transform collision to coordinates
     * in which balla is stationary in (0, 0) position, and ball2 is
     * moving in position (x, 0).
     *
     * Then we solve this collision.
     *
     * And transform back.
     *
     * @param balla
     * @param ballb
     * @return
     */
    static ImmutablePair<Vector3D, Vector3D> doCollision(Ball balla, Ball ballb){

        Vector3D aPos = balla.getPosition();
        Vector3D bPos = ballb.getPosition();

        bPos = bPos.subtract(aPos);
        double distance = bPos.getNorm();
        Rotation rotation = new Rotation(bPos, new Vector3D(distance, 0, 0));

        Vector3D aVelocity = balla.getVelocity();
        Vector3D bVelocity = ballb.getVelocity();

        Vector3D VelocityMovedBy = aVelocity;

        bVelocity = bVelocity.subtract(aVelocity);
        aVelocity = Vector3D.ZERO;
        bVelocity = rotation.applyTo(bVelocity);

        ImmutablePair<Double, Double> result = makeCollision1D(bVelocity.getX(), ballb.getMass(), balla.getMass());

        bVelocity = new Vector3D(result.getLeft(), bVelocity.getY(), 0);
        aVelocity = new Vector3D(result.getRight(), aVelocity.getY(), 0);

        aVelocity = rotation.applyInverseTo(aVelocity).add(VelocityMovedBy);
        bVelocity = rotation.applyInverseTo(bVelocity).add(VelocityMovedBy);

        return new ImmutablePair<Vector3D, Vector3D>(aVelocity, bVelocity);

    }


    /**
     * Simplest case of collision. Collision in 1d with stationary object
     */
    static ImmutablePair<Double, Double> makeCollision1D(double v1, double m1, double m2){
        double v1after = -2*m1*v1/(m1 + m2) + v1;
        double v2after = 2*m1*v1/(m1 + m2);
        return new ImmutablePair<Double, Double>(v1after, v2after);
    }


//    static Rotation calculateRotationForCollision(Ball balla, Ball ballb){
//        Vector3D b1 = balla.getPosition();
//        Vector3D b2 = ballb.getPosition();
//
//        Vector3D centerVector = b1.subtract(b2);
//        double distance = centerVector.getNorm();
//
//        Rotation rotation = new Rotation(centerVector, new Vector3D(distance, 0, 0));
//        return rotation;
//    }
//
//    CollisionEvent collideBalls(Ball balla, Ball ballb){
//
//
//        Vector3D b1rotated = rotation.applyTo(b1);
//        Vector3D b2rotated = rotation.applyTo(b2);
//
//
//
//
//
//
//    }
//
//    CollisionEvent collide1D(double x1, double x2, double v1, double v2){
//
//    }
}
