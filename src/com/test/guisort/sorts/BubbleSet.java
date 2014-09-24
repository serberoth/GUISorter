/*
 * BubbleSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements the well known bubble sort algorithm to sort
 * the data contained in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class BubbleSet<T> extends SortingSet<T> {

	/**
	 * Create a new empty BubbleSet
	 */
	public BubbleSet () {
		super ();
	}
	/**
	 * Create a new BubbleSet that contains the items in the given Collection
	 * @param collection A Collection of items to add initially to the BubbleSet
	 */
	public BubbleSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Create a new BubbleSet with the given capacity
	 * @param initialCapacity The initial capacity of the BubbleSet
	 */
	public BubbleSet (int initialCapacity) {
		super (initialCapacity);
	}
	
	/*
	 * Perform the bubble sort algorithm
	 * @param <T>
	 * @param set The BubbleSet to act upon while sorting
	 * @param left The left side offset for the sort
	 * @param right The right side offset for the sort
	 */
	private static <T> void bubblesort (BubbleSet<T> set, int left, int right) {
		for (int i = left; i < right; ++i) {
			for (int j = right - 1; j > i; --j) {
				if (((Comparable<? super T>) set._list.get (j)).compareTo (set._list.get (j - 1)) < 0) {
					Collections.swap (set._list, j, j - 1);
					fireEvent (set, j - 1, j);
				}
			}
		}
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
        bubblesort (this, 0, array.length);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Brute Force\nBest Case: O(n)\nAverage Case: ---\nWorst Case: O(n^2)\nMemory Usage: O(1)\nStable";
	}
	
	/**
	 * Perform a bubble sort on the given array
	 * @param <T>
	 * @param array The array of data to be sorted
	 * @return The sorted data array
	 */
	public static <T> T[] bubbleSort (T[] array) {
		return bubbleSort (array, null);
	}
	/**
	 * Perform a bubble sort on the given array with the specified
	 * ISortEventListener
	 * @param <T>
	 * @param array The array of data to be sorted
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] bubbleSort (T[] array, ISortEventListener<T> listener) {
		BubbleSet<T> set = new BubbleSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
}
