package edu.grinnell.celestialvisualizer.physics;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class BodyTest {

	@Test
	public void calculatedAccelerationTest() {
		NBody bodies = new NBody();
		Body body = new Body(2.0, new Point(1, 1), new Vector2d(1,1));
		Body other = new Body(200000.0, new Point(2, 2), new Vector2d(2,2));
		bodies.add(other);
		assertFalse(bodies.getBodies().isEmpty());
		assertEquals(Physics.calculateAccelerationOn(body.getPosition(), other.getMass(), other.getPosition())
				, body.calculateAcceleration(bodies.getBodies()));
	}
	
	@Test
	public void updateTest(){
		NBody bodies = new NBody();
		Body body = new Body(2.0, new Point(1, 1), new Vector2d(1,1));
		
		Body other = new Body(200000000000.0, new Point(2, 2), new Vector2d(2,2));
		bodies.add(other);
		Vector2d acc = body.calculateAcceleration(bodies.getBodies());
		body.update(50000.0, acc);
		System.out.println(acc.toString());
		System.out.println(body.getPosition().toString());
		System.out.println(body.getVelocity().toString());
		assertFalse(body.getVelocity() == new Vector2d(1,1));
	}
	
}
