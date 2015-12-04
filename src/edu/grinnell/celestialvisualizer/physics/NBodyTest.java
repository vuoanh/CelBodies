package edu.grinnell.celestialvisualizer.physics;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import edu.grinnell.celestialvisualizer.util.Point;
import edu.grinnell.celestialvisualizer.util.Vector2d;

public class NBodyTest {

	@Test
	public void calculateAccelerationsTest() {
		NBody bodies = new NBody();
		Body body = new Body(200000.0, new Point(1, 1), new Vector2d(1,1));
		Body other = new Body(200000.0, new Point(2, 2), new Vector2d(2,2));
		bodies.add(body);
		bodies.add(other);
		List<Vector2d> accs = bodies.calculateAccelerations();
		assertEquals("bodies has two element", bodies.getBodies().size(), 2);
		assertEquals("acc of body is right", accs.get(0)
				, Physics.calculateAccelerationOn(body.getPosition(), other.getMass(), other.getPosition()));
		assertEquals("acc of other is right", accs.get(1)
				, Physics.calculateAccelerationOn(other.getPosition(), body.getMass(), body.getPosition()));
		System.out.println(bodies.calculateAccelerations().toString());
	}
	
	@Test
	public void updateTest() {
		NBody bodies = new NBody();
		Body body = new Body(200000.0, new Point(1, 1), new Vector2d(1,1));
		Body other = new Body(200000.0, new Point(2, 2), new Vector2d(2,2));
		bodies.add(body);
		bodies.add(other);
		List<Vector2d> accs = bodies.calculateAccelerations();
		bodies.update(50.0);
		Body oriBody = new Body(200000.0, new Point(1, 1), new Vector2d(1,1));
		Body oriOther = new Body(200000.0, new Point(2, 2), new Vector2d(2,2));
		System.out.println(accs.toString());
		
		assertEquals("pos of body is right", bodies.getBodies().get(0).getPosition()
				, Physics.calculateUpdatedPosition(oriBody.getPosition(), 50.0, oriBody.getVelocity(), accs.get(0)));
		assertEquals("velocity of body is right", bodies.getBodies().get(0).getVelocity()
				, Physics.calculateUpdatedVelocity(oriBody.getVelocity(), 50.0, accs.get(0)));
		
		assertEquals("pos of other is right", bodies.getBodies().get(1).getPosition()
				, Physics.calculateUpdatedPosition(oriOther.getPosition(), 50.0, oriOther.getVelocity(), accs.get(1)));
		assertEquals("velocity of other is right", bodies.getBodies().get(1).getVelocity()
				, Physics.calculateUpdatedVelocity(oriOther.getVelocity(), 50.0, accs.get(1)));	
	}
}
