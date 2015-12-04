package edu.grinnell.celestialvisualizer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import edu.grinnell.celestialvisualizer.physics.Body;
import edu.grinnell.celestialvisualizer.physics.NBody;
import edu.grinnell.celestialvisualizer.physics.Physics;
import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

/**
 * A colletion of sample systems for use in your program.
 * In particular, the constants SOLAR_SYSTEM, PLANETS, and COLLISION can be
 * used in your program.
 */
public class NBodyExamples {

    /**
     * The size of our world  used throughout our simulations.  The values
     * given below are in terms of this value.
     */
    public static final double DISTANCE = 4000000000.0;
    
    /**
     * The initial bounding box encompassing our world.  This box is also used
     * throughout our simulation.
     * With this bounding box, the middle of the world is at (0, 0).
     */
    public static final BoundingBox WORLD_BOX =
            new BoundingBox(DISTANCE * -500, DISTANCE * -500, DISTANCE * 500, DISTANCE * 500);
    
    
    private static final double SUN_MASS = 2.0e30;
    private static final Body SUN = new Body(SUN_MASS, new Point(0.0, 0.0), new Vector2d(0.0, 0.0));
    private static final Body MERCURY = new Body(3.30e23, new Point(57910000.0, 0.), new Vector2d(9000.0, 9000.0));
    private static final Body VENUS = new Body(4.87e24, new Point(0.0, 108200000.0), new Vector2d(-9000.0, 0.0));
    private static final Body EARTH = new Body(5.98e24, new Point(-149600000.0, 0.0), new Vector2d(0.0, -9000.0));
    private static final Body MARS = new Body(6.42e23, new Point(0.0, -227940000.0), new Vector2d(600000.0, 0.0));
    private static final Body JUPITER = new Body(1.90e27, new Point(778330000.0, 0.0), new Vector2d(0.0, 450000.0));
    private static final Body SATURN = new Body(5.69e26, new Point(0.0, 1426940000.0), new Vector2d (-300000.0,0.0));
    private static final Body URANUS = new Body(8.69e26, new Point(-2870990000.0, 0.0), new Vector2d(0.0, -200000.0));

    /** A NBody simulation containing all of the planets and the sun. */
    public static final NBody SOLAR_SYSTEM =
            new NBody()
            .add(SUN)
            .add(MERCURY)
            .add(VENUS)
            .add(EARTH)
            .add(MARS)
            .add(JUPITER)
            .add(SATURN)
            .add(URANUS);

    /** A NBody simulation containing some of the planets and the sun. */
    public static final NBody PLANETS =
            new NBody()
            .add(SUN)
            .add(MARS)
            .add(JUPITER)
            .add(SATURN)
            .add(URANUS);
    
    private static final int NUM_GAUSSIAN_ITERS = 10;
    /**
     * @return a random number from a Gaussian distribution centered at d.
     */
    private static double sampleGaussian(double d) {
        double k = d / NUM_GAUSSIAN_ITERS;
        double acc = 0.0;
        for (int i = 0; i < NUM_GAUSSIAN_ITERS; i++) {
            acc += ThreadLocalRandom.current().nextDouble(k);
        }
        return acc - d / 2;
    }

    /**
     * @return the velocity needed to put mass m2 into a circular orbit around
     *         m1 at radius r.
     */
    private static double calculateOrbitalVelocity(double m1, double m2, double r) {
        return Math.sqrt(m2 * m2 * Physics.G / ((m1 + m2) * r));
    }

    /**
     * @return the tangent vector to a circle centered at p.
     */
    private static Vector2d calculateTangentVector(double x, double y) {
        double theta = Math.atan2(x, y);
        double vx = 0 - Math.cos(theta);
        double vy = Math.sin(theta);
        return new Vector2d(vx, vy);
    }
    
    private static List<Body> makeLots(double starMass, int n) {
        List<Body> bodies = new LinkedList<>();
        bodies.add(new Body(starMass, new Point(0, 0), new Vector2d(0, 0)));
        for (int i = 0; i < n; i++) {
            double dist = DISTANCE * 2;
            double mass = ThreadLocalRandom.current().nextDouble(1000.0) * 1e13;
            double x = sampleGaussian(dist);
            double y = sampleGaussian(dist);
            Vector2d d = new Vector2d(x, y);
            double r = d.magnitude();
            Vector2d v = calculateTangentVector(d.getX(), d.getY());
            double vel = calculateOrbitalVelocity(mass, starMass, r);
            bodies.add(new Body(mass, new Point(x, y), v.scale(vel)));          
        }
        return bodies;
    }
    
    private static void displaceBodies(List<Body> bodies, Vector2d v) {
        for (Body b : bodies) {
            b.displace(v);
        }
    }
    
    private static void addVelocity(List<Body> bodies, Vector2d v) {
        for (Body b : bodies) {
            b.addToVelocity(v);
        }
    }
    
    private static List<Body> createCollisionSystem(int numSunBodies, int numStarBodies) {
        double starMass = SUN_MASS * 0.1;
        List<Body> solar1 = makeLots(SUN_MASS, numSunBodies);
        List<Body> solar2 = makeLots(starMass, numStarBodies);
        Vector2d disp1 = new Vector2d(DISTANCE / 4.0, 0.0);
        Vector2d disp2 = new Vector2d(0.0 - (DISTANCE / 4), 0.0 - (DISTANCE / 4));
        Vector2d diff = disp1.difference(disp2);
        Vector2d v2 = calculateTangentVector(diff.getX(), diff.getY()).scale(
                calculateOrbitalVelocity(starMass, SUN_MASS, diff.magnitude()) * 0.9);
        displaceBodies(solar1, disp1);
        displaceBodies(solar2, disp2);
        addVelocity(solar2, v2);
        List<Body> bodies = new LinkedList<>();
        bodies.addAll(solar1);
        bodies.addAll(solar2);
        return bodies;
    }
    
    private static final int NUM_SUN_BODIES = 1000;
    private static final int NUM_STAR_BODIES = 300;
    
    /**
     * A NBody simulation containing many randomly generated bodies around
     * two colliding suns.
     */
    public static final NBody COLLISION = 
            new NBody(createCollisionSystem(NUM_SUN_BODIES, NUM_STAR_BODIES));
}
