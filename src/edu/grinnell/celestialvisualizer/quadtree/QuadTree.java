package edu.grinnell.celestialvisualizer.quadtree;

import edu.grinnell.celestialvisualizer.physics.Centroid;
import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class QuadTree {
	public static final BoundingBox bb = new BoundingBox(0.0, 0.0, 4.0, 4.0); 
	private Node root;
	/**
	 * Constructor
	 */
	public QuadTree(){
		this.root = new EmptyNode();
	}

	/** 
	 * 
	 * @param pos
	 * @param bb
	 * @return
	 */
	public boolean lookup(Point pos, BoundingBox bb) {
		return root.lookup(pos, bb);
	}

	public Vector2d calculateAcceleration(Point p, BoundingBox bb, double thresh) {
		return root.calculateAcceleration(p, bb, thresh);
	}

	public void insert(double mass, Point pos, BoundingBox bb) {
		if (!bb.contains(pos)){
			throw new IllegalArgumentException("posisions needs to be inside the boinding box");
		}
		root = root.insert(mass, pos, bb);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuadTree) {
			QuadTree tree = (QuadTree) obj;
			return this.root.equals(tree.root);
		} else {
			return false;
		}
	}

	public static QuadTree q0() {
		QuadTree q0 = new QuadTree();
		q0.root = new EmptyNode();
		return q0;
	}
	
	public static QuadTree q1(){
		 QuadTree q1 = q0();
		 q1.root = new LeafNode(1.0, p1);
		 return q1;
	}

	public static final Point p1 = new Point(1.5, 2.5);
	public static final Point p2 = new Point(2.1, 2.1);
	
	public static final Centroid b1 = new Centroid(1.0, p1);
	public static final Centroid b2 = new Centroid(1.0, p2);

	public static QuadTree q2() {
		QuadTree q2 = q1();
		q2.root = new CentroidNode(b1.add(b2),
				new EmptyNode(),                           
				new EmptyNode(),                           
				new LeafNode(1.0, p1),    
				new LeafNode(1.0, p2));
		return q2;
	}


	public static final Centroid b3 = new Centroid(2.0, new Point(1.0, 1.0));
	public static QuadTree q3() {
		QuadTree q3 = q2();
		q3.root = new CentroidNode(
				b1.add(b2).add(b3),
				new LeafNode(2.0, new Point(1.0, 1.0)),    // upper left
				new EmptyNode(),                           // upper right
				new LeafNode(1.0, new Point(1.5, 2.5)),    // lower left
				new LeafNode(1.0, new Point(2.1, 2.1)));   // lower right
		return q3;
	}
	
	public static final Centroid b4 = new Centroid(1.0, new Point(2.6, 2.8));
	public static final Centroid c1 = b2.add(b4);
	public static final Centroid c2 = c1.add(b3).add(b1);

	public static QuadTree q4() {
		QuadTree q4 = q3();
		q4.root =  new CentroidNode(c2,
				     new LeafNode(2.0, new Point(1.0, 1.0)),      // level 1—upper left
				      new EmptyNode(),                             // level 1—upper right
				      new LeafNode(1.0, new Point(1.5, 2.5)),      // level 1—lower left
				      new CentroidNode(c1,                         // level 1—lower right
				        new CentroidNode(c1,                       //   level 2—upper left
				          new LeafNode(1.0, new Point(2.1, 2.1)),  //     level 3—upper left
				          new EmptyNode(),                         //     level 3—upper right
				          new EmptyNode(),                         //     level 3—lower left
				          new LeafNode(1.0, new Point(2.6, 2.8))   //     level 3—lower right
				        ),
				        new EmptyNode(),                           //   level 2—upper right
				        new EmptyNode(),                           //   level 2—lower left
				        new EmptyNode()                            //   level 2—lower right
				     ) );
		return q4;
	}

}
