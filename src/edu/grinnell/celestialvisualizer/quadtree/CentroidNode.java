package edu.grinnell.celestialvisualizer.quadtree;

import java.util.LinkedList;
import java.util.List;

import edu.grinnell.celestialvisualizer.physics.Centroid;
import edu.grinnell.celestialvisualizer.physics.Physics;
import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Quadrant;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class CentroidNode implements Node {
	/**
	 * Fields of CentroidNode
	 */
	public Node upperLeft;
	public Node upperRight;
	public Node lowerLeft;
	public Node lowerRight;
	private Centroid centroid;

	public CentroidNode(Centroid centroid, Node upperLeft,  Node upperRight, Node lowerLeft, Node lowerRight){
		this.centroid = centroid;
		this.upperLeft = upperLeft;
		this.upperRight = upperRight;
		this.lowerLeft = lowerLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Looks up this point in the quad tree, returning true if it is present
	 * in the tree (as a leaf).
	 * @param pos the point to search for
	 * @param bb the bounding box encasing the world
	 * @return true if the point is in the quad tree
	 */
	@Override
	public boolean lookup(Point pos, BoundingBox bb) {
		return this.getNode(bb.quadrantOf(pos))
				.lookup(pos, bb.getQuadrant(bb.quadrantOf(pos)));
	}

	/**
	 * get the node corresponding to the quad
	 * @param quad
	 * @return the node that corresponding to the quad
	 */
	public Node getNode(Quadrant quad){
		switch(quad) {
		case UPPER_LEFT:
			return this.upperLeft;
		case UPPER_RIGHT:
			return this.lowerRight;
		case LOWER_LEFT:
			return this.lowerLeft;
		case LOWER_RIGHT:
			return this.lowerRight;
		default:
			throw new IllegalStateException();
		}
	}

	/**
	 * Calculates the acceleration on this point according to the quad tree.
	 * The rules for calculating acceleration on a body from a quad tree are:
	 *  The centroid node exerts a force as though it was a "virtual" body
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
	@Override
	public Vector2d calculateAcceleration(Point p, BoundingBox bb, double thresh) {
		if (bb.contains(p) || this.centroid.getPosition().distance(p).magnitude() < thresh){
			return upperLeft.calculateAcceleration(p, bb.getQuadrant(Quadrant.UPPER_LEFT), thresh)
					.add(upperRight.calculateAcceleration(p, bb.getQuadrant(Quadrant.UPPER_RIGHT), thresh))
					.add(lowerLeft.calculateAcceleration(p, bb.getQuadrant(Quadrant.LOWER_LEFT), thresh))
					.add(lowerRight.calculateAcceleration(p, bb.getQuadrant(Quadrant.LOWER_RIGHT), thresh));
		}
		return Physics.calculateAccelerationOn(p, this.centroid.getMass(), this.centroid.getPosition());
	}

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
	@Override
	public Node insert(double mass, Point p, BoundingBox bb) {
		this.centroid = this.centroid.add(new Centroid(mass, p));
		if (bb.quadrantOf(p) == Quadrant.LOWER_LEFT){
			this.lowerLeft = this.lowerLeft.insert(mass, p, bb.getQuadrant(Quadrant.LOWER_LEFT));	
		}
		if (bb.quadrantOf(p) == Quadrant.LOWER_RIGHT){
			this.lowerRight = this.lowerRight.insert(mass, p, bb.getQuadrant(Quadrant.LOWER_RIGHT));	
		}
		if (bb.quadrantOf(p) == Quadrant.UPPER_LEFT){
			this.upperLeft = this.upperLeft.insert(mass, p, bb.getQuadrant(Quadrant.UPPER_LEFT));	
		}
		if (bb.quadrantOf(p) == Quadrant.UPPER_RIGHT){
			this.upperRight = this.upperRight.insert(mass, p, bb.getQuadrant(Quadrant.UPPER_RIGHT));	
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CentroidNode) {
			CentroidNode node = (CentroidNode) obj;
			return this.centroid.equals(node.centroid) && this.lowerLeft.equals(node.lowerLeft) && this.lowerRight.equals(node.lowerRight)
					&& this.upperLeft.equals(node.upperLeft) && this.upperRight.equals(node.upperRight);
		} else {
			return false;
		}
	}

}
