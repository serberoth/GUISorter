/*
 * MergeSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements an optimized version of the well known merge
 * sort algorithm to sort the data contained in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class MergeSet<T> extends SortingSet<T> {

	/* The insertion sort drop-off threshold size */
	private static final int SORT_THRESHOLD = 10;
	
	/**
	 * Create a new instance of an empty MergeSet
	 */
	public MergeSet () {
		super ();
	}
	/**
	 * Create a new instance of a MergeSet initially containing the elements in
	 * the given Collection
	 * @param collection The Collection of elements to initially add to the
	 * MergeSet
	 */
	public MergeSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Crate a new instance of a MergeSet with the given initial capacity
	 * @param initialCapacity The initial capacity of the new MergeSet
	 */
	public MergeSet (int initialCapacity) {
		super (initialCapacity);
	}
	/*
	 * Create a clone of a MergeSet
	 * @param set The MergeSet to clone
	 */
	private MergeSet (MergeSet<T> set) {
		super (new ArrayList <T> (set._list), set._listeners);
	}
	
	/*
	 * Perform a recursive merge sort with a drop off to insertion sort at a
	 * given threshold on the given MergeSet
	 * @param <T>
	 * @param array The source MergeSet to sort
	 * @param buffer The destination MergeSet to sort
	 * @param left The left offset of the data
	 * @param right The right offset of the data
	 * @param offset The initial offset of the data
	 */
	private static <T> void mergesort (MergeSet<T> array, MergeSet<T> buffer, int left, int right, int offset) {
        int length = right - left;
        if (length <= 1) {
        	return;
        }
        if (length < SORT_THRESHOLD) {
        	/*
            for (int i = left; i < right; ++i) {
                for (int j = i; (j > left) && (((Comparable<? super T>) buffer._list.get (j - 1)).compareTo (buffer._list.get (j)) > 0); --j) {
                	Collections.swap (buffer._list, j, j - 1);
                	fireEvent (buffer, j, j - 1);
                }
            }
            return;
            */
        }
        
        int destLeft = left;
        int destRight = right;
        left += offset;
        right += offset;
        // int middle = (right + left) >> 1;
        /*
         * This formula works better for large data
         * sets (avoids int overflow errs)
         */
        int middle = left + ((right - left) >> 1);
        
        mergesort (buffer, array, left, middle, -offset);
        mergesort (buffer, array, middle, right, -offset);
        
        if (((Comparable<? super T>) array._list.get (middle - 1)).compareTo (array._list.get (middle)) <= 0) {
        	for (int i = 0; i < length; ++i) {
        		buffer._list.set (destLeft + i, array._list.get (left + i));
        	}
        	fireEvent (buffer, left, destLeft);
        	return;
        }
        
        for (int i = destLeft, u = left, v = middle; i < destRight; ++i) {
            if (v >= right || u < middle && (((Comparable<? super T>) array._list.get (u)).compareTo (array._list.get (v)) <= 0)) {
                buffer._list.set (i, array._list.get (u++));
                fireEvent (buffer, i, u - 1);
            } else {
                buffer._list.set (i, array._list.get (v++));
                fireEvent (buffer, i, v - 1);
            }
        }
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
        mergesort (new MergeSet<T> (this), this, 0, array.length, 0);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Divide and conquer\nBest Case: O(n log n)\nAverage Case: O(n log n)\nWorst Case: O(n log n)\nMemory Usage: O(n)\nStable";
	}
	
	/**
	 * Perform a merge sort on the given data array
	 * @param <T>
	 * @param array The array of data to sort
	 * @return The sorted data array
	 */
	public static <T> T[] mergeSort (T[] array) {
		return mergeSort (array, null);
	}
	/**
	 * Perform a merge sort on the given data array with the specified
	 * ISortEventListner
	 * @param <T>
	 * @param array The array of data to sort
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] mergeSort (T[] array, ISortEventListener<T> listener) {
		MergeSet<T> set = new MergeSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
	
}
