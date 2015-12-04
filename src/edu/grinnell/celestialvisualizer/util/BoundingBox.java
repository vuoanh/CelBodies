package edu.grinnell.celestialvisualizer.util;

/**
 * A bounding box describes a rectangular region of space.
 */
public class BoundingBox {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    
    /**
     * Constructs a bounding box defined by the coordinates of two of its
     * opposing corners.
     * @param minX the x-coordinate of the upper left-hand corner
     * @param minY the y-coordinate of the upper left-hand corner
     * @param maxX the x-coordinate of the lower right-hand corner
     * @param maxY the x-coordinate of the lower right-hand corner.
     */
    public BoundingBox(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }
    
    /**
     * @return true if the point is contained in this bounding box
     */
    public boolean contains(Point p) {
        return minX <= p.getX() && p.getX() <= maxX
                && minY <= p.getY() && p.getY() <= maxY;
    }
    
    /** @return the x-coordinate of the upper left-hand corner */
    public double getMinX() { return minX; }
    /** @return the y-coordinate of the upper left-hand corner */
    public double getMinY() { return minY; }
    /** @return the x-coordinate of the lower right-hand corner */
    public double getMaxX() { return maxX; }
    /** @return the y-coordinate of the lower right-hand corner */
    public double getMaxY() { return maxY; }
    
    /** @return the midpoint of the box along the x-axis */
    public double getMidpointX() { return (minX + maxX) / 2; }
    /** @return the midpoint of the box along the y-axis */
    public double getMidpointY() { return (minY + maxY) / 2; }
    
    /** @return the sub-region corresponding to the given quadrant */
    public BoundingBox getQuadrant(Quadrant q) {
        double mx = getMidpointX();
        double my = getMidpointY();
        switch(q) {
        case UPPER_LEFT:
            return new BoundingBox(minX, minY, mx, my);
        case UPPER_RIGHT:
            return new BoundingBox(mx, minY, maxX, my);
        case LOWER_LEFT:
            return new BoundingBox(minX, my, mx, maxY);
        case LOWER_RIGHT:
            return new BoundingBox(mx, my, maxX, maxY);
        default:
            throw new IllegalStateException();
        }
    }
    
    /** @return the quadrant that contains this point, throwing an exception
     *  if the point is not contained in this bounding box. */
    public Quadrant quadrantOf(Point pos) {
        double mx = getMidpointX();
        double my = getMidpointY();
        double x = pos.getX();
        double y = pos.getY();
        if (x <= mx && y <= my) {
            return Quadrant.UPPER_LEFT;
        } else if (x <= mx && y > my) {
            return Quadrant.LOWER_LEFT;
        } else if (x > mx && y <= my) {
            return Quadrant.UPPER_RIGHT;
        } else if (x > mx && y > my) {
            return Quadrant.LOWER_RIGHT;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public String toString() {
        return String.format("[[%f, %f, %f, %f]]", minX, minY, maxX, maxY);
    }
}
