package edu.grinnell.celestialvisualizer.util;

/**
 * A two-dimensional vector represents a magnitude coupled with a direction.
 */
public class Vector2d {
    // For the purposes of equality, we consider two vectors equal if their
    // difference is within epsilon.
    private static final double EPSILON = 0.00001;
    
    /** The zero vector */
    public static Vector2d zero = new Vector2d(0, 0);
    
    private double x;
    private double y;
    
    /**
     * Constructs a new vector.
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /** @return the x-component of the vector */
    public double getX() { return x; }
    /** @return the y-component of the vector */
    public double getY() { return y; }
    
    /** @return a new vector that is the result of adding the two vectors
     *  together */
    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }
    
    /** @return a new vector that is the result of taking the difference of
     *  the two vectors (i.e., other - this) */
    public Vector2d difference(Vector2d other) {
        return new Vector2d(other.x - x, other.y - y);
    }
    
    /** @return a new vector that is the result of scaling this vector by
     *  the given factor. */
    public Vector2d scale(double factor) {
        return new Vector2d(x * factor, y * factor);
    }
    
    /** @return the magnitude of this vector, squared (x^2 + y^2) */
    public double magnitudeSquared() {
        return Math.pow(x,  2) + Math.pow(y,  2);
    }
    
    /** @return the magnitude of this vector, sqrt(x^2 + y ^2) */
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
    
    /** @return a unit vector with the same direction as this vector */
    public Vector2d unit() {
        return scale(1.0 / magnitude());
    }
    
    /**
     * @return true if this position is equal to the given object.
     * 
     * We define equality between vectors to be whenever their magnitude
     * squared is less than Vector2d.EPSILON.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2d) {
            Vector2d rhs = (Vector2d) other;
            return difference(rhs).magnitudeSquared() < EPSILON;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Vec2d(%f, %f)", x, y);
    }
}
