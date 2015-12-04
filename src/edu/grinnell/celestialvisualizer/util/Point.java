package edu.grinnell.celestialvisualizer.util;

/**
 * A point in 2D space.
 */
public class Point {
    // For the purposes of equality, we consider two points equal if their
    // difference is within epsilon.
    private static final double EPSILON = 0.00001;
    
    private double x;
    private double y;
    
    /**
     * Constructs a new position at the given (x, y) coordinate.
     * @param the x-coordinate
     * @param the y-coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /** @return the x-coordinate of this position */
    public double getX() { return x; }
    /** @return the y-coordinate of this position */
    public double getY() { return y; }
    
    /** 
     * @return a vector corresponding to the distance (difference) between
     * this position and the other position.
     */
    public Vector2d distance(Point other) {
        return new Vector2d(other.x - x, other.y - y);
    }
    
    /**
     * @return a new position that is the result of adding this position
     * and the other given position.
     */
    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }
    
    /**
     * @return a new position that is the result of translating this position
     * by the given vector.
     */
    public Point translate(Vector2d delta) {
        return new Point(x + delta.getX(), y + delta.getY());
    }
    
    /**
     * @return a new position that is the result of scaling this position
     * by the given amount.
     */
    public Point scale(double amt) {
        return new Point(x * amt, y * amt);
    }
    
    /**
     * @param other, an other point
     * @return: a mid point between this point and other point
     */
    public Point midPoint(Point other){
    	return new Point((this.x + other.getX())/2, (this.y + other.getY())/2);
    }
    
    /**
     * @return true if this position is equal to the given object.
     * 
     * We define equality between positions to be whenever their distance
     * is less than Position.EPSILON.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point rhs = (Point) other;
            return distance(rhs).magnitudeSquared() < EPSILON;
        } else {
            return false;
        }
    }
    
    /** @return the string representation of this position. */
    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}
