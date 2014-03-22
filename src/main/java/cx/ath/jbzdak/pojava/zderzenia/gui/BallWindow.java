package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Ball;
import cx.ath.jbzdak.pojava.zderzenia.Engine;
import cx.ath.jbzdak.pojava.zderzenia.translation.LocaleHolder;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public class BallWindow extends JFrame{

    /**
     * Silnik wykonujący krok symulacji
     */
    Engine engine = new Engine(1, 1);


    /**
     * Panel umożliwiający zmianę parametróe kulek i ich podgląd
     */
    final JPanel particleControlPanel = new JPanel();

    /**
     * Tutaj będzie wyświetlana symulacja
     */
    final JPanel simulationArena = new JPanel();

    /**
     * Tworzy główny panel
     * @param ballCount liczba kulek (o losowych położeniach) początkowo dodana do panelu.
     * @throws HeadlessException
     */
    public BallWindow(int ballCount) throws HeadlessException {
        super();

        setTitle(LocaleHolder.getTrans("BallWindow.title"));

        setDefaultCloseOperation(BallWindow.DISPOSE_ON_CLOSE);

        engine.fillWithBalls(ballCount, 1, 1);

        setLayout(new BorderLayout());

        add(particleControlPanel, BorderLayout.WEST);
        add(simulationArena, BorderLayout.CENTER);

        simulationArena.add(new JLabel("Tu będzie symulacja"), BorderLayout.CENTER);

        recreateParticleControlPanel();

        initializeMenuBar();

    }


    private void initializeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        file.add(new JMenuItem("Nothing to see here move along"));
        menuBar.add(file);

        JMenuItem start = new JMenuItem(
            LocaleHolder.getTrans("BallWindow.menu.startSimulation"),
            openImageIcon("control_play_blue.png")
        );

        JMenuItem stop = new JMenuItem(
            LocaleHolder.getTrans("BallWindow.menu.stopSimulation"),
            openImageIcon("control_stop_blue.png")
        );

        menuBar.add(start);

        menuBar.add(stop);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BallWindow.this, "To rozpoczęłoby symulację");
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BallWindow.this, "To zatrzymałoby symylację");
            }
        });
    }

    /**
     * Metoda helperowa tworzy ImageIcon z obrazka zawartego w paczce cx.ath.jbzdak.pojava.zderzenia.gui
     * @param name nazwa obrazka
     * @return Ikona
     */
    private ImageIcon openImageIcon(String name){
        return new ImageIcon(
            getClass().getResource(name)
        );
    }

    /**
     * Metoda regenerująca panel zawierający kontrolki do zmiany kulek,
     * będzie wykonywana za każdym razem jak zmieni się ilość kulek na wykresie.
     *
     */
    private void recreateParticleControlPanel(){
        particleControlPanel.removeAll();
        particleControlPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        JPanel internal = new JPanel();
        particleControlPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(internal);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        List<Ball> balls = engine.getBalls();
        internal.setLayout(new GridLayout(balls.size(), 1));

        for(int ii=0; ii < balls.size(); ii++){
            BallPanel panel = new BallPanel(ii+1, balls.get(ii));
            internal.add(panel);
        }

        revalidate();
    }


    /**
     * Wybiera locale.
     *
     * Metoda blokuje się do czasu aż user niw wybierze locale.
     */
    private static void selectLocale(){
        String localeName = (String) JOptionPane.showInputDialog(
                null,
                "Wybierz język",
                "Wybierz język",
                JOptionPane.QUESTION_MESSAGE,
                null,
                LocaleHolder.getLocaleNames().toArray(),
                LocaleHolder.getLocaleNames().get(0)
        );

       Locale selectedLocale = LocaleHolder.getSupportedLocales().get(
               LocaleHolder.getLocaleNames().indexOf(localeName));

       LocaleHolder.getHolder().setLocale(selectedLocale);
    }

    public static void main(String[] args) {

        selectLocale();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BallWindow ballWindow = new BallWindow(10);
                ballWindow.setVisible(true);
                ballWindow.setSize(640, 480);
            }
        });


    }
}
