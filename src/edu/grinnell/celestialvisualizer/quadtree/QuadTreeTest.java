package edu.grinnell.celestialvisualizer.quadtree;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.grinnell.celestialvisualizer.util.BoundingBox;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class QuadTreeTest {

    @Test
    public void testQuadTreeAccel() {
        Point p = new Point(1.0, 1.0);
        BoundingBox bb = new BoundingBox(0.0, 0.0, 4.0, 4.0);
        QuadTree qtree = new QuadTree();
        assertEquals(Vector2d.zero, qtree.calculateAcceleration(p, bb, 0.0));
        
        qtree = new QuadTree();
        qtree.insert(10000000.0, new Point(2.0, 3.0), bb);
        p = new Point (2.0, 2.0);
        assertEquals(new Vector2d(0.0, 0.000667428), qtree.calculateAcceleration(p, bb, 0.0));
    }
    
    @Test
    public void testQuadTreeInsert() {
        BoundingBox bb = new BoundingBox(0.0, 0.0, 4.0, 4.0);
        
        QuadTree q = new QuadTree();
        assertEquals(q, QuadTree.q0());
        
        q.insert(1.0, new Point(1.5, 2.5), bb);
        assertEquals(q, QuadTree.q1());
        
        q.insert(1.0, new Point(2.1, 2.1), bb);
        QuadTree qTest = QuadTree.q2();
        assertEquals(q, qTest);
        
        q.insert(2.0, new Point(1.0, 1.0), bb);
        assertEquals(q, QuadTree.q3());
        
        q.insert(1.0, new Point(2.6, 2.8), bb);
        assertEquals(q, QuadTree.q4());
    }

}
