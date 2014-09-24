/*
 * ShellSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements the well known shell sorting algorithm to sort
 * the data contains in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class ShellSet<T> extends SortingSet<T> {

	/**
	 * Create a new instance of an empty ShellSet
	 */
	public ShellSet () {
		super ();
	}
	/**
	 * Create a new instance of a ShellSet that initially contains the items
	 * in the given Collection
	 * @param collection A Collection to initially add to the new ShellSet
	 */
	public ShellSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Create a new instance of a ShellSet with the given initial capacity
	 * @param initialCapacity The initial capacity of the new ShellSet
	 */
	public ShellSet (int initialCapacity) {
		super (initialCapacity);
	}
	
	/*
	 * Perform shell sort on the given ShellSet
	 * @param <T>
	 * @param set The ShellSet to perform the sort
	 * @param left The left offset of the data
	 * @param right The right offset of the data
	 */
	private static <T> void shellsort (ShellSet<T> set, int left, int right) {
		for (int increment = right / 2; increment > left; increment = ((increment == 2) ? 1 : (int) Math.round (increment / 2.2))) {
			for (int i = increment; i < right; ++i) {
				for (int j = i; j >= increment && ((Comparable<? super T>) set._list.get (j - increment)).compareTo (set._list.get (j)) > 0; j -= increment) {
					Collections.swap(set._list, j - increment, j);
					fireEvent (set, j - increment, j);
				}
			}
		}
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
		shellsort (this, 0, array.length);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Brute Force\nBest Case: ---\nAverage Case: ---\nWorst Case: O(n^1.5)\nMemory Usage: O(1)\nUnstable";
	}
	
	/**
	 * Perform shell sort on the given data array
	 * @param <T>
	 * @param array The array of data to sort
	 * @return The sorted data array
	 */
	public static <T> T[] shellSort (T[] array) {
		return shellSort (array, null);
	}
	/**
	 * Perform shell sort on the given data array with the sprcified
	 * ISortEventListener
	 * @param <T>
	 * @param array The array of data to sort
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] shellSort (T[] array, ISortEventListener<T> listener) {
		ShellSet<T> set = new ShellSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
}
