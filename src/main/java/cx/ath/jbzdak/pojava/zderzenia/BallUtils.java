package cx.ath.jbzdak.pojava.zderzenia;

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
}
