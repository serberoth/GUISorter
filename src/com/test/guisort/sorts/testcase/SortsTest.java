/*
 * SortsTest.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts.testcase;
 
import java.lang.reflect.*;
import java.util.*;
import junit.framework.*;
import com.test.guisort.sorts.*;

/**
 * Test case class for different sorting classes and options 
 * @author Nicholas DiPasquale
 */
public class SortsTest extends TestCase {

    /* The numbers 0-20 in a pseudo-random order */
    private static final Integer[] CONSTANT_LIST = new Integer[] {
        new Integer (20), new Integer (16), new Integer (7),
        new Integer (2),  new Integer (12), new Integer (4),
        new Integer (15), new Integer (19), new Integer (6),
        new Integer (11), new Integer (3),  new Integer (14),
        new Integer (8),  new Integer (5),  new Integer (18),
        new Integer (1),  new Integer (13), new Integer (9),
        new Integer (10), new Integer (17), new Integer (0),
    };
    
    private static final int RANDOM_SIZE	= 1000;
    
    /**
     * SortEventListener implements a basig sorting event listener to render
     * the array data on System.err when a SortEvent is fired
     * @author Nicholas DiPasquale
     * @param <T>
     */
    private static class SortEventListener<T> implements ISortEventListener<T> {
    	/* The class of T, the data being sorted */
    	private Class<T> _clazz;
    	/* The number of SortEvents fired */
    	private long _count;
    	
    	/**
    	 * Create a new instance of SortEventListener
    	 * @param clazz The Class of the data being sorted
    	 */
    	public SortEventListener (Class<T> clazz) {
    		_clazz = clazz;
    	}
    	
    	/**
    	 * @see com.test.guisort.sorts.ISortEventListener#swapPerformed(com.test.guisort.sorts.SortEvent)
    	 */
    	@SuppressWarnings ("unchecked")
    	public void swapPerformed (SortEvent<T> event) {
    		++_count;
    		
    		if (event.getSet ().size () <= CONSTANT_LIST.length) {
        		// T[] array = new T[event.getSet ().size ()];
        		T[] array = (T[]) Array.newInstance (_clazz, event.getSet ().size ());
        		array = event.getArray (array);
        		
    			System.err.print ("[ ");
    			for (int i = 0; i < array.length; ++i) {
    				if (i > 0) {
    					System.err.print (", ");
    				}
    				System.err.print (array[i]);
    			}
    			System.err.println (" ]");
    		}
    	}
    	
    	/**
    	 * Get the number of SortEvents handled by this SortEventListener
    	 * @return The number of SortEvents handled
    	 */
    	public long getCount () {
    		return _count;
    	}
    	
    };
    
    /*
     * Generic test for different SortingSet sorting cases
     * @param <T>
     * @param clazz The class of the SortingSet desired to perform the test
     * @param array The array of items to sort
     * @param listener Event listener called during sorting operations
     */
	@SuppressWarnings ("unchecked")
    private <T> void test_SortingSet (Class<? extends SortingSet> clazz, T[] array, ISortEventListener<T> listener) {
    	try {
	    	T[] clone = array.clone ();
	    	Constructor constructor = clazz.getConstructor (new Class<?>[] { Collection.class });
	    	SortingSet<T> set = (SortingSet<T>) constructor.newInstance (new Object[] { Arrays.asList (array), });
	    	if (listener != null) {
	    		set.addSortEventListener(listener);
	    	}
	    	
	        /* Assert the arrays are equivalent before sorting */
	    	Assert.assertNotNull (array);
	    	Assert.assertNotNull (clone);
	    	Assert.assertEquals (clone.length, array.length);
	    	for (int i = 0; i < array.length; ++i) {
	    		Assert.assertEquals (clone[i], array[i]);
	    	}
	    	
	        /* Sort the array using the SortingSet class */
	    	array = set.sort (array);
	        /* Sort using the Java sort method which is known */
	    	Arrays.sort (clone);
	    	
	        /* Assert the arrays are in the same order after sorting */
	    	Assert.assertEquals (clone.length, array.length);
	    	for (int i = 0; i < array.length; ++i) {
	    		Assert.assertEquals (clone[i], array[i]);
	    	}
    	} catch (Exception e) {
    		Assert.fail (e.getMessage ());
    	}
    }
    
    /*
     * Generic test for different SortingSet sorting cases with event handling
     * @param <T>
     * @param clazz The class of the SortingSet desired to perform the test
     * @param array The array of items to sort
     */
	@SuppressWarnings ("unchecked")
    private <T> void test_SortingSetWithEvents (Class<? extends SortingSet> clazz, T[] array) {
        SortEventListener<T> listener;
        
        listener = new SortEventListener<T> ((Class<T>) array.getClass ().getComponentType ());
        test_SortingSet (clazz, array, listener);
        Assert.assertTrue ("" + listener.getCount () + " <= 0", listener.getCount () > 0L);
    }
 
    /*
     * Generic test for different sorting cases
     * @param <T>
     * @param array The array of items to sort
     */
    private <T> void test_Sorting (T[] array) {
    	test_SortingSet (BubbleSet.class, array.clone (), null);
    	test_SortingSet (InsertionSet.class, array.clone (), null);
    	test_SortingSet (SelectionSet.class, array.clone (), null);
    	test_SortingSet (HeapSet.class, array.clone (), null);
    	test_SortingSet (MergeSet.class, array.clone (), null);
    	test_SortingSet (QuickSet.class, array.clone (), null);
    }
    
    /*
     * Generic test for different sorting cases with event handling
     * @param <T>
     * @param array The array of items to sort
     */
    private <T> void test_SortingWithEvents (T[] array) {
    	test_SortingSetWithEvents (BubbleSet.class, array.clone ()); 
    	test_SortingSetWithEvents (InsertionSet.class, array.clone ());
    	test_SortingSetWithEvents (SelectionSet.class, array.clone ());
    	test_SortingSetWithEvents (HeapSet.class, array.clone ());
    	test_SortingSetWithEvents (MergeSet.class, array.clone ());
    	test_SortingSetWithEvents (QuickSet.class, array.clone ());
    }
    
    /**
     * Test case for an easy to visualize data set, using a small constant
     * list of sortable values
     */
    public void test_SortingConstant () {
        test_Sorting (CONSTANT_LIST);
        System.gc ();
    }
    
    /**
     * Test case for an easy to visualize data set, using a small constant
     * list of sortable values and a specific sorting algorithm
     */
    public void test_SortingConstantSpecific () {
        Integer[] array = (Integer[]) CONSTANT_LIST.clone ();
        
        test_SortingSet (ShellSet.class, array, new SortEventListener<Integer> (Integer.class));
        
        array = null;
        System.gc ();
    }
    
    /**
     * Test case for randomly generated data set of Integer objects
     */
    public void test_SortingRandom () {
        /* Generate a large data set array to test the sorting algorithm */
        Integer[] array = new Integer[RANDOM_SIZE];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new Integer ((int) (Math.random () * (double) Integer.MAX_VALUE));
        }
        
        test_Sorting (array);
        
        array = null;
        System.gc ();
    }
    
    /**
     * Test case for a randomly generated medium size set of java.util.Date
     * objects 
     */
    public void test_SortingDate () {
    	Date[] array = new Date[RANDOM_SIZE];
    	for (int i = 0; i < array.length; ++i) {
    		long date = (long) (Math.random () * 2000000000000.0f);
    		array[i] = new Date (date);
    	}
    	
    	test_Sorting (array);
    	
    	array = null;
    	System.gc ();
    }
    
    /**
     * Test case for an easy to visualize data set, using a small constant
     * list of sortable values.  This test case uses the ISortEventListener
     * to listen and count the number of swap operations the sort has taken
     * on the given set.
     */
    public void test_SortingConstantWithEvents () {
        test_SortingWithEvents (CONSTANT_LIST);
        System.gc ();
    }

}
