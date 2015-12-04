package edu.grinnell.celestialvisualizer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import edu.grinnell.celestialvisualizer.physics.NBody;

public class CelestialVisualizer {
    
    /** The time step used for the simulation.  Every update will be made
     * in terms of this time step value. */
    private static final double ELAPSED_TIME = 50.0;
    
    /** Whether we should use our quad tree implementation or fall back to
     * the naive updating algorithm. */
    private static final boolean USE_QTREE = true;
    
    /** The NBody model that we will use for the simulation */
    private static final NBody MODEL = NBodyExamples.PLANETS;
    
    /** The frames per second of the simulation, i.e., how often we update */
    private static final int FPS = 60;
    
    public static void main(String[] args) {
        // Construct our frame and panel and make it all visible.
        final JFrame frame = new JFrame();
        final NBody simulation = MODEL;
        CelestialPanel panel = new CelestialPanel(simulation);
        frame.setTitle("Celestial Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        
        // Add a key listener so that we'll start the simulation when
        // the user presses the space bar.
        frame.addKeyListener(new KeyListener() {
            private boolean startedSimulation;
            
            @Override
            public void keyTyped(KeyEvent e) { }
            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!startedSimulation && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startedSimulation = true;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (USE_QTREE) {
                                simulation.updateWithQuadTree(ELAPSED_TIME);
                            } else {
                                simulation.update(ELAPSED_TIME);
                            }
                            frame.repaint();
                        }
                    }, 0,  1000 / FPS);
                }
            }
        });

    }
}
