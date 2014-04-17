package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Ball;
import cx.ath.jbzdak.pojava.zderzenia.BallContainer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SimulationPanel2D extends JPanel {


    final BallContainer engine;

    public SimulationPanel2D(BallContainer engine) {
        super();
        this.engine = engine;
    }

    @Override
    protected void paintComponent(Graphics g) {
        setForeground(Color.BLUE);
        Graphics2D g2 = (Graphics2D)g;

        /*
         *
         * Normally Graphics coordinates set 0,0 on left upper corner, and y coordinates go down.
         * I decided to use transformation to change it so 0, 0 is on lower left corner and y coordinates
         * go up.
         *
         * Main reason to do this is to show you that such thing as tranformations exist.
         *
         * This is probably not worth the effort since everyting will appear upside down...
         *
         */

        AffineTransform oldTransform = g2.getTransform();
        AffineTransform transform = new AffineTransform(g2.getTransform());
        transform.scale(1, -1);
        transform.translate(0, -getHeight());
        g2.setTransform(transform);

        // End of transformation code

        g2.clearRect(0, 0, getWidth(), getHeight());
        try{
            for(Ball b: engine.getBalls()){
                printBall(g2, b);
            }
        }finally {
            // Set old transform after done painting
            g2.setTransform(oldTransform);
        }
    }



    private void printBall(Graphics2D g, Ball b) {
        // First we scale x and y so both are from 0 to 1.
        double x = b.getX()/engine.getBoxWidth();
        double y = b.getY()/engine.getBoxHeight();

        // Then we translate those these coordinates to pixel positions
        x*=getWidth();
        y*=getHeight();

        // The same has to be done with radiuses. Note that since
        // we scale x and y to window size balls can be displayed as elpises...
        double radiusX = b.getRadius()/engine.getBoxWidth()*getWidth();
        double radiusY = b.getRadius()/engine.getBoxHeight()*getHeight();


        // Due to how fillOval works we need to substract radius from
        // x and y --- we obtain top left corner of elipse we paint.
        x-=radiusX;
        y-=radiusY;


        g.fillOval((int) x, (int) y, (int) radiusX*2, (int) radiusY*2);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BallContainer container = new BallContainer(1, 1);
                container.fillWithBalls(10, 1, 0.05);
                JFrame jFrame = new JFrame();
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setLayout(new BorderLayout());
                jFrame.add(new SimulationPanel2D(container), BorderLayout.CENTER);
                jFrame.setSize(640, 480);
                jFrame.setVisible(true);
            }
        });
    }
}
