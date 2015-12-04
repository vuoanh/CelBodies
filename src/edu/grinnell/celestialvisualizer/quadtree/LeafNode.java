package edu.grinnell.celestialvisualizer.quadtree;

import edu.grinnell.celestialvisualizer.physics.Centroid;
import edu.grinnell.celestialvisualizer.physics.Physics;
import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class LeafNode implements Node{
	private Point point;
	private double mass;


	public LeafNode(double mass, Point point){
		this.point = point;
		this.mass = mass;
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
		if (this.point.equals(pos)){
			return true;
		} else {return false;}
	}

	/**
	 * Calculates the acceleration on this point according to the quad tree.
	 * The rules for calculating acceleration on a body from a quad tree are:
	 *
	 * The leaf node contains a single body---the contributed
	 *    acceleration is calculated like normal.
	 * @param p the point we are calculating the acceleration over
	 * @param bb the bounding box of the world
	 * @param thresh the threshold value, defined above
	 * @return the acceleration on p by the quad tree
	 */
	@Override
	public Vector2d calculateAcceleration(Point p, BoundingBox bb, double thresh) {
		return Physics.calculateAccelerationOn(p, this.mass, this.point);
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
		if (this.lookup(p, bb)){
			return new LeafNode(this.mass + mass, p);
		}
		CentroidNode newNode = new CentroidNode(new Centroid(0, new Point(0,0)), 
				new EmptyNode(), new EmptyNode(), new EmptyNode(), new EmptyNode());
		
		newNode.insert(this.mass, this.point, bb);
		newNode.insert(mass, p, bb);
		return newNode;
	}
	/**
     * @return true if this Node is equal to the given Object.
     */
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof LeafNode) {
            LeafNode node = (LeafNode) obj;
            return Math.abs(node.mass - this.mass) < 0.00001 && node.point.equals(this.point);
        } else {
            return false;
        }
    }

}
