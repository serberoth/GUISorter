/*
 * UITest.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui.testcase;

import java.awt.geom.AffineTransform;

import java.awt.geom.*;
import junit.framework.*;
import com.test.guisort.ui.*;

/**
 * Test case class for UI components
 * @author Nicholas DiPasquale
 */
public class UITest extends TestCase {

	/**
	 * Test case to render an Arrow2D with no heads on either side, the
	 * QuadCurve2D degraded test.  This method explicitly tests the PathIterator
	 * of the Arrow2D.
	 */
	public void test_Arrow2D_NonHeaded () {
		Arrow2D arrow = new Arrow2D (0.0, 0.0, 5.0, 5.0, 10.0, 10.0, Arrow2D.NONE);
		Assert.assertNotNull (arrow);
		
		AffineTransform at = new AffineTransform ();
		PathIterator iterator = arrow.getPathIterator (at);
		Assert.assertNotNull (iterator);
		
		while (!iterator.isDone ()) {
			int windingRule = iterator.getWindingRule ();
			Assert.assertTrue (windingRule == PathIterator.WIND_NON_ZERO || windingRule == PathIterator.WIND_EVEN_ODD);
			
			double[] coords = new double[4];
			iterator.currentSegment (coords);
			
			iterator.next ();
		}
		Assert.assertTrue (iterator.isDone ());
	}
	
	/**
	 * Test case to render an Arrow2D with heads on both sides. This method
	 * explicitly tests the PathIterator of the Arrow2D.
	 */
	public void test_Arrow2D_DoubleHeaded () {
		Arrow2D arrow = new Arrow2D (0.0, 0.0, 5.0, 5.0, 10.0, 10.0, Arrow2D.DOUBLE_HEADED);
		Assert.assertNotNull (arrow);
		
		AffineTransform at = new AffineTransform ();
		PathIterator iterator = arrow.getPathIterator (at);
		Assert.assertNotNull (iterator);
		
		while (!iterator.isDone ()) {
			int windingRule = iterator.getWindingRule ();
			Assert.assertTrue (windingRule == PathIterator.WIND_NON_ZERO || windingRule == PathIterator.WIND_EVEN_ODD);
			
			double[] coords = new double[4];
			iterator.currentSegment (coords);
			
			iterator.next ();
		}
		Assert.assertTrue (iterator.isDone ());
	}
	
}
