package cx.ath.jbzdak.pojava.zderzenia.gui;

import cx.ath.jbzdak.pojava.zderzenia.Ball;
import cx.ath.jbzdak.pojava.zderzenia.BallContainer;
import cx.ath.jbzdak.pojava.zderzenia.Engine;
import cx.ath.jbzdak.pojava.zderzenia.SwingWorkerAnimationProvider;
import cx.ath.jbzdak.pojava.zderzenia.translation.LocaleHolder;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BallWindow extends JFrame{

    final Map<String, Class<? extends AnimationProvider>> providers = loadProviders();

    /**
     * Silnik wykonujący krok symulacji
     */
    Engine engine = new Engine();

    BallContainer ballContainer = new BallContainer(1,1);

    AnimationProvider provider = null;

    /**
     * Panel umożliwiający zmianę parametróe kulek i ich podgląd
     */
    final JPanel particleControlPanel = new JPanel();

    /**
     * Tutaj będzie wyświetlana symulacja
     */
    final JPanel simulationArena = new JPanel();

    final SimulationPanel2D simulationPanel2D;

    /**
     * Tworzy główny panel
     * @param ballCount liczba kulek (o losowych położeniach) początkowo dodana do panelu.
     * @throws HeadlessException
     */
    public BallWindow(int ballCount) throws HeadlessException {
        super();

        setTitle(LocaleHolder.getTrans("BallWindow.title"));

        setDefaultCloseOperation(BallWindow.EXIT_ON_CLOSE);

        ballContainer.fillWithBalls(ballCount, 1, 0.05);

        setLayout(new BorderLayout());

        add(particleControlPanel, BorderLayout.WEST);
        add(simulationArena, BorderLayout.CENTER);

        simulationArena.setLayout(new BorderLayout());
        simulationPanel2D = new SimulationPanel2D(ballContainer);

        simulationArena.add(simulationPanel2D, BorderLayout.CENTER);

        provider = new ThreadAnimationProvider();

        provider.setEngine(engine);
        provider.setSimulationPanel(simulationPanel2D);


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

        JMenuItem selectAnimationProvider = new JMenuItem(
                LocaleHolder.getTrans("BallWindow.menu.selectProvider")
        );

        selectAnimationProvider(LocaleHolder.getTrans("BallWindow.provider.timer"));

        menuBar.add(start);
        menuBar.add(stop);
        menuBar.add(selectAnimationProvider);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provider.start();
            }
        });

        selectAnimationProvider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BallWindow.this.queryUserForAnimationProvider();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provider.stop();
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
        List<Ball> balls = ballContainer.getBalls();
        internal.setLayout(new GridLayout(balls.size(), 1));

        for(int ii=0; ii < balls.size(); ii++){
            BallPanel panel = new BallPanel(ii+1, balls.get(ii));
            internal.add(panel);
        }

        revalidate();
    }

    void queryUserForAnimationProvider(){
        String provider = (String) JOptionPane.showInputDialog(
                this,
                LocaleHolder.getTrans("BallWindow.selectProvider"),
                LocaleHolder.getTrans("BallWindow.selectProvider"),
                JOptionPane.QUESTION_MESSAGE,
                null,
                providers.keySet().toArray(),
                null
        );

        selectAnimationProvider(provider);

    }


    void selectAnimationProvider(String name){


        Class<? extends AnimationProvider> selected =
                providers.get(name);

        if(selected == null){
            return;
        }

        if (this.provider != null){
            this.provider.destroy();
            this.provider = null;
        }


        try {
            this.provider = selected.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e); // Nie powinno się zdarzyć!
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e); // Nie powinno się zdarzyć!
        }

        this.provider.setEngine(engine);
        this.provider.setSimulationPanel(simulationPanel2D);
        this.provider.setBallContainer(ballContainer);
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
                ballWindow.setSize(640, 480);
                ballWindow.setVisible(true);
            }
        });


    }

    private static Map<String, Class<? extends AnimationProvider>>loadProviders(){
        Map<String, Class<? extends AnimationProvider>> providers = new HashMap<String, Class<? extends AnimationProvider>>();
        providers.put(LocaleHolder.getTrans("BallWindow.provider.timer"), TimerAnimationProvider.class);
        providers.put(LocaleHolder.getTrans("BallWindow.provider.thread"), ThreadAnimationProvider.class);
        providers.put(LocaleHolder.getTrans("BallWindow.provider.swing-worker"), SwingWorkerAnimationProvider.class);
        return Collections.unmodifiableMap(providers);
    }
}
