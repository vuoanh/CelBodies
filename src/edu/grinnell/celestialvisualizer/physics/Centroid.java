package edu.grinnell.celestialvisualizer.physics;

import edu.grinnell.celestialvisualizer.quadtree.LeafNode;
import edu.grinnell.celestialvisualizer.util.Point;

/**
 * A centroid is a summary of the bodies found in a given quadrant of a
 * quad tree.
 */
public class Centroid {
    private double mass;
    private Point position;

    /**
     * Creates a new Centroid.
     * @param mass the mass of the centroid
     * @param position the position of the centroid
     */
    public Centroid(double mass, Point position) {
        this.mass = mass;
        this.position = position;
    }

    /** @return the mass of the centroid */
    public double getMass() { return mass; }
    /** @return the position of the centroid */
    public Point getPosition() { return position; }

    /**
     * Produces new centroid that is the result of adding this centroid
     * with another centroid.  Addition between centroids is defined to (1)
     * sum the masses of the centroids and (2) take the weighted average of
     * the positions of the two centroids.
     * @param the other the centroid to add to this one
     * @return the new centroid that is the result of the addition
     */
    public Centroid add(Centroid other) {
        double msum = mass + other.mass;
        Point psum = position.scale(mass)
                .add(other.position.scale(other.mass)).scale(1.0 / msum);
        return new Centroid(msum, psum);
    }
    
    /** @return the string representation of the centroid */
    @Override
    public String toString() {
        return String.format("C(%f, %s)", mass, position.toString());
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof Centroid) {
    		Centroid rhs = (Centroid) o;
    		return position.equals(rhs.position) &&
    				Math.abs(mass - rhs.mass) < 0.000001;
    	} else {
    		return false;
    	}
    }
}
