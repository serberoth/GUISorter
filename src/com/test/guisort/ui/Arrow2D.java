/*
 * Arrow2D.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * This class implements the java.awt.Shape interface to draw an arrow shape on
 * a Graphics.  Essentially the shape is made up of an QuadCurve2D and Polygons at 
 * either end for the heads.  The arrow can display heads at either end, both,
 * or none, degrading the Arrow2D to the same as the QuadCurve2D class.
 * @author Nicholas DiPasquale
 */
public class Arrow2D implements Shape {
	
	/** Constant to display no arrow heads */
	public static final int NONE			= 0;
	/** Constant to display left arrow head only */
	public static final int LEFT_HEAD		= 1;
	/** Constant to display right arrow head only */
	public static final int RIGHT_HEAD		= 2;
	/** Constant to display right and left arrow heads */
	public static final int DOUBLE_HEADED	= 3;
	
	/**
	 * This class implements the java.awt.geom.PathIterator interface to iterate
	 * over a set of PathIterators for sub-objects of a Shape
	 * @author Nicholas DiPasquale
	 */
	private static class CustomPathIterator implements PathIterator {
		
		/* Iterator for a Set of PathIterators */
		private Iterator<PathIterator> _iterator;
		/* The current PathIterator */
		private PathIterator _current;
		
		/**
		 * Create a new CustomPathIterator with the given set of PathIterators
		 * @param iterators The PathIterators to iterate across
		 */
		public CustomPathIterator (PathIterator[] iterators) {
			this (Arrays.asList (iterators));
		}
		/**
		 * Create a new CustomPathIterator with the given List of PathIterators
		 * @param list The List of PathIterators to iterate across
		 */
		public CustomPathIterator (java.util.List<PathIterator> list) {
			if (list == null || list.size () == 0) {
				throw new IllegalArgumentException ();
			}
			_iterator = list.iterator ();
			_current = _iterator.next ();
		}
		
		/**
		 * @see java.awt.geom.PathIterator#currentSegment (double[])
		 */
		public int currentSegment (double[] coords) {
			return _current.currentSegment (coords);
		}
		/**
		 * @see java.awt.geom.PathIterator#currentSegment (float[])
		 */
		public int currentSegment (float[] coords) {
			return _current.currentSegment (coords);
		}
		
		/**
		 * @see java.awt.geom.PathIterator#getWindingRule ()
		 */
		public int getWindingRule () {
			return _current.getWindingRule ();
		}
		
		/**
		 * @see java.awt.geom.PathIterator#isDone ()
		 */
		public boolean isDone () {
			return !_iterator.hasNext () && (_current == null || _current.isDone ());
		}
		
		/**
		 * @see java.awt.geom.PathIterator#next ()
		 */
		public void next () {
			if (_current.isDone ()) {
				_current = _iterator.next ();
			} else {
				_current.next ();
			}
		}
		
	} /* END: class CustomPathIterator */
	
	/* The path between the arrow heads */
	private QuadCurve2D _curve;
	/* The left-hand arrow head (may not always appear on the left) */
	private Polygon _leftHead;
	/* The right-hand arrow head (may not always appear on the right) */
	private Polygon _rightHead;
	/* The type of arrow heads to use (none, left, right, both) */
	private int _type;
	
	/**
	 * Create a new instance of the Arrow2D class
	 * @param startX The starting X position of the curve
	 * @param startY The starting Y position of the curve
	 * @param ctrlX The control X position of the curve
	 * @param ctrlY The control Y position of the curve
	 * @param endX The ending X position of the curve
	 * @param endY The ending Y position of the curve
	 * @param type The type of arrow heads to use (none, left, right, both)
	 */
	public Arrow2D (double startX, double startY, double ctrlX, double ctrlY, double endX, double endY, int type) {
		double startAngle = Math.atan2 (ctrlX - startX, ctrlY - startY);
		double endAngle = Math.atan2 (endX - ctrlX, endY - ctrlY);
		double sin, cos;
		
		double height = 5;
		double base = 8;
		
		_curve = new QuadCurve2D.Double (startX, startY, ctrlX, ctrlY, endX, endY);
		_leftHead = new Polygon ();
		_leftHead.addPoint ((int) startX, (int) startY);
		sin = height * Math.sin (startAngle + .5);
		cos = height * Math.cos (startAngle + .5);
		_leftHead.addPoint ((int) (startX + sin), (int) (startY + cos));
		sin = base * Math.sin (startAngle);
		cos = base * Math.cos (startAngle);
		_leftHead.addPoint ((int) (startX + sin), (int) (startY + cos));
		sin = height * Math.sin (startAngle - .5);
		cos = height * Math.sin (startAngle - .5);
		_leftHead.addPoint ((int) (startX + sin), (int) (startY + cos));
		_leftHead.addPoint ((int) startX, (int) startY);
		
		_rightHead = new Polygon ();
		_rightHead.addPoint ((int) endX, (int) endY);
		sin = height * Math.sin (endAngle - .5);
		cos = height * Math.cos (endAngle - .5);
		_rightHead.addPoint ((int) (endX + sin), (int) (endY + cos));
		sin = base * Math.sin (endAngle);
		cos = base * Math.cos (endAngle);
		_rightHead.addPoint ((int) (endX + sin), (int) (endY + cos));
		sin = height * Math.sin (endAngle + .5);
		cos = height * Math.sin (endAngle + .5);
		_rightHead.addPoint ((int) (endX + sin), (int) (endY + cos));
		_rightHead.addPoint ((int) endX, (int) endY);
		
		_type = type;
	}
	
	/**
	 * @see java.awt.geom.Shape#contains (double, double)
	 */
	public boolean contains (double x, double y) {
		return _curve.contains (x, y);
	}
	/**
	 * @see java.awt.geom.Shape#contains (double, double, double, double)
	 */
	public boolean contains (double x, double y, double w, double h) {
		return _curve.contains (x, y, w, h);
	}
	/**
	 * @see java.awt.geom.Shape#contains (Point2D)
	 */
	public boolean contains (Point2D p) {
		return _curve.contains (p);
	}
	/**
	 * @see java.awt.geom.Shape#contains (Rectangle2D)
	 */
	public boolean contains (Rectangle2D r) {
		return _curve.contains (r);
	}
	
	/**
	 * @see java.awt.geom.Shape#getBounds ()
	 */
	public Rectangle getBounds () {
		return _curve.getBounds ();
	}
	
	/**
	 * @see java.awt.geom.Shape#getBounds2D ()
	 */
	public Rectangle2D getBounds2D () {
		return _curve.getBounds2D ();
	}
	
	/**
	 * @see java.awt.geom.Shape#getPathIterator (AffineTransform)
	 */
	public PathIterator getPathIterator (AffineTransform at) {
		if (_type == NONE) {
			return _curve.getPathIterator (at);
		}
		
		java.util.List<PathIterator> list = new ArrayList<PathIterator> ();
		if ((_type & RIGHT_HEAD) != 0) {
			list.add (_rightHead.getPathIterator (at));
		}
		if ((_type & LEFT_HEAD) != 0) {
			list.add (_leftHead.getPathIterator (at));
		}
		list.add (_curve.getPathIterator (at));
		CustomPathIterator iterator = new CustomPathIterator (list);
		
		return iterator;
	}
	/**
	 * @see java.awt.geom.Shape#getPathIterator (AffineTransform, double)
	 */
	public PathIterator getPathIterator (AffineTransform at, double flatness) {
		return new FlatteningPathIterator (getPathIterator (at), flatness);
	}
	
	/**
	 * @see java.awt.geom.Shape#intersects (double, double, double, double)
	 */
	public boolean intersects (double x, double y, double w, double h) {
		return _curve.intersects (x, y, w, h);
	}
	/**
	 * @see java.awt.geom.Shape#intersects (Rectangle2D)
	 */
	public boolean intersects (Rectangle2D r) {
		return _curve.intersects (r);
	}
	
}
