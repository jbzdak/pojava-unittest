package cx.ath.jbzdak.pojava.zderzenia;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollisionTest {

    Engine engine;

    @Before
    public void setUp(){
        engine = new Engine(10, 10);
        engine.addBalls(new Ball(0, 0, 0, 1, 0, 1));
    }

    @Test
    public void testNoCollision(){
        engine.addBalls(new Ball(0, 3, 0, -2, 0, 1));
        engine.iterate(0.1);
        Assert.assertEquals(engine.getBalls().get(0).getVY(), 1, 0.01);
        Assert.assertEquals(engine.getBalls().get(1).getVY(), -2, 0.01);
    }

    @Test
    public void testCollision(){
        engine.addBalls(new Ball(0, 2, 0, -2, 0, 1));
        engine.iterate(0.1);
        Assert.assertEquals(engine.getBalls().get(0).getVY(), -2, 0.01);
        Assert.assertEquals(engine.getBalls().get(1).getVY(), 1, 0.01);
    }


}
