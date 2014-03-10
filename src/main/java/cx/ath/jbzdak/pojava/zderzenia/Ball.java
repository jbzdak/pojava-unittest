package cx.ath.jbzdak.pojava.zderzenia;

public class Ball {

    private double[] coords = new double[6];

    private boolean collidedInLastIter = false;

	public Ball(){}
	
	public Ball(double x, double y, double vx, double vy, double mass, double radius){
        coords = new double[]{x, y, vx, vy, mass, radius};
    }
	public void iteration(double dt){
        coords[0]+=coords[2]*dt;
        coords[1]+=coords[3]*dt;
        collidedInLastIter =false;
    }


    /**
     * There is error in this function, can you spot it?
     * @param other
     */
    public void collision(Ball other){
        if (getX() != other.getX()){
            throw new IllegalStateException("For now balls may bounce only in 1D");
        }
        if(getMass() != other.getMass()){
            throw new IllegalStateException("For now balls must have equal mass");
        }
        setVY(other.getVY());
        other.setVY(getVY());
        collidedInLastIter =true;
        other.collidedInLastIter =true;
    }

    

    public double getX(){
        return coords[0];
    }

    public double getY(){
        return coords[1];
    }

    public double getRadius(){
        return coords[5];
    }

    public double getMass(){
        return coords[4];
    }

    public double getVX(){
        return coords[2];
    }


    public double getVY(){
        return coords[3];
    }

    protected void setVX(double value){
        coords[2] = value;
    }

    protected void setVY(double value){
        coords[3] = value;
    }


}
