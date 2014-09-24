/*
 * SortingPanel.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.ui;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import com.test.guisort.sorts.*;

/**
 * Cusotm drawing JPanel that visually displays a Sort in action.
 * @author Nicholas DiPasquale
 */
public class SortingPanel extends JPanel implements ISortEventListener<Integer> {
	private static final long serialVersionUID = 1L;
	/* The preferred size of the SortingPanel */
	private static final Dimension PREFERRED_SIZE = new Dimension (300, 300);
	
	/**
	 * Sorting algorithm enumeration
	 * @author Nicholas DiPasquale
	 */
	public enum Algorithm {
		BubbleSort, InsertionSort, SelectionSort, ShellSort,
		HeapSort, MergeSort, QuickSort;

		/**
		 * Execute the search using the specified algorithm
		 * @param panel The SortingPanel to display the search actions
		 * @param data The data to perform the sort
		 * @return The sorted array of data
		 */
		public Integer[] execute (SortingPanel panel, Integer[] data) {
			SortingSet<Integer> set;
			switch (this) {
			default:
			case BubbleSort: {
				set = new BubbleSet<Integer> ();
			} break;
			case InsertionSort: {
				set = new InsertionSet<Integer> ();
			} break;
			case SelectionSort: {
				set = new SelectionSet<Integer> ();
			} break;
			case ShellSort: {
				set = new ShellSet<Integer> ();
			} break;
			case HeapSort: {
				set = new HeapSet<Integer> ();
			} break;
			case MergeSort: {
				set = new MergeSet<Integer> ();
			} break;
			case QuickSort: {
				set = new QuickSet<Integer> ();
			} break;
			}
			
			set.addSortEventListener (panel);
			set.addAll (Arrays.asList (data));
			
			return set.sort (data);
		}
		
		/**
		 * Get information about the sorting algorithm
		 * @return General information about the sorting algorithm
		 */
		public String getInfo () {
			switch (this) {
			case BubbleSort: 
				return new BubbleSet ().getSortInformation ();
			case InsertionSort:
				return new InsertionSet ().getSortInformation ();
			case SelectionSort:
				return new SelectionSet ().getSortInformation ();
			case ShellSort:
				return new ShellSet ().getSortInformation ();
			case HeapSort:
				return new HeapSet ().getSortInformation ();
			case MergeSort:
				return new MergeSet ().getSortInformation ();
			case QuickSort:
				return new QuickSet ().getSortInformation ();
			}
			
			return "";
		}
		
	} /* END: enum Algorithm */
	
	/* The set of data to display on the panel */
	private Set<Integer> _displaySet;
	/* An arrow to display for a swap action taken */
	private Arrow2D _arrow;
	/* Flag to display arrows */
	private boolean _displayArrows;
	/* Flag to display columns */
	private boolean _displayColumns;
	/* The current sorting algorithm */
	private Algorithm _algorithm;
	/* Flag for thread synchronization */
	private boolean _waiting;
	
	/**
	 * Create a new instance of SortingPanel that will display arrows and
	 * columns
	 */
	public SortingPanel () {
		_displaySet = null;
		_arrow = null;
		_displayArrows = true;
		_displayColumns = true;
		_algorithm = null; // Algorithm.values ()[0];
		
		_waiting = false;
		
		setPreferredSize  (PREFERRED_SIZE);
	}
	
	/**
	 * Get the status of the display arrows flag
	 * @return The status of the display arrows flag
	 */
	public boolean displayArrows () {
		return _displayArrows;
	}
	/**
	 * Set the status of the display arrows flag
	 * @param display
	 */
	public void displayArrows (boolean display) {
		_displayArrows = display;
	}
	
	/**
	 * Get the status of the display columns flag
	 * @return The status of the display columns flag
	 */
	public boolean displayColumns () {
		return _displayColumns;
	}
	/**
	 * Set the status of the display columns flag
	 * @param display The status of the display columns flag
	 */
	public void displayColumns (boolean display) {
		_displayColumns = display;
	}
	
	/**
	 * Get the sorting algorithm to use
	 * @return The sorting algorithm to use
	 */
	public Algorithm algorighm () {
		return _algorithm;
	}
	/**
	 * Set the sorting algorithm to use
	 * @param algorighm The algorithm to use
	 */
	public void algorithm (Algorithm algorighm) {
		_algorithm = algorighm;
	}
	
	/**
	 * Get the display set of the SortingPanel
	 * @return The display set displayed on the SortingPanel
	 */
	public Set<Integer> displaySet () {
		return _displaySet;
	}
	/**
	 * Set the display set of the SortingPanel
	 * @param set The display set to display on the SortingPanel
	 */
	public void displaySet (Set<Integer> set) {
		_displaySet = set;
	}
	
	/**
	 * Override of the JComponent method to render custom drawing to the panel
	 * @see javax.swing.JComponent#paintComponent (java.awt.Graphics)
	 */
	protected void paintComponent (Graphics g) {
		super.paintComponent (g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		Dimension size = getSize ();
		
		g2d.setColor (Color.DARK_GRAY);
		g2d.drawRect (0, 0, size.width - 1, size.height - 1);
		
		if (_displaySet != null) {
			double width = (double) size.width / (double) _displaySet.size ();
			
			Integer[] array = _displaySet.toArray (new Integer[_displaySet.size ()]);
			for (int i = 0; i < array.length; ++i) {
				int pos = array[i].intValue ();
				if (_displayColumns) {
					double x = ((double) i * width) + 1.0;
					double y = (double) size.height - ((double) pos - 4.0); 
					double w = width - 1;
					double h = (double) size.height;
					
					g2d.setColor (Color.BLACK);
					Rectangle2D rect = new Rectangle2D.Double (x, y, w, h);
					g2d.fill (rect);
				}
				
				double x = ((double) i * width) + 1.0;
				double y = (double) size.height - (double) pos;
				double w = width - 1.0;
				double h = 3.0;
				
				g2d.setColor (Color.BLUE);
				Rectangle2D rect = new Rectangle2D.Double (x, y, w, h);
				g2d.fill (rect);
			}
		}

		if (_arrow != null && _displayArrows) {
			g2d.setColor (Color.RED);
			g2d.draw (_arrow);
			_arrow = null;
		}
		
		complete ();
	}
	
	/**
	 * ISortEventListener implementation to listen to a sort in progress and
	 * update the Panel
	 * @param event The event that occurred
	 */
	public void swapPerformed (SortEvent<Integer> event) {
		_displaySet  = event.getSet ();
		
		Dimension size = getSize ();
		double width = (double) size.width / (double) _displaySet.size ();
		
		Integer[] array = _displaySet.toArray(new Integer[_displaySet.size ()]);
		Point2D p0 = new Point2D.Double ((double) event.getIndex0 () * width + (width / 2.0), (double) size.height - array[event.getIndex0 ()].doubleValue ());
		Point2D p1 = new Point2D.Double ((double) event.getIndex1 () * width + (width / 2.0), (double) size.height - array[event.getIndex1 ()].doubleValue ());
		Point2D ctrl = new Point2D.Double ((p1.getX () + p0.getX ()) / 2.0, 0.0);
		
		_arrow = new Arrow2D (p0.getX (), p0.getY (), ctrl.getX (), ctrl.getY (), p1.getX (), p1.getY (), Arrow2D.DOUBLE_HEADED);
		
		update ();
	}
	
	/*
	 * Flags the completion of painting to the Monitor/Working Thread
	 */
	private synchronized void complete () {
		try {
			if (_waiting) {
				notify ();
				_waiting = false;
			}
		} catch (IllegalMonitorStateException imse) {
		}
	}
	
	/*
	 * Flags an repaint required to the Monitor/Working Thread
	 */
	private synchronized void update () {
		repaint ();
		
		try {
			wait (100);
			Thread.sleep (75);
			_waiting = true;
		} catch (InterruptedException ie) {
		} catch (IllegalMonitorStateException imse) {
		}
	}
	
}
