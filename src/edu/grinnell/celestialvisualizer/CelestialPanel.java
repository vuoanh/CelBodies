package edu.grinnell.celestialvisualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.grinnell.celestialvisualizer.physics.Body;
import edu.grinnell.celestialvisualizer.physics.NBody;

/**
 * The CelestialPanel renders a NBody simulation to the screen.
 */
public class CelestialPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    /** The width and height of the panel. */
    public static final int DIMENSION = 800;

    private NBody model;
    private int width;
    private int height;
    
    public CelestialPanel(NBody model) {
        this.model = model;
        this.width = DIMENSION;
        this.height = DIMENSION;
        setPreferredSize(new Dimension(width, height));
    }
    
    public static double worldToScreen(double d) {
        return d / NBodyExamples.DISTANCE * (DIMENSION / 2.0) + (DIMENSION / 2.0);
    }
    
    private static void drawBody(Body b, Color c, Graphics g) {
         double x = worldToScreen(b.getPosition().getX());
         double y = worldToScreen(b.getPosition().getY());
         double m = b.getMass();
         double r = m > 2.0e28 ? 10 : Math.max(Math.min(m / 2.0e18, 4), 2);
         g.setColor(c);
         g.fillOval((int) (x - r), (int) (y - r), (int) r * 2, (int) r * 2);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        for (Body b : model.getBodies()) {
            drawBody(b, Color.WHITE, g);
        }
    }
}
