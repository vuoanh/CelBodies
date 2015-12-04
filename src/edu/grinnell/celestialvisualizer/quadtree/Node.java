package edu.grinnell.celestialvisualizer.quadtree;

import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public interface Node {
    /**
     * Looks up this point in the quad tree, returning true if it is present
     * in the tree (as a leaf).
     * @param pos the point to search for
     * @param bb the bounding box encasing the world
     * @return true if the point is in the quad tree
     */
    public boolean lookup(Point pos, BoundingBox bb);
    
    /**
     * Calculates the acceleration on this point according to the quad tree.
     * The rules for calculating acceleration on a body from a quad tree are:
     * 
     * 1. The empty node exerts no force, so its contributed acceleration
     *    is zero.
     * 2. The leaf node contains a single body---the contributed
     *    acceleration is calculated like normal.
     * 3. The centroid node exerts a force as though it was a "virtual" body
     *    at its centroid unless at least one of these conditions hold:
     *    
     *    (a) the point being accelerated is inside the bounding box of the
     *        quad tree, or
     *    (b) the distance between the centroid and the body is below some
     *        threshold (i.e., magnitude(p2 - p1) < thresh)
     *        
     *    In these cases, we consider the point too close to the masses in the
     *    quad tree to use the centroid as an approximation.  We instead
     *    recursively calculate accelerations on the point due to the four
     *    quad subtrees and sum them for a better approximation.
     * 
     * @param p the point we are calculating the acceleration over
     * @param bb the bounding box of the world
     * @param thresh the threshold value, defined above
     * @return the acceleration on p by the quad tree
     */
    public Vector2d calculateAcceleration(Point p, BoundingBox bb, double thresh);
    
    /**
     * Inserts the given body (as a mass and position---the velocity is
     * irrelevant) into the quad tree.
     * 
     * @param mass the mass of the body
     * @param p the position of the body
     * @param bb the bounding box of the world
     * @return the new quad tree (as a node) that results from inserting this
     *         body into the tree.
     */
    public Node insert(double mass, Point p, BoundingBox bb);
    
    /**
     * @return true if this Node is equal to the given Object.
     */
    public boolean equals(Object other);
}
