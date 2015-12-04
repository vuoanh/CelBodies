package edu.grinnell.celestialvisualizer.physics;

import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class Physics {
    /**
     * The gravitational constant (N (m/kg)^2)
     */
    public static final double G = 6.67428e-11;
    
    /**
     * Calculates the acceleration of a body on a given target position
     * according to the formula: acc = G * mass2 / dist.  If the two
     * positions are equal, then the acceleration produced is zero.
     * @param p1 the target position
     * @param mass2 the mass of the body
     * @param p2 the position of the body
     * @return the calculated acceleration
     */
    public static Vector2d calculateAccelerationOn(Point p1, double mass2, Point p2) {
        if (p1.equals(p2)) {
            return Vector2d.zero;
        } else {
            Vector2d disp12 = p1.distance(p2);
            double r = disp12.magnitudeSquared();
            double magnitude = G * mass2 / r;
            Vector2d direction = disp12.unit();
            return direction.scale(magnitude);
        }
    }
    
    /**
     * Calculates the update position of a point given its speed and
     * acceleration according to the formula: delta = t * v + 0.5 * t^2 * a.
     * @param pos the position of the point
     * @param elapsedTime the time that has elapsed since the last update
     * @param vel the velocity of the point
     * @param acc the acceleration of the point
     * @return
     */
    public static Point calculateUpdatedPosition(Point pos, double elapsedTime, Vector2d vel, Vector2d acc) {
        return pos.translate(vel.scale(elapsedTime).add(acc.scale(0.5 * Math.pow(elapsedTime, 2))));
    }
    
    /**
     * Calculates the updated velocity of a point given its speed and
     * acceleration according to the formula: delta = t * a.
     * @param vel
     * @param elapsedTime
     * @param acc
     * @return
     */
    public static Vector2d calculateUpdatedVelocity(Vector2d vel, double elapsedTime, Vector2d acc) {
        return vel.add(acc.scale(elapsedTime));
    }
}
