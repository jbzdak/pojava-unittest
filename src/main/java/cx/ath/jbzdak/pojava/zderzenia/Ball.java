package cx.ath.jbzdak.pojava.zderzenia;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.Serializable;

public class Ball implements Serializable {

    private static final long serialVersionUID = 1;

    double[] coords = new double[6];
	
	public Ball(double x, double y, double vx, double vy, double mass, double radius){
        coords = new double[]{x, y, vx, vy, mass, radius};
    }

    /**
     * This is the copy constructor
     * @param ball ball to be copied
     */
    public Ball(Ball ball){
        System.arraycopy(
                ball.coords, 0,
                coords, 0, coords.length
        );
    }

	public void iteration(double dt){
        coords[0]+=coords[2]*dt;
        coords[1]+=coords[3]*dt;
    }

    public void collision(Ball other){

        ImmutablePair<Vector3D, Vector3D> collisionResults =
                BallUtils.doCollision(this, other);

        Vector3D thisV = collisionResults.getLeft();
        Vector3D otherV = collisionResults.getRight();

        this.setVX(thisV.getX());
        this.setVY(thisV.getY());

        other.setVX(otherV.getX());
        other.setVY(otherV.getY());

    }

    /**
     * Returns position as 3d vector with zeroed z coordinate
     */
    Vector3D getPosition(){
        return new Vector3D(getX(), getY(), 0);
    }

    Vector3D getVelocity(){
        return new Vector3D(getVX(), getVY(), 0);
    }

    public double getX(){
        return coords[0];
    }

    public double getY(){
        return coords[1];
    }

    public double getVX(){
        return coords[2];
    }

    public double getVY(){
        return coords[3];
    }

    public double getRadius(){
        return coords[5];
    }

    public double getMass(){
        return coords[4];
    }

    protected void setVX(double value){
        coords[2] = value;
    }

    protected void setVY(double value){
        coords[3] = value;
    }

}
