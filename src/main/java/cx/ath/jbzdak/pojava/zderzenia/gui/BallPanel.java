package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Ball;
import cx.ath.jbzdak.pojava.zderzenia.translation.LocaleHolder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Locale;

public class BallPanel extends JPanel {

    private final int ballIdx;
    private final Ball ball;

    JTextField positionX = new JTextField(3);
    JTextField positionY = new JTextField(3);

    JTextField velocityX = new JTextField(3);
    JTextField velocityY = new JTextField(3);

    JTextField mass = new JTextField(3);
    JTextField radius = new JTextField(3);

    public BallPanel(int ballIdx, Ball ball) {
        super();
        this.ball = ball;
        this.ballIdx = ballIdx;

        setBorder(BorderFactory.createTitledBorder(LocaleHolder.getTrans("BallPanel.title", ballIdx)));

        setLayout(new MigLayout());

        add(new JLabel("X: "));
        add(positionX);
        add(new JLabel("Y: "), "gap unrelated");
        add(positionY, "wrap");

        add(new JLabel("<html>v<sub>x</sub>:</html>"));
        add(velocityX);
        add(new JLabel("<html>v<sub>y</sub>:</html>"), "gap unrelated");
        add(velocityY, "wrap");

        add(new JLabel(LocaleHolder.getTrans("BallWindow.mass")), "");
        add(mass);
        add(new JLabel(LocaleHolder.getTrans("BallWindow.radius")), "gap unrelated");
        add(radius);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocaleHolder.getHolder().setLocale(new Locale("pl"));
                JFrame frame = new JFrame("Test");
                frame.add(new BallPanel(3, new Ball(1, 2, 3, 4, 5, 6)));
                frame.setSize(640, 480);
                frame.setVisible(true);
            }
        });
    }
}
